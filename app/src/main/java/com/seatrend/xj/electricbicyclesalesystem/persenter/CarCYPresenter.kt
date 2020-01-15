package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.CarCYModule
import com.seatrend.xj.electricbicyclesalesystem.view.CarCYView

/**
 * Created by ly on 2019/8/1 16:48
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CarCYPresenter(mView:CarCYView):BasePresenter(mView){
    private var mCarCYView:CarCYView ?= null
    private var mCarCYModule: CarCYModule?= null
    init {
        mCarCYView = mView
        mCarCYModule = CarCYModule(this)
    }
    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mCarCYModule!!.doWork(map,url)
    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if(isSuccess){
            mCarCYView!!.netWorkTaskSuccess(commonResponse)
        }else{
            mCarCYView!!.netWorkTaskfailed(commonResponse)
        }
    }

}