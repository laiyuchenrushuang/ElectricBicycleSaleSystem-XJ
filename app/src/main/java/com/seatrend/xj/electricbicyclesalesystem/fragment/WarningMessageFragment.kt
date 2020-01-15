//package com.seatrend.xj.electricbicyclesalesystem.fragment
//
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.text.TextUtils
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.seatrend.xj.electricbicyclesalesystem.R
//import com.seatrend.xj.electricbicyclesalesystem.adpater.WarningMessageAdapter
//import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
//import com.seatrend.xj.electricbicyclesalesystem.common.Constants
//import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
//import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
//import com.seatrend.xj.electricbicyclesalesystem.entity.WarningMessageEntity
//import com.seatrend.xj.electricbicyclesalesystem.persenter.WarningMessagePersenter
//import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
//import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog
//import com.seatrend.xj.electricbicyclesalesystem.view.WarningMessageView
//import kotlinx.android.synthetic.main.activity_warning_message.*
//import kotlinx.android.synthetic.main.recyclerview.*
//import java.lang.Exception
//
//class WarningMessageFragment : BaseFragment() , WarningMessageView {
//
//
//    private var isViewCreated=false
//    private var isUIVisible=false
//    private var isFirst=true
//
//    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
//        return inflater!!.inflate(R.layout.activity_warning_message, container, false)
//    }
//
//
//    private var mWarningMessagePersenter: WarningMessagePersenter?=null
//
//    private var pageNum=1;
//    private var mWarningMessageAdapter: WarningMessageAdapter?=null
//
//
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (isVisibleToUser){
//            isUIVisible=true
//           lazyLoad()
//        }else{
//            isUIVisible=false
//        }
//
//
//    }
//
//    private fun lazyLoad(){
//
//        if (isUIVisible && isViewCreated && isFirst){
//            isUIVisible=false
//            isViewCreated=false
//            isFirst=false
//            getData(true)
//        }
//
//    }
//
//    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        isViewCreated=true
//        lazyLoad()
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
//    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
//        LoadingDialog.getInstance().dismissLoadDialog()
//        try {
//            if (commonResponse.getUrl().equals(Constants.WARNING_MESSAGE)){
//                srl.finishLoadmore()
//                val warningMessageEntity = GsonUtils.gson(commonResponse.getResponseString(), WarningMessageEntity::class.java)
//                if (warningMessageEntity.data.size==0){
//                    if (pageNum>1){
//                        showToast("没有更多了")
//                    }else{
//                        srl.visibility=View.GONE
//                        showNoDataView("暂无预警信息")
//                    }
//
//                }else{
//                    srl.visibility=View.VISIBLE
//                    mWarningMessageAdapter!!.addData(warningMessageEntity.data as ArrayList<WarningMessageEntity.DataBean>)
//                }
//
//            }else if (commonResponse.getUrl().equals(Constants.WARNING_MESSAGE)){
//
//            }
//        }catch (e:Exception){
//            showToast(e.message.toString())
//        }
//
//    }
//
//    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
//        if (commonResponse.getUrl().equals(Constants.WARNING_MESSAGE)){
//            srl.finishLoadmore()
//            pageNum--;
//            if (pageNum<1) pageNum=1
//        }
//
//        LoadingDialog.getInstance().dismissLoadDialog()
//        showErrorDialog(commonResponse.getResponseString())
//        if (pageNum==1){
//            rl_refresh.visibility=View.VISIBLE
//        }
//    }
//
//    private fun getData(isShowDialog: Boolean){
//        val map=HashMap<String,String>()
//        map.put("glbm", UserInfo.GLBM+"")
//        map.put("shbj","2")
//        map.put("pageNum",pageNum.toString())
//        map.put("pageSize","20")
//
//        if (isShowDialog){
//            LoadingDialog.getInstance().showLoadDialog(context)
//        }
//        mWarningMessagePersenter!!.doNetworkTask(map,Constants.WARNING_MESSAGE)
//    }
//
//     fun queryCarMessage(zcbm: String){
//
//        if(TextUtils.isEmpty(zcbm) || zcbm.trim().isEmpty() || zcbm.equals("null")){
//            showToast("未获取到整车编码")
//            return
//        }
//
//        val map=HashMap<String,String>()
//        map.put("zcbm", zcbm)
//        LoadingDialog.getInstance().showLoadDialog(context)
//
//        mWarningMessagePersenter!!.doNetworkTask(map,Constants.FACTOTY_GET_CAR_MSG)
//    }
//
//    override fun initView() {
//        srl.isEnableRefresh = false
//        bindEvent()
//        mWarningMessagePersenter= WarningMessagePersenter(this)
//        m_recycler_view.layoutManager=LinearLayoutManager(context)
//        mWarningMessageAdapter= WarningMessageAdapter(context)
//        m_recycler_view.adapter=mWarningMessageAdapter
//    }
//
//    private fun bindEvent() {
//        srl.setOnLoadmoreListener {
//            pageNum++
//            getData(false)
//        }
//        btn_refresh.setOnClickListener {
//            getData(true)
//        }
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.i("sssss","onDestroy")
//    }
//}
