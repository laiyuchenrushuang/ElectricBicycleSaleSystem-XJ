package com.seatrend.xj.electricbicyclesalesystem.persenter

import com.seatrend.xj.electricbicyclesalesystem.common.BasePresenter
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.module.ArchiveFileModule
import com.seatrend.xj.electricbicyclesalesystem.view.ArchiveFileView

/**
 * Created by ly on 2019/7/18 16:30
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class ArchiveFilePresenter(mView: ArchiveFileView) : BasePresenter(mView) {

    private var mArchiveFileMoudule: ArchiveFileModule?=null
    private var mArchiveFileView: ArchiveFileView?=null
    init {
        mArchiveFileMoudule= ArchiveFileModule(this)
        mArchiveFileView=mView;
    }

    override fun doNetworkTask(map: Map<String, String?>, url: String) {
        mArchiveFileMoudule!!.doWork(map,url)

    }

    override fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        if (isSuccess){
            mArchiveFileView!!.netWorkTaskSuccess(commonResponse)
        }else {
            mArchiveFileView!!.netWorkTaskfailed(commonResponse)
        }
    }
}