package com.imeepwni.myfood.model.data

/**
 * 获取 菜谱分类标签查询API 返回数据类
 *
 * @param msg 返回说明
 * @param result 全部菜谱
 * @param retCode 返回码
 */
data class GetMenuTabsBean(val msg: String,
                           val result: MenuCategoryInfoBean,
                           val retCode: String)