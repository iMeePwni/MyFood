package com.imeepwni.myfood.view.ui

import android.os.Bundle
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_show_tabs.setOnClickListener {
            showTabsFragment()
        }
    }

    /**
     * 菜单标签选择器
     */
    private fun showTabsFragment() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(TabsFragment.TAG)
        if (prev != null) {
            beginTransaction.remove(prev)
        }
        beginTransaction.addToBackStack(null)
        val tabsFragment = TabsFragment.newInstance()
        tabsFragment.show(beginTransaction, TabsFragment.TAG)
    }

}
