package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.WarningMessageModule
import com.seatrend.xj.electricbicyclesalesystem.view.WarningMessageView

/**
 * Created by seatrend on 2018/12/27.
 */

class WarningMessagePersenter(mView: WarningMessageView) : BasePresenter(mView) {

    private var mWarningMessageModule: WarningMessageModule?=null
    private var mWarningMessageView: WarningMessageView?=null
    init {
        mWarningMessageModule=WarningMessageModule(this)
        mWarningMessageView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mWarningMessageModule!!.doWork(map,url)

    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mWarningMessageView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mWarningMessageView!!.netWorkTaskfailed(commonResponse)
        }

    }
}
