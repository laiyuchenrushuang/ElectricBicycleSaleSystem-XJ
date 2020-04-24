package com.seatrend.xj.electricbicyclesalesystem.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.createview.ClipperView
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarPhotoPersenter
import com.seatrend.xj.electricbicyclesalesystem.persenter.LoginPersenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.CarPhotoView
import com.seatrend.xj.electricbicyclesalesystem.view.LoginView
import kotlinx.android.synthetic.main.activity_user_password.*
import kotlinx.android.synthetic.main.common_title.*
import java.io.File
import java.util.ArrayList

class LoginByUserPasswordActivity : BaseActivity(), LoginView, CarPhotoView {


    private var zpdz: String = ""
    private var appdownloadurl: String? = null //apk 的url
    private var apkPath: String? = null //apk的路径
    var photoTagFace: Int = 0
    private var imgFile: File? = null
    private val CAMERA_REQUEST_CODE = 20
    private var mLoginPersenter: LoginPersenter? = null
    private var progressdialog: Dialog? = null
    private var progressBar: ProgressBar? = null
    private var tvPro: TextView? = null
    private var mCarPhotoPersenter: CarPhotoPersenter? = null

    override fun initView() {
        setPageTitle("用户登录")
        et_user.setText(SharedPreferencesUtils.getAdmain())

        appPermissionReq()
        if (AppUtils.isApkInDebug(this)) {
            et_user.setText("513822198909298761")
            et_pwd.setText("1q2w3e4r.")
        }

        tv_version.text = getString(R.string.cur_version, AppUtils.getVersionName(this))
        mLoginPersenter = LoginPersenter(this)
        mCarPhotoPersenter = CarPhotoPersenter(this)
        bindEvent()
    }

    var loginEntity: LoginEntity? = null
    var globalEntity: GlobalEnity? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {

        if (Constants.USER_LOGIN.equals(commonResponse.getUrl())) {
            try {
                loginEntity = GsonUtils.gson(commonResponse.getResponseString(), LoginEntity::class.java)

                if ("1".equals(loginEntity!!.data.updatePwd)) {
                    //强制修改密码
                    intent.putExtra("xm", loginEntity!!.data.sysUser.xm)
                    intent.putExtra("sfz", loginEntity!!.data.sysUser.sfzmhm)
                    intent.putExtra("yhdh", loginEntity!!.data.sysUser.yhdh)
                    intent.setClass(this, EmployeeChangePasswordActivity::class.java)
                    startActivity(intent)
                    return
                }
                goSavePhoto()
                UserInfo.XM = loginEntity!!.data.sysUser.xm
                UserInfo.SFZMHM = loginEntity!!.data.sysUser.sfzmhm
                UserInfo.GLBM = loginEntity!!.data.sysUser.glbm
                UserInfo.NewUserInfo.GLBM = loginEntity!!.data.sysUser.glbm
                UserInfo.XSDDM = loginEntity!!.data.sysUser.glbm
                UserInfo.GLBM = loginEntity!!.data.sysUser.glbm
                UserInfo.YHDH = loginEntity!!.data.sysUser.yhdh
                UserInfo.TOKEN = loginEntity!!.data.jwtToken
                UserInfo.JSLX = loginEntity!!.data.sysUser.jslx
                UserInfo.FZJG = if (null == loginEntity!!.data.seaDepartment) "" else loginEntity!!.data.seaDepartment.fzjg
                getGLBM()//管理部门名称和代码
                UserInfoActivity.mLoginEnity = loginEntity
                MainOtherActivity.mLoginEnity = loginEntity
            } catch (e: Exception) {
                e.printStackTrace()
                showToast(e.toString())
            }
        }
        if (Constants.GET_GLBM.equals(commonResponse.getUrl())) {
            val enity = GsonUtils.gson(commonResponse.getResponseString(), GLBMEnity::class.java)
            UserInfo.BMMC = enity.data.bmmc
            UserInfo.NewUserInfo.GLBM = enity.data.sjbm //上级部门
            val map = HashMap<String, String?>()
            map["glbm"] = enity.data.sjbm
            mLoginPersenter!!.doNetworkTask(map, Constants.GET_GLBMMC)
        }

        if (Constants.GLOABAL_PARAMETER.equals(commonResponse.getUrl())) {
            globalEntity = GsonUtils.gson(commonResponse.getResponseString(), GlobalEnity::class.java)
            if (!ObjectNullUtil.checknull(globalEntity!!.data) || globalEntity!!.data.size <= 0) {
                showToast("全局参数获取失败")
                return
            }
            for (db in globalEntity!!.data) {
                if ("SFQYDPXS".equals(db.gjz)) {
                    UserInfo.GlobalParameter.DPBJ = db.csz
                }
                if ("SFQYBXYZ".equals(db.gjz)) {
                    UserInfo.GlobalParameter.BXBJ = db.csz
                }
                if ("SFQYSFYZ".equals(db.gjz)) {
                    UserInfo.GlobalParameter.SFBJ = db.csz
                }
                if ("ZZXSZLQFS".equals(db.gjz)) {
                    UserInfo.GlobalParameter.LQBJ = db.csz
                }
                if ("ZPCFFS".equals(db.gjz)) {
                    UserInfo.GlobalParameter.CFBJ = db.csz
                }
                if ("SCSJLR".equals(db.gjz)) {
                    UserInfo.GlobalParameter.SCBJ = db.csz
                }
            }
            LoadingDialog.getInstance().dismissLoadDialog()
            startActivity(Intent(this, MainOtherActivity::class.java))
            SharedPreferencesUtils.setAdmain(et_user.text.toString())
            if (imgFile != null && !TextUtils.isEmpty(imgFile!!.path)) {
                PhotoFileUtils.deleteFile(imgFile!!.path)
            }
//            finish()
        }

        if (Constants.GET_GLBMMC.equals(commonResponse.getUrl())) {
            getQJCS()//全局参数
            val enity = GsonUtils.gson(commonResponse.getResponseString(), GLBMMCEnity::class.java)
            UserInfo.NewUserInfo.BMMC = enity.data//上级部门名称
        }
        if (Constants.PHOTO_INSERT.equals(commonResponse.getUrl())) {
            var enity = GsonUtils.gson(commonResponse.getResponseString(), PhotoIdEnity::class.java)
            if (!ObjectNullUtil.checknull(enity.data, enity.data.id)) {
                showToast("获取照片id异常")
                return
            }
            val map = HashMap<String, String?>()
            map["sfzmhm"] = et_user.text.toString()
            map["czpt"] = Constants.CZPT
            map["zpdz"] = enity.data.id
            mLoginPersenter!!.doNetworkTask(map, Constants.USER_POST_IMAGE)
        }

        //增加这个判断的目的删除本地的图片

        if (Constants.USER_POST_IMAGE.equals(commonResponse.getUrl()) && imgFile != null && !TextUtils.isEmpty(imgFile!!.path)) {
            PhotoFileUtils.deleteFile(imgFile!!.path)
        }
    }

