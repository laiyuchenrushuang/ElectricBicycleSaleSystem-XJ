package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.YwxxModule
import com.seatrend.xj.electricbicyclesalesystem.view.YwxxView

/**
 * Created by ly on 2019/8/8 18:55
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class YwxxPresenter(mView: YwxxView) : BasePresenter(mView) {
    private var mYwxxView: YwxxView? = null
    private var mYwxxModule: YwxxModule? = null

    init {
        mYwxxView = mView
        mYwxxModule = YwxxModule(this)
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mYwxxModule!!.doWork(map, url)
    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess) {
            mYwxxView!!.netWorkTaskSuccess(commonResponse)
        } else {
            mYwxxView!!.netWorkTaskfailed(commonResponse)
        }
    }

}