package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.YgAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.EmployeeBean
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_employee.*
import kotlinx.android.synthetic.main.activity_yw.*
import kotlinx.android.synthetic.main.recyclerview.*

class EmployeeActivity : BaseActivity(), NormalView, YgAdapter.onItemListener {

    private var page: Int = 1
    private var mData = ArrayList<EmployeeBean.Data.EmployList>()
    private var adapter: YgAdapter? = null
    private var searchFlag: Boolean = false

    private var mNormalPresenter: NormalPresenter? = null
    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.YG_SEARCH_LIST.equals(commonResponse.url)) {
            var employeeBean = GsonUtils.gson(commonResponse.getResponseString(), EmployeeBean::class.java)
            if (null == employeeBean || null == employeeBean.data || null == employeeBean.data.list || employeeBean.data.list.size == 0) {
                showToast("查询员工数据信息为空")
                if(searchFlag){
                    mData.clear()
                    runOnUiThread { adapter!!.setData(mData) }
                }
                shuaxin.finishLoadmore()
                return
            }
            if (page == 1) {
                if (!searchFlag) {//不是搜索框点击
                    shuaxin.finishLoadmore()
                    mData.clear()
                    mData.addAll(employeeBean.data.list)
                    runOnUiThread { adapter!!.setData(mData) }
                } else {
                    mData.clear()
                    mData.addAll(employeeBean.data.list)
                    runOnUiThread { adapter!!.setData(mData) }
                }
            } else if (page > 1) {
                if (!searchFlag) {//不是搜索框点击
                    shuaxin.finishLoadmore()
                    mData.addAll(employeeBean.data.list)
                    runOnUiThread { adapter!!.setData(mData) }
                } else {
                    mData.clear()
                    mData.addAll(employeeBean.data.list)
                    runOnUiThread { adapter!!.setData(mData) }
                }
            }

        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
        if (!searchFlag) {//不是搜索框点击
            shuaxin.finishLoadmore()
        }
        searchFlag = false
        if(Constants.YG_SEARCH_LIST.equals(commonResponse.url)){
            mData.clear()
            runOnUiThread { adapter!!.setData(mData) }
        }
    }

    override fun initView() {
        setPageTitle("员工备案")
        bt_next.text = "员工备案"
        mNormalPresenter = NormalPresenter(this)
        initEvent()
        initRecycleView()
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    //list的初始化
    private fun initRecycleView() {
        m_recycler_view!!.layoutManager = LinearLayoutManager(this)
        adapter = YgAdapter(this, mData)
        adapter!!.setListener(this)
        m_recycler_view.adapter = adapter
    }

    //获取数据
    private fun getData() {
        showLoadingDialog()
        val map = HashMap<String, String?>()
        map["glbm"] = UserInfo.GLBM
        map["curPage"] = "" + page
        map["pageSize"] = "10"
        mNormalPresenter!!.doNetworkTask(map, Constants.YG_SEARCH_LIST)
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
        searchview.queryHint = "请用身份证明号码查询"
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
            searchFlag = false
            showLoadingDialog()
            if(TextUtils.isEmpty(searchString)||TextUtils.isEmpty(searchString!!.trim())){
                val map = HashMap<String, String?>()
                map["glbm"] = UserInfo.GLBM
                map["curPage"] = "1"
                map["pageSize"] = "10"
                mNormalPresenter!!.doNetworkTask(map, Constants.YG_SEARCH_LIST)
            }else{
                searchFlag = true
                val map = HashMap<String, String?>()
                map["sfzmhm"] = searchString!!.trim()
                map["glbm"] = UserInfo.GLBM
                map["curPage"] = "1"
                map["pageSize"] = "1"
                mNormalPresenter!!.doNetworkTask(map, Constants.YG_SEARCH_LIST)
            }
        }

        shuaxin.setOnLoadmoreListener {
            page++
            val map = HashMap<String, String?>()
            map["glbm"] = UserInfo.GLBM
            map["curPage"] = "" + page
            map["pageSize"] = "10"

            mNormalPresenter!!.doNetworkTask(map, Constants.YG_SEARCH_LIST)
        }

        bt_next.setOnClickListener {
            val intent = Intent(this, EmployeeEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun itemClick(sfzhm: String?, position: Int) {
        if (position >= 0 && position <= mData.size) {
            intent.setClass(this, EmployeeDetailActivity::class.java)
            EmployeeDetailActivity.mEmployeeListBean = mData.get(position)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        searchFlag = false
    }

    override fun getLayout(): Int {
        return R.layout.activity_employee
    }
}
