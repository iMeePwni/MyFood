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
    private val mImage: ImageView = view.findViewById(R.id.mIVImage)

    /**
     * 菜单名
     */
    private val mName: TextView = view.findViewById(R.id.mTVMenuName)

    /**
     * 菜单所属标签
     */
    private val mCtgs: TextView = view.findViewById(R.id.mTVMenuCtgs)

    /**
     * 绑定菜单数据
     */
    fun bind(menu: Menu) {
        menu.let {
            val context = itemView.context
            Picasso.with(context)
//                    .load(it.recipe.img)
                    .load(it.recipe.img)
                    // TODO 错误图片
                    .error(android.R.drawable.stat_notify_error)
                    // TODO 占位图
                    .placeholder(android.R.drawable.ic_menu_myplaces)
                    .into(mImage)
            mName.text = it.name
            mCtgs.text = it.ctgTitles
            itemView.setOnClickListener { _ ->
                // TODO 增加过渡动画
                MenuDetailActivity.startActivity(context, it.menuId)
            }
        }
    }
}