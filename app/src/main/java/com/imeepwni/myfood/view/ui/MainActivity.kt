package com.imeepwni.myfood.view.ui

import android.os.Bundle
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container, IndexFragment.newInstance()).commit()
    }
}
