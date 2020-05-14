package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.DjLshEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_cancel.*
import kotlinx.android.synthetic.main.bottom_button.*

/**
 * Created by ly on 2019/9/30 11:59
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class YwCancelActivity : BaseActivity(), NormalView {
    companion object {
        var YWYY: Int = -1 // 业务原因 1 自行报废   2 灭失
    }

    private var data :AllBikeMsgEnity?=null
    private var headPhoto: ByteArray? = null//头像照片
    private var FACE_COMPARE_CODE: Int = 11
    private var mNormalPresenter: NormalPresenter? = null

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
        }


        intent.putExtra("syr", data!!.data.fjdcBusiness.syrmc)
        intent.putExtra("sfz", data!!.data.fjdcBusiness.sfzmhm)
        intent.putExtra("hphm", data!!.data.fjdcBusiness.cph)
        intent.setClass(this, CollectPhotoActivity::class.java)
        startActivity(intent)
        CollectPhotoActivity.photoEntranceFlag = Constants.CAR_ZX
        CollectPhotoActivity.ywlx = data!!.data.fjdcBusiness.ywlx
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }


    override fun initView() {
        setPageTitle("注销登记")
        mNormalPresenter = NormalPresenter(this)
        data = intent.getSerializableExtra("all_data") as AllBikeMsgEnity
        initData()
        bindEvent()
    }

    private fun initData() {
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_dlr_sfz)
        setSpdata(sp_zx_ywyy)
        getData()
    }

    private fun getData() {
        val enity = data!!.data.fjdcBusiness
        if (null == enity) {
            showToast("登记信息为空")
            return
        }
        try {
            rb_yes.isChecked = true
            OtherUtils.setSpinnerToDmz(enity.dlrsfzmlx,sp_dlr_sfz)
            ed_dlr_sfz.setText(enity.dlrsfzmhm)
            ed_dlr_xm.setText(enity.dlrxm)
            et_dlr_lxdh.setText(enity.dlrlxdh)
            tv_yhphm.setText(enity.cph)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }

    private fun setSpdata(spinner: Spinner) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (spinner.id) {
            R.id.sp_zx_ywyy -> {
                var listData = ArrayList<String>()
                listData.add("GA:报废")
                listData.add("GB:灭失")
                listData.add("GD:撤销登记")
                listData.add("GF:退车")
                adapter.addAll(listData)
                spinner.adapter = adapter
            }
        }
    }

    private fun bindEvent() {

        iv_dlr_scan.setOnClickListener {
            showIcCardReadModeDialog(Constants.SFZ_DLR)
        }

        bt_next.setOnClickListener {

            //进行人脸识别
            //getFaceCamera(Constants.FACE)
            postHttpRequest()
        }

        //限制名称只能输入中文和字母和数字
//        et_xsqy.filters = arrayOf(inputFilter)
//        ed_syr_xm.filters = arrayOf(inputFilter)
//        et_syr_yj_xxdz.filters = arrayOf(inputFilter)
//        et_syr_xxdz.filters = arrayOf(inputFilter)
        ed_dlr_xm.filters = arrayOf(inputFilter)
//        ed_yj_xm.filters = arrayOf(inputFilter)
//        et_yj_xxdz.filters = arrayOf(inputFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ID_CARD_READ_CODE -> {
                    showToast("身份证已读取")
//                    data.getStringExtra(Constants.NAME)
//                    ed_syr_sfz.setText(data.getStringExtra(Constants.NUMBER))
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

                Constants.SFZ_DLR -> {
                    ed_dlr_sfz.text = Editable.Factory.getInstance().newEditable(data!!.getStringExtra(Constants.NUMBER))
                    ed_dlr_xm.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NAME))
                }

                Integer.toHexString(Constants.SFZ_DLR).toInt() -> {
                    showLoadingDialog()
                    ThreadPoolManager.instance.execute(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.getPath()) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_dlr_xm, ed_dlr_sfz)
                    })
                }
            }
        }
    }

    private fun postHttpRequest() {
        try {
            val enity = data!!.data.fjdcBusiness

            enity.ywlx = intent.getStringExtra("ywlx") //业务类型
            enity.glbm = UserInfo.GLBM //管理部门
            enity.ywyy = sp_zx_ywyy.selectedItem.toString().trim().split(":")[0]//业务原因
            enity.fzjg = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG
            enity.blr = UserInfo.XM
            enity.cyrsfzmhm = UserInfo.SFZMHM//查验人身份证明号码
            enity.czpt = Constants.CZPT //查验平台
            enity.zt = Constants.CZPT //zt   状态（1正常,2注销,3转出
            enity.sqfs = "0"//申请方式  0所有人 1 代理人
            if (rb_yes.isChecked) {
                enity.hphs = "1"
            } else {
                enity.hphs = "0"
            }
            //3 代理人
            if(ObjectNullUtil.checknull(ed_dlr_sfz.text.toString()) || ObjectNullUtil.checknull(ed_dlr_xm.text.toString())||ObjectNullUtil.checknull(et_dlr_lxdh.text.toString())){

                if ("A" == sp_dlr_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_dlr_sfz.text.toString())) {
                    showToast("请正确填写代理人身份证信息")
                    return
                }
//                if (ed_syr_sfz.text.toString() == ed_dlr_sfz.text.toString()) {
//                    showToast("代理人和所有人的身份证信息是一样")
//                    return
//                }
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
            showLoadingDialog()
            val jsonstr = GsonUtils.toJson(enity)
            showLog("result [ZX] = " + jsonstr)
            mNormalPresenter!!.doJsonPost(jsonstr, Constants.YW_ADD_REGISTER_DATA)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_cancel
    }

}