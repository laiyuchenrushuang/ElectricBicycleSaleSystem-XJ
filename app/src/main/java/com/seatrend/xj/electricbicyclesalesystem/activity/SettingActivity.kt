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
import java.util.concurrent.locks.ReentrantLock


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
                showLog(commonResponse.responseString)
            }
            if (Constants.QH_SHENG.equals(commonResponse.getUrl())) {
                codeShengEntity = GsonUtils.gson(commonResponse.getResponseString(), CodeEntity::class.java)
                showLog(commonResponse.responseString)

            }
//            if (Constants.GET_USER_PERMMISION.equals(commonResponse.getUrl())) {
//                codePermissionEntity = GsonUtils.gson(commonResponse.getResponseString(), PermissionEnity::class.java)
//
//            }

            if (null != codeShengEntity && null != codeAllEntity) {
                showLog("写数据库1")
                //不开UI线程 程序卡
                writeToSqlte(codeShengEntity!!, codeAllEntity!!)
            }
        } catch (e: Exception) {
            showToast("同步失败")
            e.printStackTrace()
            dismissLoadingDialog()
        }
    }

    @Synchronized
    private fun writeToSqlte(codeSheng: CodeEntity, codeAll: CodeEntity) {
        ThreadPoolManager.instance.execute(Runnable {
            try {
                if (Looper.myLooper() == null) {
                    Looper.prepare()
                }
                showLog("写数据库-ready")
                showLog("删除数据库-")
                CodeTableSQLiteUtils.deleteAll(CodeTableSQLiteOpenHelper.TABLE_NAME)
                showLog("写区划数据库-")
                CodeTableSQLiteUtils.insertQhToDB(codeSheng.data)
                showLog("写其他数据库-")
                CodeTableSQLiteUtils.insert(codeAll.data)
                SharedPreferencesUtils.setIsFirst(false)
                showToast("同步成功")
                showLog("同步成功")
//                showLog(CodeTableSQLiteUtils.queryCodeTableCount())
                dismissLoadingDialog()
                Looper.loop()
            } catch (e: Exception) {
                dismissLoadingDialog()
                showToast("  " + e.message.toString())
            }
        })
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
                    showLog("同步成功")
                    tv_update_time.text = Constants.UPDATA_TIME
                }
            }
        }
    }

    override fun initView() {
        setPageTitle(getString(R.string.setting))
        tv_update_time.text = SharedPreferencesUtils.getVesionTime()
        mSettingPersenter = SettingPersenter(this)
        btn_server_setting.setOnClickListener {
            startActivity(Intent(this, ServiceSettingActivity::class.java))
        }

        btn_code_syn.setOnClickListener {
            showLoadingDialog()
            btn_code_syn.text = "代码同步"
            codeShengEntity = null
            codeAllEntity = null
            SharedPreferencesUtils.setIsFirst(true)

            var msg = Message.obtain()
            msg.what = REQUEST
            mHandler.sendMessage(msg)
        }
        btn_code_syn.setOnLongClickListener {
            btn_code_syn.text = resources.getString(R.string.dmtb, "" + CodeTableSQLiteUtils.queryCodeTableCount())
            true
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
        dismissLoadingDialog()
        super.onDestroy()
    }

    override fun getLayout(): Int {
        return R.layout.activity_setting
    }
}
