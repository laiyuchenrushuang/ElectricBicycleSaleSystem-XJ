package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteOpenHelper
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.SettingPersenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.SettingView
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity(), SettingView {

    private val SUCCESS: Int = 2
    private val REQUEST: Int = 1
    private var codeShengEntity: CodeEntity? = null  //省区划信息  单独拿出来去获取，整体获取
    private var codeAllEntity: CodeEntity? = null
    private var codePermissionEntity: PermissionEnity? = null //权限
    private var mSettingPersenter: SettingPersenter? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        try {
            if (commonResponse.getUrl().equals(Constants.GET_ALL_CODE)) {
                codeAllEntity = GsonUtils.gson(commonResponse.getResponseString(), CodeEntity::class.java)

            }
            if (Constants.QH_SHENG.equals(commonResponse.getUrl())) {
                codeShengEntity = GsonUtils.gson(commonResponse.getResponseString(), CodeEntity::class.java)

            }
//            if (Constants.GET_USER_PERMMISION.equals(commonResponse.getUrl())) {
//                codePermissionEntity = GsonUtils.gson(commonResponse.getResponseString(), PermissionEnity::class.java)
//
//            }

            if (null != codeShengEntity && null != codeAllEntity) {
                ThreadPoolManager.instance.execute(Runnable {
                    try {
                        Looper.prepare()
                        CodeTableSQLiteUtils.deleteAll(CodeTableSQLiteOpenHelper.TABLE_NAME)
                        CodeTableSQLiteUtils.insertQhToDB(codeShengEntity!!.data)
                        CodeTableSQLiteUtils.insert(codeAllEntity!!.data)
//                CodeTableSQLiteUtils.insertPermmisionToDB(codePermissionEntity!!.data)
                        SharedPreferencesUtils.setIsFirst(false)
                        val msg = Message.obtain()
                        msg.what = SUCCESS
                        mHandler.sendMessage(msg)
                        Looper.loop()
                    } catch (e: Exception) {
                        showToast(e.message.toString())
                    }
                })
            }
        } catch (e: Exception) {
            showToast("同步失败")
            e.printStackTrace()
            dismissLoadingDialog()
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
        if (Constants.QH_SHENG.equals(commonResponse.getUrl()) || commonResponse.getUrl().equals(Constants.GET_ALL_CODE)) {
            CodeTableSQLiteUtils.deleteAll(CodeTableSQLiteOpenHelper.TABLE_NAME)
            SharedPreferencesUtils.setIsFirst(true)  // 避免请求失败 二次进入数据为空的情况
            codeAllEntity = null
            codeShengEntity = null
        }
    }

    @SuppressLint("HandlerLeak")
    var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                REQUEST -> {
//                    Thread {
//                        Looper.prepare()
                        getAllCode()
//                        Looper.loop()
//                    }.start()
                }
                SUCCESS -> {
                    dismissLoadingDialog()
                    showToast("同步成功")
                    tv_update_time.text = Constants.UPDATA_TIME
                }
            }
        }
    }

    override fun initView() {
        setPageTitle(getString(R.string.setting))
        tv_update_time.text = SharedPreferencesUtils.getVesionTime();
        mSettingPersenter = SettingPersenter(this)
        btn_server_setting.setOnClickListener {
            startActivity(Intent(this, ServiceSettingActivity::class.java))
        }

        btn_code_syn.setOnClickListener {
            showLoadingDialog()
            codeShengEntity = null
            codeAllEntity = null
            var msg = Message.obtain()
            msg.what = REQUEST
            mHandler.sendMessage(msg)
        }
        btn_video.setOnClickListener {
//            startActivity(Intent(this, PDFActivity::class.java))
        }
    }

    private fun getAllCode() {
        mSettingPersenter!!.doNetworkTask(HashMap(), Constants.GET_ALL_CODE) //所有的
        mSettingPersenter!!.doNetworkTask(HashMap(), Constants.QH_SHENG)  //区划
//        mSettingPersenter!!.doNetworkTask(HashMap(), Constants.GET_USER_PERMMISION)  //权限
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getLayout(): Int {
        return R.layout.activity_setting
    }
}
