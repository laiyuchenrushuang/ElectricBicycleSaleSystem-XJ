package com.seatrend.xj.electricbicyclesalesystem.activity


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.YwFhAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.GDEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.CarHphmUtils
import com.seatrend.xj.electricbicyclesalesystem.util.FHUtil
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_gd_check.*

class A2FSearchActivity: BaseActivity(), NormalView {

    private var mNormalPresenter: NormalPresenter? = null
    private var mGdEnity: GDEnity?=null
    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {

        dismissLoadingDialog()
        if (Constants.GD_GET_LIST == commonResponse.getUrl()) {
            val enity = GsonUtils.gson(commonResponse.getResponseString(), GDEnity::class.java)

            runOnUiThread {
                getData(enity)
            }
        }
        if(Constants.GD_COMMIT == commonResponse.getUrl()){
            runOnUiThread {
                updateUI()
            }
        }

        if (Constants.YW_GET_YWCZ_BIKE_DATA == commonResponse.getUrl()) {
            val mAllBikeMsgEnity = GsonUtils.gson(commonResponse.getResponseString(), AllBikeMsgEnity::class.java)
            Yw3CzActivity.mAllBikeMsgEnity = mAllBikeMsgEnity
            val intent = Intent(this, Yw3CzActivity::class.java)
            intent.putExtra(Constants.UI_TYPE,"1")
            startActivityForResult(intent,1) //requestcode = 1
        }
    }

    private fun updateUI() {
        showToast("归档成功")

        val intent= Intent(this, Archive2FileActivity::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP  //致于栈顶
        startActivity(intent)
        finish()
    }

    private fun getData(enity: GDEnity?) {
        if (null == enity || null == enity.data || null == enity.data.list || enity.data.list.size == 0) {
            rl_item.visibility = View.GONE
            showToast("获取列表信息为空")
            return
        }
        mGdEnity = enity
        rl_item.visibility = View.VISIBLE
        tv_zcbm!!.text = enity.data.list[0].zcbm
        tv_cphm!!.text = enity.data.list[0].cph
        tv_fhzt!!.text = FHUtil.getDMSM(enity.data.list[0].fhbj)
        when {
            "1" == enity.data.list[0].fhbj -> tv_fhzt!!.setTextColor(Color.GREEN)
            "2" == enity.data.list[0].fhbj -> tv_fhzt!!.setTextColor(Color.RED)
            "0" == enity.data.list[0].fhbj -> tv_fhzt!!.setTextColor(Color.BLUE)
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
        runOnUiThread {
            rl_item.visibility = View.GONE
        }
    }

    var adapter: YwFhAdapter? = null

    override fun initView() {
        setPageTitle("归档信息查询")
        rl_item.visibility = View.GONE
        mNormalPresenter = NormalPresenter(this)
        initEvent()
    }

    /**
     * 按钮初始化和事件绑定
     */
    private fun initEvent() {

//        var searchString: String? = null
//
//        //搜索框不自动缩小为一个搜索图标，而是match_parent
//        searchview.setIconifiedByDefault(false)
//        //显示搜索按钮
//        searchview.isSubmitButtonEnabled = false
//        //设置提示hint
//        searchview.queryHint = "查询车辆号牌或整车编码"
//        searchview!!.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(pSubmit: String?): Boolean {
//                return false
//            }
//            override fun onQueryTextChange(pChange: String?): Boolean {
//                searchString = pChange
//                return false
//            }
//        })


        searchview.transformationMethod = CarHphmUtils.TransInformation()
        searchview.filters = arrayOf(inputFilter)

        iv_btn_search.setOnClickListener {

            var searchString: String? = searchview.text.toString()
            if (TextUtils.isEmpty(searchString)) {
                showToast("搜索的内容为空")
                return@setOnClickListener
            }
            searchview.clearFocus()
            if(searchString!!.trim().length>10){
                val map = HashMap<String, String?>()
                map["curPage"] = "1"
                map["pageSize"] = "1"
                map["gdbm"] = UserInfo.GLBM
                map["zcbm"] = searchString!!.trim().toUpperCase()
                LoadingDialog.getInstance().showLoadDialog(this)
                mNormalPresenter!!.doNetworkTask(map, Constants.GD_GET_LIST)
            }else{
                val map = HashMap<String, String?>()
                map["curPage"] = "1"
                map["pageSize"] = "1"
                map["gdbm"] = UserInfo.GLBM
                map["hphm"] = searchString!!.trim().toUpperCase()
                LoadingDialog.getInstance().showLoadDialog(this)
                mNormalPresenter!!.doNetworkTask(map, Constants.GD_GET_LIST)
            }

        }
        rl_item.setOnClickListener {
            val map = HashMap<String, String?>()
            map["lsh"] = mGdEnity!!.data.list[0].lsh!!
            mNormalPresenter!!.doNetworkTask(map, Constants.YW_GET_YWCZ_BIKE_DATA)
        }
        btn_gd.setOnClickListener {
            val map = HashMap<String,String>()
            map["lsh"] = Yw3CzActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.lsh
            map["xh"] = Yw3CzActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.xh
            map["glbm"] = UserInfo.GLBM
            map["zt"] = "E" // E-已归档
            mNormalPresenter!!.doNetworkTask(map, Constants.GD_COMMIT)
        }
        ll_back.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 1){
            rl_item.visibility = View.GONE
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_gd_check
    }

}