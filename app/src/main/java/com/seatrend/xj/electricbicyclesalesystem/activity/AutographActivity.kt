package com.seatrend.xj.electricbicyclesalesystem.activity

import android.text.TextUtils
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.common.Constants.Companion.CAR_CY
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoIdEnity
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarPhotoPersenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.CarPhotoView
import kotlinx.android.synthetic.main.activity_autograph.*
import kotlinx.android.synthetic.main.bottom_button.*
import java.io.File

class AutographActivity : BaseActivity(), CarPhotoView {

    private var photoId: String? = null  //photoId
    private var mCarPhotoPersenter: CarPhotoPersenter? = null
    private var filePath = ""

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.PHOTO_INSERT.equals(commonResponse.getUrl())) {
            var enity = GsonUtils.gson(commonResponse.getResponseString(), PhotoIdEnity::class.java)
            if (!ObjectNullUtil.checknull(enity.data, enity.data.type, enity.data.id)) {
                showToast("获取照片id异常")
                return
            }
            photoId = enity.data.id
            if (!TextUtils.isEmpty(photoId)) {
                if (CAR_CY != intent.getStringExtra("photoentranceflag")) {
                    if ("Q" == enity.data.type) {   //后台定时任务上传  引起多线程 zplx 错乱问题
                        val lsh = CollectPhotoActivity.mLsh
                        val xh = CollectPhotoActivity.mXh
                        val mapP = HashMap<String, String?>()
                        mapP["lsh"] = lsh
                        mapP["xh"] = xh
                        mapP["zpzl"] = enity.data.type
                        mapP["zpdz"] = enity.data.id
                        mapP["zpsm"] = "签名照片"
                        showLog("签名照片数据 = " + GsonUtils.toJson(mapP))
                        mCarPhotoPersenter!!.doNetworkTask(mapP, Constants.PHOTO_MSG_SAVE)
                    }
                } else {
                    if ("CQ" == enity.data.type) {  //后台定时任务上传  引起多线程 zplx 错乱问题
                        val lsh = (intent.getSerializableExtra("all_data") as CYEntranceEnity).data.lsh  //查验流水号
                        val xh = (intent.getSerializableExtra("all_data") as CYEntranceEnity).data.xh//查验序号
                        val mapP = HashMap<String, String?>()
                        mapP["lsh"] = lsh
                        mapP["xh"] = xh
                        mapP["zpzl"] = enity.data.type
                        mapP["zpdz"] = enity.data.id
                        mapP["zpsm"] = "查验签名照片"
                        showLog("查验签名照片数据 = " + GsonUtils.toJson(mapP))
                        mCarPhotoPersenter!!.doNetworkTask(mapP, Constants.PHOTO_MSG_SAVE)
                    }
                }
            }
        }

        if (Constants.PHOTO_MSG_SAVE.equals(commonResponse.getUrl())) { // 保存图片

            if (!TextUtils.isEmpty(filePath)) {
                PhotoFileUtils.deleteFile(filePath)
            }
            if (CAR_CY == intent.getStringExtra("photoentranceflag")) {  //查验签名过来的拍照 （脚踏行驶  查验类型）
                intent.setClass(this, RemindCYActivity::class.java)
                startActivity(intent)
            } else {  //其他业务登记
                intent.putExtra("lsh", CollectPhotoActivity.mLsh)
                intent.setClass(this, RemindHPBFActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("签名")
        mCarPhotoPersenter = CarPhotoPersenter(this)
        initData()
        bindEvent()
    }

    private fun bindEvent() {
        btn_clear.setOnClickListener {
            auto_view.clear()
        }
        bt_next.setOnClickListener {
            if (!auto_view.touched) {
                showToast("请先签名后执行下一步")
                return@setOnClickListener
            }
            showLoadingDialog()
            saveQm()
        }
    }

    private fun initData() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_autograph
    }

    private fun saveQm() {
        ThreadPoolManager.instance.execute(Runnable {
            filePath = BitmapUtils.saveBitmap(auto_view.getAutographBitmap(), "qmzp" + System.currentTimeMillis())
            val map = HashMap<String, String>()
            if (CAR_CY == intent.getStringExtra("photoentranceflag")) {
                map["type"] = "CQ"//查验签名照
                map["zplx"] = "1"//查验签名照
                mCarPhotoPersenter!!.uploadFile(File(filePath), map, Constants.PHOTO_INSERT)
            } else { //登记业务
                map["type"] = "Q"//自定义的签名照
                map["zplx"] = "1"//自定义的签名照
                mCarPhotoPersenter!!.uploadFile(File(filePath), map, Constants.PHOTO_INSERT)
            }
        })
    }
}
