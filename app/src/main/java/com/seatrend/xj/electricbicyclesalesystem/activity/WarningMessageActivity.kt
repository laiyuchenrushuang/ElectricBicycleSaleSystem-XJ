package com.seatrend.xj.electricbicyclesalesystem.activity

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.WarningMessageAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.entity.WarningMessageEntity
import com.seatrend.xj.electricbicyclesalesystem.persenter.WarningMessagePersenter
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog
import com.seatrend.xj.electricbicyclesalesystem.view.WarningMessageView
import kotlinx.android.synthetic.main.activity_warning.*
import kotlinx.android.synthetic.main.common_no_data.*
import kotlinx.android.synthetic.main.recyclerview.*

/**
 * Created by ly on 2019/10/25 14:24
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class WarningMessageActivity : BaseActivity(), WarningMessageView {

    private var mWarningMessagePersenter: WarningMessagePersenter? = null

    private var pageNum = 1
    private var mWarningMessageAdapter: WarningMessageAdapter? = null

    private var mTypeLv: ListView? = null
    private var pop: PopupWindow? = null  //业务状态
    private var testDataAdapter: ArrayAdapter<String>? = null
    private var listData = java.util.ArrayList<String>()
    private var searchFlag: Boolean = false
    private var ifToday = true
    override fun initView() {
        tv_titile.text = "业务预警"
        bindEvent()
        mWarningMessagePersenter = WarningMessagePersenter(this)
        m_recycler_view.layoutManager = LinearLayoutManager(this)
        mWarningMessageAdapter = WarningMessageAdapter(this)
        m_recycler_view.adapter = mWarningMessageAdapter

        listData.add("今天")
        listData.add("全部")
        initEvent()
        getData(ifToday)
    }


    /**
     * 按钮初始化和事件绑定
     */
    private fun initEvent() {
        shuaxin.isEnableRefresh = false
        var searchString: String? = null

        //搜索框不自动缩小为一个搜索图标，而是match_parent
        searchview.setIconifiedByDefault(false)
        //显示搜索按钮
        searchview.isSubmitButtonEnabled = false
        //设置提示hint
        searchview.queryHint = "请用号牌号码或整车编码查询"
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
            pageNum = 1
            searchFlag = false
            searchview.clearFocus()
            if (TextUtils.isEmpty(searchString)) { //查询条件为空，默认今天查询
                tv_right!!.text = "今天"
                getData(true)
            } else if (searchString!!.trim().length > 10) { //整车编码查询
                showLoadingDialog()
                val map = HashMap<String, String?>()
                map["curPage"] = "1"
                map["pageSize"] = "20"
                map["glbm"] = UserInfo.GLBM
                map["zcbm"] = searchString
                mWarningMessagePersenter!!.doNetworkTask(map, Constants.WARNING_MESSAGE)
            } else {  //号牌号码查询
                showLoadingDialog()
                val map = HashMap<String, String?>()
                map["hphm"] = searchString!!.trim().toUpperCase()
                map["curPage"] = "1"
                map["pageSize"] = "20"
                map["glbm"] = UserInfo.GLBM
                mWarningMessagePersenter!!.doNetworkTask(map, Constants.WARNING_MESSAGE)
            }
        }

        shuaxin.setOnLoadmoreListener {
            showLoadingDialog()
            pageNum++
            val map = HashMap<String, String>()
            map.put("curPage", pageNum.toString())
            map.put("pageSize", "20")
            map["glbm"] = UserInfo.GLBM
            if (ifToday) {
                map.put("toDay", "1") // (1返回当前的数据,不传或传null返回全部 按时间降序排列)
            }
            mWarningMessagePersenter!!.doNetworkTask(map, Constants.WARNING_MESSAGE)
        }
    }

    private fun bindEvent() {
        tv_right.setOnClickListener {
            initSelectYwztPopup()
            if (pop != null && !pop!!.isShowing) {
                pop!!.animationStyle = R.style.pop_animation
                pop!!.showAsDropDown(tv_right)
            }
        }
    }


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        try {
            if (commonResponse.getUrl() == Constants.WARNING_MESSAGE) {
                if (pageNum > 1) {
                    var enity = GsonUtils.gson(commonResponse.getResponseString(), WarningMessageEntity::class.java)
                    if (enity != null && enity.data != null && enity.data.list != null && enity.data.list.size > 0) {
                        mWarningMessageAdapter!!.addData(enity.data.list as ArrayList<WarningMessageEntity.Data.WList>)
                        runOnUiThread {
                            ll_no_data.visibility = View.GONE
                        }
                    } else {
                        pageNum--
                        if (pageNum < 1) {
                            pageNum = 1
                        }
                    }
                    shuaxin.finishLoadmore()
                } else {
                    var enity = GsonUtils.gson(commonResponse.getResponseString(), WarningMessageEntity::class.java)
                    if (enity != null && enity.data != null && enity.data.list != null && enity.data.list.size > 0) {
                        mWarningMessageAdapter!!.clearData()
                        mWarningMessageAdapter!!.addData(enity.data.list as ArrayList<WarningMessageEntity.Data.WList>)
                        runOnUiThread {
                            ll_no_data.visibility = View.GONE
                        }
                    } else {
                        showToast("size is 0")
                    }
                }

            }
            if (commonResponse.getUrl() == Constants.WARNING_QS) {
                showToast("签收成功")
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()

        if (commonResponse.getUrl() == Constants.WARNING_MESSAGE) {
            shuaxin.finishLoadmore()
            pageNum--
            if (pageNum < 1) pageNum = 1
        }
        showErrorDialog(commonResponse.getResponseString())
    }

    private fun getData(today: Boolean) {
        showLoadingDialog()
        val map = HashMap<String, String>()
        map.put("curPage", pageNum.toString())
        map.put("pageSize", "20")
        map["glbm"] = UserInfo.GLBM
        if (today) {
            map.put("toDay", "1") // (1返回当前的数据,不传或传null返回全部 按时间降序排列)
        }
        mWarningMessagePersenter!!.doNetworkTask(map, Constants.WARNING_MESSAGE)
    }

    fun qsQuest(lsh: String) {
        val map = HashMap<String, String>()
        map.put("lsh", lsh)
        map.put("qsr", UserInfo.XM)
        LoadingDialog.getInstance().showLoadDialog(this)
        mWarningMessagePersenter!!.doNetworkTask(map, Constants.WARNING_QS)
    }


    private fun initSelectYwztPopup() {
        mTypeLv = ListView(this)
        mTypeLv!!.background = ContextCompat.getDrawable(this, R.color.white)
        // 设置适配器
        testDataAdapter = ArrayAdapter(this, R.layout.popup_text_item, listData)
        mTypeLv!!.adapter = testDataAdapter
        mTypeLv!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            when (listData[position]) {
                "今天" -> {
                    showToast("今天")
                    tv_right.text = "今天"
                    ifToday = true
                    pageNum = 1
                    getData(ifToday)
                }
                "全部" -> {
                    showToast("全部")
                    tv_right.text = "全部"
                    ifToday = false
                    pageNum = 1
                    getData(ifToday)
                }
            }
            pop!!.dismiss()
        }
        pop = PopupWindow(mTypeLv, tv_right.width, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        pop!!.isFocusable = true
        pop!!.isOutsideTouchable = true
        pop!!.setOnDismissListener {
            // 关闭popup窗口
            pop!!.dismiss()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_warning
    }
}