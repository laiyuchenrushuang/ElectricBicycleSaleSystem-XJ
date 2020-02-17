package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.text.TextUtils
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.CheckEditTxetUtils
import com.seatrend.xj.electricbicyclesalesystem.util.CheckPawUtil
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_change_psw.*
import kotlinx.android.synthetic.main.bottom_button.*

class EmployeeChangePasswordActivity : BaseActivity(), NormalView {
    private var mNormalPresenter: NormalPresenter? = null
    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("密码修改")
        bt_next.text = "保存"
        mNormalPresenter = NormalPresenter(this)
        getData()
        bindEvent()
    }

    private fun getData() {
        tv_xm.text = if (null == intent.getStringExtra("xm")) "" else intent.getStringExtra("xm")
        tv_sfz.text = if (null == intent.getStringExtra("sfz")) "" else intent.getStringExtra("sfz")
    }

    private fun bindEvent() {
        //屏蔽表情
        et_old_psw.filters = arrayOf(inputEmojiFilter)
        et_psw1.filters = arrayOf(inputEmojiFilter)
        et_psw2.filters = arrayOf(inputEmojiFilter)

        bt_next.setOnClickListener {
            if (!CheckEditTxetUtils.checkEditextValuable(et_old_psw)) {
                showToast("原密码不为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_psw1.text.toString()) || !et_psw1.text.toString().equals(et_psw2.text.toString())) {
                showToast("两次新密码不一致")
                return@setOnClickListener
            }

            if (!CheckPawUtil.isSixPaw(et_psw1.text.toString()) || !CheckPawUtil.isSixPaw(et_psw2.text.toString())) {
                showToast("新密码是否是6~16位的字母、数字以及特殊符号？")
                return@setOnClickListener
            }
            showLoadingDialog()
            val map = HashMap<String, String?>()
            map["yhdh"] = intent.getStringExtra("yhdh")
            map["oldPass"] = et_old_psw.text.toString()
            map["newPass"] = et_psw1.text.toString()
            mNormalPresenter!!.doNetworkTask(map, Constants.USER_PSW_UPDATE)
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_change_psw
    }
}
