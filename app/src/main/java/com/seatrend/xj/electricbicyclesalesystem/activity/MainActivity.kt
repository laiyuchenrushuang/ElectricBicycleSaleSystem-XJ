package com.seatrend.xj.electricbicyclesalesystem.activity

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.ViewPagerAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.fragment.BusinessFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.UserInfoFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

@Suppress("PLUGIN_WARNING")
class MainActivity : BaseActivity() {

    companion object {
        var username: String = ""
    }

    private val fragmentList = ArrayList<Fragment>()
    private val pagerTitle = ArrayList<String>()


    override fun getLayout(): Int {
        return R.layout.activity_main
    }


    override fun initView() {
        setPageTitle(getString(R.string.electric_bicycle_register))
        ivBack!!.visibility = View.GONE

        fragmentList.add(BusinessFragment())
//        fragmentList.add(WarningMessageFragment())
        fragmentList.add(UserInfoFragment())

        pagerTitle.add("业务")
        pagerTitle.add("预警")
        pagerTitle.add("统计")
        pagerTitle.add("我的")
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager, fragmentList, pagerTitle)
        tableLayout.setupWithViewPager(view_pager)
        tableLayout.getTabAt(0)!!.setIcon(R.drawable.yewu_iv_seletor)
        tableLayout.getTabAt(1)!!.setIcon(R.drawable.jg_iv_seletor)
        tableLayout.getTabAt(2)!!.setIcon(R.drawable.tj_iv_seletor)
        tableLayout.getTabAt(3)!!.setIcon(R.drawable.wd_iv_seletor)

        view_pager.offscreenPageLimit = 4

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> setPageTitle(getString(R.string.electric_bicycle_register))
                    1 -> setPageTitle(getString(R.string.yjxx))
                    2 -> setPageTitle(getString(R.string.ywtj))
                    3 -> setPageTitle(getString(R.string.mine))
                    else -> setPageTitle("")
                }
            }
            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        ivRight!!.setOnClickListener {
            //  startActivity(Intent(this, UserInfoFragment::class.java))
        }


    }


    private var firstPressedTime: Long = 0
    private var secondPressedTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            secondPressedTime = System.currentTimeMillis()
            if (secondPressedTime - firstPressedTime < 2000) {
                finish()
                return true
            } else {
                firstPressedTime = secondPressedTime
                showToast(getString(R.string.exit_press_again))
                return false
            }

        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
