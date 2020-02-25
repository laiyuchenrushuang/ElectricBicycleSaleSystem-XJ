package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.widget.RadioButton
import android.widget.RadioGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.fragment.CarCYxxFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.CarMsgJscsFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.RegisterInfoFragment
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_car_info_by_cy.*
import kotlinx.android.synthetic.main.bottom_button.*

/**
 * Created by ly on 2019/9/27 14:21
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CarInfoByCyActivity : BaseActivity(), NormalView {

    companion object {
        var entranceFlag: String = "-1"  //0是VIN，1是查验，2注册，3变更 ，4转移， 5补换，6注销，7旧车换牌， 8临时号牌申请 9车辆归档，-1是defult
        var mAllBikeMsgEnity: AllBikeMsgEnity?=null  //所有的data
    }

    private var mRegisterInfoFragment: RegisterInfoFragment? = null
    private var mCarMsgJscsFG: CarMsgJscsFragment? = null
    private var mCarCYxxFG: CarCYxxFragment? = null
    private var mNormalPresenter: NormalPresenter? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
    }

    override fun initView() {
        setPageTitle("车辆信息")
        mCarMsgJscsFG = CarMsgJscsFragment()
        mRegisterInfoFragment = RegisterInfoFragment()
        mCarCYxxFG = CarCYxxFragment()
        mNormalPresenter = NormalPresenter(this)
        bindEvent()
        if (Constants.YWTB.equals(entranceFlag)) {
            bt_next.setText("退办")
        }
        initFgData()
        carmsg_rg!!.check(rb_jscs!!.id)
    }

    private fun initFgData() {
        CarMsgJscsFragment.enity = mAllBikeMsgEnity //车的信息以及图片
        RegisterInfoFragment.enity = mAllBikeMsgEnity //注册信息
        CarCYxxFragment.enity = mAllBikeMsgEnity
    }

    private fun bindEvent() {
        carmsg_rg.setOnCheckedChangeListener { p0, _ ->
            when (p0!!.checkedRadioButtonId) {
                R.id.rb_jscs -> {
                    changeColor(rb_jscs)
                    switchFrament(lastFragment, mRegisterInfoFragment)
//                        switchFragment(mRegisterInfoFragment)
                }
                R.id.rb_ggxx -> {
                    changeColor(rb_ggxx)
                    switchFrament(lastFragment, mCarMsgJscsFG)
//                        switchFragment(mCarMsgJscsFG)
                }
                R.id.rb_cyxx -> {
                    changeColor(rb_cyxx)
                    switchFrament(lastFragment, mCarCYxxFG)
//                        switchFragment(mCarCYxxFG)
                }
            }
        }
        bt_next.setOnClickListener {
            CollectPhotoActivity.photoEntranceFlag = entranceFlag
            when (entranceFlag) {
                Constants.CAR_ZC -> {
                    intent.setClass(this, YwRegisterActivity::class.java)
                    startActivity(intent)
                }
                Constants.CAR_BG -> {
                    intent.setClass(this, YwChangeActivity::class.java)
                    startActivity(intent)
                }
                Constants.CAR_ZY -> {
                    intent.setClass(this, YwTransferActivity::class.java)
                    startActivity(intent)
                }
                Constants.CAR_ZX -> {
                    intent.setClass(this, YwCancelActivity::class.java)
                    startActivity(intent)
                }
                Constants.CAR_BH -> {
                    intent.setClass(this, YwReplaceActivity::class.java)
                    startActivity(intent)
                }
                Constants.CAR_JCHP -> {
                    intent.setClass(this, YwOldCarReplaceHPHMActivity::class.java)
                    startActivity(intent)
                }

                Constants.YWTB -> { //查验流水需要退办的
                    intent.setClass(this, YwTbEditActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun changeColor(which: RadioButton) {
        rb_jscs!!.setTextColor(ContextCompat.getColor(this,R.color.black))
        rb_ggxx!!.setTextColor(ContextCompat.getColor(this,R.color.black))
        rb_cyxx!!.setTextColor(ContextCompat.getColor(this,R.color.black))
        carmsg_rg.check(which.id)
        which.setTextColor(ContextCompat.getColor(this,R.color.theme_color))
    }

    private fun switchFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction().replace(R.id.carmsg_fl, fragment).commit();
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
                ft.add(R.id.carmsg_fl, to).commit()
            } else {
                if (from != null) {
                    ft.hide(from)
                }
                ft.show(to).commit()
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_car_info_by_cy
    }

}