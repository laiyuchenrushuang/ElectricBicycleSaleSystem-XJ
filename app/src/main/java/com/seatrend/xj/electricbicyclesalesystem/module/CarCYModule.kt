package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarCYPresenter

/**
 * Created by ly on 2019/8/1 16:48
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CarCYModule: BaseModule{
    private var mCarCYPresenter : CarCYPresenter ?=null
    constructor(carCYPresenter: CarCYPresenter?):super(){
        this.mCarCYPresenter = carCYPresenter
    }
    override fun doWork(map: Map<String, String?>, url: String) {
        HttpService.getInstance().getDataFromServer(map, url, Constants.GET, this)
    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mCarCYPresenter!!.requestResults(commonResponse!!, isSuccess)
    }
}