package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.fragment.CarCYxxFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.CarMsgJscsFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.RegisterAllInfoFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.RegisterInfoFragment
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.FHUtil
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import com.seatrend.xj.electricbicyclesalesystem.util.ViewShowUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_3cz_detail.*

/**
 * Created by ly on 2019/11/8 14:39
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 *
 * 业务复核 退办 归档的综合界面
 */
class Yw3CzActivity : BaseActivity(), NormalView {

    companion object {
        var fhzt: String? = null  //复核状态  复核标记 9-无需复核，0-待复核，1-复核通过，2-复核未通过
        var zcbm: String? = null  //通过整车编码查询或者修改复核状态
        var mAllBikeMsgEnity: AllBikeMsgEnity? = null  //所有的data

        var mAllCXData: CarMsgEnity? = null  //所有的查询车辆data
    }


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.GD_COMMIT.equals(commonResponse.url)) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    private var mRegisterInfoFragment: RegisterAllInfoFragment? = null
    private var mCarMsgJscsFG: CarMsgJscsFragment? = null
    private var mCarCYxxFG: CarCYxxFragment? = null
    private var mNormalPresenter: NormalPresenter? = null

    override fun initView() {
        setPageTitle("业务详情")
        tv_fhbz!!.movementMethod = ScrollingMovementMethod.getInstance()
        mNormalPresenter = NormalPresenter(this)
        mCarMsgJscsFG = CarMsgJscsFragment()
        mRegisterInfoFragment = RegisterAllInfoFragment()
        mCarCYxxFG = CarCYxxFragment()
        bindEvent()
        carmsg_rg!!.check(rb_jscs!!.id)
        initFgData()
    }

    private fun initFgData() {
        CarMsgJscsFragment.enity = mAllBikeMsgEnity //车的信息以及图片
        RegisterAllInfoFragment.enity = mAllBikeMsgEnity //注册信息
        CarCYxxFragment.enity = mAllBikeMsgEnity
    }

    override fun onStart() {
        super.onStart()
        getUiData()
    }

    private fun getUiData() {
        //业务复核
        if ("0".equals(intent.getStringExtra(Constants.UI_TYPE))) {
            if (!TextUtils.isEmpty(fhzt)) {
                val result = intent.getStringExtra("fhzt")
                if ("0" != result) {
                    ViewShowUtils.showVisibleView(ll_fhjg)
                    ViewShowUtils.showGoneView(bt_next)
                    tv_fhr.text = intent.getStringExtra("fhr")
                    if ("1" == result) {
                        tv_fhzt.text = FHUtil.getDMSM(result)
                        tv_fhzt.setTextColor(Color.GREEN)
                        ll_fhyy.visibility = View.GONE
                        tv_fhsj.text = StringUtils.longToStringData(intent.getStringExtra("fhsj").toLong())
                        if (TextUtils.isEmpty(intent.getStringExtra("fhbz"))) tv_fhbz.text = "/" else tv_fhbz.text = intent.getStringExtra("fhbz")
                    } else if ("2" == result) {
                        tv_fhzt.text = FHUtil.getDMSM(result)
                        tv_fhzt.setTextColor(Color.RED)
                        tv_fhyy.text = intent.getStringExtra("fhyy")
                        tv_fhyy.setTextColor(Color.RED)
                        tv_fhsj.text = StringUtils.longToStringData(intent.getStringExtra("fhsj").toLong())
                        tv_fhbz.text = intent.getStringExtra("fhbz")
                        if (TextUtils.isEmpty(intent.getStringExtra("fhbz"))) tv_fhbz.text = "/" else tv_fhbz.text = intent.getStringExtra("fhbz")
                    } else {
                        ViewShowUtils.showVisibleView(bt_next)
                        ViewShowUtils.showGoneView(ll_fhjg)
                    }
                } else {  // ==0
                    ViewShowUtils.showVisibleView(bt_next)
                    ViewShowUtils.showGoneView(ll_fhjg)
                    bt_next.text = "业务复核"
                }
            } else {
                bt_next.visibility = View.VISIBLE
                bt_next.text = "业务复核"
            }
            //业务归档
        } else if ("1".equals(intent.getStringExtra(Constants.UI_TYPE))) {
            ViewShowUtils.showGoneView(ll_fhjg)
            ViewShowUtils.showVisibleView(bt_next)
            bt_next.text = "归档"
            //业务退办
        } else if ("2".equals(intent.getStringExtra(Constants.UI_TYPE))) {
            ViewShowUtils.showGoneView(ll_fhjg)
            ViewShowUtils.showVisibleView(bt_next)
            bt_next.text = "退办"
        }
    }

    private fun bindEvent() {
        carmsg_rg.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, position: Int) {
                when (p0!!.checkedRadioButtonId) {
                    R.id.rb_jscs -> {
                        changeColor(rb_jscs)
                        switchFrament(lastFragment, mCarMsgJscsFG)
                    }
                    R.id.rb_cyxx -> {
                        changeColor(rb_cyxx)
                        switchFrament(lastFragment, mCarCYxxFG)
                    }
                    R.id.rb_djxx -> {
                        changeColor(rb_djxx)
                        switchFrament(lastFragment, mRegisterInfoFragment)
                    }
                }
            }
        })
        bt_next.setOnClickListener {
            if ("0".equals(intent.getStringExtra(Constants.UI_TYPE))) {
                //复核
                startActivity(Intent(this, YwfhEditActivity::class.java))
            } else if ("2".equals(intent.getStringExtra(Constants.UI_TYPE))) {
                //退办
                startActivity(Intent(this, YwTbEditActivity::class.java))
            } else {
                //归档
                val map = HashMap<String, String?>()
                map["lsh"] = mAllBikeMsgEnity!!.data.fjdcBusiness.lsh
                map["xh"] = mAllBikeMsgEnity!!.data.fjdcBusiness.xh
                map["glbm"] = UserInfo.GLBM
                map["zt"] = "E" // E-已归档
                mNormalPresenter!!.doNetworkTask(map, Constants.GD_COMMIT)
            }
        }
    }

    private fun changeColor(which: RadioButton) {
        rb_jscs!!.setTextColor(resources.getColor(R.color.black))
        rb_djxx!!.setTextColor(resources.getColor(R.color.black))
        rb_cyxx!!.setTextColor(resources.getColor(R.color.black))
        carmsg_rg.check(which.id)
        which.setTextColor(resources.getColor(R.color.theme_color))
    }

    private fun switchFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction().replace(R.id.carmsg_fl, fragment).commit()
    }

    private var lastFragment: Fragment? = null
    fun switchFrament(from: Fragment?, to: Fragment?) {
        if (from !== to) {
            lastFragment = to
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            if (!to!!.isAdded) {
                if (from != null) {
                    ft.hide(from)
                }
                if (to != null) {
                    ft.add(R.id.carmsg_fl, to).commit()
                }
            } else {
                if (from != null) {
                    ft.hide(from)
                }
                if (to != null) {
                    ft.show(to).commit()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mAllCXData = null
        mAllBikeMsgEnity = null
    }

    override fun getLayout(): Int {
        return R.layout.activity_3cz_detail
    }
}