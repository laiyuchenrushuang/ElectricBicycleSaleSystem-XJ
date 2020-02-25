package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import com.seatrend.xj.electricbicyclesalesystem.R

/**
 * Created by ly on 2019/10/28 13:50
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class GridViewAdapter(private val mContext: Context, private val mData: ArrayList<String>) : BaseAdapter() {

    var checkList =  ArrayList<String>()
    var listener: CheckState?= null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewholder: ViewHolder
        var v: View
        if (null == convertView) {
            viewholder = ViewHolder()
            v = LayoutInflater.from(mContext).inflate(R.layout.item_ywyy_box, parent, false)
            viewholder.checkbox = v.findViewById(R.id.cb_yy_item)
            v.tag = viewholder
        } else {
            v = convertView
            viewholder = v.tag as ViewHolder
        }
        viewholder.checkbox.text = mData[position]

        //选择器的状态
        viewholder.checkbox.setOnCheckedChangeListener { _, b ->
            if (b) {
                checkList.add(mData[position])
                listener!!.getCheckState(checkList)
            } else {
                checkList.remove(mData[position])
                listener!!.getCheckState(checkList)
            }
        }
        return v
    }

    class ViewHolder {
        lateinit var checkbox: CheckBox
    }

    override fun getItem(p0: Int): Any {
        return mData[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return mData.size
    }
    fun setCheckListener(ls: CheckState){
        listener = ls
    }

    interface CheckState{
        fun  getCheckState(list:ArrayList<String>)
    }
}