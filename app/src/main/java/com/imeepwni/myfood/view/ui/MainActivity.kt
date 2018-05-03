package com.imeepwni.myfood.view.ui

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import com.imeepwni.myfood.model.repositry.DataLab
import com.imeepwni.myfood.view.adapter.SimpleMenuSection
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

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
     * 初始化控件
     */
    private fun initView() {
        tv_show_tabs.setOnClickListener {
            showTabsFragment()
        }

        rv_content.run {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect?.bottom = this@MainActivity.resources.getDimensionPixelOffset(R.dimen.common_margin) / 2
                }


            })
            adapter = SectionedRecyclerViewAdapter()
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        val map = HashMap<String, String>()
        DataLab.getMenuByTab(map) {
            it?.let {
                val mAdapter = rv_content.adapter
                mAdapter as SectionedRecyclerViewAdapter
                mAdapter.removeAllSections()
                mAdapter.addSection(SimpleMenuSection.newInstance(it.result?.list))
                mAdapter.notifyDataSetChanged()
            }
        }
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

}
