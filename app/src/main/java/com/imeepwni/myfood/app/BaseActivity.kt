package com.imeepwni.myfood.app

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {

    /**
     * 订阅管理
     */
    protected val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }
}