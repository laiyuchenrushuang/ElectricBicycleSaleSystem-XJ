package com.seatrend.xj.electricbicyclesalesystem.view

import com.seatrend.xj.electricbicyclesalesystem.common.BaseView
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonProgress
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse

/**
 * Created by seatrend on 2018/12/27.
 */
interface LoginView:BaseView{
     fun netWorkTaskSuccess(commonResponse: CommonResponse)
     fun netWorkTaskfailed(commonResponse: CommonResponse)
     fun downloadProgress(commonProgress: CommonProgress)
}