package com.seatrend.xj.electricbicyclesalesystem.activity

import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.ArchiveFileAdaper
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.common.SearchViewState
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.entity.YwSearchByZcbmBean
import com.seatrend.xj.electricbicyclesalesystem.persenter.ArchiveFilePresenter
import com.seatrend.xj.electricbicyclesalesystem.util.CarUtils
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog
import com.seatrend.xj.electricbicyclesalesystem.view.ArchiveFileView
import kotlinx.android.synthetic.main.activity_archive_file.*
import kotlinx.android.synthetic.main.activity_archive_file.iv_btn_search
import kotlinx.android.synthetic.main.common_title.*

/**
 * Created by ly on 2019/7/8 14:33
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class ArchiveFileActivity : BaseActivity(), SearchViewState, ArchiveFileView {

    private var ll: LinearLayoutManager? = null
    var adapter: ArchiveFileAdaper? = null
    var mData = ArrayList<HashMap<String, String?>>()
    private var mArchiveFilePresenter: ArchiveFilePresenter? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        if (commonResponse.getUrl() == Constants.POST_DAGD) {
            startActivity(intent.setClass(this, RemindHPBFActivity::class.java))
        } else {
            val ywSearchBean = GsonUtils.gson(commonResponse.getResponseString(), YwSearchByZcbmBean::class.java)

            val zcbm = ywSearchBean.data.vehVehicle.zcbm  //整车编码
            val hphm = CarUtils.getYwBusiness(CarUtils.ywlxMap[CarUtils.CY_ZCDJ]!!, ywSearchBean.data.busList)!!.hphm  //获取注册登记的，没问题，其他更改，注册登记都要改
            for (db in ywSearchBean.data.busList) {
                if (db.gdry == null && db.ywlx != "1") {          //没有归档人员证明没归档,并且不是车辆查验，那么这个map是有效的
                    val map = HashMap<String, String?>()
                    map["zcbm"] = zcbm
                    map["clhm"] = hphm
                    map["shzt"] = db.jgbj
                    map["ywlx"] = CarUtils.dmzGetywlxMap[db.ywlx]!!
                    map["lsh"] = db.lsh
                    mData.add(map)
                }
            }
            adapter!!.notifyDataSetChanged()
            LoadingDialog.getInstance().dismissLoadDialog()
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle(getString(R.string.archive_title))
        mArchiveFilePresenter = ArchiveFilePresenter(this)
        initRecycleView()
        initEvent()
    }

    private fun initRecycleView() {
        ll = LinearLayoutManager(this)
        m_recycler_view!!.layoutManager = ll
        adapter = ArchiveFileAdaper(this, mData)
        m_recycler_view.adapter = adapter
        adapter!!.registerSetSearchViewListener(this)
    }

    private fun initEvent() {
        shuaxin.isEnableRefresh = false //取消下拉刷新

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            header.tooltipText = ""
        }
        var searchString: String? = null

        //搜索框不自动缩小为一个搜索图标，而是match_parent
        searchview.setIconifiedByDefault(false)
        //显示搜索按钮
        searchview.isSubmitButtonEnabled = false
        //设置提示hint
        searchview.queryHint = "查询车辆号牌或整车编码"
        searchview!!.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(pSubmit: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(pChange: String?): Boolean {
                searchString = pChange
                return false
            }

        })
        iv_btn_search.setOnClickListener {
            if (TextUtils.isEmpty(searchString)) {
                showToast("搜索的内容为空")
                return@setOnClickListener
            }
            val map = HashMap<String, String?>()
            map["zcbm"] = searchString!!.trim().toUpperCase()
            showLoadingDialog()
            mArchiveFilePresenter!!.doNetworkTask(map, Constants.FACTOTY_GET_CAR_MSG)
        }

        all_select!!.setOnCheckedChangeListener { _, check -> adapter!!.setAllselectListener(check) }
        all_commit!!.setOnClickListener {
            for (s in adapter!!.getPositionList()) {
                for (map in mData) {
                    if (map["lsh"] == s && map["shzt"] != "1") {
                        showErrorDialog("请选择审核状态通过的业务选项")
                        return@setOnClickListener
                    }
                }
            }
            val map = HashMap<String, String?>()
            map["filingJSON"] = adapter!!.getPositionList().toString()+""
            map["username"] = UserInfo.SFZMHM+""
            showLoadingDialog()
            mArchiveFilePresenter!!.doNetworkTask(map, Constants.POST_DAGD)
        }
    }

    override fun searchViewChangeListener(change: Boolean) {
        if (change) {
            all_select!!.isChecked = false
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_archive_file
    }

}