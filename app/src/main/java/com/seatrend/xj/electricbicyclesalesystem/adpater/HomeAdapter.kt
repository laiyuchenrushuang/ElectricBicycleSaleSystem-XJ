package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.Constants

class HomeAdapter(val mContext: Context,val mData:ArrayList<String>) : RecyclerView.Adapter<HomeAdapter.MyHolder>() {

    var  listener:ItemOnclickListener ?=null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.activity_home_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return  mData.size
    }

    fun setOnclick( i:ItemOnclickListener){
        this.listener = i
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder!!.setItemView(position)
    }


    inner class MyHolder(val  view:View):RecyclerView.ViewHolder(view){

        var home_item_cv :CardView ?=null
        var home_item_iv:ImageView ?=null
        var home_item_tv:TextView ?= null
        var ll_item:LinearLayout ?= null

        var pos :Int ?=null

        fun setItemView(position: Int) {
            pos = position
            home_item_cv = view.findViewById(R.id.home_item_cv)
            ll_item = view.findViewById(R.id.ll_item)
            home_item_iv = view.findViewById(R.id.home_item_iv)
            home_item_tv = view.findViewById(R.id.home_item_tv)

            initItemContent(position)

            bindEvent()
        }

        private fun initItemContent(position: Int) {

            when(mData[position]){
                Constants.PMS_CY->{
                    home_item_tv!!.text = mContext.resources.getText(R.string.home_clcy)
                    home_item_iv!!.setImageResource(R.drawable.clcy_icon)
                }

                Constants.PMS_DJ->{
                    home_item_tv!!.text = mContext.resources.getText(R.string.home_ywdj)
                    home_item_iv!!.setImageResource(R.drawable.ywdj_icon)
                }

                Constants.PMS_FH->{
                    home_item_tv!!.text = mContext.resources.getText(R.string.home_ywfh)
                    home_item_iv!!.setImageResource(R.drawable.fh_icon)
                }

                Constants.PMS_GD->{
                    home_item_tv!!.text = mContext.resources.getText(R.string.home_ywgd)
                    home_item_iv!!.setImageResource(R.drawable.dagd_icon)
                }

                Constants.PMS_TB->{
                    home_item_tv!!.text = mContext.resources.getText(R.string.home_ywtb)
                    home_item_iv!!.setImageResource(R.drawable.tb_icon)
                }

                Constants.PMS_CX->{
                    home_item_tv!!.text = mContext.resources.getText(R.string.home_clcx)
                    home_item_iv!!.setImageResource(R.drawable.cx_icon)
                }

                Constants.PMS_HY->{
                    home_item_tv!!.text = mContext.resources.getText(R.string.home_ccchy)
                    home_item_iv!!.setImageResource(R.drawable.cx_icon)
                }
                Constants.PMS_BP->{
                    home_item_tv!!.text = mContext.resources.getText(R.string.home_zpbp)
                    home_item_iv!!.setImageResource(R.drawable.zpbp)
                }
            }


        }

        private fun bindEvent() {

            home_item_cv!!.setOnClickListener {
                Log.d("lylog","  clcik")
                listener!!.clickPosition(pos!!)
            }
            ll_item!!.setOnClickListener {
                Log.d("lylog","  clcik ll_item")
                listener!!.clickPosition(pos!!)
            }
        }


    }

    interface ItemOnclickListener{
        fun clickPosition(position: Int)
    }

}
