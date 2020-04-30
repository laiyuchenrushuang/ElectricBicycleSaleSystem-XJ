package com.seatrend.xj.electricbicyclesalesystem.activity

import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonProgress
import com.seatrend.xj.electricbicyclesalesystem.persenter.LoginPersenter
import com.seatrend.xj.electricbicyclesalesystem.util.LoadUrlUtils
import com.seatrend.xj.electricbicyclesalesystem.util.PhotoFileUtils
import com.seatrend.xj.electricbicyclesalesystem.util.SharedPreferencesUtils
import com.seatrend.xj.electricbicyclesalesystem.view.LoginView
import kotlinx.android.synthetic.main.pdf_activity.*
import java.io.File
import java.lang.Exception


/**
 * Created by ly on 2020/4/22 16:45
 */
class PDFActivity : BaseActivity(), LoginView {

    var mNorPre: LoginPersenter? = null
    var pdfFile: File? = null
    override fun downloadProgress(commonProgress: CommonProgress) {
        if (commonProgress.progress == "100") {
            dismissLoadingDialog()
            runOnUiThread {
//                pdfview.fromFile(pdfFile).load()
                Glide.with(this).load(pdfFile).centerCrop().error(R.drawable.error_image).into(ivPhoto)
            }
        }
    }

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.responseString)
    }


    override fun initView() {
        mNorPre = LoginPersenter(this)
        if ("1" == intent.getStringExtra("pdf_lx")) { //业务登记
            setPageTitle(getString(R.string.pdf_title, "登记"))
            val lsh = intent.getStringExtra("lsh")
//            requestPdfService("1", lsh, "" + System.currentTimeMillis() + "dengji.pdf")
            glideLoadPhoto("1", lsh, Constants.PDF_GET_DJ)
        } else {
            setPageTitle(getString(R.string.pdf_title, "查验")) //查验
            val lsh = (intent.getSerializableExtra("all_data") as CYEntranceEnity).data.lsh
//            requestPdfService("0", lsh, "" + System.currentTimeMillis() + "chayan.pdf")
            glideLoadPhoto("0", lsh, Constants.PDF_GET_CY)
        }

    }

    private fun glideLoadPhoto(lx: String, lsh: String, url: String) {

        // lx不传
//        requestPdfService(url, lsh, "" + System.currentTimeMillis() + ".jpg")

        loadPhoto(lsh, url)
    }

    private fun loadPhoto(lsh: String, url: String) {
        showLoadingDialog()
        Glide.with(this).load(LoadUrlUtils.loadPdfUrl(lsh, url)).listener(object : RequestListener<String, GlideDrawable> {
            override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                dismissLoadingDialog()
                showToast("后台表格制作中...请稍等重试")
                return false
            }

            override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                dismissLoadingDialog()
                showToast("加载成功")
                return false
            }

        }).diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .centerCrop().error(R.drawable.error_image)
                .into(ivPhoto)
    }

    private fun requestPdfService(url: String, lsh: String, filename: String) {
        showLoadingDialog()
        val map = HashMap<String, String>()
        val file = File(Constants.FILE_PATH)
        if (!file.exists()) {
            file.mkdirs()
        }
        var fileName = filename
        pdfFile = File(file, fileName)
//        map["lx"] = lx
        map["lsh"] = lsh
        val url = SharedPreferencesUtils.getNetworkAddress() + url
        mNorPre!!.downloadFile(map, url, pdfFile!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (pdfFile!=null && pdfFile!!.exists()) {
            PhotoFileUtils.deleteFile(pdfFile)
        }
    }

    override fun getLayout(): Int {
        return R.layout.pdf_activity
    }
}