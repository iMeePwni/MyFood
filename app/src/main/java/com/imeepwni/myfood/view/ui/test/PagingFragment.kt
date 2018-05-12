package com.imeepwni.myfood.view.ui.test

import android.arch.paging.PagedListAdapter
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
import kotlinx.android.synthetic.main.fragment_page.*

/**
 * 测试Paging {@link https://developer.android.google.cn/topic/libraries/architecture/paging/}
 */
class PagingFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRVContent.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = PagingTestAdapter.newInstance()
        }
    }

    /**
     * 测试Adapter
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
            fun newInstance(): PagedListAdapter<Bean, ViewHolder> {
                return PagingTestAdapter(object : DiffUtil.ItemCallback<Bean>() {
                    override fun areItemsTheSame(oldItem: Bean?, newItem: Bean?): Boolean {
                        return when {
                            oldItem != null && newItem != null -> oldItem.id == newItem.id
                            else -> oldItem == null && newItem == null
                        }
                    }

                    override fun areContentsTheSame(oldItem: Bean?, newItem: Bean?): Boolean {
                        return when {
                            oldItem != null && newItem != null -> oldItem == newItem
                            else -> oldItem == null && newItem == null
                        }
                    }
                })
            }
        }
    }

    /**
     * 测试ViewHolder
     */
    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val text1 = view.findViewById<TextView>(R.id.text1)
        private val text2 = view.findViewById<TextView>(R.id.text2)

        fun bind(@NonNull bean: Bean) {
            text1.text = "id: ${bean.id}"
            text2.text = "name: ${bean.name}"
        }
    }

    /**
     * 测试Bean
     */
    private data class Bean(val id: Int, val name: String)

    companion object {

        /**
         * 获取PageFragment实例
         */
        fun newInstance(): PagingFragment {
            return PagingFragment()
        }
    }
}