package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.ThreeCEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_ccc_detail.*
import java.lang.Exception

@SuppressLint("Registered")
class CccDetailActivity : BaseActivity(), NormalView {
    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        val cccdata = GsonUtils.gson(commonResponse.getResponseString(), ThreeCEnity::class.java)
        runOnUiThread {
            showData(cccdata)
        }
    }

    private fun showData(entity: ThreeCEnity?) {
        try {
            if (entity == null || entity.data == null || entity.data.threeCertificates == null || entity.data.threeCertificates.data == null) {
                showToast("获取3C技术参数失败")
                return
            }
            carinfo_jscs_zcbmwz.text = StringUtils.isNull(entity.data.threeCertificates.data.codeOnFrame.trim()) //车架上整车编码的位置
            carinfo_jscs_clzwsb.text = StringUtils.isNull(entity.data.threeCertificates.data.trademarkCn)//车辆中文商标
            carinfo_jscs_cpxh.text = StringUtils.isNull(entity.data.threeCertificates.data.productModel)//产品型号
            carinfo_jscs_mpgdwz.text =StringUtils.isNull(entity.data.threeCertificates.data.fixedPositionName)//铭牌固定位置
            carinfo_jscs_cphgzbh.text = StringUtils.isNull(entity.data.threeCertificates.data.qualificationCode)//产品合格证编号
            carinfo_jscs_ccczsbh.text = StringUtils.isNull(entity.data.threeCertificates.data.certcode)//CCC证书编号
            carinfo_jscs_ccczsfzrq.text = StringUtils.isNull(entity.data.threeCertificates.data.certExpiryDate)//CCC证书有效期止日期
            carinfo_jscs_csys.text = StringUtils.isNull(entity.data.threeCertificates.data.color)//车颜色
            carinfo_jscs_c.text = StringUtils.isNull(entity.data.threeCertificates.data.length)//长
            carinfo_jscs_k.text = StringUtils.isNull(entity.data.threeCertificates.data.width)//宽
            carinfo_jscs_g.text = StringUtils.isNull(entity.data.threeCertificates.data.height)//高
            carinfo_jscs_zbzl.text = StringUtils.isNull(entity.data.threeCertificates.data.weight)//整备质量
            carinfo_jscs_zgsjcs.text = StringUtils.isNull(entity.data.threeCertificates.data.maxSpeed)//最高时速
            carinfo_jscs_clzzs.text = StringUtils.isNull(entity.data.threeCertificates.data.vehicleManufacturer)//车辆制造商
            carinfo_jscs_xhlc.text = StringUtils.isNull(entity.data.threeCertificates.data.continuousMileage)//续航里程
            carinfo_jscs_ccczszt.text = StringUtils.isNull(entity.data.threeCertificates.data.certState)    //证书状态
            carinfo_jscs_zzrq.text = StringUtils.isNull(entity.data.threeCertificates.data.manufacturingDate) //制造日期
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    private var mNorpre: NormalPresenter? = null
    override fun initView() {
        setPageTitle(resources.getString(R.string.ccc_detail_title))
        mNorpre = NormalPresenter(this)

        //字数过长处理
        setScollTextView(carinfo_jscs_clzzs, carinfo_jscs_scqymc, carinfo_jscs_mpgdwz, carinfo_jscs_zcbmwz)


        getCccData()
    }

    private fun getCccData() {
        showLoadingDialog()
        val zcbm = intent.extras["ccc_check_zcbm"].toString()
        val map = HashMap<String, String>()
        map["clsbdh"] = zcbm
        mNorpre!!.doNetworkTask(map, Constants.CY_ENTRANCE_3C)
    }

    override fun getLayout(): Int {
        return R.layout.activity_ccc_detail
    }
}
