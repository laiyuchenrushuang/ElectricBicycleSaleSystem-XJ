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
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.DjLshEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.HpHmEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_change.*
import kotlinx.android.synthetic.main.activity_change.btn_hqhphm
import kotlinx.android.synthetic.main.activity_change.ed_dlr_sfz
import kotlinx.android.synthetic.main.activity_change.ed_dlr_xm
import kotlinx.android.synthetic.main.activity_change.ed_syr_sfz
import kotlinx.android.synthetic.main.activity_change.ed_syr_xm
import kotlinx.android.synthetic.main.activity_change.ed_yj_sfz
import kotlinx.android.synthetic.main.activity_change.ed_yj_xm
import kotlinx.android.synthetic.main.activity_change.et_cphm
import kotlinx.android.synthetic.main.activity_change.et_dlr_lxdh
import kotlinx.android.synthetic.main.activity_change.et_syr_lxdh
import kotlinx.android.synthetic.main.activity_change.et_syr_xxdz
import kotlinx.android.synthetic.main.activity_change.et_syr_yj_xxdz
import kotlinx.android.synthetic.main.activity_change.et_syr_yj_yzbm
import kotlinx.android.synthetic.main.activity_change.et_syr_yxdz
import kotlinx.android.synthetic.main.activity_change.et_yj_lxdh
import kotlinx.android.synthetic.main.activity_change.et_yj_xxdz
import kotlinx.android.synthetic.main.activity_change.et_yj_yzbm
import kotlinx.android.synthetic.main.activity_change.iv_dlr_scan
import kotlinx.android.synthetic.main.activity_change.iv_syr_scan
import kotlinx.android.synthetic.main.activity_change.iv_yj_scan
import kotlinx.android.synthetic.main.activity_change.ll_lqfs
import kotlinx.android.synthetic.main.activity_change.ll_yjlq
import kotlinx.android.synthetic.main.activity_change.ll_yjxx
import kotlinx.android.synthetic.main.activity_change.rb_lqfs_no
import kotlinx.android.synthetic.main.activity_change.rb_lqfs_ok
import kotlinx.android.synthetic.main.activity_change.rb_zzxsz_no
import kotlinx.android.synthetic.main.activity_change.rb_zzxsz_ok
import kotlinx.android.synthetic.main.activity_change.sp_dlr_sfz
import kotlinx.android.synthetic.main.activity_change.sp_syr_qh1
import kotlinx.android.synthetic.main.activity_change.sp_syr_qh2
import kotlinx.android.synthetic.main.activity_change.sp_syr_sfz
import kotlinx.android.synthetic.main.activity_change.sp_syr_yj_qh1
import kotlinx.android.synthetic.main.activity_change.sp_syr_yj_qh2
import kotlinx.android.synthetic.main.activity_change.sp_syxz
import kotlinx.android.synthetic.main.activity_change.sp_syyt
import kotlinx.android.synthetic.main.activity_change.sp_yj_qh1
import kotlinx.android.synthetic.main.activity_change.sp_yj_qh2
import kotlinx.android.synthetic.main.activity_change.sp_yj_sfz
import kotlinx.android.synthetic.main.activity_change.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.bottom_button.*

class YwChangeActivity : BaseActivity(), NormalView {

    private var headPhoto: ByteArray? = null//头像照片
    private var FACE_COMPARE_CODE: Int = 11
    private var mNormalPresenter: NormalPresenter? = null

