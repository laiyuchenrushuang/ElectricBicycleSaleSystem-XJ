package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.CarPhotoModule
import com.seatrend.xj.electricbicyclesalesystem.view.CarPhotoView
import java.io.File

/**
 * Created by seatrend on 2018/12/27.
 */

class CarPhotoPersenter(mView: CarPhotoView) : BasePresenter(mView) {

    private var mCarPhotoModule: CarPhotoModule?=null
    private var mCarPhotoView: CarPhotoView?=null
    init {
        mCarPhotoModule=CarPhotoModule(this)
        mCarPhotoView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mCarPhotoModule!!.doWork(map,url)

    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mCarPhotoView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mCarPhotoView!!.netWorkTaskfailed(commonResponse)
        }

    }

    fun uploadFileAndData( jsonString: String, url: String){
        mCarPhotoModule!!.uploadFileAndData(jsonString,url)
    }

    fun uploadFile(file: File, map: Map<String, String?>, url: String){
        mCarPhotoModule!!.uploadFile(file,url,map)
    }
}
