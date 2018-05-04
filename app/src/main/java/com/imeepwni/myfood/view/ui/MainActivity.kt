package com.imeepwni.myfood.view.ui

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import com.imeepwni.myfood.model.data.Menu
import com.imeepwni.myfood.model.net.MobService
import com.imeepwni.myfood.model.repositry.DataLab
import com.imeepwni.myfood.view.adapter.SimpleMenuSection
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
        btn_show_tabs.setOnClickListener {
            showTabsFragment()
        }
        btn_chaos.setOnClickListener {
            val oldList = mMenuAdapter.getData()
            val newList = MutableList<Menu>(oldList.size) {
                oldList[it]
            }
            newList.sortWith(Comparator { o1: Menu, o2: Menu ->
                (o1.menuId.toLong() - o2.menuId.toLong()).toInt()
            })
            DiffUtil.calculateDiff(Menu.getDiffUtilCallback(oldList, newList)).dispatchUpdatesTo(mSectionAdapter)
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
        mSectionAdapter.addSection(TAG_MENU_CONTENT, mMenuAdapter)
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
            adapter = mSectionAdapter
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

    companion object {

        /**
         * Section tag fo menu content
         */
        private const val TAG_MENU_CONTENT = "menu_content"
    }

}
