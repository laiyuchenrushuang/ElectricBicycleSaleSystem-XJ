package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.CarLoginModule
import com.seatrend.xj.electricbicyclesalesystem.view.CarLoginView

/**
 * Created by seatrend on 2018/12/27.
 */

class CarLoginPersenter(mView: CarLoginView) : BasePresenter(mView) {

    private var mCarLoginModule: CarLoginModule?=null
    private var mCarLoginView: CarLoginView?=null
    init {
        mCarLoginModule=CarLoginModule(this)
        mCarLoginView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mCarLoginModule!!.doWork(map,url)

    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mCarLoginView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mCarLoginView!!.netWorkTaskfailed(commonResponse)
        }

    }
}
