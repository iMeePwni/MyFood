package com.imeepwni.myfood.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.imeepwni.myfood.R
import com.imeepwni.myfood.model.data.Menu
import com.imeepwni.myfood.view.holder.SimpleMenuViewHolder
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

/**
 * 菜单简介Adapter
 */
class SimpleMenuSection private constructor(parameters: SectionParameters, var menus: List<Menu>) : Section(parameters) {

    /**
     * 设置数据源
     */
    fun setData(data: List<Menu>) {
        menus = data
    }

    /**
     * 获取数据源
     */
    fun getData(): List<Menu> {
        return menus
    }

    override fun getContentItemsTotal(): Int {
        return menus.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SimpleMenuViewHolder
        holder.bind(menus[position])
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return SimpleMenuViewHolder(view)
    }

    companion object {

        /**
         * 获取SimpleMenuAdapter实例
         */
        fun newInstance(menus: List<Menu>): SimpleMenuSection {
            val sectionParameters = SectionParameters.builder()
                    .itemResourceId(R.layout.item_simpel_menu)
                    .build()
            return SimpleMenuSection(sectionParameters, menus)
        }
    }
}