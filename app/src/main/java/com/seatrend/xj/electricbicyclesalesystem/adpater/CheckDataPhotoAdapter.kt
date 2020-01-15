package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.ShowPhotoActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoTypeEntity
import java.util.*

/**
 * Created by seatrend on 2019/5/15.
 */
class CheckDataPhotoAdapter(private var mContext: Context? = null) : RecyclerView.Adapter<CheckDataPhotoAdapter.MyViewHolder>() {


    private var data = ArrayList<PhotoTypeEntity.DataBean.ConfigBean>()
    val mCompareR: Comparator<PhotoTypeEntity.DataBean.ConfigBean> =
            Comparator { p0, p1 ->
                if ("B4" == p0!!.zplx) { //其他
                    0
                } else {
                    -1
                }
            }

    fun setPhotoType(list: ArrayList<PhotoTypeEntity.DataBean.ConfigBean>) {
        this.data = list
        Collections.sort(data, mCompareR)
        notifyDataSetChanged()
    }

    fun setPhoto(position: Int, path: String) {
        data[position].zpPath = path
        notifyItemChanged(position)
    }

    fun getDataList(): ArrayList<PhotoTypeEntity.DataBean.ConfigBean> {
        return this.data
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_check_data_photo_adapter, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.initItemView(data[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var tvType: TextView? = null
        private var ivPhoto: ImageView? = null
        private var ivDelete: ImageView? = null

        init {
            tvType = itemView.findViewById(R.id.tv_type)
            ivPhoto = itemView.findViewById(R.id.iv_photo)
            ivDelete = itemView.findViewById(R.id.iv_delete)
            ivPhoto!!.setOnClickListener {
                if (mOnClickListener != null && data[adapterPosition].zplj == null) {
                    mOnClickListener!!.itemOnClick(adapterPosition)
                } else if (data[adapterPosition].zpPath != null) {
                    val intent = Intent(mContext, ShowPhotoActivity::class.java)
                    intent.putExtra(Constants.PATH, data[adapterPosition].zpPath)
                    mContext!!.startActivity(intent)

                    (mContext as BaseActivity).startRotateAlphaAcaleAnimation()
                }
            }
            ivDelete!!.setOnClickListener {
                data[adapterPosition].zplj = null
                data[adapterPosition].zpPath = null
                notifyItemChanged(adapterPosition)
                mDeleteListener!!.itemdelete(adapterPosition)
            }

        }

        fun initItemView(bean: PhotoTypeEntity.DataBean.ConfigBean) {
            tvType!!.text = bean.zmmc
            if("B4".equals(bean.zplx)){ //其他 不是必拍项
                tvType!!.setTextColor(Color.BLACK)
            }else{
                tvType!!.setTextColor(Color.RED)
            }
            if (bean.zpPath != null && bean.zpPath.isNotEmpty()) {
                Glide.with(mContext).load(bean.zpPath).centerCrop().error(R.drawable.error_image).into(ivPhoto)
                ivDelete!!.visibility = View.VISIBLE
            } else {
                ivPhoto!!.setImageResource(R.drawable.take_photo)
                ivPhoto!!.scaleType = ImageView.ScaleType.CENTER
                ivDelete!!.visibility = View.GONE
            }
        }
    }

    private var mOnClickListener: itemOnClickListener? = null

    fun setItemOnClick(listener: itemOnClickListener) {
        mOnClickListener = listener
    }

    interface itemOnClickListener {
        fun itemOnClick(position: Int)
    }

    private var mDeleteListener: itemDeleteClickListener? = null

    fun setItemdeleteClick(listen: itemDeleteClickListener) {
        mDeleteListener = listen
    }

    interface itemDeleteClickListener {
        fun itemdelete(position: Int)
    }
}