    private fun getQJCS() {
        mLoginPersenter!!.doNetworkTask(HashMap<String, String>(), Constants.GLOABAL_PARAMETER)
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        if (Constants.PHOTO_INSERT != commonResponse.url) {
            if (Constants.USER_LOGIN == commonResponse.url) {
                try {
                    showLog(commonResponse.getResponseString())
                    var urlenity = GsonUtils.gson(commonResponse.getResponseString(), URLEnity::class.java)

                    if (Constants.GENGXIN == urlenity!!.code) {
                        showTipsDialog(urlenity.data.updatemessage)
                        appdownloadurl = urlenity.data.downloadurl
                        return
                    }
                } catch (e: Exception) {
                    showToast(e.message.toString())
                }
            }
            showErrorDialog(commonResponse.getResponseString())
        }

        //增加这个判断的目的删除本地的图片

        if (Constants.USER_POST_IMAGE.equals(commonResponse.getUrl()) && imgFile != null && !TextUtils.isEmpty(imgFile!!.path)) {
            PhotoFileUtils.deleteFile(imgFile!!.path)
        }
    }

    override fun downloadProgress(commonProgress: CommonProgress) {
        if (progressdialog == null) {
            showProgressDialog()
        }
        if (progressdialog != null && progressdialog!!.isShowing) {
            progressBar!!.progress = java.lang.Double.parseDouble(commonProgress.getProgress()).toInt()
            tvPro!!.text = String.format("%s%%", commonProgress.getProgress())
        }
        if ("100.0" == commonProgress.getProgress() && progressdialog != null) {
            progressdialog!!.dismiss()
            installApk()
        }
    }

    private fun installApk() {
        AppUtils.installApp(this, apkPath)
    }

    private fun showProgressDialog() {
        progressdialog = ProgressDialog.getProgressDialog(this)
        progressBar = progressdialog!!.findViewById<ProgressBar>(R.id.pb_pro)
        tvPro = progressdialog!!.findViewById<TextView>(R.id.tv_pro)
        val tvMsg = progressdialog!!.findViewById<TextView>(R.id.tv_msg)
        tvMsg.text = getString(R.string.downloading)
        progressdialog!!.show()
    }

    private fun getGLBM() {
        if (!"2".equals(UserInfo.JSLX) && !GLBMUtil.isSearchLargerGlbm(UserInfo.GLBM)) {
            val map = HashMap<String, String?>()
            map["glbm"] = UserInfo.GLBM
            mLoginPersenter!!.doNetworkTask(map, Constants.GET_GLBMMC)
        } else {
            val map = HashMap<String, String?>()
            map["glbm"] = UserInfo.GLBM
            mLoginPersenter!!.doNetworkTask(map, Constants.GET_GLBM)
        }
    }

    private fun goSavePhoto() {
        mCarPhotoPersenter!!.uploadFile(imgFile!!, HashMap(), Constants.PHOTO_INSERT)
    }

