package com.imeepwni.myfood.model.data

/**
 * 根据标签进行菜谱查询菜谱Bean类
 */
data class GetMenuByTabBean(val msg: String,
                            val result: MenuResult,
                            val retCode: String)