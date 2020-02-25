package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.Yw3CzActivity
import com.seatrend.xj.electricbicyclesalesystem.adpater.CyxxPhotoAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.util.*
import kotlinx.android.synthetic.main.fragment_register_all_info.*
import java.util.ArrayList

/**
 * Created by ly on 2019/11/8 15:30
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class RegisterAllInfoFragment : BaseFragment(), View.OnTouchListener {
    companion object {
        var enity: AllBikeMsgEnity? = null
    }

    private var mTextTouchListener: View.OnTouchListener? = null
    private var mCyxxPhotoAdapter: CyxxPhotoAdapter? = null
    var photoList = ArrayList<AllBikeMsgEnity.Data.PhotoList>()

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
         return inflater!!.inflate(R.layout.fragment_register_all_info, container, false)
    }

    override fun initView() {
        initUI()
        getYwLxData()
        mTextTouchListener = this
    }

    private fun getYwLxData() {
        if (activity is Yw3CzActivity && "3" == activity.intent.getStringExtra(Constants.UI_TYPE)) { //证明是车辆查询来的
            ll_ywxx.visibility = View.GONE
            initSyrXX()
            initDlrXX()
            getPhotodata()
        } else {
            try {
                if (!ObjectNullUtil.checknull(enity) || !ObjectNullUtil.checknull(enity!!.data)) {
                    showToast("登记信息获取为空")
                    return
                }
                //可以根据登记信息里面的ywlx字段
                tv_ywlx.text = DMZUtils.getDMSM(Constants.YWLX, enity!!.data.fjdcBusiness.ywlx)
                when (enity!!.data.fjdcBusiness.ywlx) {


                    //注册登记
                    "A" -> {

                        ll_zc_ywxx.visibility = View.VISIBLE
                        tv_cllx.text = CllxUtils.getCllxDMSM(enity!!.data.fjdcBusiness.cllx)
                        tv_cphm.text = enity!!.data.fjdcBusiness.cph
                        tv_csys.text = CsysUtils.getCsysMc(enity!!.data.fjdcBusiness.csys)
                        tv_hdfs.text = DMZUtils.getDMSM(Constants.HDFS, enity!!.data.fjdcBusiness.hdfs)
                        tv_llzm.text = DMZUtils.getDMSM(Constants.LLZM, enity!!.data.fjdcBusiness.llzm)
                        tv_syxz.text = DMZUtils.getDMSM(Constants.SYXZ, enity!!.data.fjdcBusiness.syxz)
                        tv_clyt.text = DMZUtils.getDMSM(Constants.CLYT, enity!!.data.fjdcBusiness.clyt)
                        tv_hqrq.text = StringUtils.longToStringDataNoHour(enity!!.data.fjdcBusiness.hqrq)

                        initSyrXX()
                        initDlrXX()
                        initYjXX()
                        getPhotodata()
                    }
                    //变更
                    "D" -> {
                        ll_bg_ywxx.visibility = View.VISIBLE
                        if ("A".equals(enity!!.data.fjdcBusiness.ywyy)) {
                            ll_bg_cphm.visibility = View.GONE
                            ll_bg_zrd.visibility = View.VISIBLE
                            tv_bg_zrd.text = DMZUtils.getDMSM(Constants.XSQY, enity!!.data.fjdcBusiness.zrd)
                            tv_bg_ywyy.text = "变更迁出"
                            ViewShowUtils.showGoneView(ll_jdxx1, ll_jdxx2)
                        } else if ("B".equals(enity!!.data.fjdcBusiness.ywyy)) {
                            ll_bg_zrd.visibility = View.GONE
                            ll_bg_cphm.visibility = View.VISIBLE
                            tv_bg_ywyy.text = "变更所有人"
                            tv_bg_cph.text = enity!!.data.fjdcBusiness.cph
                            initYjXX()
                        }
                        tv_bg_syxz.text = DMZUtils.getDMSM(Constants.SYXZ, enity!!.data.fjdcBusiness.syxz)
                        tv_bg_clyt.text = DMZUtils.getDMSM(Constants.CLYT, enity!!.data.fjdcBusiness.clyt)
                        initSyrXX()
                        initDlrXX()
                        getPhotodata()
                    }
                    //转移
                    "B" -> {
                        ll_zy_ywxx.visibility = View.VISIBLE
                        tv_zy_clyt.text = DMZUtils.getDMSM(Constants.CLYT, enity!!.data.fjdcBusiness.clyt)
                        tv_zy_syxz.text = DMZUtils.getDMSM(Constants.SYXZ, enity!!.data.fjdcBusiness.syxz)

                        if ("A".equals(enity!!.data.fjdcBusiness.ywyy)) {
                            tv_zy_ywyy.text = "辖区内转移不换号"
                            ViewShowUtils.showGoneView(ll_zy_cphm, ll_zy_zrd)
                            initYjXX()
                        } else if ("B".equals(enity!!.data.fjdcBusiness.ywyy)) {
                            tv_zy_ywyy.text = "辖区内转移换号"
                            ll_zy_cphm.visibility = View.VISIBLE
                            tv_zy_cph.text = enity!!.data.fjdcBusiness.cph
                            ll_zy_zrd.visibility = View.GONE
                            initYjXX()
                        } else if ("C".equals(enity!!.data.fjdcBusiness.ywyy)) {
                            tv_zy_ywyy.text = "辖区外转移"
                            ll_zy_cphm.visibility = View.GONE
                            ll_zy_zrd.visibility = View.VISIBLE
                            tv_zy_zrd.text = DMZUtils.getDMSM(Constants.XSQY, enity!!.data.fjdcBusiness.zrd)
                            ViewShowUtils.showGoneView(ll_jdxx1, ll_jdxx2)
                        }

                        initSyrXX()
                        initDlrXX()
                        getPhotodata()
                    }
                    //注销
                    "G" -> {
                        ll_zx_ywxx.visibility = View.VISIBLE
                        tv_zx_ywyy.text = enity!!.data.fjdcBusiness.ywyy
                        initSyrXX()
                        initDlrXX()
//                    initYjXX()
                        getPhotodata()
                    }
                    //补换
                    "K" -> {
                        ll_bh_ywxx.visibility = View.VISIBLE
                        tv_bh_ywyy.text = enity!!.data.fjdcBusiness.ywyy
                        tv_bh_ywyy!!.movementMethod = ScrollingMovementMethod.getInstance()
                        tv_bh_ywyy!!.setHorizontallyScrolling(true)
                        tv_bh_ywyy!!.setOnTouchListener(mTextTouchListener)
                        tv_bh_cph.text = if ("".equals(enity!!.data.fjdcBusiness.cph)) "/" else enity!!.data.fjdcBusiness.cph
                        initSyrXX()
                        initDlrXX()
                        initYjXX()
                        getPhotodata()
                    }
                }
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }
    }

    private fun initYjXX() {
        if (activity is Yw3CzActivity && "3" == activity.intent.getStringExtra(Constants.UI_TYPE)) { //证明是车辆查询来的
            if (ObjectNullUtil.checknull(Yw3CzActivity.mAllCXData!!.data.syrjbxx.zzxsz) && !"0".equals(UserInfo.GlobalParameter.LQBJ)) {
                ll_jdxx1.visibility = View.VISIBLE
                if ("1".equals(Yw3CzActivity.mAllCXData!!.data.syrjbxx.zzxsz)) {
                    tv_zzxsz.text = "需要"
                    if ("1".equals(Yw3CzActivity.mAllCXData!!.data.syrjbxx.lqfs)) {
                        tv_lqfs.text = "现场领取"
                        ll_jdxx2.visibility = View.GONE
                    } else {
                        tv_lqfs.text = "邮寄领取"
                        ll_jdxx2.visibility = View.VISIBLE
                        tv_yj_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, Yw3CzActivity.mAllCXData!!.data.syrjbxx.sjrsfzmlx)
                        tv_yj_sfz.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.sjrsfzmhm
                        tv_yj_xm.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.sjrxm
                        tv_yj_lxdh.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.sjrlxdh
                        tv_yj_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, Yw3CzActivity.mAllCXData!!.data.syrjbxx.sjryjxzqh)
                        tv_yj_xxdz.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.sjryjxxdz
                        tv_yj_yzbm.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.sjryzbm
                    }
                } else {
                    tv_zzxsz.text = "不需要"
                    ViewShowUtils.showGoneView(ll_lqfs, ll_jdxx2)
                }
            }
        } else {
            if (ObjectNullUtil.checknull(enity!!.data.fjdcBusiness.zzxsz) && !"0".equals(UserInfo.GlobalParameter.LQBJ)) {
                ll_jdxx1.visibility = View.VISIBLE
                if ("1".equals(enity!!.data.fjdcBusiness.zzxsz)) {
                    tv_zzxsz.text = "需要"
                    if ("1".equals(enity!!.data.fjdcBusiness.lqfs)) {
                        tv_lqfs.text = "现场领取"
                        ll_jdxx2.visibility = View.GONE
                    } else {
                        tv_lqfs.text = "邮寄领取"
                        ll_jdxx2.visibility = View.VISIBLE
                        tv_yj_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, enity!!.data.fjdcBusiness.sjrsfzmlx)
                        tv_yj_sfz.text = enity!!.data.fjdcBusiness.sjrsfzmhm
                        tv_yj_xm.text = enity!!.data.fjdcBusiness.sjrxm
                        tv_yj_lxdh.text = enity!!.data.fjdcBusiness.sjrlxdh
                        tv_yj_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, enity!!.data.fjdcBusiness.sjryjxzqh)
                        tv_yj_xxdz.text = enity!!.data.fjdcBusiness.sjryjxxdz
                        tv_yj_yzbm.text = enity!!.data.fjdcBusiness.sjryzbm
                    }
                } else {
                    tv_zzxsz.text = "不需要"
                    ViewShowUtils.showGoneView(ll_lqfs, ll_jdxx2)
                }
            }

        }
    }

    private fun initDlrXX() {
        if (activity is Yw3CzActivity && "3" == activity.intent.getStringExtra(Constants.UI_TYPE)) { //证明是车辆查询来的
            if (ObjectNullUtil.checknull(Yw3CzActivity.mAllCXData!!.data.syrjbxx.dlrsfzmhm)) {
                ll_dlrxx.visibility = View.VISIBLE
                tv_dlr_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, Yw3CzActivity.mAllCXData!!.data.syrjbxx.dlrsfzmlx)
                tv_dlr_sfz.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.dlrsfzmhm
                tv_dlr_xm.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.dlrxm
                tv_dlr_lxdh.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.dlrlxdh
            } else {
                ll_dlrxx.visibility = View.GONE
            }
        } else {
            if (ObjectNullUtil.checknull(enity!!.data.fjdcBusiness.dlrsfzmhm)) {
                ll_dlrxx.visibility = View.VISIBLE
                tv_dlr_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, enity!!.data.fjdcBusiness.dlrsfzmlx)
                tv_dlr_sfz.text = enity!!.data.fjdcBusiness.dlrsfzmhm
                tv_dlr_xm.text = enity!!.data.fjdcBusiness.dlrxm
                tv_dlr_lxdh.text = enity!!.data.fjdcBusiness.dlrlxdh
            } else {
                ll_dlrxx.visibility = View.GONE
            }
        }
    }

    private fun initSyrXX() {
        if (activity is Yw3CzActivity && "3" == activity.intent.getStringExtra(Constants.UI_TYPE)) { //证明是车辆查询来的
            tv_syr_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, Yw3CzActivity.mAllCXData!!.data.syrjbxx.sfzmmc)
            tv_syr_sfz.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.sfzmhm
            tv_syr_xm.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.syrmc
            tv_syr_lxdh.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.lxdh
            tv_syr_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, Yw3CzActivity.mAllCXData!!.data.syrjbxx.djxzqh)
            tv_syr_xxdz.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.djxxdz

            tv_syr_yj_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, Yw3CzActivity.mAllCXData!!.data.syrjbxx.lxdzxzqh)
            tv_syr_yj_xxdz.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.lxxxdz
            tv_syr_yj_yzbm.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.yzbm
            tv_syr_yxdz.text = Yw3CzActivity.mAllCXData!!.data.syrjbxx.dzyx
        } else {
            tv_syr_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, enity!!.data.fjdcBusiness.sfzmmc)
            tv_syr_sfz.text = enity!!.data.fjdcBusiness.sfzmhm
            tv_syr_xm.text = enity!!.data.fjdcBusiness.syrmc
            tv_syr_lxdh.text = enity!!.data.fjdcBusiness.lxdh
            tv_syr_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, enity!!.data.fjdcBusiness.djxzqh)
            tv_syr_xxdz.text = enity!!.data.fjdcBusiness.djxxdz

            tv_syr_yj_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, enity!!.data.fjdcBusiness.lxdzxzqh)
            tv_syr_yj_xxdz.text = enity!!.data.fjdcBusiness.lxxxdz
            tv_syr_yj_yzbm.text = enity!!.data.fjdcBusiness.yzbm
            tv_syr_yxdz.text = enity!!.data.fjdcBusiness.dzyx
        }
    }

    private fun getPhotodata() {
        LoadingDialog.getInstance().showLoadDialog(context)
        if (activity is Yw3CzActivity && "3" == activity.intent.getStringExtra(Constants.UI_TYPE)) { //证明是车辆查询来的
            try {
                val cyList = CodeTableSQLiteUtils.queryByDMLB(Constants.CYZP)
                photoList.clear()
                //只获取查验的照片 排除法  这里不太好，因为登记照片越来越多怎么办？
                for (db in Yw3CzActivity.mAllCXData!!.data.syrzpxx) {
                    var flag = false
                    for (enity in cyList) {
                        if (null != db.zpzl && db.zpzl == enity.dmz) {
                            flag = false
                            break
                        }
                        flag = true
                    }
                    if (flag) {
                        var data = com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity.Data.PhotoList()
                        data.zpzl = db.zpzl
                        data.zpsm = db.zpsm
                        data.zpdz = db.zpdz
                        if (data.zpdz != null) {
                            photoList.add(data)
                        }
                    }
                }
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        } else {
            try {
                val cyList = CodeTableSQLiteUtils.queryByDMLB(Constants.CYZP)
                photoList.clear()
                //只获取查验的照片 排除法  这里不太好，因为登记照片越来越多怎么办？
                for (db in enity!!.data.photoList) {
                    var flag = false
                    for (enity in cyList) {
                        if (null != db.zpzl && db.zpzl == enity.dmz) {
                            flag = false
                            break
                        }
                        flag = true
                    }
                    if (flag) {
                        var data = com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity.Data.PhotoList()
                        data.zpzl = db.zpzl
                        data.zpsm = db.zpsm
                        data.zpdz = db.zpdz
                        if (data.zpdz != null) {
                            photoList.add(data)
                        }
                    }
                }
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }

        LoadingDialog.getInstance().dismissLoadDialog()
    }

    private fun initUI() {
        m_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        mCyxxPhotoAdapter = CyxxPhotoAdapter(context, photoList)
        m_recycler_view.adapter = mCyxxPhotoAdapter
    }

    /**
     * 与view防止冲突
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            v!!.parent.requestDisallowInterceptTouchEvent(true)
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            // 通知ScrollView控件不要干扰
            v!!.parent.requestDisallowInterceptTouchEvent(true)
        }
        if (event.action == MotionEvent.ACTION_UP) {
            v!!.parent.requestDisallowInterceptTouchEvent(false)
        }
        return false
    }
}