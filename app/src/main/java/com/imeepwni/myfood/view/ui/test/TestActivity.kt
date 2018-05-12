package com.imeepwni.myfood.view.ui.test

import android.content.Context
import android.os.Bundle
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import org.jetbrains.anko.startActivity

/**
 * 功能测试页
 */
class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        showPagingFragment()
    }


    /**
     * 展示PagingFragment
     */
    private fun showPagingFragment() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(TAG_PAGING)
        if (prev != null) {
            beginTransaction.remove(prev)
        }
        beginTransaction.addToBackStack(null)
        val pagingFragment = PagingFragment.newInstance()
        beginTransaction.add(R.id.container, pagingFragment, TAG_PAGING)
        beginTransaction.commit()
    }

    companion object {

        private const val TAG_PAGING = "paging"

        /**
         * 跳转到TestActivity
         */
        fun startActivity(context: Context) {
            context.startActivity<TestActivity>()
        }
    }
}