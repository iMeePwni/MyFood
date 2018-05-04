package com.imeepwni.myfood.view.ui

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import com.imeepwni.myfood.model.net.MobService
import com.imeepwni.myfood.model.repositry.DataLab
import com.imeepwni.myfood.view.adapter.SimpleMenuSection
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * 主界面
 */
class MainActivity : BaseActivity() {

    /**
     * Section tag content
     */
    private val SECTION_TAG_MENU_CONTENT = "menu_content"

    /**
     * 当前根据标签获取菜单的请求参数
     */
    private var mGetMenuByTabMap: MutableMap<String, String> = HashMap()

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
        btn_load_more.setOnClickListener {
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

        rv_content.run {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect?.apply {
                        val pixelOffset = this@MainActivity.resources.getDimensionPixelOffset(R.dimen.common_margin)
                        left = pixelOffset
                        right = pixelOffset
                        bottom = pixelOffset / 2
                    }
                }

            })
            adapter = SectionedRecyclerViewAdapter()
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
            if (it.retCode != MobService.RESULT_OK) {
                toast(it.msg)
                return@getMenuByTab
            }
            val mAdapter = rv_content.adapter
            mAdapter as SectionedRecyclerViewAdapter
            mAdapter.run {
                if (isLoadMore) {
                    var section = getSection(SECTION_TAG_MENU_CONTENT)
                    if (section == null) {
                        section = SimpleMenuSection.newInstance(null)
                    }
                    section as SimpleMenuSection
                    if (it.result?.list != null) {
                        section.addMoreData(it.result.list)
                    }
                } else {
                    removeAllSections()
                    addSection(SECTION_TAG_MENU_CONTENT, SimpleMenuSection.newInstance(it.result?.list))
                }
                notifyDataSetChanged()
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
