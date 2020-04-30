package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.text.Editable
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.SelectorAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.entity.YGPostEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.YgJsqxEnity
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.manager.MyRecycleManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_employee_edit.*
import kotlinx.android.synthetic.main.bottom_button.*

class EmployeeEditActivity : BaseActivity(), NormalView, SelectorAdapter.CheckState {


    private var mNormalPresenter: NormalPresenter? = null
    private var headPhoto: ByteArray? = null//头像照片
    private var FACE_COMPARE_CODE: Int = 11
    private var mList = ArrayList<String>() //post 角色权限list
    var jsqxList = ArrayList<String>() //GET 角色权限 list

    private var ll: RecyclerView.LayoutManager? = null
    var adapter: SelectorAdapter? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.YG_JSQX_SEARCH.equals(commonResponse.getUrl())) {
            val enity = GsonUtils.gson(commonResponse.getResponseString(), YgJsqxEnity::class.java)
            if (enity == null || enity.data == null || enity.data.size == 0) {
                showToast("角色权限列表数据异常")
                return
            }
            jsqxList.clear()

            enity.data.sortBy { it.xh }
            for (db in enity.data) {
                //备注 Mr.Zhang  提示00是管理员级别，需要屏蔽管理员
                if(Constants.BA_GLY != db.jsdh){
                    jsqxList.add(db.jsdh + ":" + db.jsmc)
                }

            }
            adapter!!.notifyDataSetChanged()
        }
        if (Constants.YG_INSERT.equals(commonResponse.getUrl()) || Constants.USER_USER_UPDATE.equals(commonResponse.getUrl())) {
            intent.putExtra("yg_sfz", ed_sfz.text.toString())
            intent.putExtra("yg_xm", ed_xm.text.toString())
            CollectPhotoActivity.photoEntranceFlag = Constants.YGBA
            intent.setClass(this, CollectPhotoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        setPageTitle("员工备案")
        mNormalPresenter = NormalPresenter(this)

        getData()
        initRecycleview()
        sp_sfz.isEnabled=false
        bindEvent()
    }

    private fun initRecycleview() {
        ll = MyRecycleManager(this)
        ll!!.isAutoMeasureEnabled = true
        m_recycler_view!!.layoutManager = ll
        adapter = SelectorAdapter(this, jsqxList)
        m_recycler_view.adapter = adapter
        adapter!!.setCheckListener(this)
    }


    private fun getData() {
        try {
            SpinnerUtil.setPinnerData(this, Constants.SFZMMC, sp_sfz)
            if("2".equals(UserInfo.JSLX)){
                ViewShowUtils.showVisibleView(ll_fwz)
            }else{
                ViewShowUtils.showGoneView(ll_fwz)
            }
            if ("1".equals(intent.getStringExtra("type"))) {
                //信息详情界面点击过来
                setPageTitle("信息变更")

                ed_xm.setText(intent.getStringExtra("xm"))
                OtherUtils.setSpinnerToDmz(EmployeeDetailActivity.mEmployeeListBean!!.sfzmc, sp_sfz)
                ed_sfz.setText(EmployeeDetailActivity.mEmployeeListBean!!.sfzmhm)
                et_lxdh.setText(EmployeeDetailActivity.mEmployeeListBean!!.lxdh)
                tv_jslx.text = JslxUtils.getJslxMc(EmployeeDetailActivity.mEmployeeListBean!!.jslx)
                tv_glbm.text = EmployeeDetailActivity.mEmployeeListBean!!.sjbmmc
                tv_fwzmc.text = EmployeeDetailActivity.mEmployeeListBean!!.bmmc
                if("2".equals(EmployeeDetailActivity.mEmployeeListBean!!.jslx)){
                    ViewShowUtils.showVisibleView(ll_fwz)
                }else{
                    ViewShowUtils.showGoneView(ll_fwz)
                    if("1".equals(EmployeeDetailActivity.mEmployeeListBean!!.jslx)){
                        tv_glbm.text = EmployeeDetailActivity.mEmployeeListBean!!.bmmc
                    }
                }
                showLoadingDialog()
                val map = HashMap<String, String>()
                map["jslx"] = EmployeeDetailActivity.mEmployeeListBean!!.jslx// 1 车管部门 2 服务站 3 经销商
                mNormalPresenter!!.doNetworkTask(map, Constants.YG_JSQX_SEARCH)
            } else {
                tv_jslx.text = JslxUtils.getJslxMc(UserInfo.JSLX)
                tv_fwzmc.text = UserInfo.BMMC
                showLoadingDialog()
                val map = HashMap<String, String>()
                map["jslx"] = UserInfo.JSLX // 1 车管部门 2 服务站 3 经销商
                tv_glbm.text = UserInfo.NewUserInfo.BMMC
                mNormalPresenter!!.doNetworkTask(map, Constants.YG_JSQX_SEARCH)
    //
    //            if(GLBMUtils.isSearchLargerGlbm(UserInfo.GLBM)){
    //                val map1 = HashMap<String,String>()
    //                map1["glbm"] = UserInfo.GLBM
    //                mNormalPresenter!!.doNetworkTask(map1,Constants.YG_SEE_GLBM)
    //            }

            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    private fun bindEvent() {

        bt_next.setOnClickListener {

            if (!CheckEditTxetUtils.checkEditextValuable(ed_xm, ed_sfz, et_lxdh)) {
                showToast("请完善信息")
                return@setOnClickListener
            }

            if ("A" == sp_sfz.selectedItem.toString().split(":")[0] && !SFZCheckUtil.isCorrect(ed_sfz.text.toString())) {
                showToast("请正确填写所有人身份证信息")
                return@setOnClickListener
            }

            if (!StringUtils.isPhoneNumber(et_lxdh.text.toString())) {
                showToast("请正确填写手机信息")
                return@setOnClickListener
            }

            if (mList.size == 0) {
                showToast("请至少选择一个角色权限")
                return@setOnClickListener
            }
            showLoadingDialog()
            val enity = YGPostEnity()
            val list = ArrayList<YGPostEnity.ListRole>()
            for (db in mList) {
                val listPole = YGPostEnity.ListRole()
                listPole.jsdh = db.split(":")[0]
                listPole.yhdh = ed_sfz.text.toString()
                list.add(listPole)
            }

            enity.listRole = list
            enity.bmmc = UserInfo.BMMC
//            enity.sfzmc = sp_sfz.selectedItem.toString().split(":")[0]
            enity.createtime = "" + System.currentTimeMillis()
            enity.lxdh = et_lxdh.text.toString()
            enity.sfzmhm = ed_sfz.text.toString()
            enity.xm = ed_xm.text.toString()
            enity.yhdh = ed_sfz.text.toString()

            enity.cjr = UserInfo.XM
            enity.zhzt = "0"  // 固定 2  待审核   0 正常

            if ("1".equals(intent.getStringExtra("type"))) {
                enity.glbm = EmployeeDetailActivity.mEmployeeListBean!!.glbm
                enity.jslx = EmployeeDetailActivity.mEmployeeListBean!!.jslx
                mNormalPresenter!!.doJsonPost(GsonUtils.toJson(enity), Constants.USER_USER_UPDATE)
            } else {
                enity.glbm = UserInfo.GLBM
                enity.jslx = UserInfo.JSLX
                mNormalPresenter!!.doJsonPost(GsonUtils.toJson(enity), Constants.YG_INSERT)
            }
        }
        iv_syr_scan.setOnClickListener {
            showIcCardReadModeDialog(Constants.SFZ_SYR)
        }
        ed_xm.filters = arrayOf(inputFilter)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ID_CARD_READ_CODE -> {
                    showToast("身份证已读取")
//                    data.getStringExtra(Constants.NAME)
//                    ed_sfz.setText(data!!.getStringExtra(Constants.NUMBER))
//                    data.getStringExtra(Constants.ADDRESS)
//                    headPhoto = data!!.getByteArrayExtra(Constants.PHOTO)
//                    if (null != headPhoto) {
//                        OtherUtils.goFaceComparePlugin(this, headPhoto, FACE_COMPARE_CODE)
//                    }
                }

                FACE_COMPARE_CODE -> {

                }

                Constants.SFZ_SYR -> {
                    ed_sfz.text = Editable.Factory.getInstance().newEditable(data!!.getStringExtra(Constants.NUMBER))
                    ed_xm.text = Editable.Factory.getInstance().newEditable(data.getStringExtra(Constants.NAME))
                }

                //OCR
                Integer.toHexString(Constants.SFZ_SYR).toInt() -> {
                    showLoadingDialog()
                    ThreadPoolManager.instance.execute(Runnable {
                        val bitmap = BitmapFactory.decodeFile(imgOCRFile!!.path) //父类的fileimage
                        onStartOCRSFZ(bitmap, ed_xm, ed_sfz)
                    })
                }
            }
        }
    }

    override fun getCheckState(list: ArrayList<String>) {
        mList = list
    }

    override fun getLayout(): Int {
        return R.layout.activity_employee_edit
    }

}
