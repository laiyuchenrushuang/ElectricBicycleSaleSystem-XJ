package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.ViewUtils
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.CarInfoByCyActivity
import com.seatrend.xj.electricbicyclesalesystem.activity.Yw3CzActivity
import com.seatrend.xj.electricbicyclesalesystem.adpater.CyxxPhotoAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.entity.YWRegisterEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.util.*
import kotlinx.android.synthetic.main.activity_tuiban.*
import kotlinx.android.synthetic.main.fragment_car_cy_info.*
import kotlinx.android.synthetic.main.recyclerview.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.ArrayList as ArrayList1

class CarCYxxFragment : BaseFragment() {
    companion object {
        var enity: AllBikeMsgEnity? = null
    }

    private var mCyxxPhotoAdapter: CyxxPhotoAdapter? = null
    var photoList = ArrayList<AllBikeMsgEnity.Data.PhotoList>()

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_car_cy_info, container, false)
    }

    override fun initView() {
        initUI()


        //是否隐藏带牌销售和车牌号码
        if ("0" == UserInfo.GlobalParameter.DPBJ) {
            ViewShowUtils.showGoneView(ll_dpxs_all)
        } else {  //全局参数
            ViewShowUtils.showVisibleView(ll_dpxs_all)
        }
        if ("0" == UserInfo.GlobalParameter.SCBJ) {
            ViewShowUtils.showGoneView(ll_scsj)
        } else {  //全局参数
            ViewShowUtils.showVisibleView(ll_scsj)
        }

        if (activity is CarInfoByCyActivity) {
            showLog(activity.intent.getStringExtra("ywlx"))
            if ("A".equals(activity.intent.getStringExtra("ywlx")) ||
                    "B".equals(activity.intent.getStringExtra("ywlx")) ||
                    "D".equals(activity.intent.getStringExtra("ywlx"))) { //A注册 D变更 B转移 7旧车换牌
                getData()
            } else {
                ViewShowUtils.showGoneView(ll_cyxx)
                ViewShowUtils.showVisibleView(tv_cyxx)
            }
        } else if (activity is Yw3CzActivity) {
            if (null == enity || null == enity!!.data || null == enity!!.data.fjdcBusiness) {
                showToast("查询查验信息为空")
                return
            }

            if ("A".equals(enity!!.data.fjdcBusiness.ywlx) ||
                    "B".equals(enity!!.data.fjdcBusiness.ywlx) ||
                    "D".equals(enity!!.data.fjdcBusiness.ywlx)) { //A注册 D变更 B转移 7旧车换牌
                getData()
            } else {
                ViewShowUtils.showGoneView(ll_cyxx)
                ViewShowUtils.showVisibleView(tv_cyxx)
            }
        }
    }

    private fun initUI() {
        m_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        mCyxxPhotoAdapter = CyxxPhotoAdapter(context, photoList)
        m_recycler_view.adapter = mCyxxPhotoAdapter
        bindEvent()
    }

    private fun getData() {
        try {
            if (enity == null || enity!!.data == null || enity!!.data.checkData == null || enity!!.data.photoList == null || enity!!.data.photoList.size == 0) {
                showToast("获取查验信息失败")
                return
            }
            if ("A".equals(enity!!.data.checkData.ywlx)) {
                ll_item_cyxx.visibility = View.VISIBLE
                tv_csys.text = CsysUtils.getCsysMc(enity!!.data.checkData.csys)
                tv_ckg.text = enity!!.data.checkData.scc + "*" + enity!!.data.checkData.sck + "*" + enity!!.data.checkData.scg
                tv_zczl.text = enity!!.data.checkData.sczbzl
                tv_zgss.text = enity!!.data.checkData.sczgcs
                tv_zxj.text = enity!!.data.checkData.scqhlzxj  //中心距
                tv_jtxs.text = if ("1".equals(enity!!.data.checkData.jtgn)) "有" else "否"
                tv_dpxs.text = if ("1".equals(enity!!.data.checkData.shdp)) "是" else "否"
                tv_cphm.text = if (ObjectNullUtil.checknull(enity!!.data.checkData.cph)) enity!!.data.checkData.cph else "/"
            } else {
                ll_item_cyxx.visibility = View.GONE
            }

            tv_cyjl.text = if ("1".equals(enity!!.data.checkData.cyjl)) "合格" else "不合格"
            tv_cyr.text = if (TextUtils.isEmpty(enity!!.data.checkData.cyr)) "无" else enity!!.data.checkData.cyr
            tv_cysj.text = StringUtils.longToStringData(enity!!.data.checkData.cyrq)
//        tv_glbm.text = enity!!.data.checkData.cybm
            tv_glbm.text = UserInfo.NewUserInfo.BMMC
            tv_zxj.text = enity!!.data.checkData.scqhlzxj
            val cyList = CodeTableSQLiteUtils.queryByDMLB(Constants.CYZP)
            //只获取查验的照片
            photoList.clear()
            for (db in enity!!.data.photoList) {
                for (enity in cyList) {
                    if (db.zpzl.equals(enity.dmz)) {
                        var data = AllBikeMsgEnity.Data.PhotoList()
                        data.zpzl = db.zpzl
                        data.zpsm = db.zpsm
                        data.zpdz = db.zpdz
                        photoList.add(data)
                        break
                    }
                }
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    private fun bindEvent() {
        m_recycler_view.isNestedScrollingEnabled = false //滑动冲突问题
    }
}
