package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.widget.CompoundButton
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity3C
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.ThreeCEnity
import com.seatrend.xj.electricbicyclesalesystem.fragment.CarMsgGgxxFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.CarMsgJscsFragment
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_car_info.*
import kotlinx.android.synthetic.main.bottom_button.*


class CarInfoActivity : BaseActivity(), NormalView {

    var mDataZcbm: CYEntranceEnity? = null  //普通信息  all data
//    var mData3C: ThreeCEnity? = null  //3C信息  注册登记有用 ,注册登记才请求3c信息存数据库

    private var mCarMsgJscsFG: CarMsgJscsFragment? = null
    private var mCarMsgggxxFG: CarMsgGgxxFragment? = null
    private var mNormalPresenter: NormalPresenter? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {

    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {

    }

    override fun initView() {
        setPageTitle("技术参数查验")
        mDataZcbm = intent.getSerializableExtra("all_data") as CYEntranceEnity
        mNormalPresenter = NormalPresenter(this)
        mCarMsgJscsFG = CarMsgJscsFragment()
        mCarMsgggxxFG = CarMsgGgxxFragment()
        bindEvent()
        rb_jscs.isChecked = true
    }

    private fun bindEvent() {

        rb_jscs.setOnCheckedChangeListener { _: CompoundButton, check: Boolean ->
            if (check) {
                rb_jscs!!.setTextColor(ContextCompat.getColor(this, R.color.theme_color))
                rb_ggxx!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                switchFragment(mCarMsgJscsFG)
            } else {
                rb_ggxx!!.setTextColor(ContextCompat.getColor(this, R.color.theme_color))
                rb_jscs!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                switchFragment(mCarMsgggxxFG)
            }
        }

        bt_next!!.setOnClickListener {
            if (Constants.A.equals(intent.getStringExtra("ywlx"))) {
//                intent.setClass(this, ChaYanActivity::class.java)
                intent.setClass(this, CarInspectionActivity::class.java)
                startActivity(intent)
            } else {
//
//                CollectPhotoActivity.photoEntranceFlag = Constants.CAR_CY
//                startActivity(intent.setClass(this, CollectPhotoActivity::class.java))
                intent.setClass(this,CYProjectJudgeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_car_info
    }

}
