package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarLoginPersenter

/**
 * Created by seatrend on 2018/12/27.
 */
class CarLoginModule:BaseModule{

    private var mCarLoginPersenter: CarLoginPersenter?=null

    constructor(mCarLoginPersenter: CarLoginPersenter?) : super() {
        this.mCarLoginPersenter = mCarLoginPersenter
    }


    override fun doWork(map: Map<String, String?>, url: String) {
       HttpService.getInstance().getDataFromServer(map,url,Constants.GET,this)
    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mCarLoginPersenter!!.requestResults(commonResponse!!,isSuccess)
    }


}