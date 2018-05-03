package com.imeepwni.myfood.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseFragment
import com.imeepwni.myfood.model.net.MobService
import kotlinx.android.synthetic.main.fragment_menu_detail.*

/**
 * 菜单详情页
 */
class MenuDetailFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text1.text = arguments?.getString(MobService.KEY_ID)
    }

    companion object {

        /**
         * 获取菜单详情实例
         */
        fun newInstance(menuId: String): MenuDetailFragment {
            return MenuDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(MobService.KEY_ID, menuId)
                }
            }
        }
    }
}
