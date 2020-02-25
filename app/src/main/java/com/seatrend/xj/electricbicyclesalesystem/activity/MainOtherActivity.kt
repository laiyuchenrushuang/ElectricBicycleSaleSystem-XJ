package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.ViewPagerAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.LoginEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.entity.YgHeaderPhotoEnity
import com.seatrend.xj.electricbicyclesalesystem.fragment.HomeFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.StationManagerFrament
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadUrlUtils
import com.seatrend.xj.electricbicyclesalesystem.util.RoundHeadImageViewUtil
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_other.*
import kotlinx.android.synthetic.main.activity_main_other.tableLayout
import kotlinx.android.synthetic.main.activity_main_other.view_pager
import java.util.ArrayList

/**
 * Created by ly on 2019/10/21 13:24
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class MainOtherActivity : BaseActivity(), NormalView {

    companion object{
        var mLoginEnity: LoginEntity? = null
    }

    private val fragmentList = ArrayList<Fragment>()
    private val pagerTitle = ArrayList<String>()
    private var mNormalPresenter :NormalPresenter ?= null

    private var photoUrl = "" //用户照片的url

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        if(Constants.YG_PHOTO_SEARCH.equals(commonResponse.url)){
            var enity = GsonUtils.gson(commonResponse.getResponseString(), YgHeaderPhotoEnity::class.java)
            if(enity == null || enity.data == null || enity.data.size < 1){
                showToast("获取头像数据为空")
                runOnUiThread {
                    RoundHeadImageViewUtil.loadImage(this, iv_head)
                }
                return
            }
            for(db in enity.data){
                if ("C1".equals(db.zpzl)){
                    runOnUiThread {
                        RoundHeadImageViewUtil.loadImageByPath(this, LoadUrlUtils.loadurl(db.zpdz),iv_head)
                    }
                    photoUrl = db.zpdz
                    break
                }
            }
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()

        if(Constants.YG_PHOTO_SEARCH.equals(commonResponse.url)){
            RoundHeadImageViewUtil.loadImage(this, iv_head)
            showErrorDialog(commonResponse.getResponseString())
        }
    }

    override fun initView() {
        mNormalPresenter = NormalPresenter(this)
        initHeadView()
        initFragment()
    }

    private fun initFragment() {
        fragmentList.add(HomeFragment())
        fragmentList.add(StationManagerFrament())
        pagerTitle.add("首页")
        pagerTitle.add("站点")
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager, fragmentList, pagerTitle)
        tableLayout.setupWithViewPager(view_pager)
        tableLayout.getTabAt(0)!!.text = "首页"
        tableLayout.getTabAt(1)!!.text = "站点"

        tableLayout.getTabAt(0)!!.setIcon(R.drawable.yewu_iv_seletor)
        tableLayout.getTabAt(1)!!.setIcon(R.drawable.jg_iv_seletor)
        view_pager.offscreenPageLimit = 2


        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                //                    0 -> setPageTitle(getString(R.string.electric_bicycle_register))
//                    1 -> setPageTitle(getString(R.string.yjxx))
//                    else -> setPageTitle("")
                when {
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        iv_head.setOnClickListener {
            intent.setClass(this, UserInfoActivity::class.java)
            intent.putExtra("photo_url",photoUrl)
            startActivity(intent)
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout)
        }
    }

    private fun initHeadView() {
        val map = HashMap<String,String>()
        map["sfzmhm"] = UserInfo.SFZMHM
        mNormalPresenter!!.doNetworkTask(map,Constants.YG_PHOTO_SEARCH)
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

    override fun getLayout(): Int {
        return R.layout.activity_main_other
    }


}