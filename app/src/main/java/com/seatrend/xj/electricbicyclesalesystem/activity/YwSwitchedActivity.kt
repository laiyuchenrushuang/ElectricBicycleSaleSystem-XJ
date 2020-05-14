package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_switch.*
import kotlinx.android.synthetic.main.bottom_button.*

/**
 * Created by ly on 2020/5/7 10:20
 */
class YwSwitchedActivity : BaseActivity(), NormalView {

    private var mNormalPresenter: NormalPresenter? = null
    private var data: AllBikeMsgEnity? = null
    private var changed1 = false
    private var changed2 = false
    private var changed3 = false

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()

        if (Constants.SYSTEM_PRODUCT_HPHM.equals(commonResponse.getUrl())) {
            val enity = GsonUtils.gson(commonResponse.responseString, HpHmEnity::class.java)
            if (!ObjectNullUtil.checknull(enity.data)) {
                showToast("号牌号码获取为空！")
                return
            }
            runOnUiThread {
                et_cphm.text = enity.data
                btn_hqhphm.visibility = View.GONE
            }
        }

        if (Constants.YW_ADD_REGISTER_DATA.equals(commonResponse.getUrl())) {
            val enity = GsonUtils.gson(commonResponse.getResponseString(), DjLshEnity::class.java)
            if (enity.data != null) {
                CollectPhotoActivity.mLsh = enity.data.lsh
                CollectPhotoActivity.mXh = enity.data.xh
            }

            if (enity.data.photo != null && enity.data.photo.size > 0) {
                val bundle = Bundle()
                bundle.putParcelable("photo_list", enity)
                intent.putExtras(bundle)
            }

            intent.putExtra("syr", ed_syr_xm.text.toString())
            intent.putExtra("sfz", ed_syr_sfz.text.toString())
            intent.putExtra("hphm", if (TextUtils.isEmpty(data!!.data.fjdcBusiness.cph)) et_cphm.text.toString().toUpperCase() else data!!.data.fjdcBusiness.cph)
            intent.setClass(this, CollectPhotoActivity::class.java)
            startActivity(intent)
            InsuranceActivity.dzpzFlag = rb_zzxsz_ok.isChecked
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("转入业务")
        mNormalPresenter = NormalPresenter(this)
        data = intent.getSerializableExtra("all_data") as AllBikeMsgEnity
        initData()
        bindEvent()
    }

