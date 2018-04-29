package com.imeepwni.myfood.model.data

/**
 * 根据标签进行菜谱查询菜谱Bean类
 */
data class GetCookByTabsBean(val msg: String,
                             val result: CategoryInfoAndChilds?,
                             val retCode: String)