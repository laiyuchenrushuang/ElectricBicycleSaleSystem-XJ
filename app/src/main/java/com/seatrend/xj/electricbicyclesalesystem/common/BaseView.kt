package com.seatrend.xj.electricbicyclesalesystem.common

/**
 * Created by seatrend on 2018/8/20.
 */

interface BaseView {
    fun showToast(msg: String)
    fun showToast(msgId: Int)
    fun showErrorDialog(msg: String)
}
