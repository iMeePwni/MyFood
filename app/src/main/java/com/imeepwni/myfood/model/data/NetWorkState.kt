package com.imeepwni.myfood.model.data

import android.support.annotation.IntDef

/**
 * 网络状态
 *
 * @param status 请求状态
 * @param msg 网络状态描述
 */
@Suppress("DataClassPrivateConstructor")
data class NetWorkState private constructor(
        @Status val status: Int,
        val msg: String? = null) {

    companion object {
        /**
         * 正在请求
         */
        public const val RUNNING = 0

        /**
         * 请求成功
         */
        public const val SUCCESS = 1

        /**
         * 请求失败
         */
        public const val FAILED = -1

        /**
         * 请求状态
         */
        @IntDef(RUNNING, SUCCESS, FAILED)
        @Retention(AnnotationRetention.SOURCE)
        public annotation class Status

        /**
         * 加载完成
         */
        val LOADED = NetWorkState(SUCCESS)

        /**
         * 加载中
         */
        val LOADING = NetWorkState(RUNNING)

        /**
         * 加载失败
         */
        fun error(msg: String?) = NetWorkState(FAILED, msg)
    }
}