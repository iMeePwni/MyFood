package com.imeepwni.myfood.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.annotation.UiThread
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.imeepwni.myfood.model.data.Menu
import com.imeepwni.myfood.model.data.NetWorkState
import com.imeepwni.myfood.view.holder.NetworkStateViewHolder
import com.imeepwni.myfood.view.holder.SimpleMenuViewHolder

/**
 * 菜单简介Adapter具备分页功能
 */
class SimpleMenuAdapter private constructor(itemCallback: DiffUtil.ItemCallback<Menu>) : PagedListAdapter<Menu, RecyclerView.ViewHolder>(itemCallback) {

    /**
     * 当前网络请求状态
     *
     * null: 初始状态
     * @see NetWorkState.LOADING 正在加载（还有数据未加载完）
     * @see NetWorkState.LOADED 所有数据加载完成（“已经到底了”）
     * @see NetWorkState.FAILED 请求失败（可能为空或网络中断）
     */
    private var mNetWorkState: NetWorkState? = null

    /**
     * 重试按钮回调
     */
    public var mRetryCallback: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_DATA -> SimpleMenuViewHolder.create(parent)
            TYPE_NETWORK_STATE -> NetworkStateViewHolder.create(parent, mRetryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SimpleMenuViewHolder -> holder.bind(getItem(position))
            is NetworkStateViewHolder -> holder.bind(mNetWorkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val isLastShownItem = position == itemCount - 1
        return if (isLastShownItem) {
            TYPE_NETWORK_STATE
        } else {
            TYPE_DATA
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    /**
     * 设置当前网络状态,并且更新网络状态Item。
     *
     * 不要用notifyDataSetChanged(),消耗太大。
     *
     * @param networkState 当前请求网络状态
     */
    @UiThread
    fun setNetworkState(networkState: NetWorkState) {
        val previousState = mNetWorkState
        mNetWorkState = networkState

        val networkStateItemPosition = itemCount - 1
        if (previousState == null) {
            notifyItemInserted(networkStateItemPosition)
        } else if (previousState != networkState) {
            notifyItemChanged(networkStateItemPosition)
        }
    }

    companion object {

        /**
         * 当前ItemView展示数据
         */
        private const val TYPE_DATA = 1

        /**
         * 当前ItemView展示网络请求状况
         */
        private const val TYPE_NETWORK_STATE = 2

        /**
         * 获取实例
         */
        fun newInstance(): SimpleMenuAdapter {
            return SimpleMenuAdapter(Menu.diffUtilItemCallback)
        }
    }
}