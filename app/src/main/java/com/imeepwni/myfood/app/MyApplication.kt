package com.imeepwni.myfood.app

import android.app.Application
import com.imeepwni.myfood.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlin.properties.Delegates

/**
 * 应用Application
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initLogger()
    }

    /**
     * initial logger
     */
    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    companion object {
        /**
         * 应用Context实例
         */
        var INSTANCE: MyApplication by Delegates.notNull()
    }
}