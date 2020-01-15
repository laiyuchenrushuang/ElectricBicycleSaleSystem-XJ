package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.module.NormalModule
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView

/**
 * Created by ly on 2019/9/25 14:34
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class NormalPresenter(mView:NormalView) :BasePresenter(mView){
    private var mNormalModule: NormalModule?=null
    private var mNormalView: NormalView?=null
    init {
        mNormalModule= NormalModule(this)
        mNormalView=mView;
    }
    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mNormalModule!!.doWork(map,url)
    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mNormalView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mNormalView!!.netWorkTaskfailed(commonResponse)
        }

    }
    // json Post
    fun doJsonPost(json:String?,url :String?){
        HttpService.getInstance().getDataFromServerByJson(json,url,mNormalModule)
    }
}