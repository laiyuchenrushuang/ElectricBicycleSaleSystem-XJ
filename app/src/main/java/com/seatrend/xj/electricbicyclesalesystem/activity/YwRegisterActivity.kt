package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
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
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.bottom_button.*


class YwRegisterActivity : BaseActivity(), NormalView {

    private var headPhoto: ByteArray? = null//头像照片
    private var FACE_COMPARE_CODE: Int = 11
    private var mNormalPresenter: NormalPresenter? = null
    private var changed1 = false
    private var changed2 = false
    private var changed3 = false
    private var data: AllBikeMsgEnity? = null

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
            intent.putExtra("hphm", if (TextUtils.isEmpty(data!!.data.checkData.cph)) et_cphm.text.toString().toUpperCase() else data!!.data.checkData.cph)
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
        setPageTitle("业务登记")
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

        tv_hqrq.setOnClickListener {
            showTimeDialog(tv_hqrq)
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
        et_xsqy.filters = arrayOf(inputFilter)
        ed_syr_xm.filters = arrayOf(inputFilter)
        et_syr_yj_xxdz.filters = arrayOf(inputFilter)
        et_syr_xxdz.filters = arrayOf(inputFilter)
        ed_dlr_xm.filters = arrayOf(inputFilter)
        ed_yj_xm.filters = arrayOf(inputFilter)
        et_yj_xxdz.filters = arrayOf(inputFilter)

    }

