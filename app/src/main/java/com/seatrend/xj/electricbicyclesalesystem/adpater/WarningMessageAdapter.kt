package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.WarningMessageActivity
import com.seatrend.xj.electricbicyclesalesystem.entity.WarningMessageEntity
import com.seatrend.xj.electricbicyclesalesystem.util.CllxUtils
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils

/**
 * Created by seatrend on 2018/12/27.
 */

class WarningMessageAdapter(private val mContext: Context) : RecyclerView.Adapter<WarningMessageAdapter.MyViewHolder>() {


    private val mCompare: Comparator<in WarningMessageEntity.Data.WList> = Comparator { o1, o2 ->
        if(o1.qs == o2.qs){
             o1.yjsj.compareTo(o2.yjsj)
        }else{
            o1.qs.compareTo(o2.qs)
        }
    }
    private var listData = ArrayList<WarningMessageEntity.Data.WList>()

    fun addData(data: ArrayList<WarningMessageEntity.Data.WList>) {
        listData.addAll(data)
//        listData.sortWith(mCompare)
        notifyDataSetChanged()
    }

    fun clearData() {
        listData.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_warning_message, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initItemView(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLsh: TextView? = null
        var tvCllx: TextView? = null
        var tvCphm: TextView? = null
        var tvYjlx: TextView? = null
        var tvYjms: TextView? = null
        var tvYjsj: TextView? = null
        var qsBtn: Button? = null
        var enity: WarningMessageEntity.Data.WList? = null

        init {
            tvLsh = itemView.findViewById(R.id.tv_lsh)
            tvCllx = itemView.findViewById(R.id.tv_cllx)
            tvCphm = itemView.findViewById(R.id.tv_cphm)
            tvYjlx = itemView.findViewById(R.id.tv_yjlx)
            tvYjms = itemView.findViewById(R.id.tv_yjms)
            tvYjsj = itemView.findViewById(R.id.tv_yjsj)
            qsBtn = itemView.findViewById(R.id.bt_next)
            bindEvent()
        }

        private fun bindEvent() {
            qsBtn!!.setOnClickListener {
                (mContext as WarningMessageActivity).qsQuest(enity!!.lsh)
            }
        }

        fun initItemView(dataBean: WarningMessageEntity.Data.WList) {
            enity = dataBean

            try {
                tvLsh!!.text = dataBean.lsh
                tvCllx!!.text = CllxUtils.getCllxDMSM(dataBean.cllx)
                tvCphm!!.text = dataBean.hphm
                tvYjlx!!.text = dataBean.yjlx
                tvYjms!!.text = dataBean.yjyy
                tvYjsj!!.text = StringUtils.longToStringData(dataBean.yjsj)

                qsBtn!!.isEnabled = "1" != dataBean.qs
            } catch (e: Exception) {

            }
        }
    }
}
