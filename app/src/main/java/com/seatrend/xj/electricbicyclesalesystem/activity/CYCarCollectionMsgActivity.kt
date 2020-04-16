package com.seatrend.xj.electricbicyclesalesystem.activity

import android.widget.ArrayAdapter
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.CsysCompareSameUtils
import com.seatrend.xj.electricbicyclesalesystem.util.ParseQcodeUtil
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_car_collection_msg.*
import kotlinx.android.synthetic.main.bottom_button.*

/**
 * Created by ly on 2020/4/3 10:07
 */
class CYCarCollectionMsgActivity :BaseActivity(),NormalView {

    var  mNormalPresenter :NormalPresenter ?=null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.responseString)
    }


    override fun initView() {

        setPageTitle(resources.getString(R.string.cy_collection_msg))
        mNormalPresenter = NormalPresenter(this)
        setInitView()

        setSpinner()

        bindEvent()
    }

    private fun setInitView() {
        setEditNoEmoj(et_ddjbh,et_zcbm)
        setEditUppercase(et_ddjbh,et_zcbm)
    }

    private fun setSpinner() {
        setSpinnerAdapter(sp_csys_a)
        setSpinnerAdapter(sp_csys_b)
        setSpinnerAdapter(sp_csys_c)
    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (spinner.id) {
            R.id.sp_csys_a, R.id.sp_csys_b, R.id.sp_csys_c -> {
                adapter.clear()
                adapter.add("")
                val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(Constants.CSYS)
                for (db in sfzmmcList) {
                    val dmz = db.dmz
                    val dmsm1 = db.dmsm1
                    adapter.add(dmz + ":" + dmsm1)
                }
                spinner.adapter = adapter
            }
        }
    }


    private fun bindEvent() {
        bt_next.setOnClickListener {
            if (!CsysCompareSameUtils.compare3(sp_csys_a, sp_csys_b, sp_csys_c)) {
                showToast("请正确输入车身颜色，不能两两选择相同颜色")
                return@setOnClickListener
            }
            if (!ParseQcodeUtil.isZcbmString(et_zcbm.text.toString())) {
                showToast("整车编码是否是15位数字?")
                return@setOnClickListener
            }


            val map = HashMap<String,String>()
            mNormalPresenter!!.doNetworkTask(map,"")
        }


    }

    override fun getLayout(): Int {
        return R.layout.activity_car_collection_msg
    }
}