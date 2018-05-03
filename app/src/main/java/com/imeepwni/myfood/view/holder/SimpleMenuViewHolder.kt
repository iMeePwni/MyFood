package com.imeepwni.myfood.view.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.imeepwni.myfood.R
import com.imeepwni.myfood.model.data.Menu
import com.imeepwni.myfood.view.ui.MenuDetailActivity
import com.squareup.picasso.Picasso

/**
 * 菜谱简介ViewHolder
 */
class SimpleMenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    /**
     * 菜单缩略图
     */
    private val mThumbNail: ImageView = view.findViewById(R.id.iv_thumbnail)

    /**
     * 菜单名
     */
    private val mTitle: TextView = view.findViewById(R.id.tv_name)

    /**
     * 菜单所属标签
     */
    private val mCtgs: TextView = view.findViewById(R.id.tv_ctgs)

    /**
     * 绑定菜单数据
     */
    fun bind(menu: Menu) {
        menu.let {
            val context = itemView.context
            Picasso.with(context)
                    .load(it.recipe.img)
                    // TODO 错误图片
                    .error(R.mipmap.ic_launcher)
                    // TODO 占位图
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mThumbNail)
            mTitle.text = it.name
            mCtgs.text = it.ctgTitles
            itemView.setOnClickListener { _ ->
                // TODO 增加过渡动画
                MenuDetailActivity.startActivity(context, it.menuId)
            }
        }
    }
}