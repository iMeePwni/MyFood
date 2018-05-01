package com.imeepwni.myfood

import android.support.annotation.CheckResult
import com.google.gson.Gson
import com.imeepwni.myfood.model.data.GetMenuByTabsBean
import com.imeepwni.myfood.model.data.GetMenuTabsBean
import com.imeepwni.myfood.model.net.MobService
import org.junit.Test
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ModelUnitTest {

    /**
     * 检测获取全部菜单标签Bean类转化
     */
    @Test
    fun testGetMenuTabsBean() {
        val path = "/Users/guofeng/AndroidStudioProjects/MyFood/app/src/test/java/com/imeepwni/myfood/data/file/getMenuTabs"
        commonBeanTestFromPath<GetMenuTabsBean>(path)
    }

    /**
     * 从网络获取菜单标签
     */
    @Test
    fun getMenuTabsFromNet() {
        MobService.getMenuTabs()
                .subscribe {
                    print(it)
                    assert(it != null)
                }
    }

    /**
     * 检测根据标签进行菜谱查询菜谱Bean类转化
     */
    @Test
    fun testGetMenuByTabs() {
        val path = "/Users/guofeng/AndroidStudioProjects/MyFood/app/src/test/java/com/imeepwni/myfood/data/file/getMenuByTabs"
        commonBeanTestFromPath<GetMenuByTabsBean>(path)
    }

    /**
     * 从网络根据标签获取菜单
     */
    @Test
    fun getMenuByTabsFromNet() {
        val param = HashMap<String, String>()
        param[MobService.KEY_CID] = "0010001007"
        param[MobService.KEY_NAME] = "红烧肉"
        param[MobService.KEY_PAGE] = "1"
        param[MobService.KEY_SIZE] = MobService.DEFAULT_PAGE_SIZE.toString()

        MobService.getMenuByTabs(param)
                .subscribe {
                    print(it)
                    assert(it != null)
                }
    }

    /**
     * 公共方法
     */
    companion object {

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

    }
}