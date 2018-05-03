package com.imeepwni.myfood.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import com.imeepwni.myfood.model.net.MobService

/**
 * 菜单详情页
 */
class MenuDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)
    }

    companion object {

        /**
         * 跳转到菜单详情页面
         */
        fun startActivity(context: Context, menuId: String) {
            Intent(context, MenuDetailActivity::class.java).apply {
                putExtra(MobService.KEY_ID, menuId)
            }.let {
                context.startActivity(it)
            }
        }
    }
}
