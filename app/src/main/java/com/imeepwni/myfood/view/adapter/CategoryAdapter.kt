package com.imeepwni.myfood.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.imeepwni.myfood.model.data.CategoryInfo
import com.imeepwni.myfood.model.data.CategoryInfoAndChilds
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

/**
 * 菜单标签Adapter
 */
class CategoryAdapter private constructor(parameters: SectionParameters, categoryInfoAndChilds: CategoryInfoAndChilds) : Section(parameters) {

    /**
     * 菜单标签
     */
    private val info = categoryInfoAndChilds.categoryInfo

    /**
     * 子标签
     */
    private val data = categoryInfoAndChilds.childs

    /**
     * 初始化
     */
    init {
        setHasHeader(true)
        // TODO emptyView
    }

    override fun getContentItemsTotal(): Int {
        return data?.size ?: 0
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ItemViewHolder
        holder.bind(data!![position])
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder = ItemViewHolder(view)

    /**
     * 菜单标签 Item
     */
    private class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(categoryInfoAndChilds: CategoryInfoAndChilds) {
            // 菜单标签名称
            view.findViewById<TextView>(android.R.id.text1).apply {
                text = categoryInfoAndChilds.categoryInfo.name
            }
        }
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        holder as HeaderViewHolder
        holder.bind(info)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder = HeaderViewHolder(view)

    /**
     * 菜单标签头部
     */
    private class HeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(categoryInfo: CategoryInfo) {
            // 菜单标签名称
            view.findViewById<TextView>(android.R.id.title).apply {
                text = categoryInfo.name
            }
        }
    }

    companion object {
        /**
         * 获取CategoryAdapter
         */
        fun newInstance(categoryInfoAndChilds: CategoryInfoAndChilds): CategoryAdapter {
            val sectionParameters = SectionParameters.builder().apply {
                headerResourceId(android.R.layout.preference_category)
                itemResourceId(android.R.layout.simple_list_item_1)
            }.build()
            return CategoryAdapter(sectionParameters, categoryInfoAndChilds)
        }
    }
}