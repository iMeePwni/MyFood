package com.imeepwni.myfood.model.data

/**
 * 食谱
 *
 * @param img 预览图
 * @param ingredients 配料
 * @param sumary 一句话简介
 * @param title 标题
 */
data class Recipe(val img: String,
                  val ingredients: String,
                  val sumary: String,
                  val title: String)