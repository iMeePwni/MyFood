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
class SimpleMenuSection private constructor(parameters: SectionParameters, menus: List<Menu>?) : Section(parameters) {

    private val mMenus = ArrayList<Menu>()

    init {
        menus?.let {
            mMenus.addAll(menus)
        }
    }

    override fun getContentItemsTotal(): Int {
        return mMenus.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SimpleMenuViewHolder
        holder.bind(mMenus[position])
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return SimpleMenuViewHolder(view)
    }

    companion object {

        /**
         * 获取SimpleMenuAdapter实例
         */
        fun newInstance(menus: List<Menu>?): SimpleMenuSection {
            val sectionParameters = SectionParameters.builder()
                    .itemResourceId(R.layout.item_simpel_menu)
                    .build()
            return SimpleMenuSection(sectionParameters, menus)
        }
    }
}