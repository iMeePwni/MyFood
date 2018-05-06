package com.imeepwni.myfood.view.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import com.imeepwni.myfood.model.net.MobService
import kotlinx.android.synthetic.main.activity_menu_detail.*
import org.jetbrains.anko.startActivity

/**
 * 菜单详情页
 */
class MenuDetailActivity : BaseActivity() {

    /**
     * Menu ID
     */
    lateinit var menuId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        initData()
        initView()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        menuId = intent.getStringExtra(MobService.KEY_ID)
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        vp_container.apply {
            adapter = object : FragmentPagerAdapter(supportFragmentManager) {
                override fun getItem(position: Int): Fragment {
                    return MenuDetailFragment.newInstance(menuId)
                }

                override fun getCount(): Int {
                    return 1
                }
            }
            offscreenPageLimit = 1
        }
    }

    companion object {

        /**
         * 跳转到菜单详情页面
         */
        fun startActivity(context: Context, menuId: String) {
            context.startActivity<MenuDetailActivity>(MobService.KEY_ID to menuId)
        }
    }
}
