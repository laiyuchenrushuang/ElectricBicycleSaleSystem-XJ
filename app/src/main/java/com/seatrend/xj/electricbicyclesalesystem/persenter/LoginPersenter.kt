package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonProgress
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.LoginModule
import com.seatrend.xj.electricbicyclesalesystem.view.LoginView
import java.io.File

/**
 * Created by seatrend on 2018/12/27.
 */

class LoginPersenter(mView: LoginView) : BasePresenter(mView) {

    private var mLoginModule: LoginModule?=null
    private var mLoginView:LoginView?=null
    init {
        mLoginModule=LoginModule(this)
        mLoginView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mLoginModule!!.doWork(map,url)

    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mLoginView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mLoginView!!.netWorkTaskfailed(commonResponse)
        }

    }

    fun downloadFile(map: Map<String,String>?, url:String?,file: File) {
        mLoginModule!!.downloadFile(map,url, file)
    }

    fun downloadProgress(commonProgress: CommonProgress) {
        mLoginView!!.downloadProgress(commonProgress)
    }
}
