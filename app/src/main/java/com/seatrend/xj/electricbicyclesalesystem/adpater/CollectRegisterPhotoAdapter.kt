package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R

/**
 * Created by seatrend on 2019/5/15.
 */

class CollectRegisterPhotoAdapter(private var mContext:Context?=null): RecyclerView.Adapter<CollectRegisterPhotoAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
       var view = LayoutInflater.from(mContext).inflate(R.layout.item_collect_register_photo_adapter,parent,false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.initItemView()
    }


    inner class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

        public fun initItemView(){

        }
    }

}