package com.imeepwni.myfood.view.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.imeepwni.myfood.R
import com.imeepwni.myfood.model.data.Menu

/**
 * 菜谱简介ViewHolder
 */
class SimpleMenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val iv_thumbnail: ImageView = view.findViewById(R.id.iv_thumbnail)
    val tv_title: TextView = view.findViewById(R.id.tv_name)
    val tv_ctgs: TextView = view.findViewById(R.id.tv_ctgs)

    /**
     * 绑定菜单数据
     */
    fun bind(menu: Menu) {
        menu.let {
            tv_title.text = it.name
            tv_ctgs.text = it.ctgTitles
        }
    }
}