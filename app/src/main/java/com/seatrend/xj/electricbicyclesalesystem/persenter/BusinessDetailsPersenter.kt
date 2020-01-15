package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.BusinessDetailsModule
import com.seatrend.xj.electricbicyclesalesystem.view.BusinessDetailsView

/**
 * Created by seatrend on 2018/12/27.
 */

class BusinessDetailsPersenter(mView: BusinessDetailsView) : BasePresenter(mView) {

    private var mBusinessDetailsModule: BusinessDetailsModule?=null
    private var mBusinessDetailsView: BusinessDetailsView?=null
    init {
        mBusinessDetailsModule=BusinessDetailsModule(this)
        mBusinessDetailsView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mBusinessDetailsModule!!.doWork(map,url)
    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mBusinessDetailsView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mBusinessDetailsView!!.netWorkTaskfailed(commonResponse)
        }
    }
}