    /**
     * 记录一下 怕忘了
     * 1. 省级的spinner是固定的，全国30多个，可以一次获取完，第二次进来去锁定，为什么可以，是自己构造了省级的代码值6500 [00]去找的
     * 2. 第二个spinner,以第一个spinner 去找代码值，为什么代码值 没弄在spinner的item里面（长度不够），然后找到省下的一级城市代码生成规律，拉所有一级城市池装在spinner中
     * 3.
     * @param dmsm  第一个spinner的选择内容
     * @param spinner  第二个spinner的对象
     *
     * 代码功能  （1）dmz 获取第一个spinner的代码值
     *          （2）dataList 获取第二个spinner的代码值集合
     *
     */
    private fun startThreadUpdateSp(dmsm: String, spinner: Spinner?) {
        ThreadPoolManager.instance.execute(Runnable {
            try {
                val dmz = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, dmsm)
                var dataList = QHUtils.getAllOneLevelCitys(dmz)
                showLog(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, dmz))
                runOnUiThread {
                    SpinnerUtil.setPinnerQHData(this@YwRegisterActivity, dmz, dataList, spinner, mHandler)
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
        try {
            bt_next.text = "下一步"
            var cphm = data!!.data.checkData.cph
            var cphm1 = data!!.data.fjdcBusiness.cph

            if (!TextUtils.isEmpty(cphm1)) {
                cphm = cphm1
            }

            initSpData()

            if (!TextUtils.isEmpty(cphm)) {
                ViewShowUtils.showVisibleView(ll_tv_cphm)
                ViewShowUtils.showGoneView(ll_cphm)
                tv_cph.text = cphm
            } else {
                ViewShowUtils.showGoneView(ll_tv_cphm)
                ViewShowUtils.showVisibleView(ll_cphm)
            }
            if ("0".equals(UserInfo.GlobalParameter.LQBJ)) {
                ll_yjxx.visibility = View.GONE
            } else {
                ll_yjxx.visibility = View.VISIBLE
            }

            getData() //再次注册，数据自动填充
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    private fun getData() {
        val enity = data!!.data.fjdcBusiness
        if (null == enity) {
            showToast("登记信息为空")
            return
        }
        try {
            OtherUtils.setSpinnerToDmz(enity.hdfs, sp_hqfs)
            OtherUtils.setSpinnerToDmz(enity.llzm, sp_llzm)
            OtherUtils.setSpinnerToDmz(enity.syxz, sp_syxz)
            OtherUtils.setSpinnerToDmz(enity.clyt, sp_syyt)
            tv_hqrq.text = StringUtils.longToStringDataNoHour(enity.hqrq)
            et_xsqy.setText(enity.xsqymc)

            OtherUtils.setSpinnerToDmz(enity.sfzmmc, sp_syr_sfz)
            ed_syr_sfz.setText(enity.sfzmhm)
            ed_syr_xm.setText(enity.syrmc)
            et_syr_lxdh.setText(enity.lxdh)
            et_syr_xxdz.setText(enity.djxxdz)
            et_syr_yj_xxdz.setText(enity.lxxxdz)
            et_syr_yj_yzbm.setText(enity.yzbm)
            et_syr_yxdz.setText(enity.dzyx)

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
        SpinnerUtil.setPinnerData(this, Constants.HDFS, sp_hqfs)
        SpinnerUtil.setPinnerData(this, Constants.LLZM, sp_llzm)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ID_CARD_READ_CODE -> {
                    showToast("身份证已读取")
//                    data.getStringExtra(Constants.NAME)
//                    ed_syr_sfz.setText(data!!.getStringExtra(Constants.NUMBER))
//                    data.getStringExtra(Constants.ADDRESS)
//                    headPhoto = data!!.getByteArrayExtra(Constants.PHOTO)
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
            if (!SpinnerUtil.checkSpinnerValuable(sp_hqfs, sp_llzm, sp_syxz, sp_syyt, sp_syr_sfz, sp_syr_qh1)) {
                showToast("网络同步信息失败，请先设置界面同步代码")
                return
            }

            if (!CheckEditTxetUtils.checkEditextValuable(tv_hqrq)) {
                showToast("请填写完成获取日期")
                return
            }
            if (!CheckEditTxetUtils.checkEditextValuable(et_xsqy)) {
                showToast("请填写完成销售企业")
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
            val cph = if (TextUtils.isEmpty(data!!.data.checkData.cph)) et_cphm.text.toString().toUpperCase() else data!!.data.checkData.cph
            val ywlx = data!!.data.checkData.ywlx
            val xh = data!!.data.checkData.xh

            enity.lsh = lsh
            enity.xh = xh
            enity.zcbm = zcbm
            if (TextUtils.isEmpty(enity.cph)) {
                enity.cph = cph  //不为空 我都不想传
            }
            enity.blr = UserInfo.XM
            enity.ywlx = ywlx //业务类型
            enity.glbm = UserInfo.GLBM //管理部门
            enity.fzjg = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG//发证机关
            enity.cyrsfzmhm = UserInfo.SFZMHM//查验人身份证明号码
            enity.czpt = Constants.CZPT //查验平台
            enity.sqfs = "0"//申请方式  0所有人 1 代理人
            //1
            enity.hdfs = sp_hqfs.selectedItem.toString().split(":")[0]
            enity.llzm = sp_llzm.selectedItem.toString().split(":")[0]
            enity.syxz = sp_syxz.selectedItem.toString().split(":")[0]
            enity.clyt = sp_syyt.selectedItem.toString().split(":")[0]
            enity.hqrq = StringUtils.dateToStamp(tv_hqrq.text.toString())
            enity.xsqymc = et_xsqy.text.toString()
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
            //3 代理人(只要填写 就要填写完全)
            if (ObjectNullUtil.checknull(ed_dlr_sfz.text.toString()) || ObjectNullUtil.checknull(ed_dlr_xm.text.toString()) || ObjectNullUtil.checknull(et_dlr_lxdh.text.toString())) {

                if ("A" == sp_dlr_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_dlr_sfz.text.toString())) {
                    showToast("请正确填写代理人身份证信息")
                    return
                }
                if (ed_syr_sfz.text.toString() == ed_dlr_sfz.text.toString()) {
                    showToast("代理人和所有人的身份证信息是一样")
                    return
                }
                //只要不是全写了的 就提示写全
                if (!ObjectNullUtil.checknull(ed_dlr_xm.text.toString())) {
                    showToast("请完善代理人姓名")
                    return
                }
                if (!ObjectNullUtil.checknull(et_dlr_lxdh.text.toString()) || !StringUtils.isPhoneNumber(et_dlr_lxdh.text.toString())) {
                    showToast("请正确输入代理人联系电话")
                    return
                }

                enity.dlrsfzmlx = sp_dlr_sfz.selectedItem.toString().split(":")[0]
                enity.dlrsfzmhm = ed_dlr_sfz.text.toString()
                enity.dlrxm = ed_dlr_xm.text.toString()
                enity.dlrlxdh = et_dlr_lxdh.text.toString()
                enity.sqfs = "1"//申请方式  0所有人 1 代理人
            } else {
                enity.dlrsfzmlx = ""
                enity.dlrsfzmhm = ""
                enity.dlrxm = ""
                enity.dlrlxdh = ""
            }
            //4 寄递信息相关
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
            showLog("result [ZC] = " + jsonstr)
            mNormalPresenter!!.doJsonPost(jsonstr, Constants.YW_ADD_REGISTER_DATA)
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(e.message.toString())
        }
    }


    override fun getLayout(): Int {
        return R.layout.activity_register
    }
}
