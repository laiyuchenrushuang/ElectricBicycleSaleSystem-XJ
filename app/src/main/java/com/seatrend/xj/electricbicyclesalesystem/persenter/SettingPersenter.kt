package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.SettingModule
import com.seatrend.xj.electricbicyclesalesystem.view.SettingView

/**
 * Created by seatrend on 2018/12/27.
 */

class SettingPersenter(mView: SettingView) : BasePresenter(mView) {

    private var mSettingModule: SettingModule?=null
    private var mSettingView:SettingView?=null
    init {
        mSettingModule=SettingModule(this)
        mSettingView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mSettingModule!!.doWork(map,url)
    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mSettingView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mSettingView!!.netWorkTaskfailed(commonResponse)
        }

    }
}