    private var changed1 = false
    private var changed2 = false
    private var changed3 = false
    private var changed4 = false

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
            intent.putExtra("hphm", if ("B".equals(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywyy)) et_cphm.text.toString().toUpperCase() else CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.cph)
            intent.setClass(this, AutographActivity::class.java)
            startActivity(intent)
            CollectPhotoActivity.photoEntranceFlag = Constants.CAR_BG
            CollectPhotoActivity.ywlx = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywlx
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
            }
        }

    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("变更登记")
        mNormalPresenter = NormalPresenter(this)
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
        et_cphm.transformationMethod = CarHphmUtils.TransInformation()

        btn_hqhphm.setOnClickListener {
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
        sp_zrd1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var provinceDmz = sp_syr_qh1.selectedItem.toString()
                startThreadUpdateSp(provinceDmz, sp_zrd2)
            }
        }
    }

    private fun initData() {
        bt_next.text = "人证核验"
        initShowScrean()
        initSp()
        getData()
    }

    private fun getData() {
        val enity = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness
        if (null == enity) {
            showToast("登记信息为空")
            return
        }
        try {
            OtherUtils.setSpinnerToDmz(enity.syxz, sp_syxz)
            OtherUtils.setSpinnerToDmz(enity.clyt, sp_syyt)
            et_cphm.text = enity.cph

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

    private fun initSp() {
        SpinnerUtil.setPinnerData(this, Constants.SYXZ, sp_syxz)
        SpinnerUtil.setPinnerData(this, Constants.CLYT, sp_syyt)

        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_syr_qh1, if(null == CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.djxzqh || TextUtils.isEmpty(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.djxzqh)) null else CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.djxzqh.substring(0, 2) + "0000")
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_yj_qh1, if(null == CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.sjryjxzqh || TextUtils.isEmpty(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.sjryjxzqh)) null else CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.sjryjxzqh.substring(0, 2) + "0000")
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_syr_yj_qh1, if(null == CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.lxdzxzqh || TextUtils.isEmpty(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.lxdzxzqh)) null else CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.lxdzxzqh.substring(0, 2) + "0000")

        //转入地
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_zrd1,if(null == CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.zrd || TextUtils.isEmpty( CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.zrd)) null else CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.zrd.substring(0, 2) + "0000")

        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_syr_sfz)
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_dlr_sfz)
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_yj_sfz)
    }


    private fun initShowScrean() {
        if ("B".equals(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywyy)) {
            ViewShowUtils.showGoneView(ll_zrd)
            ViewShowUtils.showVisibleView(ll_yjxx, ll_xhphm)
            tv_ywyy.text = "变更所有人"
            if ("0".equals(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.sfkyghhp)) {
                ViewShowUtils.showGoneView(btn_hqhphm)
            }
        } else if ("A".equals(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywyy)) {
            ViewShowUtils.showVisibleView(ll_zrd)
            ViewShowUtils.showGoneView(ll_yjxx, ll_xhphm)
            tv_ywyy.text = "变更迁出"
        }
        if ("0".equals(UserInfo.GlobalParameter.LQBJ)) {
            ll_yjxx.visibility = View.GONE
        } else {
            ll_yjxx.visibility = View.VISIBLE
        }
    }

    private fun startThreadUpdateSp(dmsm: String, spinner: Spinner?) {
        var pcThread = Thread {
            val dmz = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, dmsm)
            var dataList = QHUtils.getAllOneLevelCitys(dmz)
            runOnUiThread {
                SpinnerUtil.setPinnerQHData(this@YwChangeActivity, dmz, dataList, spinner, mHandler)
            }
        }
        pcThread.start()
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
        val enity = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness
        when (obj) {
            sp_syr_qh2 -> {
                if (enity.djxzqh == null  || changed1) {
                    return
                }
                changed1 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.djxzqh), sp_syr_qh2)
            }
            sp_syr_yj_qh2 -> {
                if (enity.lxdzxzqh == null  || changed2) {
                    return
                }
                changed2 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.lxdzxzqh), sp_syr_yj_qh2)
            }
            sp_yj_qh2 -> {
                if (enity.sjryjxzqh == null  || changed3) {
                    return
                }
                changed3 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.sjryjxzqh), sp_yj_qh2)
            }
            sp_zrd2 -> {
                if (enity.zrd == null  || changed4) {
                    return
                }
                changed4 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.zrd), sp_zrd2)
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
//                    ed_syr_sfz.setText(data.getStringExtra(Constants.NUMBER))
////                    data.getStringExtra(Constants.ADDRESS)
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
                    LoadingDialog.getInstance().showLoadDialog(this)
                    Thread(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.path) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_syr_xm, ed_syr_sfz)
                    }).start()
                }
                Integer.toHexString(Constants.SFZ_DLR).toInt() -> {
                    LoadingDialog.getInstance().showLoadDialog(this)
                    Thread(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.path) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_dlr_xm, ed_dlr_sfz)
                    }).start()
                }
                Integer.toHexString(Constants.SFZ_YJ).toInt() -> {
                    LoadingDialog.getInstance().showLoadDialog(this)
                    Thread(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.path) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_yj_xm, ed_yj_sfz)
                    }).start()
                }
            }
        }
    }

    private fun postHttpRequest() {
        try {
            if (!SpinnerUtil.checkSpinnerValuable(sp_syxz, sp_syyt, sp_syr_sfz, sp_syr_qh1)) {
                showToast("网络同步信息失败，请先设置界面同步代码")
                return
            }
            if ("B".equals(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywyy)) {
                if (!CheckEditTxetUtils.checkEditextValuable(et_cphm)) {
                    showToast("请获取车牌号码")
                    return
                }
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
            val enity = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness

            val lsh = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.lsh
            val xh = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.xh
            val zcbm = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.zcbm
            //1
            enity.lsh = lsh
            enity.xh = xh
            enity.zcbm = zcbm
            enity.blr = UserInfo.XM  //办理人
            enity.glbm = UserInfo.GLBM //管理部门
            enity.ywyy = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywyy
            enity.ywlx = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywlx
            enity.fzjg = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG
            enity.cyrsfzmhm = UserInfo.SFZMHM//查验人身份证明号码
            enity.czpt = Constants.CZPT //查验平台
            enity.sqfs = "0"//申请方式  0所有人 1 代理人

            if ("B".equals(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywyy)) {
                //变更所有人
                val cph = et_cphm.text.toString().toUpperCase()
                enity.cph = cph
            } else if ("A".equals(CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.ywyy)) {
                //变更迁出
                enity.zrd = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, sp_zrd2.selectedItem.toString())
                enity.zt = "3" //zt   状态（1正常,2注销,3转出
            }
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
            showLog("[BG] result = " + GsonUtils.toJson(enity))
            showLoadingDialog()
            mNormalPresenter!!.doJsonPost(GsonUtils.toJson(enity), Constants.YW_ADD_REGISTER_DATA)
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(e.message.toString())
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_change
    }
}
