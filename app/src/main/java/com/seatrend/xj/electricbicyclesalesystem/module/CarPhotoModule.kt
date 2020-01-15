package com.seatrend.xj.electricbicyclesalesystem.module

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarPhotoPersenter
import java.io.File

/**
 * Created by seatrend on 2018/12/27.
 */
class CarPhotoModule : BaseModule {

    private var mCarPhotoPersenter: CarPhotoPersenter? = null

    constructor(mCarPhotoPersenter: CarPhotoPersenter?) : super() {
        this.mCarPhotoPersenter = mCarPhotoPersenter
    }


    override fun doWork(map: Map<String, String?>, url: String) {
        if (url == Constants.PSOT_CAR_YWXX ||
                url == Constants.POST_HPBF ||
                url == Constants.PHOTO_MSG_SAVE ||
                url == Constants.SAVE_CY_MSG ||
                url == Constants.UPDATA_LS_ZT ||
                 url == Constants.YG_PHOTO_SAVE) {
            HttpService.getInstance().getDataFromServer(map, url, Constants.POST, this)
        } else {
            HttpService.getInstance().getDataFromServer(map, url, Constants.GET, this)
        }

    }

    override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
        mCarPhotoPersenter!!.requestResults(commonResponse!!, isSuccess)
    }

    fun uploadFileAndData(jsonString: String, url: String) {
        HttpService.getInstance().getDataFromServerByJson(jsonString, url, this)
    }

    fun uploadFile(file: File, url: String, map: Map<String, String?>?) {
        HttpService.getInstance().uploadFileToServer(url, file, map, this)
    }

}