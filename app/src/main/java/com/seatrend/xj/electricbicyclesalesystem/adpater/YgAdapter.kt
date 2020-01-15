package com.seatrend.xj.electricbicyclesalesystem.adpater

/**
 * Created by ly on 2019/11/4 15:31
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.entity.EmployeeBean
import com.seatrend.xj.electricbicyclesalesystem.entity.FHEnity
import com.seatrend.xj.electricbicyclesalesystem.util.*

/**
 * Created by ly on 2019/10/23 14:17
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class YgAdapter(private var mContext: Context? = null, private var mData: ArrayList<EmployeeBean.Data.EmployList>? = null) : RecyclerView.Adapter<YgAdapter.MyHolder>() {

    private var mListener: onItemListener?= null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.activity_yg_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun setData(data: ArrayList<EmployeeBean.Data.EmployList>?) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder!!.setItemView(position)
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_header:ImageView?=null
        var tv_xm:TextView?=null
        var tv_sfz:TextView?=null
        var tv_zt:TextView?=null
        var ll_item: LinearLayout? = null

        init {
            iv_header = view.findViewById(R.id.iv_header)
            tv_xm = view.findViewById(R.id.tv_xm)
            tv_sfz = view.findViewById(R.id.tv_sfz)
            tv_zt = view.findViewById(R.id.tv_zt)
            ll_item = view.findViewById(R.id.ll_item)
            bindItemOnclickEvent()
        }

        /**
         * item的点击事件
         */
        private fun bindItemOnclickEvent() {
            ll_item!!.setOnClickListener {
                mListener!!.itemClick(mData!!.get(adapterPosition).sfzmhm,adapterPosition)
            }
        }


        fun setItemView(position: Int) {
            if(null != mData!![position].photos && mData!![position].photos.size >0){
                for(db in mData!![position].photos){
                    if("C1".equals(db.zpzl)){
                        Glide.with(mContext).load(LoadUrlUtils.loadurl(db.zpdz)).centerCrop().error(R.drawable.error_image).into(iv_header)
                        break
                    }
                }
            }else{
                Glide.with(mContext).load(R.drawable.error_image).into(iv_header)
            }

            tv_xm!!.text = mData!![position].xm
            tv_sfz!!.text = mData!![position].sfzmhm
            tv_zt!!.text = mData!!.get(position).zhzt
        }
    }

    fun setListener(ls : onItemListener){
        mListener = ls
    }

    interface onItemListener {
        fun itemClick(sfz: String?, position: Int)
    }
}
