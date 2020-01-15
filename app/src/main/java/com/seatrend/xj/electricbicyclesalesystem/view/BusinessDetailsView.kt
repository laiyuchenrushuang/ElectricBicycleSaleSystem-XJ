package com.seatrend.xj.electricbicyclesalesystem.view

import com.seatrend.xj.electricbicyclesalesystem.common.BaseView
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse

/**
 * Created by seatrend on 2018/12/27.
 */
interface BusinessDetailsView: BaseView {
     fun netWorkTaskSuccess(commonResponse: CommonResponse)
     fun netWorkTaskfailed(commonResponse: CommonResponse)
}