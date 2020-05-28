package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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
import com.seatrend.xj.electricbicyclesalesystem.manager.AppManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.FHUtil
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import com.seatrend.xj.electricbicyclesalesystem.util.ViewShowUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_3cz_detail.*
import kotlinx.android.synthetic.main.common_title.*

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
    }

    private var isTbDetals: Boolean = false //是否是退办详情界面

    var mAllCXData: CarMsgEnity? = null  //所有的查询车辆data
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
        if("3" == intent.getStringExtra(Constants.UI_TYPE)){
            setPageTitle("车辆详情")
        }else {
            setPageTitle("业务详情")
        }
        tv_fhbz!!.movementMethod = ScrollingMovementMethod.getInstance()
        tv_tbbz!!.movementMethod = ScrollingMovementMethod.getInstance()
        tv_tbyy!!.movementMethod = ScrollingMovementMethod.getInstance()
        tv_fhyy!!.movementMethod = ScrollingMovementMethod.getInstance()
        mNormalPresenter = NormalPresenter(this)
        mCarMsgJscsFG = CarMsgJscsFragment()
        mRegisterInfoFragment = RegisterAllInfoFragment()
        mCarCYxxFG = CarCYxxFragment()
        bindEvent()
        carmsg_rg!!.check(rb_jscs!!.id)
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
            if(!isTbDetals){
                ViewShowUtils.showGoneView(ll_fhjg)
                ViewShowUtils.showVisibleView(bt_next)
                bt_next.text = "退办"
            }else{
                ViewShowUtils.showGoneView(ll_fhjg,bt_next)
                ViewShowUtils.showVisibleView(ll_tbjg)
            }

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
                intent.setClass(this, YwfhEditActivity::class.java)
                startActivity(intent)
            } else if ("2".equals(intent.getStringExtra(Constants.UI_TYPE))) {
                //退办
                intent.setClass(this, YwTbEditActivity::class.java)
                startActivityForResult(intent, 1)
            } else {
                //归档
                val map = HashMap<String, String?>()
                val mAllBikeMsgEnity  = intent.getSerializableExtra("all_data") as AllBikeMsgEnity
                map["lsh"] = mAllBikeMsgEnity.data.fjdcBusiness.lsh
                map["xh"] = mAllBikeMsgEnity.data.fjdcBusiness.xh
                map["glbm"] = UserInfo.GLBM
                map["zt"] = "E" // E-已归档
                mNormalPresenter!!.doNetworkTask(map, Constants.GD_COMMIT)
            }
        }
        iv_back.setOnClickListener {
            if (isTbDetals) {
                val intent = Intent(this, YwTBSearchActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP  //致于栈顶
                startActivity(intent)
            }
            finish()
        }
    }

    private fun changeColor(which: RadioButton) {
        rb_jscs!!.setTextColor(ContextCompat.getColor(this, R.color.black))
        rb_djxx!!.setTextColor(ContextCompat.getColor(this, R.color.black))
        rb_cyxx!!.setTextColor(ContextCompat.getColor(this, R.color.black))
        carmsg_rg.check(which.id)
        which.setTextColor(ContextCompat.getColor(this, R.color.theme_color))
    }

    private var lastFragment: Fragment? = null
    fun switchFrament(from: Fragment?, to: Fragment?) {
        if (from != to) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            runOnUiThread {
                isTbDetals = true
                tv_tbr.text = UserInfo.XM
                tv_tbzt.text = "已退办"
                tv_tbyy.text = data!!.getStringExtra("tb_reason")
                tv_tbsj.text = data.getStringExtra("tb_time")
                tv_tbbz.text = data.getStringExtra("tb_beizhu")
            }

        }
    }

    override fun onDestroy() {
        isTbDetals = false
        super.onDestroy()
        mAllCXData = null
    }

    override fun getLayout(): Int {
        return R.layout.activity_3cz_detail
    }
}