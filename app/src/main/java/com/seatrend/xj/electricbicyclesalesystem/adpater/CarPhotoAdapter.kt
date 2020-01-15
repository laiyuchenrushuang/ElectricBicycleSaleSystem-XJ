package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R

/**
 * Created by ly on 2019/7/4 13:21
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CarPhotoAdapter(private var mContext: Context? = null, private var list: ArrayList<HashMap<String, String?>>? = null) : RecyclerView.Adapter<CarPhotoAdapter.MyViewHolder>() {

    private var mItemOnclcik: ItemOnclickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_check_data_photo_adapter, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.initItemView(list!!.get(position))
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvType: TextView? = null
        private var ivPhoto: ImageView? = null
        private var ivDelete: ImageView? = null

        init {
            tvType = view.findViewById(R.id.tv_type)
            ivPhoto = view.findViewById(R.id.iv_photo)
            ivDelete = view.findViewById(R.id.iv_delete)
            ivPhoto!!.setOnClickListener {
                mItemOnclcik!!.itemOnClick(adapterPosition)
            }
            ivDelete!!.setOnClickListener {
                var map: HashMap<String, String?> = list!!.get(adapterPosition)
                map[getKey(map)] = ""
                notifyItemChanged(adapterPosition)
            }

        }

        fun initItemView(map: HashMap<String, String?>) {
            tvType!!.text = getKey(map)
            if (!"".equals(map[tvType!!.text])) {
                Glide.with(mContext).load(map[tvType!!.text]).centerCrop().error(R.drawable.error_image).into(ivPhoto)
            } else {
                ivPhoto!!.setImageResource(R.drawable.take_photo)
                ivPhoto!!.scaleType = ImageView.ScaleType.CENTER
            }
            ivDelete!!.visibility = View.VISIBLE

        }

    }

    private fun getKey(map: HashMap<String, String?>): String {
        var type = map.keys
        var key = ""
        for (s: String in type) {
            key = s
        }
        return key
    }

    fun setItemClickListener(listener: ItemOnclickListener) {
        mItemOnclcik = listener
    }

    interface ItemOnclickListener {
        fun itemOnClick(position: Int)
    }
}