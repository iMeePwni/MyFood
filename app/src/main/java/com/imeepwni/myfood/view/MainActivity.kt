package com.imeepwni.myfood.view

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.imeepwni.myfood.R
import com.imeepwni.myfood.model.net.MobService
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), IndexFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container, IndexFragment.newInstance()).commit()

        initData()
    }

    private fun initData() {
        MobService.getMenuTabs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Logger.d("OnNext", it)
                }
                .doOnError {
                    Logger.d("OnError", it)
                }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
