package com.imeepwni.myfood.model.data

/**
 * 根据菜谱ID获取菜单Bean类
 *
 * @param msg 返回说明
 * @param result 菜单
 * @param retCode 返回码
 */
data class GetMenuById(val msg: String,
                       val result: Menu,
                       val retCode: String)