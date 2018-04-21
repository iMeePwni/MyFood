package com.imeepwni.myfood.view.ui

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseFragment
import com.imeepwni.myfood.model.net.MobService
import com.imeepwni.myfood.view.adapter.CategoryAdapter
import com.orhanobut.logger.Logger
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_index.*

/**
 * 菜单标签Fragment
 */
class IndexFragment : BaseFragment() {

    private val sectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_index, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_content.run {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = sectionedRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        MobService.getMenuTabs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(it)
                    it.result?.let {
                        // 全部菜谱
                        tv_tabs_title.text = it.categoryInfo.name
                        removeSections()
                        // 按工艺
                        it.childs?.forEach {
                            // 具体标签
                            sectionedRecyclerViewAdapter.addSection(CategoryAdapter.newInstance(it))
                        }
                        notifySections()
                    } ?: removeSections()
                }, {
                    Logger.d(it)
                    removeSections()
                })
    }

    /**
     * 更新数据
     */
    private fun notifySections() {
        sectionedRecyclerViewAdapter.notifyDataSetChanged()
    }

    /**
     * 清除数据
     */
    private fun removeSections() {
        sectionedRecyclerViewAdapter.removeAllSections()
        notifySections()
    }

    companion object {
        /**
         * 获取菜单标签Fragment实例
         */
        @JvmStatic
        fun newInstance() = IndexFragment()
    }
}
