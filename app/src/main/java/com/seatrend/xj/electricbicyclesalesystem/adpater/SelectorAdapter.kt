package com.seatrend.xj.electricbicyclesalesystem.adpater


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.Yw3CzActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.FHEnity
import com.seatrend.xj.electricbicyclesalesystem.util.*

/**
 * Created by ly on 2019/10/23 14:17
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class SelectorAdapter(private var mContext: Context? = null, private var mData: ArrayList<String>? = null) : RecyclerView.Adapter<SelectorAdapter.MyHolder>() {
    var checkList = ArrayList<String>()
    var listener: CheckState? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.item_ywyy_box, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun setData(data: ArrayList<String>?) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder!!.setItemView(position)
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {


        var cb_yy_item: CheckBox? = null

        init {
            cb_yy_item = view.findViewById(R.id.cb_yy_item)
            bindEvent()
        }

        private fun bindEvent() {
            cb_yy_item!!.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    checkList.add(mData!!.get(adapterPosition))
                    listener!!.getCheckState(checkList)
                } else {
                    checkList.remove(mData!!.get(adapterPosition))
                    listener!!.getCheckState(checkList)
                }
            }
        }

        fun setItemView(position: Int) {
            cb_yy_item!!.text = mData!!.get(position)
        }
    }

    fun setCheckListener(ls: CheckState) {
        listener = ls
    }

    interface CheckState {
        fun getCheckState(list: ArrayList<String>)
    }
}
