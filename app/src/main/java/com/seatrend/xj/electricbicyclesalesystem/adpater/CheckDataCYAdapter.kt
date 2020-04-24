package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.JudgeProjectEnity
import com.seatrend.xj.electricbicyclesalesystem.util.CheckBoxUtils
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog

class CheckDataCYAdapter(private var mContext: Context? = null, private var list: ArrayList<JudgeProjectEnity>? = null) : RecyclerView.Adapter<CheckDataCYAdapter.MyViewHolder>() {

    private var listener: DataChange? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.pan_ding_item, parent, false)
        return MyViewHolder(view)
    }

    fun setListener(ls: DataChange) {
        this.listener = ls
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.initItemView(position)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var text_content: TextView? = null
        var text_csys: TextView? = null
        var ll_check_box: LinearLayout? = null
        var cb_ok: CheckBox? = null
        var cb_no: CheckBox? = null


        fun initItemView(position: Int) {
            text_content = itemView.findViewById(R.id.pan_ding_content)
            ll_check_box = itemView.findViewById(R.id.ll_check_box)
            text_csys = itemView.findViewById(R.id.tv_csys)
            cb_ok = itemView.findViewById(R.id.cb_ok)
            cb_no = itemView.findViewById(R.id.cb_no)
            text_content!!.text = list!![position].content
            if ("0" == list!![position].order) {
                cb_no!!.isChecked = true
            } else {
                cb_ok!!.isChecked = true
            }
//            if (Constants.PD_CSYS == list!![position].content.split(":")[0]) {  //车身颜色的选项
//                text_csys!!.visibility = View.VISIBLE
//                text_csys!!.text = list!![position].csys
//                ll_check_box!!.visibility = View.GONE
//            } else {
//                ll_check_box!!.visibility = View.VISIBLE
//                text_csys!!.visibility = View.GONE
//            }
            bindEvent()
        }

        private fun bindEvent() {
            CheckBoxUtils.setListener(cb_ok, cb_no)
            cb_ok!!.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    cb_ok!!.isChecked = true
                    cb_no!!.isChecked = false
                    list!![adapterPosition].order = "1"
                } else {
                    cb_ok!!.isChecked = false
                    cb_no!!.isChecked = true
                    list!![adapterPosition].order = "0"
                }
                listener!!.DataChangeListener(list!!)

            }
            cb_no!!.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    cb_no!!.isChecked = true
                    cb_ok!!.isChecked = false
                    list!![adapterPosition].order = "0"
                    listener!!.DataChangeListener(list!!)
                } else {
                    cb_no!!.isChecked = false
                    cb_ok!!.isChecked = true
                    list!![adapterPosition].order = "1"
                }
                listener!!.DataChangeListener(list!!)
            }
        }
    }

    interface DataChange {
        fun DataChangeListener(data: ArrayList<JudgeProjectEnity>)
    }
}
