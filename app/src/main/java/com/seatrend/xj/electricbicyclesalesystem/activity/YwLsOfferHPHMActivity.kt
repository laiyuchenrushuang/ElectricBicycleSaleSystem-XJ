package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.util.CheckBoxUtils
import com.seatrend.xj.electricbicyclesalesystem.util.OtherUtils
import kotlinx.android.synthetic.main.activity_ls_offer.*
import kotlinx.android.synthetic.main.activity_register.ll_yjlq
import kotlinx.android.synthetic.main.activity_register.rb_lqfs_no
import kotlinx.android.synthetic.main.activity_register.rb_lqfs_ok
import kotlinx.android.synthetic.main.activity_register.rb_zzxsz_no
import kotlinx.android.synthetic.main.activity_register.rb_zzxsz_ok
import kotlinx.android.synthetic.main.bottom_button.*

/**
 * Created by ly on 2019/9/30 13:09
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class YwLsOfferHPHMActivity: BaseActivity() {

    private var headPhoto: ByteArray? = null//头像照片
    private var FACE_COMPARE_CODE: Int = 11

    override fun initView() {
        setPageTitle("临时号牌申请")
        initData()
        bindEvent()
    }

    private fun bindEvent() {
        rb_zzxsz_ok.isChecked = true
        rb_lqfs_ok.isChecked = true
        CheckBoxUtils.setListener(rb_zzxsz_ok, rb_zzxsz_no)
        CheckBoxUtils.setListenerAndView(rb_lqfs_ok, rb_lqfs_no, ll_yjlq)

        tv_hqrq.setOnClickListener {
            showTimeDialog(tv_hqrq)
        }

        iv_syr_scan.setOnClickListener {
            goNfcReadPlugin(Constants.SFZ_SYR)
        }

        iv_dlr_scan.setOnClickListener {
            goNfcReadPlugin(Constants.SFZ_DLR)
        }

        iv_yj_scan.setOnClickListener {
            goNfcReadPlugin(Constants.SFZ_YJ)
        }

        bt_next.setOnClickListener {
            //            goNfcReadPlugin() //人脸比对
            startActivity(Intent(this, AutographActivity::class.java))
            CollectPhotoActivity.photoEntranceFlag = Constants.CAR_LSHP
            CollectPhotoActivity.dzpzFlag = rb_zzxsz_ok.isChecked
        }
    }

    private fun initData() {
        bt_next.setText("人证核验")
        initShowScrean()
    }

    private fun initShowScrean() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                ID_CARD_READ_CODE -> {
                    showToast("身份证已读取")
//                    data.getStringExtra(Constants.NAME)
//                    sfzhm = data.getStringExtra(Constants.NUMBER)
//                    data.getStringExtra(Constants.ADDRESS)
                    headPhoto = data.getByteArrayExtra(Constants.PHOTO)

                    if (null != headPhoto) {
                        OtherUtils.goFaceComparePlugin(this, headPhoto, FACE_COMPARE_CODE)
                    }
                }

                FACE_COMPARE_CODE -> {

                }

                Constants.SFZ_SYR -> {
                    ed_syr_sfz.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NUMBER))
                    ed_syr_xm.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NAME))
                }
                Constants.SFZ_DLR -> {
                    ed_dlr_sfz.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NUMBER))
                    ed_dlr_xm.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NAME))
                }
                Constants.SFZ_YJ -> {
                    ed_yj_sfz.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NUMBER))
                    ed_yj_xm.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NAME))
                }
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_ls_offer
    }
}