package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.support.v7.widget.RecyclerView
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.SelectorAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.manager.MyRecycleManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_tb_edit.*
import kotlinx.android.synthetic.main.bottom_button.*

class YwTbEditActivity: BaseActivity(), SelectorAdapter.CheckState,NormalView {

    private var mNormalPresenter : NormalPresenter?=null
    private var mList:ArrayList<String> ?= null
    var yyList = ArrayList<String>() //业务原因list
    private var ll: RecyclerView.LayoutManager? = null
    var adapter: SelectorAdapter? = null


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
//        startActivity(Intent(this,RemindCommonActivity::class.java))
        dismissLoadingDialog()
        showToast("退办成功")
        val intent= Intent(this, YwTBSearchActivity::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP  //致于栈顶
        startActivity(intent)
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
        yyList.add("原因1")
        yyList.add("原因2")
        yyList.add("原因3")
        yyList.add("原因4")
    }

    private fun bindEvent() {
        bt_next.setOnClickListener {
            showLoadingDialog()
            var resultyy = StringBuffer()
            val map = HashMap<String,String>()
            map["lsh"] = Yw3CzActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.lsh
            map["xh"] = Yw3CzActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.xh
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
        }
        ed_tbbz.filters = arrayOf(inputFilter)
    }

    override fun getLayout(): Int {
        return R.layout.activity_tb_edit
    }

}
