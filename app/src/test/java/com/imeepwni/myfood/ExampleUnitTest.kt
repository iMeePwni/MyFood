package com.imeepwni.myfood

import android.support.annotation.CheckResult
import com.google.gson.Gson
import com.imeepwni.myfood.model.data.GetMenuTabsBean
import org.junit.Test
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    /**
     * 检测获取全部菜单标签Bean类转化
     */
    @Test
    fun testGetMenuTabsBean() {
        val jsonString = readJsonStringFromFile("E:\\MyFood\\app\\src\\test\\java\\com\\imeepwni\\myfood\\data\\getMenuTabsBean")
        var getMenuTabsBean: GetMenuTabsBean? = null
        try {
            getMenuTabsBean = Gson().fromJson(jsonString, GetMenuTabsBean::class.java)
            print(getMenuTabsBean)
        } catch (e: Exception) {
            print(e)
        }
        assert(getMenuTabsBean != null)
    }

    /**
     * 从本地路径读取测试Json字符串
     */
    @CheckResult
    private fun readJsonStringFromFile(path: String): String {
        val stringBuilder = StringBuilder()
        File(path).forEachLine {
            stringBuilder.append(it.trim())
        }
        return stringBuilder.toString()
    }

    /**
     * 检测根据标签进行菜谱查询菜谱Bean类转化
     */
    @Test
    fun testGetCookByTabs() {
        val jsonString = readJsonStringFromFile("E:\\MyFood\\app\\src\\test\\java\\com\\imeepwni\\myfood\\data\\getCookByTabs")
    }
}
