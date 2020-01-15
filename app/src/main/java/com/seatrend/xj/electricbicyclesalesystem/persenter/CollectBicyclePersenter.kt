package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.CollectBicycleModule
import com.seatrend.xj.electricbicyclesalesystem.view.CollectBicycleView
import java.io.File

/**
 * Created by seatrend on 2018/12/27.
 */

class CollectBicyclePersenter(mView: CollectBicycleView) : BasePresenter(mView) {

    private var mCollectBicycleModule: CollectBicycleModule?=null
    private var mCollectBicycleView: CollectBicycleView?=null
    init {
        mCollectBicycleModule=CollectBicycleModule(this)
        mCollectBicycleView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mCollectBicycleModule!!.doWork(map,url)

    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mCollectBicycleView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mCollectBicycleView!!.netWorkTaskfailed(commonResponse)
        }

    }
    fun uploadFile(file: File, map: Map<String, String?>, url: String){
        mCollectBicycleModule!!.uploadFile(file,url,map)
    }
}
