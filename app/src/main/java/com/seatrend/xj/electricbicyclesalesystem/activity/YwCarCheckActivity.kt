package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CarMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.CarHphmUtils
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_carcheck.*

/**
 * Created by ly on 2020/1/15 16:05
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class YwCarCheckActivity : BaseActivity(), NormalView {

    private var mNormalPresenter: NormalPresenter? = null
    var mEnity: CarMsgEnity? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.CAR_MSG_SEARCH.equals(commonResponse.url)) {
            mEnity = GsonUtils.gson(commonResponse.responseString, CarMsgEnity::class.java)
            runOnUiThread {
                updateUI(mEnity)
            }
        }
    }

    private fun updateUI(mEnity: CarMsgEnity?) {

        try {
            ll_item.visibility = View.VISIBLE
            tv_syr.text = mEnity!!.data.syrjbxx.syrmc
            tv_zcbm.text = mEnity.data.syrjbxx.zcbm
            tv_hphm.text = mEnity.data.syrjbxx.cph
        } catch (e: Exception) {
            showToast(e.message.toString())
            ll_item.visibility = View.GONE
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.responseString)
        runOnUiThread {
            ll_item.visibility = View.GONE
        }
    }


    override fun initView() {
        mNormalPresenter = NormalPresenter(this)
        ll_item.visibility = View.GONE
        initEvent()
    }

    private fun initEvent() {

//        var searchString: String? = null
//
//        //搜索框不自动缩小为一个搜索图标，而是match_parent
//        searchview.setIconifiedByDefault(false)
//        //显示搜索按钮
//        searchview.isSubmitButtonEnabled = false
//        //设置提示hint
//        searchview.queryHint = "查询车辆号牌"
//        searchview!!.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(pSubmit: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(pChange: String?): Boolean {
//                searchString = pChange!!.toUpperCase()
//                return false
//            }
//        })
        searchview.transformationMethod = CarHphmUtils.TransInformation()
        searchview.filters = arrayOf(inputFilter)
        setSelection(searchview)

        iv_btn_search.setOnClickListener {
            var searchString: String? = searchview.text.toString()
            if (TextUtils.isEmpty(searchString)) {
                showToast("搜索的内容为空")
                return@setOnClickListener
            }
            searchview.clearFocus()

            val map = HashMap<String, String?>()
            map["cph"] = searchString!!.trim().toUpperCase()
            showLoadingDialog()
            mNormalPresenter!!.doNetworkTask(map, Constants.CAR_MSG_SEARCH)
        }

        item_cardview.setOnClickListener {
            intent.setClass(this, Yw3CzActivity::class.java)
            intent.putExtra(Constants.UI_TYPE, "3") //车辆查询入口进去
            intent.putExtra("cy_data",mEnity)
            startActivity(intent)
        }
        ll_back.setOnClickListener {
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_carcheck
    }
}