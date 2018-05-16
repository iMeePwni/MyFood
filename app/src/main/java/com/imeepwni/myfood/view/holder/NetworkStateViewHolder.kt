package com.imeepwni.myfood.view.holder

import android.support.annotation.CheckResult
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.imeepwni.myfood.R
import com.imeepwni.myfood.model.data.NetWorkState
import com.orhanobut.logger.Logger

/**
 * 网络状况ViewHolder
 */
class NetworkStateViewHolder private constructor(view: View, private val retryCallBack: View.OnClickListener?) : RecyclerView.ViewHolder(view) {

    /**
     * 进度条
     */
    private val mProgressBar = itemView.findViewById<ContentLoadingProgressBar>(R.id.mCPBLoadingFromNet)

    /**
     * 错误信息
     */
    private val mErrorMsg = itemView.findViewById<TextView>(R.id.mTVErrorMsg)

    /**
     * 重试
     */
    private val mRetry = itemView.findViewById<Button>(R.id.mBtnRetry)

    fun bind(netWorkState: NetWorkState?) {
        netWorkState?.run {
            mProgressBar.visibility = toVisibility(status == NetWorkState.RUNNING)
            mErrorMsg.visibility = toVisibility(!TextUtils.isEmpty(msg))
            mErrorMsg.text = msg
            mRetry.visibility = toVisibility(status == NetWorkState.FAILED)
            mRetry.setOnClickListener(retryCallBack)
        } ?: Logger.d("bind null")
    }

    companion object {

        /**
         * 根据父布局生成ViewHolder
         *
         * @param parent 父布局
         */
        fun create(parent: ViewGroup, retryCallback: View.OnClickListener?): NetworkStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent, false)
            return NetworkStateViewHolder(view, retryCallback)
        }

        /**
         * 根据是否显示获取可见度
         */
        @CheckResult
        fun toVisibility(isVisible: Boolean): Int {
            return if (isVisible) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }
}