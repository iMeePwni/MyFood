package com.imeepwni.myfood.model.data

/**
 * 菜单标签和子集标签
 *
 * @param categoryInfo 本层标签
 * @param childs 下层分类集合
 */
data class CategoryInfoAndChilds(val categoryInfo: CategoryInfo,
                                 val childs: List<CategoryInfoAndChilds>?)