package com.imeepwni.myfood.view.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.imeepwni.myfood.model.repositry.TabsLab
import com.imeepwni.myfood.view.adapter.CategoryAdapter
import com.orhanobut.logger.Logger
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

/**
 * 菜单标签Fragment
 */
class TabsFragment : DialogFragment() {

    private val sectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val recyclerView = RecyclerView(context)
        val alertDialog = AlertDialog.Builder(context!!)
                .setTitle("菜谱标签选择")
                .setView(recyclerView)
                .setNegativeButton("取消", { _, _ -> Logger.d("you click cancel") })
                .setNeutralButton("重置", { _, _ -> Logger.d("you click reset") })
                .setPositiveButton("确定", { _, _ -> Logger.d("you click confirm") })
        recyclerView.run {
            val gridLayoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    sectionedRecyclerViewAdapter.getItemViewType(position).let {
                        return when (it) {
                            SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER -> 3
//                            SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED -> sectionedRecyclerViewAdapter.getSectionForPosition(position).get
                            else -> 1
                        }
                    }
                }
            }
            layoutManager = gridLayoutManager
            adapter = sectionedRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        return alertDialog.create()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        TabsLab.getMenuTabs {
            sectionedRecyclerViewAdapter.removeAllSections()
            // 返回结果
            it?.let {
                Logger.d(it)
                // 全部菜谱标签
                it.result?.let {
                    // 按工艺
                    it.childs?.forEach {
                        // 具体标签
                        sectionedRecyclerViewAdapter.addSection(CategoryAdapter.newInstance(it))
                    }
                }
            }
            sectionedRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

    companion object {

        val TAG = TabsFragment::class.java.simpleName!!
        /**
         * 获取菜单标签Fragment实例
         */
        @JvmStatic
        fun newInstance() = TabsFragment()
    }
}
