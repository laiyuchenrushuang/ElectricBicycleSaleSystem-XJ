package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.BaseEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_exit_login.*
import java.lang.Exception

@SuppressLint("Registered")
class ExitLoginActivity : BaseActivity() {

    override fun initView() {
        setPageTitle("退出登录")
//        iv_read.setOnClickListener {
//            goNfcReadPlugin()
//        }
        btn_exit.setOnClickListener {
            if (!TextUtils.isEmpty(et_sfzhm.text.toString()) && et_sfzhm.text.toString().trim().length>0){
                exitLogin();
            }else{
                showToast("身份证明号码不可为空")
            }
        }
    }

    private fun exitLogin(){
        showLoadingDialog()
        val map=HashMap<String,String>()
        map.put("username", et_sfzhm.text.toString())
        HttpService.getInstance().getDataFromServer(map, Constants.EXIT_LOGIN, Constants.GET,object : BaseModule() {
            override fun doWork(map: Map<String, String?>, url: String) {
                showToast("退出失败")
            }
            override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {
                try {
                    LoadingDialog.getInstance().dismissLoadDialog()
                    val entity= GsonUtils.gson(commonResponse.getResponseString(), BaseEntity::class.java)
                    showToast(entity.getMessage())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==ID_CARD_READ_CODE && resultCode== Activity.RESULT_OK && data!=null){
            showToast("身份证已读取")
            et_sfzhm.setText(data.getStringExtra(Constants.NUMBER))
        }
    }


    override fun getLayout(): Int {
        return R.layout.activity_exit_login;
    }
}
