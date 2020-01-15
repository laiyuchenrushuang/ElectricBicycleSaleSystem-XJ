package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.DjLshEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_cancel.*
import kotlinx.android.synthetic.main.activity_chayan_entrance.*
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

        }
        intent.putExtra("syr", CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.syrmc)
        intent.putExtra("sfz", CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.sfzmhm)
        intent.putExtra("hphm", CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.cph)
        intent.setClass(this, AutographActivity::class.java)
        startActivity(intent)
        CollectPhotoActivity.photoEntranceFlag = Constants.CAR_ZX
        CollectPhotoActivity.ywlx = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness.ywlx
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }


    override fun initView() {
        setPageTitle("注销登记")
        mNormalPresenter = NormalPresenter(this)
        initData()
        bindEvent()
    }

    private fun initData() {
        SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_dlr_sfz)
        setSpdata(sp_zx_ywyy)
        getData()
    }

    private fun getData() {
        val enity = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness
        if (null == enity) {
            showToast("登记信息为空")
            return
        }
        try {
            OtherUtils.setSpinnerToDmz(enity.dlrsfzmlx,sp_dlr_sfz)
            ed_dlr_sfz.setText(enity.dlrsfzmhm)
            ed_dlr_xm.setText(enity.dlrxm)
            et_dlr_lxdh.setText(enity.dlrlxdh)
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
                listData.add("自行报废")
                listData.add("灭失")
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
            getFaceCamera(Constants.FACE)
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
                    LoadingDialog.getInstance().showLoadDialog(this)
                    Thread(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.getPath()) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_dlr_xm, ed_dlr_sfz)
                    }).start()
                }
            }
        }
    }

    private fun postHttpRequest() {
        try {
            val enity = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.fjdcBusiness

            enity.ywlx = intent.getStringExtra("ywlx") //业务类型
            enity.glbm = UserInfo.GLBM //管理部门
            enity.ywyy = sp_zx_ywyy.selectedItem.toString()//业务原因
            enity.fzjg = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG
            enity.blr = UserInfo.XM
            enity.cyrsfzmhm = UserInfo.SFZMHM//查验人身份证明号码
            enity.czpt = Constants.CZPT //查验平台
            enity.zt = Constants.CZPT //zt   状态（1正常,2注销,3转出
            enity.sqfs = "0"//申请方式  0所有人 1 代理人

            if (ObjectNullUtil.checknull(ed_dlr_sfz.text.toString(), ed_dlr_xm.text.toString(), et_dlr_lxdh.text.toString())) {
                if("A" == sp_dlr_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_dlr_sfz.text.toString())){
                    showToast("请正确填写代理人身份证信息")
                    return
                }
                enity.dlrsfzmlx = sp_dlr_sfz.selectedItem.toString().split(":")[0]
                enity.dlrsfzmhm = ed_dlr_sfz.text.toString()
                enity.dlrxm = ed_dlr_xm.text.toString()
                enity.dlrlxdh = et_dlr_lxdh.text.toString()
                enity.sqfs = "1"//申请方式  0所有人 1 代理人
            }
            showLoadingDialog()
            val JsonStr = GsonUtils.toJson(enity)
            mNormalPresenter!!.doJsonPost(JsonStr, Constants.YW_ADD_REGISTER_DATA)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_cancel
    }

}