package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.support.v7.widget.RecyclerView
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.SelectorAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.manager.MyRecycleManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.OtherUtils
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_tb_edit.*
import kotlinx.android.synthetic.main.bottom_button.*

class YwTbEditActivity: BaseActivity(), SelectorAdapter.CheckState,NormalView {

    private var mNormalPresenter : NormalPresenter?=null
    private var mList:ArrayList<String> ?= null
    var yyList = ArrayList<String>() //业务原因list
    private var ll: RecyclerView.LayoutManager? = null
    var adapter: SelectorAdapter? = null
    private var data : AllBikeMsgEnity?=null
    var resultyy = StringBuffer()

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
//        startActivity(Intent(this,RemindCommonActivity::class.java))
        dismissLoadingDialog()
        intent.putExtra("tb_reason",resultyy.toString())
        intent.putExtra("tb_beizhu",ed_tbbz.text.toString())
        intent.putExtra("tb_time",StringUtils.longToStringData(System.currentTimeMillis()))

        setResult(Activity.RESULT_OK,intent)
        showToast("退办成功")
        finish()
    }


    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }


    override fun getCheckState(list: ArrayList<String>) {
        mList = list
    }

    override fun initView() {
        setPageTitle("退办详情")
        bt_next.setText("保存")
        mNormalPresenter = NormalPresenter(this)
        getData()
        initRecycleview()
        bindEvent()
    }

    private fun initRecycleview() {
        ll = MyRecycleManager(this)
        ll!!.isAutoMeasureEnabled = true
        m_recycler_view!!.layoutManager = ll
        adapter = SelectorAdapter(this, yyList)
        m_recycler_view.adapter = adapter
        adapter!!.setCheckListener(this)
    }

    private fun getData() {
        data = intent.getSerializableExtra("all_data") as AllBikeMsgEnity
        yyList.add("原因1")
        yyList.add("原因2")
        yyList.add("原因3")
        yyList.add("原因4")
    }

    private fun bindEvent() {
        bt_next.setOnClickListener {
            showLoadingDialog()
            try {
                val map = HashMap<String,String>()
                map["lsh"] = data!!.data.fjdcBusiness.lsh
                map["xh"] = data!!.data.fjdcBusiness.xh
                map["glbm"] = UserInfo.GLBM
                map["zt"] = "Q" // Q-已退办
                if(mList != null && mList!!.size >0){
                    for(db in mList!!){
                        if(db != mList!!.last()){
                            resultyy.append("$db,")
                        }else{
                            resultyy.append(db)
                        }
                    }
                }
                map["yy"] = resultyy.toString()
                map["bz"] = ed_tbbz.text.toString()
                mNormalPresenter!!.doNetworkTask(map, Constants.TB_COMMIT)
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }
        ed_tbbz.filters = arrayOf(inputFilter)
    }

    override fun getLayout(): Int {
        return R.layout.activity_tb_edit
    }

}
