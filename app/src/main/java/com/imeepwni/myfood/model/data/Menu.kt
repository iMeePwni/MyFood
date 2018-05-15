package com.imeepwni.myfood.model.data

import android.support.v7.util.DiffUtil

/**
 * 菜谱
 *
 * @param ctgIds 菜谱所属分类标签集合
 * @param menuId 菜谱ID
 * @param name 菜谱名字
 * @param recipe 食谱
 * @param thumbnail 预览图
 */
data class Menu(val ctgIds: List<String>,
                val ctgTitles: String,
                val menuId: String,
                val name: String,
                val recipe: Recipe,
                val thumbnail: String) {

    companion object {

        /**
         * 获取数据类的DiffUtil.Callback
         *
         * @param oldList 旧数据
         * @param newList 新数据
         */
        fun getDiffUtilCallback(oldList: List<Menu>, newList: List<Menu>): DiffUtil.Callback {

            return object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldList.size
                }

                override fun getNewListSize(): Int {
                    return newList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition].menuId == newList[newItemPosition].menuId
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition] == newList[newItemPosition]
                }
            }
        }

        /**
         * 获取数据类的DiffUtil.ItemCallback
         */
        val diffUtilItemCallback: DiffUtil.ItemCallback<Menu> = object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem.menuId == newItem.menuId
            }

            override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem == newItem
            }
        }
    }
}