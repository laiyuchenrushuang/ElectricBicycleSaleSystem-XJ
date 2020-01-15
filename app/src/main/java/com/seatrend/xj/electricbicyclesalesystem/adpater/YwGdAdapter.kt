package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.seatrend.xj.electricbicyclesalesystem.R
import android.view.animation.Animation
import android.view.animation.Transformation
import com.seatrend.xj.electricbicyclesalesystem.entity.GDEnity
import com.seatrend.xj.electricbicyclesalesystem.util.FHUtil


/**
 * Created by ly on 2019/10/23 14:17
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class YwGdAdapter(private var mContext: Context? = null, private var mData: ArrayList<GDEnity.Data.GDList>? = null) : RecyclerView.Adapter<YwGdAdapter.MyHolder>() {


    private var mItemListener: itemOnclickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.activity_gd_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun setData(data: ArrayList<GDEnity.Data.GDList>?) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder!!.setItemView(position)
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tv_zcbm: TextView? = null
        var tv_clhp: TextView? = null
        var tv_fhzt: TextView? = null
        var btn_gd: Button? = null
        var rl_item: RelativeLayout? = null


        init {
            tv_zcbm = view.findViewById(R.id.tv_zcbm)
            tv_clhp = view.findViewById(R.id.tv_clhp)
            tv_fhzt = view.findViewById(R.id.tv_fhzt)
            btn_gd = view.findViewById(R.id.btn_gd)
            rl_item = view.findViewById(R.id.rl_item)

            bindItemOnclickEvent()
        }

        /**
         * item的点击事件
         */
        private fun bindItemOnclickEvent() {

            rl_item!!.setOnClickListener {
                var zcbm = mData!![adapterPosition].zcbm
                var ywlx = mData!![adapterPosition].ywlx
                var lsh = mData!![adapterPosition].lsh
                mItemListener!!.itemOnclick(zcbm,ywlx,lsh,adapterPosition)
            }
            btn_gd!!.setOnClickListener {
//                deleteItem(rl_item!!, adapterPosition)
                mItemListener!!.itemBtnOnclick( adapterPosition)
            }
        }

        fun setItemView(position: Int) {
            var zcbm: String? = mData!![position].zcbm
            var clhp: String? = mData!![position].cph
            var fhzt: String? = mData!![position].fhbj //复核标记 9-无需复核，0-待复核，1-复核通过，2-复核未通过

            tv_fhzt!!.text = FHUtil.getDMSM(fhzt)
            tv_zcbm!!.text = zcbm
            tv_clhp!!.text = clhp
            if ("1".equals(fhzt)) {
                tv_fhzt!!.setTextColor(Color.GREEN)
            } else if ("2".equals(fhzt)) {
                tv_fhzt!!.setTextColor(Color.RED)
            } else if ("0".equals(fhzt)) {
                tv_fhzt!!.setTextColor(Color.BLUE)
            }
        }
    }

    fun setOnclickListener(l: itemOnclickListener) {
        mItemListener = l
    }

    fun collapse(view: View, al: Animation.AnimationListener?) {
        val originHeight = view.measuredHeight
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1.0f) {
                    view.layoutParams.height = originHeight//更改部分避免删除两个Item
                } else {
                    view.layoutParams.height = originHeight - (originHeight * interpolatedTime).toInt()
                }
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        if (al != null) {
            animation.setAnimationListener(al)
        }
        animation.duration = 120
        view.startAnimation(animation)
    }

    private fun deleteItem(view: View, position: Int) {

        val al = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                mData!!.removeAt(position)
                notifyDataSetChanged()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        }
        collapse(view, al)
    }

    interface itemOnclickListener {
        fun itemOnclick(zcbm: String?,ywlx: String?,lsh: String?, posion: Int)
        fun itemBtnOnclick( posion: Int)
    }
}
