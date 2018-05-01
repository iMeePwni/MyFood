package com.imeepwni.myfood.model.data

/**
 * 菜谱返回结果集合
 *
 * @param curPage 当前页 默认一页有20个条目
 * @param list 菜谱集合
 * @param total 总数
 */
data class MenuResult(val curPage: Int,
                      val list: List<Menu>,
                      val total: Int)