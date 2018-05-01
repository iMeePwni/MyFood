package com.imeepwni.myfood

import android.support.annotation.CheckResult
import com.google.gson.Gson
import com.imeepwni.myfood.model.data.GetMenuByTabsBean
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
        val jsonString = readJsonStringFromFile("E:\\MyFood\\app\\src\\test\\java\\com\\imeepwni\\myfood\\data\\getMenuTabs")
        commonBeanTestFromPath<GetMenuTabsBean>(jsonString)
    }

    /**
     * 将路径中对应的内容转换为Bean类
     *
     * @param path 文件路径
     */
    private inline fun <reified T> commonBeanTestFromPath(path: String) {
        val jsonString = readJsonStringFromFile(path)
        commonBeanTestFromString<T>(jsonString)
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
     * 将字符串内容转换为Bean类
     *
     * @param jsonString Json字符串
     */
    private inline fun <reified T> commonBeanTestFromString(jsonString: String) {
        var bean: T?
        try {
            bean = Gson().fromJson(jsonString, T::class.java)
            print(bean)
        } catch (e: Exception) {
            bean = null
            print(e)
        }
        assert(bean != null)
    }

    /**
     * 检测根据标签进行菜谱查询菜谱Bean类转化
     */
    @Test
    fun testGetCookByTabs() {
        // Windows
//        val path = "E:\\MyFood\\app\\src\\test\\java\\com\\imeepwni\\myfood\\data\\getMenuByTabs"
        // MAC
        val path = "/Users/guofeng/AndroidStudioProjects/MyFood/app/src/test/java/com/imeepwni/myfood/data/getMenuByTabs"
        commonBeanTestFromPath<GetMenuByTabsBean>(path)
    }
}