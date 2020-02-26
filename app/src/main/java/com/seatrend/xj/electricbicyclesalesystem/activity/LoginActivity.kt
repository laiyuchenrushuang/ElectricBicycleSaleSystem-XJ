package com.seatrend.xj.electricbicyclesalesystem.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonProgress
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.LoginEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.LoginPersenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.common_title.*
import java.io.File
import java.util.ArrayList



@Suppress("PLUGIN_WARNING", "DEPRECATED_IDENTITY_EQUALS")
class LoginActivity : BaseActivity(), LoginView {
    override fun downloadProgress(commonProgress: CommonProgress) {

    }


    private var mLoginPersenter: LoginPersenter? = null
    private var sfzhm = ""
    private var FACE_COMPARE_CODE: Int = 11
    private var headPhoto: ByteArray? = null//头像照片
    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()

        if (commonResponse.getUrl().equals(Constants.USER_LOGIN)) {
            try {
                val loginEntity = GsonUtils.gson(commonResponse.getResponseString(), LoginEntity::class.java)
                UserInfo.XM = loginEntity.data.sysUser.xm
                UserInfo.SFZMHM = loginEntity.data.sysUser.sfzmhm
                UserInfo.GLBM = loginEntity.data.sysUser.glbm
                UserInfo.XSDDM = loginEntity.data.seaDepartment.glbm
                UserInfo.YHDH = loginEntity.data.sysUser.sfzmhm
                UserInfo.BMQC = loginEntity.data.seaDepartment.bmqc
                UserInfo.TOKEN = loginEntity.data.jwtToken
                UserInfo.JSLX = loginEntity.data.sysUser.jslx
                UserInfoActivity.mLoginEnity = loginEntity
                startActivity(Intent(this, MainOtherActivity::class.java))
                goSavePhoto()
//                versionCheck()   //检测版本

                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun goSavePhoto() {
//        RoundHeadImageViewUtil.imagePath = File(ByteToFileUtil.getLpginPhotoPath())
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        intNFC()
        setPageTitle(getString(R.string.electric_bicycle_register))
        mLoginPersenter = LoginPersenter(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            appRequestPermissions()
        }
        iv_back.visibility = View.GONE
        btn_next.setOnClickListener {

            startActivity(Intent(this, LoginByUserPasswordActivity::class.java))
//            if (!SharedPreferencesUtils.getIsFirst()) {
//                if (ed_username != null && !TextUtils.isEmpty(ed_username.text.toString())) {
//                    doLogin(ed_username.text.toString())
//                } else {
//                    showToast("输入账号为空，请重新输入")
//                }
//                // 511365199411210351
//            } else {
//                showToast("请先同步代码")
//            }
        }
        btn_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

//        tvRight!!.setText("帮助")
//        tvRight!!.setOnClickListener {
//            startActivity(Intent(this, HelpActivity::class.java))
//        }
        tv_version.text = getString(R.string.cur_version, AppUtils.getVersionName(this))
        btn_system!!.setOnClickListener {
            goNfcReadPlugin(ID_CARD_READ_CODE)
        }
    }

    private fun intNFC() {

//        if ("HC" != AppUtils.getSystemProperty()) {
//            btn_system.setBackgroundColor(ContextCompat.getColor(this,R.color.black_50))
//            btn_system.isEnabled = false
//        }
        val pm = packageManager
        if(!pm.hasSystemFeature(PackageManager.FEATURE_NFC)){
            btn_system.setBackgroundColor(ContextCompat.getColor(this,R.color.black_50))
            btn_system.isEnabled = false
            btn_system.visibility =View.GONE
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun appRequestPermissions() {

        val permission = ArrayList<String>()
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.CAMERA)
        }

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (checkSelfPermission(Manifest.permission.NFC) !== PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.NFC)
        }

        if (permission.size > 0) {
            ActivityCompat.requestPermissions(this@LoginActivity, permission.toTypedArray(), 1)
        }
    }

    private fun doLogin(sfzhm: String) {
        showLoadingDialog()
        val map = HashMap<String, String?>()
        map.put("sfzmhm", sfzhm)
        mLoginPersenter!!.doNetworkTask(map, Constants.USER_LOGIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ID_CARD_READ_CODE && resultCode == Activity.RESULT_OK && data != null) {
            showToast("身份证已读取")
//            data.getStringExtra(Constants.NAME)
            sfzhm = data.getStringExtra(Constants.NUMBER)
//            data.getStringExtra(Constants.ADDRESS)
            headPhoto = data.getByteArrayExtra(Constants.PHOTO)
            ByteToFileUtil.bytesToImageFile(headPhoto)

            if (null != headPhoto) {
//                OtherUtils.goFaceComparePlugin(this, headPhoto, FACE_COMPARE_CODE)
                getFaceCamera(FACE_COMPARE_CODE)
            }else{
                showToast("nfc身份证照片读取失败")
            }
        } else if (requestCode == FACE_COMPARE_CODE && resultCode == Activity.RESULT_OK) {
//            doLogin(sfzhm)
        }
    }
}
