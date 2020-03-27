package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.PayPostEnity
import com.seatrend.xj.electricbicyclesalesystem.manager.AppManager
import com.seatrend.xj.electricbicyclesalesystem.util.DMZUtils
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import kotlinx.android.synthetic.main.activity_pay.*
import kotlinx.android.synthetic.main.bottom_button.*
import java.util.*
import java.io.IOException
import kotlin.collections.ArrayList


/**
 * Created by ly on 2019/10/22 10:11
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class PayActivity : BaseActivity() {
    private var isStart = true

    var TIME_SPACE = 1 * 1000L
    var TIME_TOTAL = 60 * 1000L
    var TIME_END = -1
    private var count = TIME_TOTAL.toInt()/TIME_SPACE.toInt()
    @SuppressLint("HandlerLeak","SetTextI18n")
    var mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            tv_time.text = ""+count-- + "s后刷新"
            if(count <= TIME_END){
                count = TIME_TOTAL.toInt()/TIME_SPACE.toInt()
                updateQCode()
            }
        }
    }

    var timer: Timer? = null

    var task: TimerTask = object : TimerTask() {
        override fun run() {
            try {
                if (isStart) {
                    mHandler.sendEmptyMessage(0)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        isStart = true
        count = TIME_TOTAL.toInt()/TIME_SPACE.toInt()

        updatePayState()
        updateQCode()
    }

    override fun onStop() {
        super.onStop()
        isStart = false
    }

    override fun initView() {
        setPageTitle("牌照收费")
        bindEvent()
        getData()
        timer = Timer()
        timer!!.schedule(task, 0, TIME_SPACE)
    }

    private fun getData() {
        tv_xm.text = intent.getStringExtra("syr")
        tv_sfzhm.text = intent.getStringExtra("sfz")
        tv_hphm.text = intent.getStringExtra("hphm")
        bt_next.text = "完成"
    }

    private fun bindEvent() {
        bt_next.setOnClickListener {
            val intent = Intent(this, YWCheckActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP  //致于栈顶
            startActivity(intent)
            finish()
        }
        btn_update.setOnClickListener {
            count = TIME_TOTAL.toInt()/TIME_SPACE.toInt()
            updateQCode()
        }
        btn_pay_state.setOnClickListener {
            updatePayState()
        }
    }

    /**
     * 更新支付状态
     */
    private fun updatePayState() {
        checkTvPayState()
        showToast("更新支付状态")
    }

    private fun checkTvPayState() {
        if("未支付".equals(tv_pay_state.text.toString())){
            tv_pay_state.setTextColor(Color.RED)
        }else{
            tv_pay_state.setTextColor(Color.BLACK)
        }
    }

    /**
     * 更新二维码
     */
    private fun updateQCode() {
        showToast("更新二维码状态")
//        Glide.with(this).load(StringUtils.createQRCode("url")).error(R.drawable.error_image).into(iv_ewm)
//        var url = "url"+System.currentTimeMillis()
        var list = ArrayList<PayPostEnity.FeeDatas> ()
        var feeDatas = PayPostEnity.FeeDatas()
        feeDatas.amount = 1
        feeDatas.price = tv_money.text.toString().toInt()
        feeDatas.busCode = intent.getStringExtra("ywlx")
        list.add(feeDatas)

        var payenity = PayPostEnity()
        payenity.feeDatas = list
        payenity.money = 50
        payenity.payerName = tv_xm.text.toString()
        payenity.certNo = tv_sfzhm.text.toString()
        payenity.amount = 1
        payenity.desc = "关于"+ DMZUtils.getDMSM(Constants.YWLX,intent.getStringExtra("ywlx"))+"的支付"
//        payenity.notifyUrl

        var url = GsonUtils.toJson(payenity)

        var bm = StringUtils.createQRCode(url)
        if(!TextUtils.isEmpty(url)){
            iv_ewm.setImageBitmap(bm)
        }else{
            iv_ewm.setImageBitmap(BitmapFactory.decodeResource(resources,R.drawable.error_image))
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_pay
    }
}