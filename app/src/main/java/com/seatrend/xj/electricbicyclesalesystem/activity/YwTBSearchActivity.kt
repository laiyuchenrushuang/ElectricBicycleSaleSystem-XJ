package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.TBEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_tuiban.*


class YwTBSearchActivity : BaseActivity(), NormalView {

    private var mTbEnity: TBEnity? = null
    var lczt: String? = null  // 流程状态 1为已查验

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.TB_GET_LIST.equals(commonResponse.url)) {
            var enity = GsonUtils.gson(commonResponse.getResponseString(), TBEnity::class.java)
            runOnUiThread {
                updateUI(enity)
            }
        }

        if (Constants.YW_GET_YWCZ_BIKE_DATA.equals(commonResponse.getUrl())) {
            val mAllBikeMsgEnity = GsonUtils.gson(commonResponse.getResponseString(), AllBikeMsgEnity::class.java)
            if (!"1".equals(lczt)) {
                intent.putExtra("all_data", mAllBikeMsgEnity)
                intent.setClass(this, Yw3CzActivity::class.java)
                intent.putExtra(Constants.UI_TYPE, "2")
                startActivity(intent)
            }
            if ("1".equals(lczt)) { //查验需要退办的
                intent.setClass(this, CarInfoByCyActivity::class.java)
                intent.putExtra("ywlx", mAllBikeMsgEnity.data.fjdcBusiness.ywlx)
                intent.putExtra("all_data", mAllBikeMsgEnity)
                CarInfoByCyActivity.entranceFlag = Constants.YWTB
                startActivity(intent)
            }
        }
    }


    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
        runOnUiThread {
            ll_item.visibility = View.GONE
        }
    }


    private fun updateUI(enity: TBEnity) {
        if (enity.data == null || enity.data.list == null || enity.data.list.size == 0) {
            showToast("当前查询退办数据获取为空")
            ll_item.visibility = View.GONE
            return
        }
        ll_item.visibility = View.VISIBLE
        mTbEnity = enity
        lczt = enity.data.list[0].lczt
        tv_syr.text = StringUtils.isNull(enity.data.list[0].syr)
        tv_hphm.text = StringUtils.isNull(enity.data.list[0].cph)
        tv_zcbm.text = StringUtils.isNull(enity.data.list[0].zcbm)
//        if(null == enity.data.list[0].ywry || TextUtils.isEmpty(enity.data.list[0].ywry)){
//            text_ry_flag.text = "业务人员"
//            tv_ywry.text = enity.data.list[0].ywry
//        }else{
//            text_ry_flag.text = "业务人员"
        tv_ywry.text = StringUtils.isNull(enity.data.list[0].ywry)
//        }
        tv_ywlx.text = DMZUtils.getDMSM(Constants.YWLX, enity.data.list[0].ywlx)
        tv_slsj.text = StringUtils.isNull(StringUtils.longToStringData(enity.data.list[0].kssj))
        tv_fhzt.text = StringUtils.isNull(FHUtil.getDMSM(enity.data.list[0].fhbj))
        tv_ywzt.text = StringUtils.isNull(LcStateUtils.getDMSM(enity.data.list[0].lczt))
        if ("1".equals(enity.data.list[0].fhbj)) {
            tv_fhzt.setTextColor(Color.GREEN)
        }
    }

    private var mNormalPresenter: NormalPresenter? = null
    override fun initView() {
        setPageTitle("业务退办")
        mNormalPresenter = NormalPresenter(this)
        ll_item.visibility = View.GONE
        initEvent()
        getData()
    }

    /**
     * 首次获取数据
     */
    private fun getData() {

    }

    private fun initEvent() {

//        //搜索框不自动缩小为一个搜索图标，而是match_parent
//        searchview.setIconifiedByDefault(false)
//        //显示搜索按钮
//        searchview.isSubmitButtonEnabled = false
        //设置提示hint
//        searchview.queryHint = "查询车辆号牌或整车编码"
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
            if (ParseQcodeUtil.isZcbmString(searchString!!.trim())) {
                val map = HashMap<String, String?>()
                map["zcbm"] = searchString.trim().toUpperCase()
                map["curPage"] = "1"
                map["pageSize"] = "1"
                map["glbm"] = UserInfo.GLBM
                showLoadingDialog()
                mNormalPresenter!!.doNetworkTask(map, Constants.TB_GET_LIST)
            } else {
                if (TextUtils.isEmpty(searchString.trim()) || !CphmUtils.checkXjValueCphm(searchString.trim().toUpperCase())) {
                    showToast("请正确输入车牌号")
                    return@setOnClickListener
                }
                val map = HashMap<String, String?>()
                map["hphm"] = searchString.trim().toUpperCase()
                map["curPage"] = "1"
                map["pageSize"] = "1"
                map["glbm"] = UserInfo.GLBM
                showLoadingDialog()
                mNormalPresenter!!.doNetworkTask(map, Constants.TB_GET_LIST)
            }
        }
        ll_item.setOnClickListener {
            val map = HashMap<String, String?>()
            map["lsh"] = mTbEnity!!.data.list[0].lsh!!
            mNormalPresenter!!.doNetworkTask(map, Constants.YW_GET_YWCZ_BIKE_DATA)
        }
        ll_back.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Activity.RESULT_OK){
            ll_item.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ll_item.visibility = View.GONE
    }

    override fun getLayout(): Int {
        return R.layout.activity_tuiban
    }
}
