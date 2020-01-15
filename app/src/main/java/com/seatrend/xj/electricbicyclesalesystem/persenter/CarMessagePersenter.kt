package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.CarMessageModule
import com.seatrend.xj.electricbicyclesalesystem.view.CarMessageView

/**
 * Created by seatrend on 2018/12/27.
 */

class CarMessagePersenter(mView: CarMessageView) : BasePresenter(mView) {

    private var mCarMessageModule: CarMessageModule?=null
    private var mCarMessageView: CarMessageView?=null
    init {
        mCarMessageModule=CarMessageModule(this)
        mCarMessageView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mCarMessageModule!!.doWork(map,url)

    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mCarMessageView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mCarMessageView!!.netWorkTaskfailed(commonResponse)
        }

    }
}
