package com.imeepwni.myfood.model.net

import com.imeepwni.myfood.BuildConfig
import com.imeepwni.myfood.app.CommonHelper
import com.imeepwni.myfood.model.data.GetMenuById
import com.imeepwni.myfood.model.data.GetMenuByTabBean
import com.imeepwni.myfood.model.data.GetMenuTabsBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
 * 请求Mob API工具类
 */
object MobService {

    /**
     * 菜单标签ID 对应KEY
     */
    const val KEY_CID = "cid"

    /**
     * 菜谱名称 对应KEY
     */
    const val KEY_NAME = "name"

    /**
     * 页码 对应KEY
     */
    const val KEY_PAGE_NUM = "page"

    /**
     * 每页Item数量 对应KEY
     */
    const val KEY_PER_PAGE_SIZE = "size"

    /**
     * 菜单ID 对应KEY
     */
    const val KEY_ID = "id"

    /**
     * 默认返回条数
     */
    const val DEFAULT_PAGE_SIZE = 20

    /**
     * 返回结果成功
     */
    const val RESULT_OK = "200"

    /**
     * AppKey 对应Key
     */
    private const val KEY_KEY = "key"

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
    private val CLIENT = CommonHelper.getBaseUrlRetrofit(BASE_URL)

    /**
     * 菜谱请求实例
     */
    private val cookMenu = CLIENT.create(CookMenu::class.java)

    /**
     * Mob菜谱大全
     * @see <a href="http://api.mob.com/#/apiwiki/cookmenu">菜谱大全</a>
     */
    private interface CookMenu {
        /**
         * 获取菜单标签页
         *
         * @param key App_KEY
         */
        @GET("v1/cook/category/query")
        fun getMenuTabs(@Query(KEY_KEY) key: String): Observable<GetMenuTabsBean>

        /**
         * 根据标签获取菜单
         *
         * @param param 请求参数
         *
         * @see KEY_CID
         * @see KEY_NAME
         * @see KEY_PAGE_NUM
         * @see KEY_PER_PAGE_SIZE
         */
        @GET("v1/cook/menu/search")
        fun getMenuByTabs(@QueryMap param: Map<String, String>): Observable<GetMenuByTabBean>

        /**
         * 根据菜谱ID获取菜谱
         */
        @GET("/v1/cook/menu/query")
        fun getMenuById(@Query(KEY_KEY) key: String, @Query(KEY_ID) menuId: String): Observable<GetMenuById>
    }

    /**
     * 获取菜单标签页
     */
    fun getMenuTabs(): Observable<GetMenuTabsBean> {
        return cookMenu.getMenuTabs(APP_KEY_COOK_MENU)
    }

    /**
     * 根据标签获取菜单
     */
    fun getMenuByTab(map: MutableMap<String, String>): Observable<GetMenuByTabBean> {
        map[KEY_KEY] = APP_KEY_COOK_MENU
        return cookMenu.getMenuByTabs(map)
    }

    /**
     * 根据菜单ID获取菜单
     */
    fun getMenuById(menuId: String): Observable<GetMenuById> {
        return cookMenu.getMenuById(APP_KEY_COOK_MENU, menuId)
    }

}