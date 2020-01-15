package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.YwFhAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_fh_check.*

class YwFhSearchActivity : BaseActivity(), NormalView {

    private var mTextTouchListener: View.OnTouchListener? = null
    private var mFhdata: FHEnity? = null
    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.FH_GET_LIST == commonResponse.getUrl()) {
            var enity = GsonUtils.gson(commonResponse.getResponseString(), FHEnity::class.java)
            runOnUiThread {
                getData(enity)
                viewChangeByState()
            }
        }

        if (Constants.YW_GET_YWCZ_BIKE_DATA.equals(commonResponse.getUrl())) {
            val mAllBikeMsgEnity = GsonUtils.gson(commonResponse.getResponseString(), AllBikeMsgEnity::class.java)
            Yw3CzActivity.mAllBikeMsgEnity = mAllBikeMsgEnity
            var intent = Intent(this, Yw3CzActivity::class.java)
            Yw3CzActivity.fhzt = mFhdata!!.data.list[0].fhbj
            intent.putExtra(Constants.UI_TYPE, "0")
            intent.putExtra("fhr", mFhdata!!.data.list[0].fhr)
            intent.putExtra("fhzt", mFhdata!!.data.list[0].fhbj)
            intent.putExtra("fhyy", mFhdata!!.data.list[0].btgyy)
            intent.putExtra("fhsj", mFhdata!!.data.list[0].fhsj.toString())
            intent.putExtra("fhbz", mFhdata!!.data.list[0].fhbz)
            startActivity(intent)
        }
    }

    private fun getData(enity: FHEnity?) {
        if (null == enity || null == enity.data || null == enity.data.list || enity.data.list.size == 0) {
            ll_item.visibility = View.GONE
            showToast("获取列表信息为空")
            return
        }
        mFhdata = enity
        ll_item.visibility = View.VISIBLE
        tv_fh_syr!!.text = enity.data.list[0].syr
        tv_fh_zcbm!!.text = enity.data.list[0].zcbm
        tv_fh_cphm!!.text = enity.data.list[0].cph
        tv_fh_ywlx!!.text = DMZUtils.getDMSM(Constants.YWLX, enity.data.list[0].ywlx)
        tv_fh_ywry!!.text = enity.data.list[0].ywry
        tv_fh_slsj!!.text = StringUtils.longToStringData(enity.data.list[0].slsj)
//        tv_fh_fhzt!!.text = FHUtil.getDMSM(enity.data.list[0].fhbj)

        tv_fh_fhr!!.text = enity.data.list[0].fhr
        tv_fh_fhyj!!.text = if (TextUtils.isEmpty(enity.data.list[0].fhbz)) "/" else enity.data.list[0].fhbz
        tv_fh_fhsj!!.text = StringUtils.longToStringData(enity.data.list[0].fhsj)
        val fhzt = enity.data.list[0].fhbj
        if ("2".equals(fhzt)) {
            tv_fh_fhzt!!.setTextColor(Color.RED)
            tv_fh_yy!!.setTextColor(Color.RED)
            tv_fh_fhzt!!.text = FHUtil.getDMSM(fhzt)
            tv_fh_yy.visibility = View.VISIBLE
            tv_fh_yy.text = enity.data.list[0].btgyy
        } else if ("1".equals(fhzt)) {
            tv_fh_fhzt!!.setTextColor(Color.GREEN)
            ViewShowUtils.showGoneView(ll_yy)
            tv_fh_fhzt!!.text = FHUtil.getDMSM(fhzt)
        } else if ("0".equals(fhzt) || "".equals(fhzt)) {
            tv_fh_fhzt!!.setTextColor(Color.RED)
            tv_fh_fhzt!!.text = FHUtil.getDMSM(fhzt)
            ViewShowUtils.showGoneView(ll_yy, ll_fhr, ll_fhsj, ll_fhyj) //不显示
        }

    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
        runOnUiThread {
            ll_item.visibility = View.GONE
        }
    }

    private var mNormalPresenter: NormalPresenter? = null
    var adapter: YwFhAdapter? = null

    override fun initView() {
        setPageTitle("复核信息查询")
        ll_item.visibility = View.GONE
        mNormalPresenter = NormalPresenter(this)
        initEvent()
    }

    /**
     * 对于原因和意见的TextView控件显示监听
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun viewChangeByState() {
        tv_fh_yy!!.movementMethod = ScrollingMovementMethod.getInstance()
        tv_fh_fhyj!!.movementMethod = ScrollingMovementMethod.getInstance()
        tv_fh_yy!!.setOnTouchListener(mTextTouchListener)
        tv_fh_fhyj!!.setOnTouchListener(mTextTouchListener)
    }

    /**
     * 按钮初始化和事件绑定
     */
    private fun initEvent() {

        var searchString: String? = null

        //搜索框不自动缩小为一个搜索图标，而是match_parent
        searchview.setIconifiedByDefault(false)
        //显示搜索按钮
        searchview.isSubmitButtonEnabled = false
        //设置提示hint
        searchview.queryHint = "查询车辆号牌或整车编码"
        searchview!!.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(pSubmit: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(pChange: String?): Boolean {
                searchString = pChange
                return false
            }

        })
        iv_btn_search.setOnClickListener {
            if (TextUtils.isEmpty(searchString)) {
                showToast("搜索的内容为空")
                return@setOnClickListener
            }
            searchview.clearFocus()
            if(searchString!!.trim().length>10){
                val map = HashMap<String, String?>()
                map["zcbm"] = searchString!!.trim().toUpperCase()
                map["curPage"] = "1"
                map["pageSize"] = "1"
                map["djbm"] = UserInfo.GLBM
                LoadingDialog.getInstance().showLoadDialog(this)
                mNormalPresenter!!.doNetworkTask(map, Constants.FH_GET_LIST)
            }else{
                val map = HashMap<String, String?>()
                map["hphm"] = searchString!!.trim().toUpperCase()
                map["curPage"] = "1"
                map["pageSize"] = "1"
                map["djbm"] = UserInfo.GLBM
                LoadingDialog.getInstance().showLoadDialog(this)
                mNormalPresenter!!.doNetworkTask(map, Constants.FH_GET_LIST)
            }

        }
        ll_item.setOnClickListener {
            val map = HashMap<String, String?>()
            map["lsh"] = mFhdata!!.data.list[0].lsh
            mNormalPresenter!!.doNetworkTask(map, Constants.YW_GET_YWCZ_BIKE_DATA)
        }
        ll_back.setOnClickListener {
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_fh_check
    }

}