    private fun bindEvent() {
        iv_back.visibility = View.GONE

        btn_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        iv_photo.setOnClickListener {

            if (photoTagFace == 0) {
                getPicFromCamera()
            } else {
                val intent = Intent(this, ShowPhotoActivity::class.java)
                intent.putExtra(Constants.PATH, imgFile!!.path)
                intent.putExtra(Constants.ZPMC, "现场照片")
//                intent.putExtra(Constants.ZPLX, "A1")  //测试 要删除
//                intent.putExtra(Constants.CLIPP, "1") //测试 要删除
                startActivity(intent)
                startRotateAlphaAcaleAnimation()
            }
        }
        iv_delete.setOnClickListener {
            iv_delete.visibility = View.GONE
            Glide.with(this).load(R.drawable.take_photo_head).centerCrop().error(R.drawable.error_image).into(iv_photo)
            photoTagFace = 0
            imgFile = null
        }
        btn_login.setOnClickListener {
            if (TextUtils.isEmpty(et_user.text)) {
                showToast("请输入账号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_pwd.text)) {
                showToast("请输入密码")
                return@setOnClickListener
            }
            if (null == imgFile || TextUtils.isEmpty(imgFile!!.path)) {
                showToast("请拍摄图片")
                return@setOnClickListener
            }
            if (SharedPreferencesUtils.getIsFirst()) {
                showToast("请先同步代码")
                return@setOnClickListener
            }

            loginEntity = null //reset
            showLoadingDialog()
            val map = HashMap<String, String?>()
            map.put("username", et_user.text.toString())
            map.put("password", et_pwd.text.toString())
            map.put("appVersion", AppUtils.getVersionName(this))
            map.put("ly", Constants.CZPT) // app的登录
            mLoginPersenter!!.doNetworkTask(map, Constants.USER_LOGIN)
        }
    }

    private fun getPicFromCamera() {
        if (!appGetPermission()) {
            appPermissionReq()
            return
        }


        deleteAllFileImage()
        val tempFile = File(Constants.IMAGE_PATH)//
        val imageUri: Uri
        if (!tempFile.exists()) {
            tempFile.mkdirs()
        }
        imgFile = File(tempFile, System.currentTimeMillis().toString() + ".jpg")
        if (Build.VERSION.SDK_INT >= 24) {//判断版本
            imageUri = FileProvider.getUriForFile(this, getString(R.string.authority), imgFile)
        } else {
            imageUri = Uri.fromFile(imgFile)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    //删除上次缓存的照片（定时器使用上传的缓存照片）
    private fun deleteAllFileImage() {
        PhotoFileUtils.deleteFile(File(Constants.IMAGE_PATH))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            showLoadingDialog()
//            showLog(" RRRR "+BitmapUtils.getBitmapWithRightRotation(imgFile!!.path))
            ThreadPoolManager.instance.execute(Runnable {
                val bt: Bitmap = BitmapUtils.getSmallBitmap(imgFile!!.path)
                val bitmap = BitmapUtils.compressImage(bt)
                BitmapUtils.saveBitmap(bitmap, imgFile!!.name.replace(".jpg", ""))
                bitmap?.recycle()
                runOnUiThread {
                    LoadingDialog.getInstance().dismissLoadDialog()
                    Glide.with(this).load(imgFile).centerCrop().error(R.drawable.error_image).into(iv_photo)
                    iv_delete.visibility = View.VISIBLE
                    photoTagFace = 1
                }
            })
        }

//        if (requestCode == 219 && resultCode == Activity.RESULT_OK) {
//            val path = data!!.getStringExtra("clipper_path")
//            ThreadPoolManager.instance.execute(Runnable {
//                Glide.get(this).clearDiskCache()
//                runOnUiThread {
//                    Glide.get(this).clearMemory()
//                    Glide.with(this).load(path).centerCrop().error(R.drawable.error_image).into(iv_photo)
//                }
//            })
//        }

        else {
            imgFile = null
        }

    }

    //在线更新apk

    private fun showTipsDialog(mes: String) {
        val mDialog = Dialog(this)
        mDialog.setContentView(R.layout.dialog_update_tips)
        mDialog.setCanceledOnTouchOutside(false)
        val tvMsg = mDialog.findViewById<TextView>(R.id.tv_tips_msg)
        val btnOk = mDialog.findViewById<Button>(R.id.btn_ok)
        val btnCancel = mDialog.findViewById<Button>(R.id.btn_cancel)
        tvMsg.text = getString(R.string.update_message, mes)
        tvMsg.setLineSpacing(6f, 1f)
        btnOk.setOnClickListener {
            mDialog.dismiss()
            downloadApk()
        }
        btnCancel.setOnClickListener { mDialog.dismiss() }
        mDialog.show()
        mDialog.setOnKeyListener { _, _, _ -> true }
    }

    private fun downloadApk() {
        val file = File(Constants.FILE_PATH)
        if (!file.exists()) {
            file.mkdirs()
        }
        val f = File(file, "ElectricBicycleSaleSystem-XJ.apk")
        apkPath = f.path
        val map = HashMap<String, String>()
        map["url"] = ""
        mLoginPersenter!!.downloadFile(HashMap(), appdownloadurl, f)
    }


    override fun getLayout(): Int {
        return R.layout.activity_user_password
    }
}
