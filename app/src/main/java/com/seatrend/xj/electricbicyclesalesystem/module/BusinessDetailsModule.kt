package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.BusinessDetailsPersenter

/**
 * Created by seatrend on 2018/12/27.
 */
class BusinessDetailsModule:BaseModule{

    private var mBusinessDetailsPersenter: BusinessDetailsPersenter?=null

    constructor(mBusinessDetailsPersenter: BusinessDetailsPersenter?) : super() {
        this.mBusinessDetailsPersenter = mBusinessDetailsPersenter
    }

    override fun doWork(map: Map<String, String?>, url: String) {
       HttpService.getInstance().getDataFromServer(map,url,Constants.GET,this)
    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mBusinessDetailsPersenter!!.requestResults(commonResponse!!,isSuccess)
    }
}