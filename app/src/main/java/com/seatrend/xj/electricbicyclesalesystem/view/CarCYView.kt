package com.seatrend.xj.electricbicyclesalesystem.view

import com.seatrend.xj.electricbicyclesalesystem.common.BaseView
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse

interface CarCYView :BaseView{
    fun netWorkTaskSuccess(commonResponse: CommonResponse)
    fun netWorkTaskfailed(commonResponse: CommonResponse)
}
