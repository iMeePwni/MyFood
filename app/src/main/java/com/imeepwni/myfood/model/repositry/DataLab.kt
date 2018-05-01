package com.imeepwni.myfood.model.repositry

import com.imeepwni.myfood.model.data.GetMenuTabsBean
import com.imeepwni.myfood.model.net.MobService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterNext {
                    handleTabs(it)
                }
                .subscribe({
                    sMenuTabs = it
                }, {
                    sMenuTabs = null
                })
    }
}