package com.imeepwni.myfood.view.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.imeepwni.myfood.R
import com.imeepwni.myfood.model.repositry.DataLab
import com.imeepwni.myfood.view.adapter.CategoryAdapter
import com.orhanobut.logger.Logger
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.dialog_common_title.*


/**
 * 菜单标签Fragment
 */
class TabsFragment : DialogFragment() {

    private val sectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val recyclerView = RecyclerView(context)
        val dialogTitle = activity!!.layoutInflater.inflate(R.layout.dialog_common_title, null)
        val alertDialog = AlertDialog.Builder(activity!!)
                .setCustomTitle(dialogTitle)
                .setView(recyclerView)
                .setNegativeButton("取消", { _, _ -> Logger.d("you click cancel") })
                .setNeutralButton("重置", { _, _ -> Logger.d("you click reset") })
                .setPositiveButton("确定", { _, _ -> Logger.d("you click confirm") })
        val chipsLayoutManager = ChipsLayoutManager.newBuilder(activity)
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.CENTER)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row
//                .setMaxViewsInRow(6)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver { Gravity.CENTER }
                //you are able to break row due to your conditions. Row breaker should return true for that views
//                .setRowBreaker { position -> position == 6 || position == 11 || position == 2 }
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                // whether strategy is applied to last row. FALSE by default
                .withLastRow(true)
                .build()
        val gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT, GridLayoutManager.VERTICAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return sectionedRecyclerViewAdapter.getSectionItemViewType(position).let {
                    when (it) {
                        SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER -> SPAN_COUNT
                        else -> 1
                    }
                }
            }
        }
        recyclerView.run {
            layoutManager = chipsLayoutManager
            adapter = sectionedRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
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
        DataLab.getMenuTabs {
            sectionedRecyclerViewAdapter.removeAllSections()
            // 返回结果
            it?.let {
                Logger.d(it)
                it.result?.let {
                    // 全部菜谱标签
                    dialog.tv_dialog_title.text = it.categoryInfo.name
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
         * 菜单标签框每行显示的最大标签数量
         */
        private const val SPAN_COUNT = 4

        /**
         * 获取菜单标签Fragment实例
         */
        @JvmStatic
        fun newInstance() = TabsFragment()
    }
}
