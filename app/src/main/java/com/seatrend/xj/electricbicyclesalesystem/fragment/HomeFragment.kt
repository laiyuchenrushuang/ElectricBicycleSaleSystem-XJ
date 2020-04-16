package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.*
import com.seatrend.xj.electricbicyclesalesystem.adpater.HomeAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import kotlinx.android.synthetic.main.fragment_home_1.*

/**
 * Created by ly on 2019/10/25 13:40
 */
class HomeFragment : BaseFragment() , HomeAdapter.ItemOnclickListener {


    val mData = ArrayList<String>()

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_home_1, container, false)
    }

    override fun initView() {

        initRecycleView()
        getData()

        showLog(mData.toString())
    }

    private fun getData() {
        var enity = MainOtherActivity.mLoginEnity

        if (enity != null && enity.data != null && enity.data.seaPrograms != null && enity.data.seaPrograms.size > 0) {
            for (db in enity.data.seaPrograms) {

                if (Constants.PMS_CX.equals(db.qxdm)) {
                    if(!mData.contains(Constants.PMS_HY)){
                        mData.add(0,Constants.PMS_HY)//设置first位置
                    }
                    if(!mData.contains(Constants.PMS_CX)) {
                        mData.add(1, Constants.PMS_CX)//设置second位置
                    }

                }else{
                    //这个没配置 所以强行写在这里
                    if(!mData.contains(Constants.PMS_HY)){
                        mData.add(0,Constants.PMS_HY) //设置first位置
                    }
                }

                if (Constants.PMS_CY.equals(db.qxdm)) {
                    if(!mData.contains(Constants.PMS_CY)) {
                        mData.add(Constants.PMS_CY)
                    }
                }
                if (Constants.PMS_DJ.equals(db.qxdm)) {
                    if(!mData.contains(Constants.PMS_DJ)) {
                        mData.add(Constants.PMS_DJ)
                    }
                }
                if (Constants.PMS_FH.equals(db.qxdm)) {
                    if(!mData.contains(Constants.PMS_FH)) {
                        mData.add(Constants.PMS_FH)
                    }
                }
                if (Constants.PMS_GD.equals(db.qxdm)) {
                    if(!mData.contains(Constants.PMS_GD)) {
                        mData.add(Constants.PMS_GD)
                    }
                }
                if (Constants.PMS_TB.equals(db.qxdm)) {
                    if(!mData.contains(Constants.PMS_TB)) {
                        mData.add(Constants.PMS_TB)
                    }
                }
            }
        }
    }

    private fun initRecycleView() {
        home_rv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        var adapter = HomeAdapter(context,mData)
        adapter.setOnclick(this)
        home_rv.adapter = adapter
    }



    override fun clickPosition(position: Int) {
        Log.d("lylog callck","  clcik")
        when(mData[position]){

            //查验
            Constants.PMS_CY->{
                startActivity(Intent(context, ChaYanEntranceActivity::class.java))
            }
            //登记
            Constants.PMS_DJ->{
                startActivity(Intent(context, YWEntranceActivity::class.java))
            }
            //复核
            Constants.PMS_FH->{
                startActivity(Intent(context, YWCheckActivity::class.java))
            }
            //归档
            Constants.PMS_GD->{
                startActivity(Intent(context, Archive2FileActivity::class.java))
            }
            //退办
            Constants.PMS_TB->{
                startActivity(Intent(context, YwTBSearchActivity::class.java))
            }
            //CCC 查询
            Constants.PMS_HY->{
                startActivity(Intent(context, CccCheckActivity::class.java))
            }
            //车辆 查询
            Constants.PMS_CX->{
                startActivity(Intent(context, YwCarCheckActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mData.clear()
    }
}