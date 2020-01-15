package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter

/**
 * Created by ly on 2019/9/25 14:34
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class NormalModule : BaseModule {

    private var mNormalPresenter: NormalPresenter? = null

    constructor(mNormalPresenter: NormalPresenter?) : super() {
        this.mNormalPresenter = mNormalPresenter
    }

    override fun doWork(map: Map<String, String?>, url: String) {
        if (Constants.SAVE_CY_MSG.equals(url) ||   //查验信息保存
                Constants.PHOTO_MSG_SAVE.equals(url) ||  //图片保存
                Constants.YW_ADD_REGISTER_DATA.equals(url) || //业务数据保存
                Constants.FH_COMMIT.equals(url) ||  // 复核提交
                Constants.TB_COMMIT.equals(url) ||// 退办提交
                Constants.GD_COMMIT.equals(url) || //归档提交
                Constants.FORBIDDEN_COMMIT.equals(url) || //黑名单
                Constants.USER_PSW_UPDATE.equals(url) //修改密码
        ) {
            HttpService.getInstance().getDataFromServer(map, url, Constants.POST, this)
        } else {
            HttpService.getInstance().getDataFromServer(map, url, Constants.GET, this)
        }
    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mNormalPresenter!!.requestResults(commonResponse!!, isSuccess)
    }
}