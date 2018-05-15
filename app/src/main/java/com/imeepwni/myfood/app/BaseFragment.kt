package com.imeepwni.myfood.app

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    /**
     * 订阅管理
     */
    protected val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }
}