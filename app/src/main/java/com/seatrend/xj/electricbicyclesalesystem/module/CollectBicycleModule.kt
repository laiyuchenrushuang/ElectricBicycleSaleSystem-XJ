package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.CollectBicyclePersenter
import java.io.File

/**
 * Created by seatrend on 2018/12/27.
 */
class CollectBicycleModule:BaseModule{

    private var mCollectBicyclePersenter: CollectBicyclePersenter?=null

    constructor(mCollectBicyclePersenter: CollectBicyclePersenter?) : super() {
        this.mCollectBicyclePersenter = mCollectBicyclePersenter
    }


    override fun doWork(map: Map<String, String?>, url: String) {
        if(url.equals(Constants.SAVE_INSURANCEP_MES)
                || url.equals(Constants.GET_COMPANY_NAME)
                || url.equals(Constants.GET_INSURANCE_DETAILS)
                || url.equals(Constants.DECRYPT_INSURANCE_MSG)
                || url.equals(Constants.UPDATA_LS_ZT)){
            HttpService.getInstance().getDataFromServer(map,url,Constants.POST,this)
        }else{
            HttpService.getInstance().getDataFromServer(map,url,Constants.GET,this)
        }

    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mCollectBicyclePersenter!!.requestResults(commonResponse!!,isSuccess)
    }
    fun uploadFile(file: File, url: String, map: Map<String, String?>?){
        HttpService.getInstance().uploadFileToServer(url,file,map,this)
    }

}