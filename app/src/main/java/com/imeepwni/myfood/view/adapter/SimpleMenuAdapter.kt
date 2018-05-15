package com.imeepwni.myfood.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.imeepwni.myfood.model.data.Menu
import com.imeepwni.myfood.view.holder.SimpleMenuViewHolder

/**
 * 菜单简介Adapter具备分页功能
 */
class SimpleMenuAdapter private constructor(itemCallback: DiffUtil.ItemCallback<Menu>) : PagedListAdapter<Menu, SimpleMenuViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleMenuViewHolder {
        return SimpleMenuViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SimpleMenuViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {

        /**
         * 获取实例
         */
        fun newInstance(): SimpleMenuAdapter {
            return SimpleMenuAdapter(Menu.diffUtilItemCallback)
        }
    }
}