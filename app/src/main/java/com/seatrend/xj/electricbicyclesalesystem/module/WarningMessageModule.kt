package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.WarningMessagePersenter

/**
 * Created by seatrend on 2018/12/27.
 */
class WarningMessageModule:BaseModule{

    private var mWarningMessagePersenter: WarningMessagePersenter?=null

    constructor(mWarningMessagePersenter: WarningMessagePersenter?) : super() {
        this.mWarningMessagePersenter = mWarningMessagePersenter
    }

    override fun doWork(map: Map<String, String?>, url: String) {
        if(Constants.WARNING_QS == url){
            HttpService.getInstance().getDataFromServer(map,url,Constants.POST,this)
        }else{
            HttpService.getInstance().getDataFromServer(map,url,Constants.GET,this)
        }

    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mWarningMessagePersenter!!.requestResults(commonResponse!!,isSuccess)
    }
}