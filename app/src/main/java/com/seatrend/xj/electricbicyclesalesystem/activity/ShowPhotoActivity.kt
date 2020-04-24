package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.graphics.BitmapFactory
import android.view.KeyEvent
import android.view.View
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.createview.ClipperView
import kotlinx.android.synthetic.main.activity_show_photo.*
import kotlinx.android.synthetic.main.common_title.*
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.util.BitmapUtils
import android.view.MotionEvent
import java.io.File


class ShowPhotoActivity : BaseActivity(), BaseActivity.DialogListener {


    private var clipped: Boolean = false  // true 是需要裁剪      false 已經裁剪過 或者不用裁剪
    var clipperView: ClipperView? = null

    override fun initView() {

        setListener(this)
        clipped = "1" == intent.getStringExtra(Constants.CLIPP)

        if (null != intent.getStringExtra(Constants.ZPMC)) {
            setPageTitle(intent.getStringExtra(Constants.ZPMC))
            if (null != intent.getStringExtra(Constants.ZPLX) && (Constants.TYPE_ZCBM == intent.getStringExtra(Constants.ZPLX) || (intent.getStringExtra(Constants.ZPLX).length > 2 && Constants.TYPE_ZCBM == intent.getStringExtra(Constants.ZPLX).substring(0, 2))) && !clipped && "0" == intent.getStringExtra(Constants.CLIPP)) {
                setPageTitle(intent.getStringExtra(Constants.ZPMC) + resources.getString(R.string.clipped))
            }
            if (null != intent.getStringExtra(Constants.ZPLX) && (Constants.TYPE_TYH == intent.getStringExtra(Constants.ZPLX) || (intent.getStringExtra(Constants.ZPLX).length > 2 && Constants.TYPE_TYH == intent.getStringExtra(Constants.ZPLX).substring(0, 2))) && !clipped && "0" == intent.getStringExtra(Constants.CLIPP)) {
                setPageTitle(intent.getStringExtra(Constants.ZPMC) + resources.getString(R.string.clipped))
            }
        } else {
            setPageTitle("图片详情")
        }

        if (needClipPicture()) {
            try {
                iv_photo.visibility = View.GONE

                clip_view.visibility = View.VISIBLE
                iv.visibility = View.VISIBLE
                clipperView = ClipperView(this)
                tv_right.text = "确定"

                var b = BitmapFactory.decodeFile(intent.getStringExtra(Constants.PATH))

//                if (b.width > b.height) {  //横拍照注意
//                    val matrix = Matrix()
//                    matrix.setRotate(90f)
//                    val nw = b.width
//                    val nh = b.height
//                    b = Bitmap.createBitmap(b, 0, 0, nw, nh, matrix, true)
//                }
                iv.setImageBitmap(b)
                clipperView!!.setBitmap(b)
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }

        Glide.with(this).load(intent.getStringExtra(Constants.PATH)).placeholder(R.drawable.image_loading)
                .error(R.drawable.error_image).into(iv_photo)
        bindEvent()
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            var r = RectF(ll.left * 1.0f, ll.top * 1.0f, ll.right * 1.0f, ll.bottom * 1.0f)
            if (clipperView != null) {
                clipperView!!.setParentScaleRect(r)
            }
        }
    }

    var nLenStart: Double = 0.0
    private fun bindEvent() {
        iv_back.setOnClickListener {
            finishActivity()
        }
        tv_right.setOnClickListener {
            if (needClipPicture()) {
                showTipDialog(resources.getString(R.string.tips), resources.getString(R.string.clippview_msg), 0)
            }
        }
    }


    // 是否需要裁减  约定裁剪的 不用再裁剪，因为照片的宽高已经变化（？新建立）
    private fun needClipPicture(): Boolean {
        if (Constants.TYPE_ZCBM == intent.getStringExtra(Constants.ZPLX) && clipped) {
            return true
        }

        if (Constants.TYPE_TYH == intent.getStringExtra(Constants.ZPLX) && clipped) {
            return true
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishActivity()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun tipDialogOKListener(flag: Int) {
        showLoadingDialog()
        ThreadPoolManager.instance.execute(Runnable {
            val path = intent.getStringExtra(Constants.PATH)
            val file = File(path)
            val pPath = file.parentFile.path + "/" + System.currentTimeMillis() + ".jpg"
            BitmapUtils.saveBitmapToFile(ClipperView.getBitmap(), pPath)  //处理图片
            showLog("  ShowPhotoActivity $pPath")
            showLog("  ShowPhotoActivity $path")
            if (file.isFile) {  // 原图片要清除掉
                file.delete()
            }
            dismissLoadingDialog()
            intent.putExtra(Constants.CLIPP, "1")
            intent.putExtra(Constants.CLIPP_PICTURE_PATH, pPath)
            setResult(Activity.RESULT_OK, intent)
            finishActivity()
        })
    }

    override fun tipDialogNOListener(flag: Int) {
//        finishActivity()
    }

    private fun finishActivity() {
        finish()
        exitRotateAlphaAcaleAnimation()
    }

    override fun getLayout(): Int {
        return R.layout.activity_show_photo
    }
}
