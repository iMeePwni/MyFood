package com.imeepwni.myfood.view.ui

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.imeepwni.myfood.R
import com.imeepwni.myfood.app.BaseActivity
import com.imeepwni.myfood.model.data.Menu
import com.imeepwni.myfood.model.datasource.SimpleMenuDataSource
import com.imeepwni.myfood.model.net.MobService
import com.imeepwni.myfood.view.adapter.SimpleMenuAdapter
import com.imeepwni.myfood.view.ui.test.TestActivity
import io.reactivex.BackpressureStrategy
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        mTvCurrentCategory.text = getString(R.string.app_name)
        // 菜单按钮
        setSupportActionBar(mToolBar)

        mRVSimpleMenu.run {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect?.apply {
                        val pixelOffset = this@MainActivity.resources.getDimensionPixelOffset(R.dimen.recycler_view_item_margin)
                        left = pixelOffset
                        top = pixelOffset
                        right = pixelOffset
                        bottom = 0
                    }
                }
            })
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        val simpleMenuAdapter = SimpleMenuAdapter.newInstance()
        mRVSimpleMenu.adapter = simpleMenuAdapter
        val dataSourceFactory: DataSource.Factory<Int, Menu> = object : DataSource.Factory<Int, Menu>() {
            override fun create(): DataSource<Int, Menu> {
                return SimpleMenuDataSource.newInstance()
            }
        }
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(MobService.DEFAULT_PAGE_SIZE * 2)
                .setPageSize(MobService.DEFAULT_PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build()
        val flowable = RxPagedListBuilder<Int, Menu>(dataSourceFactory, config).buildFlowable(BackpressureStrategy.BUFFER)
        mCompositeDisposable.add(flowable.subscribe {
            simpleMenuAdapter.submitList(it)
        })
    }

    /**
     * 设置标题栏更多按钮
     */
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * 更多按钮点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_show_tab -> showTabsFragment()
            R.id.menu_test -> goToTestActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
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

    /**
     * 跳转到测试页面
     */
    private fun goToTestActivity() {
        TestActivity.startActivity(this)
    }

}
