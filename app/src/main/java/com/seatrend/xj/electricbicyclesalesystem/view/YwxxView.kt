package com.seatrend.xj.electricbicyclesalesystem.view

import com.seatrend.xj.electricbicyclesalesystem.common.BaseView
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse

/**
 * Created by ly on 2019/8/8 18:54
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
interface YwxxView :BaseView{
    fun netWorkTaskSuccess(commonResponse: CommonResponse)
    fun netWorkTaskfailed(commonResponse: CommonResponse)
}