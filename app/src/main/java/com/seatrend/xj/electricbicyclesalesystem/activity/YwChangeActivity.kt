package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.SyncStateContract
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_change.*
import kotlinx.android.synthetic.main.bottom_button.*

class YwChangeActivity : BaseActivity(), NormalView {

    private var headPhoto: ByteArray? = null//头像照片
    private var FACE_COMPARE_CODE: Int = 11
    private var mNormalPresenter: NormalPresenter? = null
    private var data: AllBikeMsgEnity? = null

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

            if(enity.data.photo!=null && enity.data.photo.size >0){
                val bundle = Bundle()
                bundle.putParcelable("photo_list", enity)
                intent.putExtras(bundle)
            }
            intent.putExtra("syr", ed_syr_xm.text.toString())
            intent.putExtra("sfz", ed_syr_sfz.text.toString())
            intent.putExtra("hphm", if ("B".equals(data!!.data.checkData.ywyy)) et_cphm.text.toString().toUpperCase() else data!!.data.checkData.cph)
            intent.setClass(this, CollectPhotoActivity::class.java)
            startActivity(intent)
            CollectPhotoActivity.photoEntranceFlag = Constants.CAR_BG
            CollectPhotoActivity.ywlx = data!!.data.checkData.ywlx
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
                btn_hqhphm.visibility = View.GONE
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
        data = intent.getSerializableExtra("all_data") as AllBikeMsgEnity
        initData()
        bindEvent()
    }

    private fun bindEvent() {
        rb_zzxsz_ok.isChecked = true
        rb_lqfs_ok.isChecked = true
        rb_yes.isChecked = true
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
        et_cphm.transformationMethod = CarHphmUtils.TransInformation()

        btn_hqhphm.setOnClickListener {
            showLoadingDialog()
            val map = HashMap<String, String>()
            map["lybm"] = UserInfo.GLBM
            mNormalPresenter!!.doNetworkTask(map, Constants.SYSTEM_PRODUCT_HPHM)
        }

        sp_syr_qh1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            @SuppressLint("ResourceAsColor")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var provinceDmz = sp_syr_qh1.selectedItem.toString()
                if(Constants.BG_DC == data!!.data.checkData.ywyy){
                    //变更颜色的  这个字段Textview  灰色
                    var txtvwSpinner: TextView = p1!!.findViewById(R.id.text1)
                    txtvwSpinner.setTextColor(R.color.gray)
                    sp_syr_qh1.isEnabled = false
                }
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

            @SuppressLint("ResourceAsColor")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var provinceDmz = sp_syr_yj_qh1.selectedItem.toString()
                if(Constants.BG_DC == data!!.data.checkData.ywyy){
                    //变更颜色的  这个字段Textview  灰色
                    var txtvwSpinner: TextView = p1!!.findViewById(R.id.text1)
                    txtvwSpinner.setTextColor(R.color.gray)
                    sp_syr_yj_qh1.isEnabled = false
                }
                startThreadUpdateSp(provinceDmz, sp_syr_yj_qh2)
            }
        }
        sp_zrd1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var provinceDmz = sp_zrd1.selectedItem.toString()
                startThreadUpdateSp(provinceDmz, sp_zrd2)
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

        rb_hphs.setOnCheckedChangeListener { p0, p1 ->
            when (p1) {
                //button Yes
                R.id.rb_yes -> {
                }
                //button No
                R.id.rb_no -> {
                }
            }
        }
    }

    private fun initData() {
        bt_next.text = "下一步"
        try {
            initSpData()
            initShowScrean()
            getData()
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
            OtherUtils.setSpinnerToDmz(enity.syxz, sp_syxz)
            OtherUtils.setSpinnerToDmz(enity.clyt, sp_syyt)
            tv_hphm.text = enity.cph

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
            tv_yhphm.setText(enity.cph)

            //变更颜色 数据的回填
            if (Constants.BG_DC == data!!.data.checkData.ywyy) {

                //登记有信息 用登记的
                if (enity.csys != null) {
                    if ( enity.csys.contains(",")) {
                        var str = enity.csys.split(",")
                        if (str.size == 2) {
                            OtherUtils.setSpinnerToDmz(str[0], sp_csys_a)
                            OtherUtils.setSpinnerToDmz(str[1], sp_csys_b)
                        } else if (str.size == 3) {
                            OtherUtils.setSpinnerToDmz(str[0], sp_csys_a)
                            OtherUtils.setSpinnerToDmz(str[1], sp_csys_b)
                            OtherUtils.setSpinnerToDmz(str[2], sp_csys_c)
                        }
                    } else { //只有一个数据
                        OtherUtils.setSpinnerToDmz(enity.csys, sp_csys_a)
                    }
                } else {  //没有就查验的ccc的
                    if (data!!.data.cccData.csys != null && data!!.data.cccData.csys.contains(",")) {
                        var str = data!!.data.cccData.csys.split(",")
                        if (str.size == 2) {
                            OtherUtils.setSpinnerToDmz(str[0], sp_csys_a)
                            OtherUtils.setSpinnerToDmz(str[1], sp_csys_b)
                        } else if (str.size == 3) {
                            OtherUtils.setSpinnerToDmz(str[0], sp_csys_a)
                            OtherUtils.setSpinnerToDmz(str[1], sp_csys_b)
                            OtherUtils.setSpinnerToDmz(str[2], sp_csys_c)

                        }
                    } else { //只有一个数据
                        OtherUtils.setSpinnerToDmz(data!!.data.cccData.csys, sp_csys_a)
                    }
                }
            }

        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }

    private fun initSpData() {
        SpinnerUtil.setPinnerData(this, Constants.SYXZ, sp_syxz)
        SpinnerUtil.setPinnerData(this, Constants.CLYT, sp_syyt)

        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_syr_qh1, if (null == data!!.data.fjdcBusiness.djxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.djxzqh)) null else data!!.data.fjdcBusiness.djxzqh.substring(0, 2) + "0000")
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_yj_qh1, if (null == data!!.data.fjdcBusiness.sjryjxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.sjryjxzqh)) null else data!!.data.fjdcBusiness.sjryjxzqh.substring(0, 2) + "0000")
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_syr_yj_qh1, if (null == data!!.data.fjdcBusiness.lxdzxzqh || TextUtils.isEmpty(data!!.data.fjdcBusiness.lxdzxzqh)) null else data!!.data.fjdcBusiness.lxdzxzqh.substring(0, 2) + "0000")

        //转入地
        SpinnerUtil.setPinnerDataQh(this, Constants.MY_QH_SHENG_DMLB, sp_zrd1, if (null == data!!.data.fjdcBusiness.zrd || TextUtils.isEmpty(data!!.data.fjdcBusiness.zrd)) null else data!!.data.fjdcBusiness.zrd.substring(0, 2) + "0000")

        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_syr_sfz)
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_dlr_sfz)
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_yj_sfz)
    }


    private fun initShowScrean() {
        if (Constants.BG_DB == data!!.data.checkData.ywyy) {
            ViewShowUtils.showGoneView(ll_zrd, ll_yhphm, ll_hphs, ll_hphm)
            ViewShowUtils.showVisibleView(ll_yjxx)
            tv_ywyy.text = "变更所有人"
            if ("0" == data!!.data.fjdcBusiness.sfkyghhp) {
                ViewShowUtils.showGoneView(btn_hqhphm)
            }
        } else if (Constants.BG_DA == data!!.data.checkData.ywyy) {
            ViewShowUtils.showVisibleView(ll_zrd, ll_yhphm, ll_hphs)
            ViewShowUtils.showGoneView(ll_yjxx, ll_hphm)
            tv_ywyy.text = "变更迁出"
            rb_yes.isChecked = true

            //所有人信息不能更改
            setNoEdit(sp_syr_sfz,ed_syr_xm, ed_syr_sfz, et_syr_lxdh, et_syr_xxdz, et_syr_yj_xxdz, et_syr_yj_yzbm, et_syr_yxdz, sp_syr_qh2, sp_syr_yj_qh2)
        } else if (Constants.BG_DC == data!!.data.checkData.ywyy) {
            setSpinner()
            tv_ywyy.text = "变更车身颜色"
            ViewShowUtils.showGoneView(ll_clyt,ll_syxz,ll_zrd, ll_yhphm, ll_hphs)
            ViewShowUtils.showVisibleView(ll_csys)
            setNoEdit(sp_syr_sfz,ed_syr_xm, ed_syr_sfz, et_syr_lxdh, et_syr_xxdz, et_syr_yj_xxdz, et_syr_yj_yzbm, et_syr_yxdz, sp_syr_qh2, sp_syr_yj_qh2)
        }
        if ("0" == UserInfo.GlobalParameter.LQBJ) {
            ll_yjxx.visibility = View.GONE
        } else {
            ll_yjxx.visibility = View.VISIBLE
        }
    }

    private fun setSpinner() {
        setSpinnerAdapter(sp_csys_a)
        setSpinnerAdapter(sp_csys_b)
        setSpinnerAdapter(sp_csys_c)
    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (spinner.id) {
            R.id.sp_csys_a, R.id.sp_csys_b, R.id.sp_csys_c -> {
                adapter.clear()
                adapter.add("")
                val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(Constants.CSYS)
                for (db in sfzmmcList) {
                    val dmz = db.dmz
                    val dmsm1 = db.dmsm1
                    adapter.add("$dmz:$dmsm1")
                }
                spinner.adapter = adapter
            }
        }
    }

    private fun startThreadUpdateSp(dmsm: String, spinner: Spinner?) {
        ThreadPoolManager.instance.execute(Runnable {
            try {
                val dmz = CodeTableSQLiteUtils.queryByDmlbAndDmsm(Constants.XSQY, dmsm)
                var dataList = QHUtils.getAllOneLevelCitys(dmz)
                runOnUiThread {
                    SpinnerUtil.setPinnerQHData(this@YwChangeActivity, dmz, dataList, spinner, mHandler)
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
                if (enity.djxzqh == null || changed1) {
                    return
                }
                changed1 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.djxzqh), sp_syr_qh2)
            }
            sp_syr_yj_qh2 -> {
                if (enity.lxdzxzqh == null || changed2) {
                    return
                }
                changed2 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.lxdzxzqh), sp_syr_yj_qh2)
            }
            sp_yj_qh2 -> {
                if (enity.sjryjxzqh == null || changed3) {
                    return
                }
                changed3 = true
                OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.XSQY, enity.sjryjxzqh), sp_yj_qh2)
            }
            sp_zrd2 -> {
                if (enity.zrd == null || changed4) {
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
            if (!SpinnerUtil.checkSpinnerValuable(sp_syxz, sp_syyt, sp_syr_sfz, sp_syr_qh1)) {
                showToast("网络同步信息失败，请先设置界面同步代码")
                return
            }
//            if (Constants.BG_DB.equals(data!!.data.checkData.ywyy)) {
//                if (!CheckEditTxetUtils.checkEditextValuable(et_cphm)) {
//                    showToast("请获取号牌号码")
//                    return
//                }
//            }
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

            if (Constants.BG_DC == data!!.data.checkData.ywyy && !CsysCompareSameUtils.compare3(sp_csys_a, sp_csys_b, sp_csys_c)) {
                showToast("请正确输入车身颜色，不能两两选择相同颜色")
                return
            }
            val enity = data!!.data.fjdcBusiness

            val lsh = data!!.data.checkData.lsh
            val xh = data!!.data.checkData.xh
            val zcbm = data!!.data.checkData.zcbm
            //1
            enity.lsh = lsh
            enity.xh = xh
            enity.zcbm = zcbm
            enity.blr = UserInfo.XM  //办理人
            enity.glbm = UserInfo.GLBM //管理部门
            enity.ywyy = data!!.data.checkData.ywyy
            enity.ywlx = data!!.data.checkData.ywlx
            enity.fzjg = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG
            enity.cyrsfzmhm = UserInfo.SFZMHM//查验人身份证明号码
            enity.czpt = Constants.CZPT //查验平台
            enity.sqfs = "0"//申请方式  0所有人 1 代理人

            if (Constants.BG_DB.equals(data!!.data.checkData.ywyy)) {
                //变更所有人  用以前的hphm
//                val cph = et_cphm.text.toString().toUpperCase()
//                enity.cph = cph
            } else if (Constants.BG_DA.equals(data!!.data.checkData.ywyy)) {
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
            if (Constants.BG_DA == data!!.data.checkData.ywyy) {
                if (rb_yes.isChecked) {
                    enity.hphs = "1"
                } else {
                    enity.hphs = "0"
                }
            }

            if (Constants.BG_DC == data!!.data.checkData.ywyy) {
                enity.csys = CsysUtils.getSpCommitString(sp_csys_a, sp_csys_b, sp_csys_c)
            }
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
            showLoadingDialog()
            val jsonstr = GsonUtils.toJson(enity)
            showLog("result [BG] = " + jsonstr)
            mNormalPresenter!!.doJsonPost(jsonstr, Constants.YW_ADD_REGISTER_DATA)
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(e.message.toString())
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_change
    }
}
