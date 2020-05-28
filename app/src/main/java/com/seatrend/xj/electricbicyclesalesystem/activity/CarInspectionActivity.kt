package com.seatrend.xj.electricbicyclesalesystem.activity

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.GestureDetector
import android.widget.CompoundButton
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.fragment.CYActualMsgFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.CYProjectJudgeFragment
import com.seatrend.xj.electricbicyclesalesystem.holder.FragmentGestureDetector
import kotlinx.android.synthetic.main.activity_inspection.*
import com.seatrend.xj.electricbicyclesalesystem.common.Constants.Companion.CAR_CY
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.FastClickUtils
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.bottom_button.*
import kotlinx.android.synthetic.main.fragment_cy_actual_msg.et_zczl
import kotlinx.android.synthetic.main.fragment_cy_actual_msg.et_zgss
import android.os.Bundle




/**
 * Created by ly on 2020/4/3 11:05
 */
class CarInspectionActivity : BaseActivity(), NormalView {


    private var count: Int = 0  //请求计数

    companion object {
        var data1: CYEntranceEnity? = null
        var data3c: ThreeCEnity? = null
    }

    var mCarScFG: CYActualMsgFragment? = null
    var mCarPdFG: CYProjectJudgeFragment? = null
    var mDetector: GestureDetector? = null

    private var mNormalPresenter: NormalPresenter? = null


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.SAVE_CY_PD == commonResponse.url) {
            var enity = GsonUtils.gson(commonResponse.responseString, ServicePhotoCamebackEnity::class.java)

            if (enity != null && enity.data != null && enity.data.size > 0) {
                val bundle = Bundle()
                bundle.putParcelable("photo_list", enity)
                intent.putExtras(bundle)
            }
        }
        count++
        showLog("url  S =" + commonResponse.url)
        if (count == 2) {

            val flag = getJudeProject() && et_zczl.text.toString().toFloat() <= 55.0 && et_zgss.text.toString().toFloat() <= 25.0
            CollectPhotoActivity.photoEntranceFlag = CAR_CY
            CollectPhotoActivity.jtxsFlag = flag

            intent.putExtra("photoentranceflag", CAR_CY)
            intent.putExtra("jtxsflag", flag)

            intent.setClass(this, CollectPhotoActivity::class.java)
            startActivity(intent)
        }
    }

    //判定项目是否是全是OK
    private fun getJudeProject(): Boolean {
        var map = mCarPdFG!!.getSendData()
        for (key: String in map.keys) {
            if ("0" == map[key]) {
                return false
            }
        }
        return true
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        showLog("url  E =" + commonResponse.url)
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
        count--
    }

    override fun initView() {

        setPageTitle(resources.getString(R.string.home_clcy))
        mNormalPresenter = NormalPresenter(this)
        mCarScFG = CYActualMsgFragment()
        mCarPdFG = CYProjectJudgeFragment()

        getData()
        bindEvent()
    }

    private fun getData() {

        data1 = intent.getSerializableExtra("all_data") as CYEntranceEnity

        data3c = intent.getSerializableExtra("3c_data") as ThreeCEnity

        supportFragmentManager.beginTransaction().replace(R.id.carmsg_fl, mCarScFG).commit()
        rb_scxx.isChecked = true


        var list = ArrayList<JudgeProjectEnity>()
        val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(Constants.PD_LIST)
        for (db in sfzmmcList) {
            var enity = JudgeProjectEnity()
            val dmz = db.dmz
            val dmsm1 = db.dmsm1
            enity.content = "$dmz:$dmsm1"
            enity.order = "1"
            list.add(enity)
        }
//
        mCarPdFG!!.setGetData(list)
        mCarPdFG!!.setGetData(data1!!)
    }

    private fun bindEvent() {
        rb_scxx.setOnCheckedChangeListener { _: CompoundButton, check: Boolean ->
            if (check) {
                rb_scxx!!.setTextColor(ContextCompat.getColor(this, R.color.theme_color))
                rb_xmpd!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                switchFrament(lastFragment, mCarScFG)
            } else {
                rb_xmpd!!.setTextColor(ContextCompat.getColor(this, R.color.theme_color))
                rb_scxx!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                switchFrament(mCarScFG, mCarPdFG)
            }
        }

        mDetector = GestureDetector(this, FragmentGestureDetector(object : FragmentGestureDetector.GestureListener {
            override fun toLeft() {
                if (mCarScFG!!.isVisible) {
                    rb_xmpd.isChecked = true
                }
            }

            override fun toRight() {
                if (mCarPdFG!!.isVisible) {
                    rb_scxx.isChecked = true
                }
            }
        }))

        bt_next.setOnClickListener {
            //要判定项目fragement才能提交数据 方便人员观看判定内容
            if (!mCarPdFG!!.isVisible) {
                rb_xmpd.performClick()
                return@setOnClickListener
            }

            if (!FastClickUtils.isFastClick()) {
                try {
                    count = 0
                    showLoadingDialog()
                    mNormalPresenter!!.doNetworkTask(mCarScFG!!.getSendData(), Constants.SAVE_CY_MSG)
                    mNormalPresenter!!.doNetworkTask(mCarPdFG!!.getSendData(), Constants.SAVE_CY_PD)
                } catch (e: Exception) {
                    showToast(e.message.toString())
                    dismissLoadingDialog()
                    count = 0
                }
            }

        }
    }

    //手势操作 fragment滑动
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return mDetector!!.onTouchEvent(event)
//    }


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

    fun getJcData(): CYEntranceEnity? {
        return data1
    }

    fun get3cData(): ThreeCEnity? {
        return data3c
    }

    override fun getLayout(): Int {
        return R.layout.activity_inspection
    }
}