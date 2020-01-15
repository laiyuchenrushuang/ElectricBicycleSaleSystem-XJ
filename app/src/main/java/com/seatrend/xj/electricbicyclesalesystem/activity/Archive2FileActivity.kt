package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.*
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.YwGdAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.ObjectNullUtil
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_dagd2.*
import kotlinx.android.synthetic.main.bottom_button.*
import kotlinx.android.synthetic.main.common_title.*

class Archive2FileActivity : BaseActivity(), YwGdAdapter.itemOnclickListener, BaseActivity.DialogListener, NormalView {

    private var plCommit = false
    private var count: Int = 0
    private var adapter: YwGdAdapter? = null
    private var ll: LinearLayoutManager? = null
    private var typeSelectYwztPopup: PopupWindow? = null  //业务状态
    private var mTypeLv: ListView? = null
    private var testDataAdapter: ArrayAdapter<String>? = null
    var mData = ArrayList<GDEnity.Data.GDList>()
    var itemPosition: Int = 0
    var page = 1
    var mNormalPresenter: NormalPresenter? = null


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.GD_GET_LIST.equals(commonResponse.url)) {
            var enity = GsonUtils.gson(commonResponse.getResponseString(), GDEnity::class.java)
            if (page == 1) {
                if (null == enity || null == enity.data || null == enity.data.list || enity.data.list.size == 0) {
                    mData.clear()
                    runOnUiThread {  adapter!!.setData(mData) }
                    showToast("获取列表信息为空")
                    return
                }
                mData.clear()
                mData = enity.data.list
                runOnUiThread {  adapter!!.setData(mData) }
            } else {
                shuaxin.finishLoadmore()
                mData.addAll(enity.data.list)
                runOnUiThread {  adapter!!.setData(mData) }
            }
        }

        if (Constants.GD_COMMIT.equals(commonResponse.url)) {
            runOnUiThread {
                if (!plCommit) {
                    showToast("归档成功")
                    mData.remove(mData.get(itemPosition))
                    adapter!!.notifyDataSetChanged()
                } else {
                    count++
                    if (count == mData.size) {
                        showToast("批量上传成功")
                        mData.clear()
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }

        }
        if (Constants.YW_GET_YWCZ_BIKE_DATA.equals(commonResponse.getUrl())) {
            val mAllBikeMsgEnity = GsonUtils.gson(commonResponse.getResponseString(), AllBikeMsgEnity::class.java)
            Yw3CzActivity.mAllBikeMsgEnity = mAllBikeMsgEnity
            var intent = Intent(this, Yw3CzActivity::class.java)
            intent.putExtra(Constants.UI_TYPE, "1")
            startActivityForResult(intent, 1)
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        shuaxin.finishLoadmore()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("档案归档")
        iv_right.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.search_icon))
        mNormalPresenter = NormalPresenter(this)
        bt_next.setText("批量归档")
        setListener(this)
        initRecycleView()
        bindEvent()
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun getData() {
        showLoadingDialog()
        val map = HashMap<String, String?>()
        map["curPage"] = "" + page
        map["pageSize"] = "10"
        map["gdbm"] = UserInfo.GLBM
        map["ywlx"] = if (getString(R.string.all).equals(tv_ywlx.text.toString()) || "业务类型".equals(tv_ywlx.text.toString())) "" else tv_ywlx.text.toString().split(":")[0]
        mNormalPresenter!!.doNetworkTask(map, Constants.GD_GET_LIST)
    }

    private fun initRecycleView() {
        m_recycler_view!!.layoutManager = LinearLayoutManager(this)
        adapter = YwGdAdapter(this, mData)
        adapter!!.setOnclickListener(this)
        m_recycler_view.adapter = adapter
    }

    private fun bindEvent() {
        shuaxin.isEnableRefresh = false //取消下拉刷新

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            header.tooltipText = ""
//        }
        iv_right.setOnClickListener {
            startActivity(Intent(this, A2FSearchActivity::class.java))
        }

        ll_ywlx.setOnClickListener {
            initSelectYwztPopup()
            if (typeSelectYwztPopup != null && !typeSelectYwztPopup!!.isShowing) {
                typeSelectYwztPopup!!.animationStyle = R.style.pop_animation
                typeSelectYwztPopup!!.showAsDropDown(tv_ywlx)
            }
        }
        bt_next.setOnClickListener {
            showTipDialog("操作提示", "即将进行批量归档操作，确认以继续。\n",0)
        }

        shuaxin.setOnLoadmoreListener {
            page++
            getData()
        }
    }

    private fun initSelectYwztPopup() {
        val listData = ArrayList<String>()
        listData.add(this.getString(R.string.all))
        val ywList = CodeTableSQLiteUtils.queryByDMLB(Constants.YWLX)
        for (db in ywList) {
            val dmz = db.dmz
            val dmsm1 = db.dmsm1
            if ("A".equals(dmz) || "B".equals(dmz) || "D".equals(dmz) || "G".equals(dmz) || "K".equals(dmz)) {
                listData.add(dmz + ":" + dmsm1)
            }
        }

        mTypeLv = ListView(this)
        mTypeLv!!.background = ContextCompat.getDrawable(this, R.color.white)
        // 设置适配器
        testDataAdapter = ArrayAdapter(this, R.layout.popup_text_item, listData)
        mTypeLv!!.adapter = testDataAdapter!!
        //mTypeLv!!.addFooterView(TextView(this))
        mTypeLv!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val value = listData[position]
            // 把选择的数据展示对应的TextView上
            tv_ywlx.text = value
            // 选择完后关闭popup窗口
            getData()
            mData.clear()
            typeSelectYwztPopup!!.dismiss()
        }
        typeSelectYwztPopup = PopupWindow(mTypeLv, ll_ywlx.width, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        typeSelectYwztPopup!!.isFocusable = true
        typeSelectYwztPopup!!.isOutsideTouchable = true
        typeSelectYwztPopup!!.setOnDismissListener {
            // 关闭popup窗口
            typeSelectYwztPopup!!.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            showToast("归档成功")
            mData.remove(mData.get(itemPosition))
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun itemOnclick(zcbm: String?, ywlx: String?, lsh: String?, position: Int) {
        showLoadingDialog()
        itemPosition = position
        val map = HashMap<String, String?>()
        map["lsh"] = lsh!!
        mNormalPresenter!!.doNetworkTask(map, Constants.YW_GET_YWCZ_BIKE_DATA)
    }

    override fun itemBtnOnclick(posion: Int) {
        plCommit = false
        val map = HashMap<String, String?>()
        map["lsh"] = mData[posion].lsh
        map["xh"] = mData[posion].xh
        map["glbm"] = UserInfo.GLBM
        map["zt"] = "E" // E-已归档
        mNormalPresenter!!.doNetworkTask(map, Constants.GD_COMMIT)
    }


    override fun tipDialogOKListener(flag:Int) {
        count = 0
        plCommit = true
        for (db in mData) {
            val map = HashMap<String, String?>()
            map["lsh"] = db.lsh
            map["xh"] = db.xh
            map["glbm"] = UserInfo.GLBM
            map["zt"] = "E" // E-已归档
            mNormalPresenter!!.doNetworkTask(map, Constants.GD_COMMIT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        itemPosition = 0
    }

    override fun getLayout(): Int {
        return R.layout.activity_dagd2
    }

}
