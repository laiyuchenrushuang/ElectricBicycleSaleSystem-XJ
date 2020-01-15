package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.ArchiveFilePresenter


class ArchiveFileModule : BaseModule {
    private var mArchiveFilePresenter: ArchiveFilePresenter? = null

    constructor(mArchiveFilePresenter: ArchiveFilePresenter?) : super() {
        this.mArchiveFilePresenter = mArchiveFilePresenter
    }

    override fun doWork(map: Map<String, String?>, url: String) {
        if(url == Constants.POST_DAGD){
            HttpService.getInstance().getDataFromServer(map, url, Constants.POST, this)
        }else{
            HttpService.getInstance().getDataFromServer(map, url, Constants.GET, this)
        }

    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mArchiveFilePresenter!!.requestResults(commonResponse!!, isSuccess)
    }
}