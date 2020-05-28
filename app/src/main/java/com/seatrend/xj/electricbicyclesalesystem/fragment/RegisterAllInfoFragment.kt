package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
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
import com.seatrend.xj.electricbicyclesalesystem.entity.CarMsgEnity
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

    var enity: AllBikeMsgEnity? = null
    private var mCyxxPhotoAdapter: CyxxPhotoAdapter? = null
    var photoList = ArrayList<AllBikeMsgEnity.Data.PhotoList>()
    private var cydata: CarMsgEnity? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_register_all_info, container, false)
    }

    override fun initView() {
        initUI()
        getYwLxData()
        setLongTextview(tv_syr_xxdz)
        setLongTextview(tv_syr_yj_xxdz)
    }

    private fun getYwLxData() {

        if (activity is Yw3CzActivity && "3" == activity.intent.getStringExtra(Constants.UI_TYPE)) { //证明是车辆查询来的
            cydata = activity.intent.getSerializableExtra("cy_data") as CarMsgEnity
            ll_ywxx.visibility = View.GONE
            initSyrXX()
            initDlrXX()
            getPhotodata()
        } else {
            enity = activity.intent.getSerializableExtra("all_data") as AllBikeMsgEnity
            try {
                if (!ObjectNullUtil.checknull(enity) || !ObjectNullUtil.checknull(enity!!.data) || null == enity!!.data.fjdcBusiness || null == enity!!.data.fjdcBusiness.ywlx || TextUtils.isEmpty(enity!!.data.fjdcBusiness.ywlx)) {
//                    showToast("登记信息获取为空")
                    return
                }

                //可以根据登记信息里面的ywlx字段
                tv_ywlx.text = DMZUtils.getDMSM(Constants.YWLX, enity!!.data.fjdcBusiness.ywlx)
                showLog("sssssss = " + tv_ywlx.text.toString())
                when (enity!!.data.fjdcBusiness.ywlx) {


                    //注册登记
                    Constants.A -> {

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
                    Constants.D -> {
                        ll_bg_ywxx.visibility = View.VISIBLE
                        if (Constants.BG_DA.equals(enity!!.data.fjdcBusiness.ywyy)) {
                            ll_bg_cphm.visibility = View.GONE
                            ll_bg_zrd.visibility = View.VISIBLE
                            tv_bg_zrd.text = DMZUtils.getDMSM(Constants.XSQY, enity!!.data.fjdcBusiness.zrd)
                            tv_bg_ywyy.text = "变更迁出"
                            ViewShowUtils.showGoneView(ll_jdxx1, ll_jdxx2)
                        } else if (Constants.BG_DB.equals(enity!!.data.fjdcBusiness.ywyy)) {
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
                    Constants.B -> {
                        ll_zy_ywxx.visibility = View.VISIBLE
                        tv_zy_clyt.text = DMZUtils.getDMSM(Constants.CLYT, enity!!.data.fjdcBusiness.clyt)
                        tv_zy_syxz.text = DMZUtils.getDMSM(Constants.SYXZ, enity!!.data.fjdcBusiness.syxz)

                        if (Constants.ZY_BA.equals(enity!!.data.fjdcBusiness.ywyy)) {
                            tv_zy_ywyy.text = "辖区内转移不换号"
                            ViewShowUtils.showGoneView(ll_zy_cphm, ll_zy_zrd)
                            initYjXX()
                        } else if (Constants.ZY_BB.equals(enity!!.data.fjdcBusiness.ywyy)) {
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
                    Constants.G -> {
                        ll_zx_ywxx.visibility = View.VISIBLE
                        tv_zx_ywyy.text = enity!!.data.fjdcBusiness.ywyy
                        initSyrXX()
                        initDlrXX()
//                    initYjXX()
                        getPhotodata()
                    }
                    //补换
                    Constants.K -> {
                        ll_bh_ywxx.visibility = View.VISIBLE
                        tv_bh_ywyy.text = enity!!.data.fjdcBusiness.ywyy
                        tv_bh_ywyy!!.movementMethod = ScrollingMovementMethod.getInstance()
                        tv_bh_ywyy!!.setHorizontallyScrolling(true)
                        tv_bh_ywyy!!.setOnTouchListener(onTouchListener)
                        tv_bh_cph.text = if ("".equals(enity!!.data.fjdcBusiness.cph)) "/" else enity!!.data.fjdcBusiness.cph
                        initSyrXX()
                        initDlrXX()
                        initYjXX()
                        getPhotodata()
                    }
                    //转入
                    Constants.I -> {
                        ll_zr_ywxx.visibility = View.VISIBLE
                        tv_zr_hphm.text = enity!!.data.fjdcBusiness.cph
                        if (Constants.ZR_IA == enity!!.data.fjdcBusiness.ywyy) {
                            tv_zr_ywyy.text = "转移出辖区"
                        } else {
                            tv_zr_ywyy.text = "变更转入"
                        }
                        tv_zr_clyt.text = DMZUtils.getDMSM(Constants.CLYT, enity!!.data.fjdcBusiness.clyt)
                        tv_zr_syxz.text = DMZUtils.getDMSM(Constants.SYXZ, enity!!.data.fjdcBusiness.syxz)
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
            if (ObjectNullUtil.checknull(cydata!!.data.syrjbxx.zzxsz) && !"0".equals(UserInfo.GlobalParameter.LQBJ)) {
                ll_jdxx1.visibility = View.VISIBLE
                if ("1".equals(cydata!!.data.syrjbxx.zzxsz)) {
                    tv_zzxsz.text = "需要"
                    if ("1".equals(cydata!!.data.syrjbxx.lqfs)) {
                        tv_lqfs.text = "现场领取"
                        ll_jdxx2.visibility = View.GONE
                    } else {
                        tv_lqfs.text = "邮寄领取"
                        ll_jdxx2.visibility = View.VISIBLE
                        tv_yj_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, cydata!!.data.syrjbxx.sjrsfzmlx)
                        tv_yj_sfz.text = cydata!!.data.syrjbxx.sjrsfzmhm
                        tv_yj_xm.text = cydata!!.data.syrjbxx.sjrxm
                        tv_yj_lxdh.text = cydata!!.data.syrjbxx.sjrlxdh
                        tv_yj_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, cydata!!.data.syrjbxx.sjryjxzqh)
                        tv_yj_xxdz.text = cydata!!.data.syrjbxx.sjryjxxdz
                        tv_yj_yzbm.text = cydata!!.data.syrjbxx.sjryzbm
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
            if (ObjectNullUtil.checknull(cydata!!.data.syrjbxx.dlrsfzmhm)) {
                ll_dlrxx.visibility = View.VISIBLE
                tv_dlr_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, cydata!!.data.syrjbxx.dlrsfzmlx)
                tv_dlr_sfz.text = cydata!!.data.syrjbxx.dlrsfzmhm
                tv_dlr_xm.text = cydata!!.data.syrjbxx.dlrxm
                tv_dlr_lxdh.text = cydata!!.data.syrjbxx.dlrlxdh
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
            tv_syr_sfzmc.text = DMZUtils.getDMSM(Constants.SFZMMC, cydata!!.data.syrjbxx.sfzmmc)
            tv_syr_sfz.text = cydata!!.data.syrjbxx.sfzmhm
            tv_syr_xm.text = cydata!!.data.syrjbxx.syrmc
            tv_syr_lxdh.text = cydata!!.data.syrjbxx.lxdh
            tv_syr_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, cydata!!.data.syrjbxx.djxzqh)
            tv_syr_xxdz.text = cydata!!.data.syrjbxx.djxxdz

            tv_syr_yj_xzqh.text = DMZUtils.getDMSM(Constants.XSQY, cydata!!.data.syrjbxx.lxdzxzqh)
            tv_syr_yj_xxdz.text = cydata!!.data.syrjbxx.lxxxdz
            tv_syr_yj_yzbm.text = cydata!!.data.syrjbxx.yzbm

            tv_syr_yxdz.text = if (TextUtils.isEmpty(cydata!!.data.syrjbxx.dzyx)) "/" else cydata!!.data.syrjbxx.dzyx
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
            tv_syr_yxdz.text = if (TextUtils.isEmpty(enity!!.data.fjdcBusiness.dzyx)) "/" else enity!!.data.fjdcBusiness.dzyx
        }
    }

    private fun getPhotodata() {
        LoadingDialog.getInstance().showLoadDialog(context)
        if (activity is Yw3CzActivity && "3" == activity.intent.getStringExtra(Constants.UI_TYPE)) { //证明是车辆查询来的(只做了查验)
            try {
                photoList.clear()

                for (db in cydata!!.data.syrzpxx) {
                    if (db.zplx == "2") {
                        var data = AllBikeMsgEnity.Data.PhotoList()
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
                photoList.clear()

                for (db in enity!!.data.photoList) {

                    if (db.zplx == "2") {
                        var data = AllBikeMsgEnity.Data.PhotoList()
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