package com.imeepwni.myfood.view.ui

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import com.imeepwni.myfood.model.data.Menu
import com.imeepwni.myfood.model.net.MobService
import com.imeepwni.myfood.model.repositry.DataLab
import com.imeepwni.myfood.view.adapter.SimpleMenuSection
import com.imeepwni.myfood.view.ui.test.TestActivity
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

/**
 * 主界面
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    /**
     * Section Adapter
     */
    private val mSectionAdapter = SectionedRecyclerViewAdapter()

    /**
     * Section
     */
    private val mMenuAdapter = SimpleMenuSection.newInstance(Collections.emptyList())

    /**
     * 当前根据标签获取菜单的请求参数
     */
    private var mGetMenuByTabMap: MutableMap<String, String> = HashMap()

    /**
     * 初始化控件
     */
    private fun initView() {
        mTvCurrentCategory.text = getString(R.string.app_name)
        // 菜单按钮
        setSupportActionBar(mToolBar)

        mSectionAdapter.addSection(TAG_MENU_CONTENT, mMenuAdapter)
        mRVTestBean.run {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = mSectionAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect?.apply {
                        val pixelOffset = this@MainActivity.resources.getDimensionPixelOffset(R.dimen.recycler_view_item_margin)
                        left = pixelOffset
                        right = pixelOffset
                        val currentViewPosition = layoutManager.getPosition(view)
                        val theLastViewPosition = adapter.itemCount - 1
                        val isLastView = currentViewPosition == theLastViewPosition
                        if (isLastView) {
                            top = pixelOffset
                            bottom = pixelOffset
                        } else {
                            top = pixelOffset
                            bottom = 0
                        }
                    }
                }

            })
        }

    }

    /**
     * 初始化数据
     */
    private fun initData() {
        mGetMenuByTabMap.clear()
        getMenuByTabs(false)
    }

    /**
     * 根据标签获取菜单
     */
    private fun getMenuByTabs(isLoadMore: Boolean) {
        DataLab.getMenuByTab(mGetMenuByTabMap) {
            if (it == null) {
                return@getMenuByTab
            }
            if (it.retCode != MobService.RESULT_OK || it.result == null) {
                toast(it.msg)
                return@getMenuByTab
            }

            val oldList = mMenuAdapter.getData()
            val moreList = it.result.list
            val newList: List<Menu> = when {
                isLoadMore -> ArrayList<Menu>().apply {
                    addAll(oldList)
                    addAll(moreList)
                }
                else -> moreList
            }
            mMenuAdapter.setData(newList)
            DiffUtil.calculateDiff(Menu.getDiffUtilCallback(oldList, newList)).dispatchUpdatesTo(mSectionAdapter)
        }
    }

    /**
     * 设置标题栏更多按钮
     */
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * 更多按钮点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_show_tab -> showTabsFragment()
            R.id.menu_chaos -> chaosData()
            R.id.menu_load_more -> loadMoreData()
            R.id.menu_test -> goToTestActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    /**
     * 菜单标签选择器
     */
    private fun showTabsFragment() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(TabsFragment.TAG)
        if (prev != null) {
            beginTransaction.remove(prev)
        }
        beginTransaction.addToBackStack(null)
        val tabsFragment = TabsFragment.newInstance()
        tabsFragment.show(beginTransaction, TabsFragment.TAG)
    }

    /**
     * 打乱数据
     */
    private fun chaosData() {
        val oldList = mMenuAdapter.getData()
        val newList = MutableList(oldList.size) {
            oldList[it]
        }
        newList.sortWith(Comparator { o1: Menu, o2: Menu ->
            (o1.menuId.toLong() - o2.menuId.toLong()).toInt()
        })
        DiffUtil.calculateDiff(Menu.getDiffUtilCallback(oldList, newList)).dispatchUpdatesTo(mSectionAdapter)
    }

    /**
     * 加载更多
     */
    private fun loadMoreData() {
        mGetMenuByTabMap.let {
            val currentPage = it[MobService.KEY_PAGE]
            val isNotSetPage = TextUtils.isEmpty(currentPage)
            it[MobService.KEY_PAGE] = when {
                isNotSetPage -> 1.toString()
                else -> currentPage!!.toInt().plus(1).toString()
            }
        }
        getMenuByTabs(true)
    }

    /**
     * 跳转到测试页面
     */
    private fun goToTestActivity() {
        TestActivity.startActivity(this)
    }

    companion object {

        /**
         * Section tag fo menu content
         */
        private const val TAG_MENU_CONTENT = "menu_content"
    }

}
