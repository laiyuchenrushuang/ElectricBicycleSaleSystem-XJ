package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonProgress
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.LoginPersenter
import java.io.File

/**
 * Created by seatrend on 2018/12/27.
 */
class LoginModule : ProgressModule {

    private var mLoginPersenter: LoginPersenter? = null

    constructor(mLoginPersenter: LoginPersenter?) : super() {
        this.mLoginPersenter = mLoginPersenter
    }


    override fun doWork(map: Map<String, String?>, url: String) {
        if(Constants.USER_POST_IMAGE == url || Constants.USER_LOGIN == url){
            HttpService.getInstance().getDataFromServer(map, url, Constants.POST, this)
        }else{
            HttpService.getInstance().getDataFromServer(map, url, Constants.GET, this)
        }
    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mLoginPersenter!!.requestResults(commonResponse!!, isSuccess)
    }

    override fun downloadProgress(commonProgress: CommonProgress) {
        mLoginPersenter!!.downloadProgress(commonProgress)
    }

    fun downloadFile(map: Map<String, String?>?, url: String?, file: File) {
        HttpService.getInstance().downLoadFileFromServer(map, file, url, Constants.GET, this)
    }
}