package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonProgress

/**
 * Created by ly on 2019/11/12 16:20
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
abstract class ProgressModule : BaseModule() {
    abstract fun downloadProgress(commonProgress: com.seatrend.xj.electricbicyclesalesystem.entity.CommonProgress)

}