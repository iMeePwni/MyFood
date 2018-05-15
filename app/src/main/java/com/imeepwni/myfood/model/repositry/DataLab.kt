package com.imeepwni.myfood.model.repositry

import com.imeepwni.myfood.app.CommonHelper.getObservableTransformer
import com.imeepwni.myfood.model.data.GetMenuByTabBean
import com.imeepwni.myfood.model.data.GetMenuTabsBean
import com.imeepwni.myfood.model.net.MobService

object DataLab {

    /**
     * 全部菜单标签
     */
    private var sMenuTabs: GetMenuTabsBean? = null

    /**
     * 获取菜单标签
     */
    fun getMenuTabs(handleTabs: (GetMenuTabsBean?) -> Unit) {
        if (sMenuTabs == null) {
            getMenuTabsFromNet(handleTabs)
        }
        handleTabs(sMenuTabs)
    }

    /**
     * 从网络获取菜单标签并在成功后将数据存储到本地
     */
    private inline fun getMenuTabsFromNet(crossinline handleTabs: (GetMenuTabsBean?) -> Unit) {
        MobService.getMenuTabs()
                .compose(getObservableTransformer())
                .doFinally {
                    handleTabs(sMenuTabs)
                }
                .subscribe({
                    sMenuTabs = it
                }, {
                    sMenuTabs = null

                })

    }

    /**
     * 根据菜单标签获取菜单
     */
    fun getMenuByTab(map: MutableMap<String, String>, handleMenus: (GetMenuByTabBean?) -> Unit) {
        MobService.getMenuByTab(map)
                .compose(getObservableTransformer())
                .subscribe({
                    handleMenus(it)
                }, {
                    handleMenus(null)
                })
    }

}