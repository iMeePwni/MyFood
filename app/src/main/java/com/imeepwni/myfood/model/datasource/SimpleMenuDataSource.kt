package com.imeepwni.myfood.model.datasource

import android.arch.paging.PageKeyedDataSource
import android.view.View
import com.imeepwni.myfood.app.MyApplication
import com.imeepwni.myfood.model.data.Menu
import com.imeepwni.myfood.model.data.MenuResult
import com.imeepwni.myfood.model.data.NetWorkState
import com.imeepwni.myfood.model.net.MobService
import com.imeepwni.myfood.view.adapter.SimpleMenuAdapter
import com.orhanobut.logger.Logger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

/**
 * 根据标签获取菜单的DataSource
 */
class SimpleMenuDataSource private constructor(private val cid: String) : PageKeyedDataSource<Int, Menu>() {

    /**
     * 当前数据网络请求状况
     */
    private lateinit var mNetworkState: NetWorkState

    var mPageAdapter: SimpleMenuAdapter? = null

    /**
     * 请求参数Map
     */
    private lateinit var mRequestMap: MutableMap<String, String>

    /**
     * 当前加载的数据量
     */
    private var currentLoadedSize = 0

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Menu>) {
        val requestedLoadSize = params.requestedLoadSize
        // 初始化加载多页
        mRequestMap = HashMap()
        mRequestMap[MobService.KEY_CID] = cid
        mRequestMap[MobService.KEY_PAGE_NUM] = 1.toString()
        mRequestMap[MobService.KEY_PER_PAGE_SIZE] = requestedLoadSize.toString()

        mNetworkState = NetWorkState.LOADING
        updateAdapterNetworkState()

        MobService.getMenuByTab(mRequestMap)
                .doFinally {
                    mPageAdapter?.mRetryCallback = View.OnClickListener {
                        doAsync {
                            loadInitial(params, callback)
                        }
                    }
                    updateAdapterNetworkState()
                }
                .subscribe({
                    val menuResult: MenuResult? = it.result
                    mNetworkState = if (it.retCode != MobService.RESULT_OK || menuResult == null) {
                        MyApplication.INSTANCE.toast(it.msg)
                        NetWorkState.error(it.msg)
                    } else {
                        val list = menuResult.list
                        currentLoadedSize = list.size
                        val isHaveNextPage = currentLoadedSize < menuResult.total
                        if (isHaveNextPage) {
                            callback.onResult(list, null, 1 + params.requestedLoadSize / MobService.DEFAULT_PAGE_SIZE)
                        } else {
                            callback.onResult(list, null, null)
                        }
                        NetWorkState.LOADED
                    }
                }, {
                    Logger.d(it)
                    mNetworkState = NetWorkState.error(it.toString())
                })
    }

    /**
     * 更新Adapter网络状态
     */
    private fun updateAdapterNetworkState() {
        MyApplication.INSTANCE.runOnUiThread {
            mPageAdapter?.setNetworkState(mNetworkState)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Menu>) {
        loadPage(params.key, true, callback, View.OnClickListener {
            doAsync {
                loadAfter(params, callback)
            }
        })
    }

    /**
     * 加载对应页面数据
     *
     * @param page 要加载的页码
     */
    private fun loadPage(page: Int, isLoadAfter: Boolean, callback: LoadCallback<Int, Menu>, retryCallback: View.OnClickListener) {
        mRequestMap[MobService.KEY_PER_PAGE_SIZE] = MobService.DEFAULT_PAGE_SIZE.toString()
        mRequestMap[MobService.KEY_PAGE_NUM] = page.toString()

        mNetworkState = NetWorkState.LOADING
        MobService.getMenuByTab(mRequestMap)
                .doFinally {
                    mPageAdapter?.mRetryCallback = retryCallback
                    updateAdapterNetworkState()
                }
                .subscribe({
                    val menuResult = it.result
                    mNetworkState = if (it.retCode != MobService.RESULT_OK || menuResult == null) {
                        MyApplication.INSTANCE.toast(it.msg)
                        NetWorkState.error(it.msg)
                    } else {
                        val list = menuResult.list
                        val currentLoadedSize = page * MobService.DEFAULT_PAGE_SIZE + list.size
                        val isHaveNextPage = currentLoadedSize < menuResult.total
                        if (isHaveNextPage) {
                            callback.onResult(list, if (isLoadAfter) page + 1 else page - 1)
                        } else {
                            callback.onResult(list, null)
                        }
                        NetWorkState.LOADED
                    }
                }, {
                    Logger.d(it)
                    mNetworkState = NetWorkState.error(it.toString())
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Menu>) {
        loadPage(params.key, false, callback, View.OnClickListener {
            doAsync {
                loadBefore(params, callback)
            }
        })
    }

    companion object {

        /**
         * 获取查询全部标签的DataSource实例
         */
        fun newInstance(): SimpleMenuDataSource {
            val defaultCid = ""
            return newInstance(defaultCid)
        }

        /**
         * 获取查询指定标签的DataSource实例
         *
         * @param cid 指定标签ID
         */
        fun newInstance(cid: String): SimpleMenuDataSource {
            return SimpleMenuDataSource(cid)
        }
    }
}