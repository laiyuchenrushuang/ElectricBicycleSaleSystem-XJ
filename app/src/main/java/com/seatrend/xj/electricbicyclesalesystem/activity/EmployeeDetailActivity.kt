package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.CyxxPhotoAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.EmployeeBean
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.DMZUtils
import com.seatrend.xj.electricbicyclesalesystem.util.JslxUtils
import com.seatrend.xj.electricbicyclesalesystem.util.ViewShowUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activty_employee_detail.*
import kotlinx.android.synthetic.main.recyclerview.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set




/**
 * Created by ly on 2019/10/28 16:29
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class EmployeeDetailActivity : BaseActivity(), NormalView, BaseActivity.DialogListener {
    override fun tipDialogOKListener(flag: Int) {
        when (flag) {
            1 -> {
                val map = HashMap<String, String?>()
                map["id"] = if (null == mEmployeeListBean) "" else mEmployeeListBean!!.id
                map["type"] = "2" // 用户管理 1删除 2停用 3重置密码 4启用
                mNormalPresenter!!.doNetworkTask(map, Constants.FORBIDDEN_COMMIT)
            }
            2 -> {
                val map = HashMap<String, String?>()
                map["id"] = if (null == mEmployeeListBean) "" else mEmployeeListBean!!.id
                map["type"] = "4" // 用户管理 1删除 2停用 3重置密码 4启用
                mNormalPresenter!!.doNetworkTask(map, Constants.FORBIDDEN_COMMIT)
            }
        }

    }

    companion object {
        var mEmployeeListBean: EmployeeBean.Data.EmployList? = null
    }

    var glbmDmz = ""  // glbm的dmz
    var fwzDmz = ""  //服务站的dmz

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.FORBIDDEN_COMMIT.equals(commonResponse.url)) {
            val intent = Intent(this@EmployeeDetailActivity, RemindCommonActivity::class.java)
            intent.putExtra(Constants.FORBIDDEN, "1")
            startActivity(intent)
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
        tv_right.isEnabled = false
    }

    private var mCyxxPhotoAdapter: CyxxPhotoAdapter? = null
    private var mTypeLv: ListView? = null
    private var testDataAdapter: ArrayAdapter<String>? = null
    private var pop: PopupWindow? = null  //业务状态
    var photoList = ArrayList<AllBikeMsgEnity.Data.PhotoList>()
    private var mNormalPresenter: NormalPresenter? = null
    private var listData = ArrayList<String>()

    @SuppressLint("ResourceType")
    override fun initView() {
        tv_titile.text = "员工详情"
        mDialogListenr = this
        mNormalPresenter = NormalPresenter(this)
        initRecycle()
        getData()
        bindEvent()
    }

    private fun getData() {
        try {
            if (null == mEmployeeListBean) {
                showToast("数据异常")
                return
            }
            if ("2".equals(mEmployeeListBean!!.jslx)) {
                ViewShowUtils.showVisibleView(ll_fwz)
            } else {
                ViewShowUtils.showGoneView(ll_fwz)
            }
//            tv_sfzmc.text = CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.SFZMMC,mEmployeeListBean!!.sfzmc)
            tv_xm.text = mEmployeeListBean!!.xm
            tv_zt.text = mEmployeeListBean!!.zhzt
            tv_sfz.text = mEmployeeListBean!!.sfzmhm
            tv_lxdh.text = mEmployeeListBean!!.lxdh
            tv_jslx.text = JslxUtils.getJslxMc(mEmployeeListBean!!.jslx)
            tv_glbm.text = mEmployeeListBean!!.sjbmmc

            tv_fwzmc.text = mEmployeeListBean!!.bmmc
//            showLog(GsonUtils.toJson(mEmployeeListBean))

            listData.clear()
            if ("停用".equals(mEmployeeListBean!!.zhzt)) {
                listData.add("账号启用")
            } else if ("审核不通过".equals(mEmployeeListBean!!.zhzt)) {
                listData.add("信息变更")
            } else {
                listData.add("信息变更")
                listData.add("修改密码")
                listData.add("账号禁用")
            }

            if ("1".equals(mEmployeeListBean!!.jslx)) { //车管部门
                listData.remove("信息变更")
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

//        getGlbmAndFwzmc()
    }

    private fun getGlbmAndFwzmc() {
        val map = HashMap<String, String?>()
        map["glbm"] = mEmployeeListBean!!.glbm
        mNormalPresenter!!.doNetworkTask(map, Constants.YG_SEE_GLBM)
    }

    private fun bindEvent() {

        tv_right.setOnClickListener {
            initSelectYwztPopup()
            if (pop != null && !pop!!.isShowing) {
                pop!!.animationStyle = com.seatrend.xj.electricbicyclesalesystem.R.style.pop_animation
                pop!!.showAsDropDown(tv_right)
            }
        }

        iv_back.setOnClickListener {
            finish()
        }
    }

    private fun initSelectYwztPopup() {

        mTypeLv = ListView(this)
        mTypeLv!!.background = ContextCompat.getDrawable(this, com.seatrend.xj.electricbicyclesalesystem.R.color.white)
        // 设置适配器
        testDataAdapter = ArrayAdapter(this, com.seatrend.xj.electricbicyclesalesystem.R.layout.popup_text_item, listData)
        mTypeLv!!.adapter = testDataAdapter
        mTypeLv!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            when (listData[position]) {
                "信息变更" -> {
                    var intent = Intent(this@EmployeeDetailActivity, EmployeeEditActivity::class.java)
                    intent.putExtra("type", "1") //１是信息查询过来，修改信息，０是初次信息录入
                    intent.putExtra("xm", tv_xm.text.toString())
                    intent.putExtra("sfzmc", tv_sfzmc.text.toString())
                    intent.putExtra("sfz", tv_sfz.text.toString())
                    intent.putExtra("lxdh", tv_lxdh.text.toString())
                    intent.putExtra("jslx", tv_jslx.text.toString()) // 角色类型
                    intent.putExtra("glbm", tv_glbm.text.toString())
                    intent.putExtra("fwzmc", tv_fwzmc.text.toString())  //服务站名称

                    intent.putExtra("glbm_dmz", glbmDmz)  //管理部门代码值
                    intent.putExtra("fwzmc_dmz", fwzDmz)  //服务站代码值
                    startActivity(intent)
                }
                "修改密码" -> {
                    var intent = Intent(this@EmployeeDetailActivity, EmployeeChangePasswordActivity::class.java)
                    intent.putExtra("xm", tv_xm.text.toString())
                    intent.putExtra("sfz", tv_sfz.text.toString())
                    intent.putExtra("yhdh", mEmployeeListBean!!.yhdh)
                    startActivity(intent)
                }
                "账号禁用" -> {
                    showLoadingDialog()
                    showTipDialog("提示", "确定需要禁用此账号吗？", 1)
                }
                "账号启用" -> {
                    showLoadingDialog()
                    showTipDialog("提示", "确定需要启用此账号吗？", 2)
                }
            }
            pop!!.dismiss()
        }
        pop = PopupWindow(mTypeLv, tv_right.width, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        pop!!.setBackgroundDrawable(resources.getDrawable(R.color.white))
        pop!!.isFocusable = false
        pop!!.isOutsideTouchable = true
        pop!!.setOnDismissListener {
            // 关闭popup窗口
            pop!!.dismiss()
        }
    }

    private fun initRecycle() {
        m_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        showLoadingDialog()
        if (mEmployeeListBean != null && mEmployeeListBean!!.photos != null && mEmployeeListBean!!.photos.size > 0) {
            for (db in mEmployeeListBean!!.photos) {
                val enity = AllBikeMsgEnity.Data.PhotoList()
                enity.zpdz = db.zpdz
                enity.zpzl = db.zpzl
                enity.zpsm = DMZUtils.getDMSM(Constants.YGZP, db.zpzl)
                photoList.add(enity)
            }
        }
        dismissLoadingDialog()
        mCyxxPhotoAdapter = CyxxPhotoAdapter(this, photoList)
        m_recycler_view.adapter = mCyxxPhotoAdapter
    }


    override fun getLayout(): Int {
        return R.layout.activty_employee_detail
    }
}