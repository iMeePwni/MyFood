package com.imeepwni.myfood.model.data

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
                val thumbnail: String)