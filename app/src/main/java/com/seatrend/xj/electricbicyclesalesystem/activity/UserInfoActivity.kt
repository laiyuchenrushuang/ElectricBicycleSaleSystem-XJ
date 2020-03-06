package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.text.method.ScrollingMovementMethod
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.ActivityCollector
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.BaseEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.LoginEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService
import com.seatrend.xj.electricbicyclesalesystem.util.*
import kotlinx.android.synthetic.main.activity_main_other.*
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.activity_user_info.iv_head
import kotlinx.android.synthetic.main.common_title.*
import java.lang.Exception
import java.util.regex.Pattern

/**
 * Created by ly on 2019/10/21 14:01
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class UserInfoActivity : BaseActivity() {
    companion object {
        var mLoginEnity: LoginEntity? = null
    }


    override fun initView() {
        setPageTitle("个人信息")
        initHeadView()
        bindEvent()
        getData()
    }

    private fun getData() {
        try {
            if("2".equals(UserInfo.JSLX)){
                ViewShowUtils.showVisibleView(ll_fwz,line_fwz)
            }else{
                ViewShowUtils.showGoneView(ll_fwz,line_fwz)
            }
            tv_xm.text = UserInfo.XM
            tv_sfz.text = UserInfo.SFZMHM
            tv_glbm.text = UserInfo.NewUserInfo.BMMC
            tv_fwz.text = UserInfo.BMMC
            tv_jslx.text = JslxUtils.getJslxMc(mLoginEnity!!.data.sysUser.jslx)

            val regEx = "(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)"
            val result = SharedPreferencesUtils.getNetworkAddress()
            val p = Pattern.compile(regEx)
            val m = p.matcher(result)
            if (m.find()) {
                tv_ip.text = m.group(1)
                tv_dkh.text = m.group(2)
            }

            RoundHeadImageViewUtil.loadImageByPath(this, LoadUrlUtils.loadurl(intent.getStringExtra("photo_url")), iv_head)
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(e.toString())
        }
    }

    private fun initHeadView() {
        RoundHeadImageViewUtil.loadImage(this, iv_head)
    }

    private fun bindEvent() {
        btn_exit.setOnClickListener {
            exitLogin()
        }
        btn_change_psw.setOnClickListener {
            intent.setClass(this, EmployeeChangePasswordActivity::class.java)
            intent.putExtra("xm", tv_xm.text.toString())
            intent.putExtra("sfz", tv_sfz.text.toString())
            intent.putExtra("yhdh", UserInfo.YHDH)
            startActivity(intent)
        }
//        iv_head.setOnClickListener {
//            intent.setClass(this, ShowPhotoActivity::class.java)
//            intent.putExtra(Constants.PATH,  LoadUrlUtils.loadurl(intent.getStringExtra("photo_url")))
//            startActivity(intent)
//        }

        iv_back.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout)
        }
        tv_glbm!!.movementMethod = ScrollingMovementMethod.getInstance()
        tv_fwz!!.movementMethod = ScrollingMovementMethod.getInstance()
    }

    private fun exitLogin() {
        showLoadingDialog()
        val map = HashMap<String, String?>()
        map.put("username", UserInfo.XM)
        HttpService.getInstance().getDataFromServer(map, Constants.EXIT_LOGIN, Constants.GET, object : BaseModule() {
            override fun doWork(map: Map<String, String?>, url: String) {
                showToast("退出失败")
            }

            override fun doWorkResults(commonResponse: CommonResponse, isSuccess: Boolean) {

                try {
                    LoadingDialog.getInstance().dismissLoadDialog()
                    val entity = GsonUtils.gson(commonResponse.getResponseString(), BaseEntity::class.java)
                    showToast(entity.message)
                    ActivityCollector.finishAll()
                    startActivity(Intent(this@UserInfoActivity, LoginActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                    finish()
                }
            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.activity_user_info
    }
}