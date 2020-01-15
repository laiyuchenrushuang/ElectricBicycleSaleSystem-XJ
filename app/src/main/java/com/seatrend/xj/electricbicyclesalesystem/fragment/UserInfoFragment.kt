package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.LoginActivity
import com.seatrend.xj.electricbicyclesalesystem.common.*
import com.seatrend.xj.electricbicyclesalesystem.entity.BaseEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog
import com.seatrend.xj.electricbicyclesalesystem.util.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_user_info.*
import java.lang.Exception

class UserInfoFragment : BaseFragment() {



    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.activity_user_info, container, false)
    }

    override fun initView() {

        tv_sfz.text= UserInfo.SFZMHM
        tv_glbm.text= UserInfo.GLBM
        tv_xm.text= UserInfo.XM
        tv_ip.text = SharedPreferencesUtils.getIpAddress()
        tv_dkh.text = SharedPreferencesUtils.getPort()

        btn_exit.setOnClickListener {
            exitLogin()
        }
    }

    private fun exitLogin(){
        LoadingDialog.getInstance().showLoadDialog(context)
        val map=HashMap<String,String>()
        map.put("username", UserInfo.XM)
        HttpService.getInstance().getDataFromServer(map,Constants.EXIT_LOGIN,Constants.GET,object : BaseModule() {
            override fun doWork(map: Map<String, String?>, url: String) {
                showToast("退出失败")
            }

            override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {

                try {
                    LoadingDialog.getInstance().dismissLoadDialog()
                   val entity= GsonUtils.gson(commonResponse.getResponseString(), BaseEntity::class.java)
                    showToast(entity.getMessage())
                    ActivityCollector.finishAll()
                    startActivity(Intent(context, LoginActivity::class.java))
                    activity.finish()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        })
    }
}
