package com.imeepwni.myfood.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseFragment
import com.imeepwni.myfood.model.net.MobService
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 菜单标签Fragment
 */
class IndexFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_index, container, false)
        initData()
        return view
    }

    private fun initData() {
        MobService.getMenuTabs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(it)
                }, {
                    Logger.d(it)
                })
    }

    companion object {
        /**
         * 获取菜单标签Fragment实例
         */
        @JvmStatic
        fun newInstance() = IndexFragment()
    }
}
