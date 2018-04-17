package com.imeepwni.myfood.model.net

import com.imeepwni.myfood.BuildConfig
import com.imeepwni.myfood.model.data.GetMenuTabsBean
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * 请求Mob API工具类
 */
object MobService {
    /**
     * Mob API Base URL
     */
    private const val BASE_URL: String = "http://apicloud.mob.com/"

    /**
     * Mob API for CookMenu
     */
    private const val APP_KEY_COOK_MENU = BuildConfig.APP_KEY_MOB_COOK_MENU

    /**
     * Mob Client
     */
    private val CLIENT = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()!!

    /**
     * Mob菜谱大全
     * @see <a href="http://api.mob.com/#/apiwiki/cookmenu">菜谱大全</a>
     */
    private interface CookMenu {
        @GET("v1/cook/category/query")
        fun getMenuTabs(@Query("key") key: String): Observable<GetMenuTabsBean>
    }

    /**
     * 获取标签页
     */
    fun getMenuTabs(): Observable<GetMenuTabsBean> {
        return CLIENT.create(CookMenu::class.java).getMenuTabs(APP_KEY_COOK_MENU)
    }

}