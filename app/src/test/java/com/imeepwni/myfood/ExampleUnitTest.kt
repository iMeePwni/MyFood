package com.imeepwni.myfood

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
        val stringBuilder = StringBuilder()
        File("E:\\MyFood\\app\\src\\test\\java\\com\\imeepwni\\myfood\\data\\getMenuTabsBean").forEachLine {
            stringBuilder.append(it)
        }
        var getMenuTabsBean: GetMenuTabsBean? = null
        try {
            getMenuTabsBean = Gson().fromJson(stringBuilder.toString(), GetMenuTabsBean::class.java)
            print(getMenuTabsBean)
        } catch (e: Exception) {
            print(e)
        }
        assert(getMenuTabsBean != null)
    }
}
