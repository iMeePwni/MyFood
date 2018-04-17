package com.imeepwni.myfood.app

import android.app.Application
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
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    companion object {
        /**
         * 应用Context实例
         */
        var INSTANCE: MyApplication by Delegates.notNull()
    }
}