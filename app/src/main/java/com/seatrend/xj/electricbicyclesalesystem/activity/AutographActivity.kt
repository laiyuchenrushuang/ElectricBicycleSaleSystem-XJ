package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.text.TextUtils
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoIdEnity
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarPhotoPersenter
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.BitmapUtils
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.ObjectNullUtil
import com.seatrend.xj.electricbicyclesalesystem.view.CarPhotoView
import kotlinx.android.synthetic.main.activity_autograph.*
import kotlinx.android.synthetic.main.bottom_button.*
import java.io.File

class AutographActivity: BaseActivity(),CarPhotoView {

    private var photoId: String? = null  //photoId
    private var mCarPhotoPersenter: CarPhotoPersenter?=null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if(Constants.PHOTO_INSERT.equals(commonResponse.getUrl())){
            var enity = GsonUtils.gson(commonResponse.getResponseString(), PhotoIdEnity::class.java)
            if (!ObjectNullUtil.checknull(enity.data,enity.data.type,enity.data.id)) {
                showToast("获取照片id异常")
                return
            }
            photoId = enity.data.id
           if(!TextUtils.isEmpty(photoId) ){
               val lsh = CollectPhotoActivity.mLsh
               val xh = CollectPhotoActivity.mXh
               val mapP = HashMap<String, String?>()
               mapP["lsh"] = lsh
               mapP["xh"] = xh
               mapP["zpzl"] = enity.data.type
               mapP["zpdz"] = enity.data.id
               mapP["zpsm"] = "签名照片"
               mCarPhotoPersenter!!.doNetworkTask(mapP, Constants.PHOTO_MSG_SAVE)
           }
        }

        if( Constants.PHOTO_MSG_SAVE.equals(commonResponse.getUrl())){ // 保存图片
            startActivity(Intent(this, CollectPhotoActivity::class.java))
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
            if(!auto_view.touched){
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
       return  R.layout.activity_autograph
    }

    private fun saveQm(){
        Thread(Runnable{
            val qm = BitmapUtils.saveBitmap(auto_view.getAutographBitmap(), "qmzp" + System.currentTimeMillis())
            val map  = HashMap<String,String>()
            map["type"] = "Q"//自定义的签名照
            mCarPhotoPersenter!!.uploadFile(File(qm), map, Constants.PHOTO_INSERT)
        }).start()
    }
}
