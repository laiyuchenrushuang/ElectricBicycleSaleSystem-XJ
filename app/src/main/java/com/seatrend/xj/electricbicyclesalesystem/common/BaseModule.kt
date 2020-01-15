package com.seatrend.xj.electricbicyclesalesystem.common

import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse

/**
 * Created by seatrend on 2018/8/20.
 */

abstract class BaseModule {

    abstract fun doWork(map: Map<String, String?>, url: String)
    abstract fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean)

}
