package com.imeepwni.myfood.model.data

/**
 * 菜单标签
 *
 * @param ctgId 标签ID
 * @param name 标签name
 * @param parentId 父标签
 */
data class CategoryInfo(val ctgId: String,
                        val name: String,
                        val parentId: String?)