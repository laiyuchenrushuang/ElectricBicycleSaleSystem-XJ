package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
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
import kotlinx.android.synthetic.main.activity_replace.*
import kotlinx.android.synthetic.main.bottom_button.*

/**
 * Created by ly on 2019/9/30 12:13
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class YwReplaceActivity : BaseActivity(), NormalView {


    private var mxCphm: String? = null //新号牌号码
    private var moCphm: String? = null //原来的号牌号码
    private var headPhoto: ByteArray? = null//头像照片
    private var FACE_COMPARE_CODE: Int = 11
    private var mNormalPresenter: NormalPresenter? = null
    private var data : AllBikeMsgEnity?=null
    private var changed1 = false
    private var changed2 = false
    private var changed3 = false

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.YW_ADD_REGISTER_DATA.equals(commonResponse.getUrl())) {
            var enity = GsonUtils.gson(commonResponse.getResponseString(), DjLshEnity::class.java)
            if (enity.data != null) {
                CollectPhotoActivity.mLsh = enity.data.lsh
                CollectPhotoActivity.mXh = enity.data.xh
            }
            intent.putExtra("syr", ed_syr_xm.text.toString())
            intent.putExtra("sfz", ed_syr_sfz.text.toString())
            intent.putExtra("hphm", if (cb_hp_huan.isChecked || cb_hp_bu.isChecked) et_cphm.text.toString().toUpperCase() else data!!.data.checkData.cph)
            intent.setClass(this, AutographActivity::class.java)
            startActivity(intent)
            CollectPhotoActivity.ywlx =data!!.data.checkData.ywlx
            CollectPhotoActivity.photoEntranceFlag = Constants.CAR_BH
            CollectPhotoActivity.dzpzFlag = rb_zzxsz_ok.isChecked
        }

        if (Constants.SYSTEM_PRODUCT_HPHM.equals(commonResponse.getUrl())) {
            val enity = GsonUtils.gson(commonResponse.responseString, HpHmEnity::class.java)
            if (!ObjectNullUtil.checknull(enity.data)) {
                showToast("号牌号码获取为空！")
                return
            }
            runOnUiThread {
                et_cphm.text = enity.data
                mxCphm = enity.data
            }
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("补换号牌")
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

            getFaceCamera(Constants.FACE)
        }

        btn_hqhphm.setOnClickListener {
            showLoadingDialog()
            val map = HashMap<String, String>()
            map["lybm"] = UserInfo.GLBM
            mNormalPresenter!!.doNetworkTask(map, Constants.SYSTEM_PRODUCT_HPHM)
        }

        et_cphm.transformationMethod = CarHphmUtils.TransInformation()

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
        //限制名称只能输入中文和字母和数字
//        et_xsqy.filters = arrayOf(inputFilter)
        ed_syr_xm.filters = arrayOf(inputFilter)
        et_syr_yj_xxdz.filters = arrayOf(inputFilter)
        et_syr_xxdz.filters = arrayOf(inputFilter)
        ed_dlr_xm.filters = arrayOf(inputFilter)
        ed_yj_xm.filters = arrayOf(inputFilter)
        et_yj_xxdz.filters = arrayOf(inputFilter)
    }

    private fun startThreadUpdateSp(dmsm: String, spinner: Spinner?) {
        ThreadPoolManager.instance.execute(Runnable {
            val dmz = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, dmsm)
            var dataList = QHUtils.getAllOneLevelCitys(dmz)
            runOnUiThread {
                SpinnerUtil.setPinnerQHData(this@YwReplaceActivity, dmz, dataList, spinner, mHandler)
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
        val enity =data!!.data.fjdcBusiness
        when (obj) {
            sp_syr_qh2 -> {
                if (enity.djxzqh == null || "0" != enity.sfkyghhp || changed1) {
                    return
                }
                changed1 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.djxzqh), sp_syr_qh2)
            }
            sp_syr_yj_qh2 -> {
                if (enity.lxdzxzqh == null || "0" != enity.sfkyghhp || changed2) {
                    return
                }
                changed2 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.lxdzxzqh), sp_syr_yj_qh2)
            }
            sp_yj_qh2 -> {
                if (enity.sjryjxzqh == null || "0" != enity.sfkyghhp || changed3) {
                    return
                }
                changed3 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.sjryjxzqh), sp_yj_qh2)
            }
        }
    }

    private fun initData() {
        bt_next.text = "人证核验"
        try {
            initSpData()
            cb_xsz_huan.performClick()
            if ("0".equals(UserInfo.GlobalParameter.LQBJ)) {
                ll_yjxx.visibility = View.GONE
            } else {
                ll_yjxx.visibility = View.VISIBLE
            }

            if ("0".equals(data!!.data.fjdcBusiness.sfkyghhp)) {
                ViewShowUtils.showGoneView(btn_hqhphm)
            }
            getData()
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    private fun getData() {
        val enity =data!!.data.fjdcBusiness
        if (null == enity) {
            showToast("登记信息为空")
            return
        }
        try {

            OtherUtils.setSpinnerToDmz(enity.sfzmmc, sp_syr_sfz)
            ed_syr_sfz.setText(enity.sfzmhm)
            ed_syr_xm.setText(enity.syrmc)
            et_syr_lxdh.setText(enity.lxdh)
            et_syr_xxdz.setText(enity.djxxdz)
            et_syr_yj_xxdz.setText(enity.lxxxdz)
            et_syr_yj_yzbm.setText(enity.yzbm)
            et_syr_yxdz.setText(enity.dzyx)
            et_cphm.text = enity.cph
            moCphm = enity.cph
            OtherUtils.setSpinnerToDmz(enity.dlrsfzmlx, sp_dlr_sfz)
            ed_dlr_sfz.setText(enity.dlrsfzmhm)
            ed_dlr_xm.setText(enity.dlrxm)
            et_dlr_lxdh.setText(enity.dlrlxdh)

            OtherUtils.setSpinnerToDmz(enity.sjrsfzmlx, sp_yj_sfz)
            ed_yj_sfz.setText(enity.sjrsfzmhm)
            ed_yj_xm.setText(enity.sjrxm)
            et_yj_lxdh.setText(enity.sjrlxdh)
            et_yj_yzbm.setText(enity.yzbm)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }

    private fun initSpData() {

        //省的设置
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_syr_qh1, if (null ==data!!.data.fjdcBusiness.djxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.djxzqh)) null else data!!.data.fjdcBusiness.djxzqh.substring(0, 2) + "0000")
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_yj_qh1, if (null ==data!!.data.fjdcBusiness.sjryjxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.sjryjxzqh)) null else data!!.data.fjdcBusiness.sjryjxzqh.substring(0, 2) + "0000")
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_syr_yj_qh1, if (null ==data!!.data.fjdcBusiness.lxdzxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.lxdzxzqh)) null else data!!.data.fjdcBusiness.lxdzxzqh.substring(0, 2) + "0000")

        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_syr_sfz)
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_dlr_sfz)
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_yj_sfz)

        initBuHuanSp(cb_hp_huan, cb_hp_bu, sp_bh_hp)
        initBuHuanSp(cb_xsz_huan, cb_xsz_bu, sp_bh_xsz)
    }

    private fun initBuHuanSp(cb1: CheckBox, cb2: CheckBox, spinner: Spinner) {
        spinner.visibility = View.INVISIBLE
        cb1.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                cb1.isChecked = true
                cb2.isChecked = false
                initYYSp(2, spinner)
            }
            setBtnInvisible()
            if (cb1.isChecked || cb2.isChecked) {
                spinner.visibility = View.VISIBLE
            } else {
                spinner.visibility = View.INVISIBLE
            }
        }
        cb2.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                cb2.isChecked = true
                cb1.isChecked = false
                initYYSp(1, spinner)
            }
            setBtnInvisible()
            if (cb1.isChecked || cb2.isChecked) {
                spinner.visibility = View.VISIBLE
            } else {
                spinner.visibility = View.INVISIBLE
            }
        }
    }

    //车牌号是否需要新建
    private fun setBtnInvisible() {
        if (cb_hp_bu.isChecked || cb_hp_huan.isChecked) { //车牌号是否被点击
            btn_hqhphm.visibility = View.VISIBLE
            tv_hphm.text = "新号牌号码"
            et_cphm.text = mxCphm // 号牌号码的缓存

            if(TextUtils.isEmpty(mxCphm)){ //没有获取新号牌 就用旧号牌
                et_cphm.text = moCphm
            }

        } else {          //行驶证的
            btn_hqhphm.visibility = View.GONE
            tv_hphm.text = "号牌号码"
            et_cphm.text = moCphm // 号牌号码的缓存
        }
    }

    private fun initYYSp(flag: Int?, spiner: Spinner) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (flag) {
            1 -> {
                adapter.clear()
                adapter.add("丢失")
                adapter.add("灭失")
                spiner.adapter = adapter
            }
            2 -> {
                adapter.clear()
                adapter.add("损坏")
                adapter.add("正常换领")
                spiner.adapter = adapter
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ID_CARD_READ_CODE -> {
                    showToast("身份证已读取")
//                    data.getStringExtra(Constants.NAME)
//                    sfzhm = data.getStringExtra(Constants.NUMBER)
//                    data.getStringExtra(Constants.ADDRESS)
//                    headPhoto = data.getByteArrayExtra(Constants.PHOTO)
//
//                    if (null != headPhoto) {
//                        OtherUtils.goFaceComparePlugin(this, headPhoto, FACE_COMPARE_CODE)
//                    }
                }

                FACE_COMPARE_CODE -> {

                }

                //人脸返回
                Constants.FACE -> {
                    showLog(imgFaceFile!!.path)
                    postHttpRequest()
                }

                Constants.SFZ_SYR -> {
                    ed_syr_sfz.text = Editable.Factory.getInstance().newEditable(data!!.getStringExtra(Constants.NUMBER))
                    ed_syr_xm.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NAME))
                }
                Constants.SFZ_DLR -> {
                    ed_dlr_sfz.text = Editable.Factory.getInstance().newEditable(data!!.getStringExtra(Constants.NUMBER))
                    ed_dlr_xm.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NAME))
                }
                Constants.SFZ_YJ -> {
                    ed_yj_sfz.text = Editable.Factory.getInstance().newEditable(data!!.getStringExtra(Constants.NUMBER))
                    ed_yj_xm.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NAME))
                }

                //OCR
                Integer.toHexString(Constants.SFZ_SYR).toInt() -> {
                    showLoadingDialog()
                    ThreadPoolManager.instance.execute(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.path) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_syr_xm, ed_syr_sfz)
                    })
                }
                Integer.toHexString(Constants.SFZ_DLR).toInt() -> {
                    showLoadingDialog()
                    ThreadPoolManager.instance.execute(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.path) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_dlr_xm, ed_dlr_sfz)
                    })
                }
                Integer.toHexString(Constants.SFZ_YJ).toInt() -> {
                    showLoadingDialog()
                    ThreadPoolManager.instance.execute(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.path) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_yj_xm, ed_yj_sfz)
                    })
                }
            }
        }
    }

    private fun postHttpRequest() {
        try {
            if (!SpinnerUtil.checkSpinnerValuable(sp_syr_sfz, sp_syr_qh1)) {
                showToast("网络同步信息失败，请先设置界面同步代码")
                return
            }
            if (TextUtils.isEmpty(et_cphm.text.toString())) {
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

            val ywyy = StringBuffer()
            if (cb_xsz_huan.isChecked) {
                ywyy.append(cb_xsz_huan.text.toString() + "," + sp_bh_xsz.selectedItem.toString())
            }
            if (cb_xsz_bu.isChecked) {
                ywyy.append(cb_xsz_bu.text.toString() + "," + sp_bh_xsz.selectedItem.toString())
            }

            if (cb_hp_huan.isChecked) {
                if (cb_xsz_huan.isChecked || cb_xsz_bu.isChecked) {
                    ywyy.append("," + cb_hp_huan.text.toString() + "," + sp_bh_hp.selectedItem.toString())
                } else {
                    ywyy.append(cb_hp_huan.text.toString() + "," + sp_bh_hp.selectedItem.toString())
                }

            }
            if (cb_hp_bu.isChecked) {
                if (cb_xsz_huan.isChecked || cb_xsz_bu.isChecked) {
                    ywyy.append("," + cb_hp_bu.text.toString() + "," + sp_bh_hp.selectedItem.toString())
                } else {
                    ywyy.append(cb_hp_bu.text.toString() + "," + sp_bh_hp.selectedItem.toString())
                }
            }
            val enity =data!!.data.fjdcBusiness

            enity.blr = UserInfo.XM
            enity.ywlx = intent.getStringExtra("ywlx") //业务类型
            enity.glbm = UserInfo.GLBM //管理部门
            enity.ywyy = ywyy.toString()
            enity.fzjg = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG//发证机关
            enity.cyrsfzmhm = UserInfo.SFZMHM//查验人身份证明号码
            enity.czpt = Constants.CZPT //查验平台
            enity.sqfs = "0"//申请方式  0所有人 1 代理人
            //没做限制，可不填写
            if (CheckEditTxetUtils.checkEditextValuable(et_cphm)) {
//                if(!CphmUtils.checkXjValueCphm(et_cphm.text.toString().toUpperCase())){
//                    showToast("请获取车牌号信息")
//                    return
//                }
                enity.cph = et_cphm.text.toString().toUpperCase()
            } else {
                enity.cph =data!!.data.fjdcBusiness.cph
            }

            //1

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
            //3
            if (ObjectNullUtil.checknull(ed_dlr_sfz.text.toString(), ed_dlr_xm.text.toString(), et_dlr_lxdh.text.toString())) {
                if ("A" == sp_dlr_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_dlr_sfz.text.toString())) {
                    showToast("请正确填写代理人身份证信息")
                    return
                }
                if (ed_syr_sfz.text.toString() == ed_dlr_sfz.text.toString()) {
                    showToast("代理人和所有人的身份证信息是一样")
                    return
                }
                enity.dlrsfzmlx = sp_dlr_sfz.selectedItem.toString().split(":")[0]
                enity.dlrsfzmhm = ed_dlr_sfz.text.toString()
                enity.dlrxm = ed_dlr_xm.text.toString()
                enity.dlrlxdh = et_dlr_lxdh.text.toString()
                enity.sqfs = "1"//申请方式  0所有人 1 代理人
            }
            //4
            if (!"0".equals(UserInfo.GlobalParameter.LQBJ)) {
                enity.zzxsz = if (rb_zzxsz_ok.isChecked) "1" else "2" // 纸质行驶证 1-需要，2-不需要
                enity.lqfs = if (rb_lqfs_ok.isChecked) "1" else "2" // 纸质行驶证 1-需要，2-不需要
                if (!rb_lqfs_ok.isChecked) {  // 邮寄领取
                    if (!CheckEditTxetUtils.checkEditextValuable(ed_yj_sfz, ed_yj_xm, et_yj_lxdh, et_yj_xxdz, et_yj_yzbm)) {
                        showToast("请填写完成邮寄信息")
                        return
                    }
                    if (!StringUtils.isPhoneNumber(et_yj_lxdh.text.toString())) {
                        showToast("请正确填写邮寄手机信息")
                        return
                    }
                    if ("A" == sp_yj_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_yj_sfz.text.toString())) {
                        showToast("请正确填写邮寄身份证信息")
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
            showLog("[BH] result = " + GsonUtils.toJson(enity))
            showLoadingDialog()
            mNormalPresenter!!.doJsonPost(GsonUtils.toJson(enity), Constants.YW_ADD_REGISTER_DATA)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_replace
    }
}