    private fun bindEvent() {

        rb_zzxsz_ok.isChecked = true
        rb_lqfs_ok.isChecked = true
        CheckBoxUtils.setListenerAndView(rb_zzxsz_ok, rb_zzxsz_no, ll_lqfs, ll_yjlq)
        CheckBoxUtils.setListenerAndView(rb_lqfs_ok, rb_lqfs_no, ll_yjlq)

        iv_syr_scan.setOnClickListener {
            showIcCardReadModeDialog(Constants.SFZ_SYR)
        }

        iv_dlr_scan.setOnClickListener {
            showIcCardReadModeDialog(Constants.SFZ_DLR)
        }

        iv_yj_scan.setOnClickListener {
            showIcCardReadModeDialog(Constants.SFZ_YJ)
        }


        bt_next.setOnClickListener {
            //进行人脸识别

            //getFaceCamera(Constants.FACE)
            postHttpRequest()
        }


        btn_hqhphm.setOnClickListener {
            showLoadingDialog()
            val map = HashMap<String, String>()
            map["lybm"] = UserInfo.GLBM
            mNormalPresenter!!.doNetworkTask(map, Constants.SYSTEM_PRODUCT_HPHM)
        }

        sp_syr_qh1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var provinceDmz = sp_syr_qh1.selectedItem.toString()
                startThreadUpdateSp(provinceDmz, sp_syr_qh2)
            }
        }
        sp_yj_qh1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var provinceDmz = sp_yj_qh1.selectedItem.toString()
                startThreadUpdateSp(provinceDmz, sp_yj_qh2)
            }
        }
        sp_syr_yj_qh1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var provinceDmz = sp_syr_yj_qh1.selectedItem.toString()
                startThreadUpdateSp(provinceDmz, sp_syr_yj_qh2)
            }
        }
    }

    private fun postHttpRequest() {
        try {
            if (!SpinnerUtil.checkSpinnerValuable(sp_syxz, sp_syyt, sp_syr_sfz, sp_syr_qh1)) {
                showToast("网络同步信息失败，请先设置界面同步代码")
                return
            }

            if (ll_cphm.visibility == View.VISIBLE && !CheckEditTxetUtils.checkEditextValuable(et_cphm)) {
                showToast("请获取号牌号码")
                return
            }
            if (!CheckEditTxetUtils.checkEditextValuable(ed_syr_sfz, ed_syr_xm, et_syr_lxdh, et_syr_xxdz, et_syr_yj_xxdz, et_syr_yj_yzbm)) {
                showToast("请填写完成所有人信息")
                return
            }

            if ("A" == sp_syr_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_syr_sfz.text.toString())) {
                showToast("请正确填写所有人身份证信息")
                return
            }
            if (!TextUtils.isEmpty(et_syr_yxdz.text.toString()) && !StringUtils.isEmailAddress(et_syr_yxdz.text.toString())) {
                showToast("请正确填写邮箱信息")
                return
            }
            if (!StringUtils.isPhoneNumber(et_syr_lxdh.text.toString())) {
                showToast("请正确填写手机信息")
                return
            }
            if (!CheckUtil.isYzbmCorrect(et_syr_yj_yzbm.text.toString())) {
                showToast("请正确填写邮政编码")
                return
            }
            val enity = data!!.data.fjdcBusiness

            val lsh = data!!.data.checkData.lsh
            val zcbm = data!!.data.checkData.zcbm
            val ywlx = data!!.data.checkData.ywlx
            val xh = data!!.data.checkData.xh

            enity.lsh = lsh
            enity.xh = xh
            enity.zcbm = zcbm
            enity.cph = et_cphm.text.toString()  //不为空 我都不想传
            enity.blr = UserInfo.XM
            enity.ywlx = ywlx //业务类型
            enity.glbm = UserInfo.GLBM //管理部门
            enity.fzjg = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG//发证机关
            enity.cyrsfzmhm = UserInfo.SFZMHM//查验人身份证明号码
            enity.czpt = Constants.CZPT //查验平台
            enity.sqfs = "0"//申请方式  0所有人 1 代理人
            //1
            enity.syxz = sp_syxz.selectedItem.toString().split(":")[0]
            enity.clyt = sp_syyt.selectedItem.toString().split(":")[0]
            //2
            enity.sfzmmc = sp_syr_sfz.selectedItem.toString().split(":")[0]
            enity.sfzmhm = ed_syr_sfz.text.toString()
            enity.syrmc = ed_syr_xm.text.toString()
            enity.lxdh = et_syr_lxdh.text.toString()
            enity.djxzqh = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, sp_syr_qh2.selectedItem.toString())
            enity.djxxdz = et_syr_xxdz.text.toString()
            enity.lxdzxzqh = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, sp_syr_yj_qh2.selectedItem.toString())
            enity.lxxxdz = et_syr_yj_xxdz.text.toString()
            enity.yzbm = et_syr_yj_yzbm.text.toString()
            enity.dzyx = et_syr_yxdz.text.toString()
            //3 代理人
            if(ObjectNullUtil.checknull(ed_dlr_sfz.text.toString()) || ObjectNullUtil.checknull(ed_dlr_xm.text.toString())||ObjectNullUtil.checknull(et_dlr_lxdh.text.toString())){

                if ("A" == sp_dlr_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_dlr_sfz.text.toString())) {
                    showToast("请正确填写代理人身份证信息")
                    return
                }
                if (ed_syr_sfz.text.toString() == ed_dlr_sfz.text.toString()) {
                    showToast("代理人和所有人的身份证信息是一样")
                    return
                }
                //只要不是全写了的 就提示写全
                if (!ObjectNullUtil.checknull( ed_dlr_xm.text.toString())){
                    showToast("请完善代理人姓名")
                    return
                }
                if (!ObjectNullUtil.checknull(et_dlr_lxdh.text.toString()) || !StringUtils.isPhoneNumber(et_dlr_lxdh.text.toString())){
                    showToast("请正确输入代理人联系电话")
                    return
                }

                enity.dlrsfzmlx = sp_dlr_sfz.selectedItem.toString().split(":")[0]
                enity.dlrsfzmhm = ed_dlr_sfz.text.toString()
                enity.dlrxm = ed_dlr_xm.text.toString()
                enity.dlrlxdh = et_dlr_lxdh.text.toString()
                enity.sqfs = "1"//申请方式  0所有人 1 代理人
            }else{
                enity.dlrsfzmlx = ""
                enity.dlrsfzmhm = ""
                enity.dlrxm = ""
                enity.dlrlxdh = ""
            }

            //4

            if (!"0".equals(UserInfo.GlobalParameter.LQBJ)) {
                enity.zzxsz = if (rb_zzxsz_ok.isChecked) "1" else "2" // 纸质行驶证 1-需要，2-不需要
                enity.lqfs = if (rb_lqfs_ok.isChecked) "1" else "2" // 领取 1-现场，2-不邮寄
                if (rb_zzxsz_ok.isChecked && !rb_lqfs_ok.isChecked) {  // 邮寄领取
                    if (!CheckEditTxetUtils.checkEditextValuable(ed_yj_sfz, ed_yj_xm, et_yj_lxdh, et_yj_xxdz, et_yj_yzbm)) {
                        showToast("请填写完成邮寄信息")
                        return
                    }

                    if ("A" == sp_yj_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_yj_sfz.text.toString())) {
                        showToast("请正确填写邮寄身份证信息")
                        return
                    }
                    if (!StringUtils.isPhoneNumber(et_yj_lxdh.text.toString())) {
                        showToast("请正确填写邮寄手机信息")
                        return
                    }
                    enity.sjrsfzmlx = sp_yj_sfz.selectedItem.toString().split(":")[0]
                    enity.sjrsfzmhm = ed_yj_sfz.text.toString()
                    enity.sjrxm = ed_yj_xm.text.toString()
                    enity.sjrlxdh = et_yj_lxdh.text.toString()
                    enity.sjryjxzqh = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, sp_yj_qh2.selectedItem.toString())
                    enity.sjryjxxdz = et_yj_xxdz.text.toString()
                    enity.sjryzbm = et_yj_yzbm.text.toString()
                }
            }

            showLoadingDialog()
            val jsonstr = GsonUtils.toJson(enity)
            showLog("result [ZR] = " + jsonstr)
            mNormalPresenter!!.doJsonPost(jsonstr, Constants.YW_ADD_REGISTER_DATA)
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(e.message.toString())
        }
    }

    private fun startThreadUpdateSp(dmsm: String, spinner: Spinner?) {
        ThreadPoolManager.instance.execute(Runnable {
            try {
                val dmz = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, dmsm)
                var dataList = QHUtils.getAllOneLevelCitys(dmz)
                showLog(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, dmz))
                runOnUiThread {
                    SpinnerUtil.setPinnerQHData(this@YwSwitchedActivity, dmz, dataList, spinner, mHandler)
                }
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        })
    }

    var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                SpinnerUtil.INSERT_DATA -> {
                    insertData(msg.obj)
                }
            }
        }
    }

    private fun insertData(obj: Any) {
        val enity = data!!.data.fjdcBusiness
        when (obj) {
            sp_syr_qh2 -> {
                if (null == enity.djxzqh || changed1) {
                    return
                }
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.djxzqh), sp_syr_qh2)
                changed1 = true
            }
            sp_syr_yj_qh2 -> {
                if (null == enity.lxdzxzqh || changed2) {
                    return
                }
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.lxdzxzqh), sp_syr_yj_qh2)
                changed2 = true
            }
            sp_yj_qh2 -> {
                if (null == enity.sjryjxzqh || changed3) {
                    return
                }
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.sjryjxzqh), sp_yj_qh2)
                changed3 = true
            }
        }
    }

    private fun initData() {
        bt_next.text = "下一步"
        if ("0" == UserInfo.GlobalParameter.LQBJ) {
            ll_yjxx.visibility = View.GONE
        } else {
            ll_yjxx.visibility = View.VISIBLE
        }
        initSpData()
        getData() //再次注册，数据自动填充
    }

    private fun getData() {

        try {
            if (Constants.ZR_IA == data!!.data.fjdcBusiness.ywyy) {
                tv_ywyy.text = "转移出辖区"
            } else {
                tv_ywyy.text = "变更转入"
            }


            tv_hpzl.text = CllxUtils.getCllxDMSM(data!!.data.fjdcBusiness.cllx)
            tv_cph.text = data!!.data.fjdcBusiness.cph

            tv_zcbm.text = data!!.data.fjdcBusiness.zcbm
            tv_djrq.text = StringUtils.longToStringDataNoHour(data!!.data.fjdcBusiness.djrq.toLong())
            tv_djjg.text = data!!.data.fjdcBusiness.fzjg
            OtherUtils.setSpinnerToDmz(data!!.data.fjdcBusiness.syxz, sp_syxz)
            OtherUtils.setSpinnerToDmz(data!!.data.fjdcBusiness.clyt, sp_syyt)
            tv_syxz.text = sp_syxz.selectedItem.toString().split(":")[1]

            OtherUtils.setSpinnerToDmz(data!!.data.fjdcBusiness.sfzmmc, sp_syr_sfz)
            ed_syr_sfz.setText(data!!.data.fjdcBusiness.sfzmhm)
            ed_syr_xm.setText(data!!.data.fjdcBusiness.syrmc)
            et_syr_lxdh.setText(data!!.data.fjdcBusiness.lxdh)
            et_syr_xxdz.setText(data!!.data.fjdcBusiness.djxxdz)
            et_syr_yj_xxdz.setText(data!!.data.fjdcBusiness.lxxxdz)
            et_syr_yj_yzbm.setText(data!!.data.fjdcBusiness.yzbm)
            et_syr_yxdz.setText(data!!.data.fjdcBusiness.dzyx)

            OtherUtils.setSpinnerToDmz(data!!.data.fjdcBusiness.dlrsfzmlx, sp_dlr_sfz)
            ed_dlr_sfz.setText(data!!.data.fjdcBusiness.dlrsfzmhm)
            ed_dlr_xm.setText(data!!.data.fjdcBusiness.dlrxm)
            et_dlr_lxdh.setText(data!!.data.fjdcBusiness.dlrlxdh)

            OtherUtils.setSpinnerToDmz(data!!.data.fjdcBusiness.sjrsfzmlx, sp_yj_sfz)
            ed_yj_sfz.setText(data!!.data.fjdcBusiness.sjrsfzmhm)
            ed_yj_xm.setText(data!!.data.fjdcBusiness.sjrxm)
            et_yj_lxdh.setText(data!!.data.fjdcBusiness.sjrlxdh)
            et_yj_yzbm.setText(data!!.data.fjdcBusiness.yzbm)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    private fun initSpData() {
        SpinnerUtil.setPinnerData(this, Constants.SYXZ, sp_syxz)
        SpinnerUtil.setPinnerData(this, Constants.CLYT, sp_syyt)

        //省的设置
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_syr_qh1, if (null == data!!.data.fjdcBusiness.djxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.djxzqh)) null else data!!.data.fjdcBusiness.djxzqh.substring(0, 2) + "0000")
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_yj_qh1, if (null == data!!.data.fjdcBusiness.sjryjxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.sjryjxzqh)) null else data!!.data.fjdcBusiness.sjryjxzqh.substring(0, 2) + "0000")
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_syr_yj_qh1, if (null == data!!.data.fjdcBusiness.lxdzxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.lxdzxzqh)) null else data!!.data.fjdcBusiness.lxdzxzqh.substring(0, 2) + "0000")

        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_syr_sfz)
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_dlr_sfz)
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_yj_sfz)
    }

    override fun getLayout(): Int {
        return R.layout.activity_switch
    }
}