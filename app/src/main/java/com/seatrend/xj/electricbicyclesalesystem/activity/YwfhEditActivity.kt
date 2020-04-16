package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.SelectorAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.ReasonEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.manager.AppManager
import com.seatrend.xj.electricbicyclesalesystem.manager.MyRecycleManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.CheckBoxUtils
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_ywfh_edit.*
import kotlinx.android.synthetic.main.bottom_button.*


class YwfhEditActivity : BaseActivity(), SelectorAdapter.CheckState, NormalView {
    private var mNormalPresenter: NormalPresenter? = null
    private var mList = ArrayList<String>()
    var yyList = ArrayList<String>() //业务原因list
    private var ll: RecyclerView.LayoutManager? = null
    var adapter: SelectorAdapter? = null
    private var data : AllBikeMsgEnity?=null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()

        if (Constants.REASON_LIST == commonResponse.url) {
            yyList.clear()
            val entity = GsonUtils.gson(commonResponse.responseString, ReasonEntity::class.java)
            if (entity == null || entity.data == null || entity.data.size < 1) {
                showToast("原因列表为空")
                return
            }

            entity.data.forEach {
                if ("1" == it.lx) {  // 复核
                    yyList.add(it.ly)
                }
            }
            runOnUiThread {
                adapter!!.notifyDataSetChanged()
            }


        }

        if (Constants.FH_COMMIT.equals(commonResponse.getUrl())) {

            if ("1" == UserInfo.GlobalParameter.SFBJ && !rb_fh_no.isChecked  ) {//不通过不收费
                val intent = Intent(this, PayActivity::class.java)
                data!!.data.fjdcBusiness

                intent.putExtra("syr", data!!.data.fjdcBusiness.syrmc)
                intent.putExtra("sfz", data!!.data.fjdcBusiness.sfzmhm)
                intent.putExtra("hphm", data!!.data.fjdcBusiness.cph)
                startActivity(intent)
            } else {
                val intent = Intent(this, YWCheckActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP  //致于栈顶
                startActivity(intent)
                finish()
            }

        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun getCheckState(list: ArrayList<String>) {
        mList = list
    }


    override fun initView() {
        setPageTitle("复核信息编辑")
        bt_next.text = "保存"
        mNormalPresenter = NormalPresenter(this)
        getData()
        initRecycleview()
        bindEvent()
        //初始化
        rb_fh_ok.isChecked = true
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
        showLoadingDialog()
        mNormalPresenter!!.doNetworkTask(HashMap(), Constants.REASON_LIST)
    }

    private fun bindEvent() {
        CheckBoxUtils.setListenerAndView(rb_fh_ok, rb_fh_no, ll_yy)
        bt_next.setOnClickListener {
            if (rb_fh_ok.isChecked) {
                showLoadingDialog()
                Yw3CzActivity.fhzt = "1"
                val map = HashMap<String, String?>()
                map["fhbj"] = "1"    //复核标记 9-无需复核，0-待复核，1-复核通过，2-复核未通过
                map["lsh"] = data!!.data.fjdcBusiness.lsh
                map["xh"] = data!!.data.fjdcBusiness.xh
                map["fhr"] = UserInfo.XM
                map["fhbz"] = et_fhbz.text.toString()
                mNormalPresenter!!.doNetworkTask(map, Constants.FH_COMMIT)
            } else {
                if (mList!!.size < 1) {
                    showToast("请选择一个原因")
                    return@setOnClickListener
                }

                if (yyList.size >= 1 && mList!!.contains(yyList[yyList.size - 1]) && TextUtils.isEmpty(et_fhbz.text.toString())) {
                    showToast("选择备注原因需填写备注")
                    return@setOnClickListener
                }

                showLoadingDialog()
                Yw3CzActivity.fhzt = "2"



                val map = HashMap<String, String?>()
                map["fhbj"] = "2"    //复核标记 9-无需复核，0-待复核，1-复核通过，2-复核未通过
                map["lsh"] = data!!.data.fjdcBusiness.lsh
                map["xh"] = data!!.data.fjdcBusiness.xh
                map["fhr"] = UserInfo.XM

                map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
                map["czpt"] = Constants.CZPT //查验平台
                var resultyy = StringBuffer()
                if (mList != null && mList!!.size > 0) {
                    for (db in mList!!) {
                        if (db != mList!!.last()) {
                            resultyy.append(db + ",")
                        } else {
                            resultyy.append(db)
                        }
                    }
                }
                map["btgyy"] = resultyy.toString()
                map["fhbz"] = et_fhbz.text.toString()
                mNormalPresenter!!.doNetworkTask(map, Constants.FH_COMMIT)
            }
        }
        et_fhbz.filters = arrayOf(inputFilter)
    }

    override fun getLayout(): Int {
        return R.layout.activity_ywfh_edit
    }
}
