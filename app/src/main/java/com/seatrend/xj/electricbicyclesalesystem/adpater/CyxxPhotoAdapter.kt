package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.ShowPhotoActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.util.LoadUrlUtils
import com.seatrend.xj.electricbicyclesalesystem.util.SharedPreferencesUtils
import java.util.ArrayList

/**
 * Created by ly on 2019/9/27 16:09
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CyxxPhotoAdapter(private var mContext: Context? = null, private var list: ArrayList<AllBikeMsgEnity.Data.PhotoList>? = null) : RecyclerView.Adapter<CyxxPhotoAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder? {
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_show_photo_adapter, parent, false)

        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.initItemView(list!!.get(position))
    }


    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var tvType: TextView? = null
        private var ivPhoto: ImageView? = null
        init {
            tvType = view.findViewById(R.id.tv_type)
            ivPhoto = view.findViewById(R.id.iv_photo)
            bindEvent()
        }

        private fun bindEvent() {
            ivPhoto!!.setOnClickListener {
                val intent = Intent(mContext, ShowPhotoActivity::class.java)
                intent.putExtra(Constants.PATH, LoadUrlUtils.loadurl(list!!.get(adapterPosition).zpdz))
                mContext!!.startActivity(intent)

                (mContext as BaseActivity).startRotateAlphaAcaleAnimation()
            }
        }

        fun initItemView(db: AllBikeMsgEnity.Data.PhotoList) {
            tvType!!.text = db.zpsm
            if (!"".equals(tvType!!.text)) {
//                Glide.with(mContext).load(LoadUrlUtils.loadurl(db.zpdz)).centerCrop().error(R.drawable.error_image)
//                        .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivPhoto)
                Glide.with(mContext).load(LoadUrlUtils.loadurl(db.zpdz)).centerCrop().error(R.drawable.error_image)
                        .into(ivPhoto)
            } else {
                ivPhoto!!.setImageResource(R.drawable.take_photo)
                ivPhoto!!.scaleType = ImageView.ScaleType.CENTER
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
    }
}
