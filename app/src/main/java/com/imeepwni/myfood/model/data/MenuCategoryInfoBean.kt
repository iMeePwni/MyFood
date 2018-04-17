package com.imeepwni.myfood.model.data

/**
 * 菜单标签
 *
 * @param ctdId 分类ID
 * @param name 分类描述
 * @param parentId 上层分类ID
 * @param children 下层分类集合
 */
data class MenuCategoryInfoBean(val ctdId: String,
                                val name: String,
                                val parentId: String?,
                                val children: List<MenuCategoryInfoBean>?)