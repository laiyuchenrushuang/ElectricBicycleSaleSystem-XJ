package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.YwFhAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.FHEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.FHUtil
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_yw_check.*
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.recyclerview.*


/**
 * Created by ly on 2019/10/21 13:55
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class YWCheckActivity : BaseActivity(), NormalView, YwFhAdapter.onItemListener {


    private var mNormalPresenter: NormalPresenter? = null

    private var adapter: YwFhAdapter? = null
    private var ll: LinearLayoutManager? = null
    private var typeSelectYwztPopup: PopupWindow? = null  //业务类型
    private var typeSelectFhztPopup: PopupWindow? = null //复核状态
    private var mTypeLv: ListView? = null
    private var testDataAdapter: ArrayAdapter<String>? = null

    var mData = ArrayList<FHEnity.Data.FHList>()

    private var page = 1
    private var position = 0

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        try {
            if (Constants.FH_GET_LIST.equals(commonResponse.getUrl())) {
                var enity = GsonUtils.gson(commonResponse.getResponseString(), FHEnity::class.java)
                if (page == 1) {
                    if (null == enity.data || null == enity.data.list || enity.data.list.size == 0) {
                        mData.clear()
                        runOnUiThread {  adapter!!.setData(mData) }
                        showToast("获取列表信息为空")
                        return
                    }
                    mData.clear()
                    mData = FHUtil.getSortList(enity.data.list)  //复核顺序 以复核时间排序
                    runOnUiThread{ adapter!!.setData(mData) }
                } else if (page > 1 && Constants.FH_GET_LIST.equals(commonResponse.getUrl())) {
                    shuaxin.finishLoadmore()
                    mData.addAll(FHUtil.getSortList(enity.data.list))
                    runOnUiThread{ adapter!!.setData(mData) }
                }
            }
            if (Constants.YW_GET_YWCZ_BIKE_DATA.equals(commonResponse.getUrl())) {
                val mAllBikeMsgEnity = GsonUtils.gson(commonResponse.getResponseString(), AllBikeMsgEnity::class.java)
                intent.setClass(this, Yw3CzActivity::class.java)
                intent.putExtra(Constants.UI_TYPE, "0")
                showLog("[BZ] = "+mData[position].fhbz)
                intent.putExtra("fhr", mData[position].fhr)
                intent.putExtra("fhzt", mData[position].fhbj)
                intent.putExtra("fhyy", mData[position].btgyy)
                intent.putExtra("fhsj", mData[position].fhsj.toString())
                intent.putExtra("fhbz", mData[position].fhbz)
                intent.putExtra("all_data",mAllBikeMsgEnity)
                startActivity(intent)
                return
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
        shuaxin.finishLoadmore()
    }

    override fun initView() {
        setPageTitle("业务复核")
        iv_right.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.search_icon))
        mNormalPresenter = NormalPresenter(this)
        bindEvent()
        initRecycleView()
        adapter!!.setListener(this)
        getData()
    }

    private fun initRecycleView() {

        ll = LinearLayoutManager(this)
        m_recycler_view!!.layoutManager = ll
        adapter = YwFhAdapter(this, mData)
        m_recycler_view.adapter = adapter

        shuaxin.isEnableRefresh = false //取消下拉刷新
    }

    private fun bindEvent() {
        ll_ywlx.setOnClickListener {
            initSelectYwztPopup()
            if (typeSelectYwztPopup != null && !typeSelectYwztPopup!!.isShowing) {
                typeSelectYwztPopup!!.animationStyle = R.style.pop_animation
                typeSelectYwztPopup!!.showAsDropDown(tv_ywlx)
            }
        }
        ll_fhzt.setOnClickListener {
            initSelectFhztPopup()
            if (typeSelectFhztPopup != null && !typeSelectFhztPopup!!.isShowing) {
                typeSelectFhztPopup!!.animationStyle = R.style.pop_animation
                typeSelectFhztPopup!!.showAsDropDown(tv_fhzt)
            }
        }

        iv_right.setOnClickListener {
            startActivity(Intent(this, YwFhSearchActivity::class.java))
        }

        shuaxin.setOnLoadmoreListener {
            page++
            getData()
        }
    }

    private fun getData() {
        showLoadingDialog()
        val map = HashMap<String, String?>()
        map["curPage"] = "" + page
        map["pageSize"] = "10"
        map["djbm"] = UserInfo.GLBM
        map["ywlx"] = if (getString(R.string.all).equals(tv_ywlx.text.toString()) || "业务类型".equals(tv_ywlx.text.toString())) "" else tv_ywlx.text.toString().split(":")[0]
        map["fhbj"] = if (getString(R.string.all).equals(tv_fhzt.text.toString()) || "复核状态".equals(tv_fhzt.text.toString())) "" else tv_fhzt.text.toString().split(":")[0]
        mNormalPresenter!!.doNetworkTask(map, Constants.FH_GET_LIST)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initSelectYwztPopup() {
        val listData = ArrayList<String>()
        listData.add(this.getString(R.string.all))
        val ywList = CodeTableSQLiteUtils.queryByDMLB(Constants.YWLX)
        for (db in ywList) {
            val dmz = db.dmz
            val dmsm1 = db.dmsm1
            if (Constants.A.equals(dmz) || Constants.B.equals(dmz) || Constants.D.equals(dmz) || Constants.G.equals(dmz) || Constants.K.equals(dmz) || Constants.I.equals(dmz)) {
                listData.add(dmz + ":" + dmsm1)
            }
        }

        mTypeLv = ListView(this)
        mTypeLv!!.background = ContextCompat.getDrawable(this, R.color.white)
        // 设置适配器
        testDataAdapter = ArrayAdapter(this, R.layout.popup_text_item, listData)
        mTypeLv!!.adapter = testDataAdapter
        //mTypeLv!!.addFooterView(TextView(this))
        mTypeLv!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val value = listData[position]
            // 把选择的数据展示对应的TextView上
            tv_ywlx.text = value
            // 选择完后关闭popup窗口
            typeSelectYwztPopup!!.dismiss()
            searchList(tv_ywlx.text.toString(), tv_fhzt.text.toString())
        }
        typeSelectYwztPopup = PopupWindow(mTypeLv, ll_state.width, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        typeSelectYwztPopup!!.setBackgroundDrawable(ContextCompat.getDrawable(this,R.color.white))
        typeSelectYwztPopup!!.isFocusable = false
        typeSelectYwztPopup!!.isOutsideTouchable = true
        typeSelectYwztPopup!!.setOnDismissListener {
            // 关闭popup窗口
            typeSelectYwztPopup!!.dismiss()
        }
    }

    private fun searchList(ywlx: String, fhzt: String) {
        showLoadingDialog()
        val map = HashMap<String, String?>()
        mData.clear()
        map["curPage"] = "1"
        map["pageSize"] = "10"
        map["djbm"] = UserInfo.GLBM
        map["ywlx"] = if (getString(R.string.all).equals(ywlx) || "业务类型".equals(tv_ywlx.text.toString())) "" else ywlx.split(":")[0]
        //复核状态 有时为null的情况
        map["fhbj"] = if (getString(R.string.all).equals(fhzt) || "复核状态".equals(tv_fhzt.text.toString())) "" else if ("".equals(fhzt)) "0" else fhzt.split(":")[0]

        mNormalPresenter!!.doNetworkTask(map, Constants.FH_GET_LIST)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initSelectFhztPopup() {
        val listData = ArrayList<String>()
        listData.add(this.getString(R.string.all))
        listData.add("0" + ":" + "待复核")  //0-待复核，1-复核通过，2-复核未通过
        listData.add("1" + ":" + "复核通过")
        listData.add("2" + ":" + "复核未通过")
        mTypeLv = ListView(this)
        mTypeLv!!.background = ContextCompat.getDrawable(this, R.color.white)
        // 设置适配器
        testDataAdapter = ArrayAdapter(this, R.layout.popup_text_item, listData)
        mTypeLv!!.adapter = testDataAdapter
        //mTypeLv!!.addFooterView(TextView(this))
        mTypeLv!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val value = listData[position]
            // 把选择的数据展示对应的TextView上
            tv_fhzt.text = value
            // 选择完后关闭popup窗口
            typeSelectFhztPopup!!.dismiss()
            searchList(tv_ywlx.text.toString(), tv_fhzt.text.toString())
        }
        typeSelectFhztPopup = PopupWindow(mTypeLv, ll_state.width, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        typeSelectFhztPopup!!.setBackgroundDrawable(ContextCompat.getDrawable(this,R.color.white))
        typeSelectFhztPopup!!.isFocusable = false
        typeSelectFhztPopup!!.isOutsideTouchable = true
        typeSelectFhztPopup!!.setOnDismissListener {
            // 关闭popup窗口
            typeSelectFhztPopup!!.dismiss()
        }
    }

    override fun itemClick(zcbm: String?, ywlx: String?,lsh: String?,position: Int) {
        showLoadingDialog()
        this.position = position
        val map = HashMap<String, String?>()
        map["lsh"] = lsh!!
        mNormalPresenter!!.doNetworkTask(map, Constants.YW_GET_YWCZ_BIKE_DATA)
    }

    override fun getLayout(): Int {
        return R.layout.activity_yw_check
    }

}