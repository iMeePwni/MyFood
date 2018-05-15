package com.imeepwni.myfood.view.ui.test

import android.arch.paging.*
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseFragment
import com.imeepwni.myfood.app.CommonHelper
import io.reactivex.BackpressureStrategy
import kotlinx.android.synthetic.main.fragment_page.*


/**
 * 测试Paging {@link https://developer.android.google.cn/topic/libraries/architecture/paging/}
 */
class PagingFragment : BaseFragment() {

    /**
     * 加载策略
     */
    private lateinit var mPageListConfig: PagedList.Config

    /**
     * 数据源
     */
    private lateinit var mDataSource: PositionalDataSource<Bean>

    /**
     * 原始数据
     */
    private lateinit var mBeans: MutableList<Bean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingTestAdapter = PagingTestAdapter.newInstance()
        val rxDataSource = RxPagedListBuilder(object : DataSource.Factory<Int, Bean>() {
            override fun create(): DataSource<Int, Bean> = mDataSource
        }, mPageListConfig).buildFlowable(BackpressureStrategy.BUFFER)
        mCompositeDisposable.add(rxDataSource
                .compose(CommonHelper.getFlowableTransformer<PagedList<Bean>>())
                .subscribe {
                    pagingTestAdapter.submitList(it)
                })
        mRVTestBean.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = pagingTestAdapter
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        mBeans = ArrayList()
        (0..1000).forEach {
            val bean = Bean(it, it.toChar().toString())
            mBeans.add(bean)
        }
        mDataSource = object : PositionalDataSource<Bean>() {
            override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Bean>) {
                callback.onResult(mBeans.subList(params.startPosition, Math.min(params.startPosition + params.loadSize, mBeans.size)))
            }

            override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Bean>) {
                val size = Math.min(params.requestedLoadSize, mBeans.size)
                callback.onResult(mBeans.subList(params.requestedStartPosition, size), params.requestedStartPosition)
            }
        }
        mPageListConfig = PagedList.Config.Builder()
                .setPageSize(PagingTestAdapter.PER_PAGE_SIZE)
                .setInitialLoadSizeHint(PagingTestAdapter.FIRST_LOAD_SIZE)
                .setEnablePlaceholders(false)
                .build()!!
    }

    /**
     * 普通Adapter
     */
    private class CommonAdapter(val data: MutableList<Bean>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_expandable_list_item_2, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(data[position])
        }

    }

    /**
     * 测试Paging Adapter
     */
    private class PagingTestAdapter(diffCallback: DiffUtil.ItemCallback<Bean>) : PagedListAdapter<Bean, ViewHolder>(diffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_expandable_list_item_2, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val bean = getItem(position)
            bean?.let {
                holder.bind(it)
            }
        }

        companion object {

            /**
             * 每页数据
             */
            const val PER_PAGE_SIZE = 20

            /**
             * 第一次加载的数据
             */
            const val FIRST_LOAD_SIZE = PER_PAGE_SIZE * 2

            /**
             * 获取Adapter实例
             */
            fun newInstance(): PagingTestAdapter {
                return PagingTestAdapter(Bean.itemCallback)
            }
        }
    }

    /**
     * 测试ViewHolder
     */
    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val text1 = itemView.findViewById<TextView>(android.R.id.text1)
        private val text2 = itemView.findViewById<TextView>(android.R.id.text2)

        fun bind(@NonNull bean: Bean) {
            text1.text = "id: ${bean.id}"
            text2.text = "name: ${bean.name}"
        }
    }

    /**
     * 测试Bean
     */
    private data class Bean(val id: Int, val name: String) {

        companion object {
            /**
             * 数据对比
             */
            val itemCallback = object : DiffUtil.ItemCallback<Bean>() {
                override fun areItemsTheSame(oldItem: Bean, newItem: Bean): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Bean, newItem: Bean): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }

    companion object {

        /**
         * 获取PageFragment实例
         */
        fun newInstance(): PagingFragment {
            return PagingFragment()
        }
    }
}