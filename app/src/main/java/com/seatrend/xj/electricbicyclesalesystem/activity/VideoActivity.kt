package com.seatrend.xj.electricbicyclesalesystem.activity

import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import kotlinx.android.synthetic.main.activity_video.*
import java.io.File


class VideoActivity:BaseActivity(){

    override fun initView() {
//        pdfview.fromAsset("test1.docx").pageFling(true).load()
        pdfview.fromFile(File("/storage/emulated/0/BicycleFile/1587554130963chayan.pdf")).load()
    }

    override fun getLayout(): Int {
        return R.layout.activity_video
    }
}
