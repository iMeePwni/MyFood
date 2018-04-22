package com.imeepwni.myfood.model.repositry

import com.imeepwni.myfood.model.data.GetMenuTabsBean
import com.imeepwni.myfood.model.net.MobService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object TabsLab {

    private var sMenuTabs: GetMenuTabsBean? = null

    fun getMenuTabs(handleTabs: (GetMenuTabsBean?) -> Unit) {
        if (sMenuTabs == null) {
            getMenuTabsFromNet(handleTabs)
        }
        handleTabs(sMenuTabs)
    }

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