package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarMessagePersenter

/**
 * Created by seatrend on 2018/12/27.
 */
class CarMessageModule:BaseModule{

    private var mCarMessagePersenter: CarMessagePersenter?=null

    constructor(mCarMessagePersenter: CarMessagePersenter?) : super() {
        this.mCarMessagePersenter = mCarMessagePersenter
    }


    override fun doWork(map: Map<String, String?>, url: String) {
       HttpService.getInstance().getDataFromServer(map,url,Constants.GET,this)
    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mCarMessagePersenter!!.requestResults(commonResponse!!,isSuccess)
    }


}