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
class YwFhAdapter(private var mContext: Context? = null, private var mData: ArrayList<FHEnity.Data.FHList>? = null) : RecyclerView.Adapter<YwFhAdapter.MyHolder>(), View.OnTouchListener {

    private var mListener: onItemListener?= null
    private var mTextTouchListener: View.OnTouchListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.activity_fhzt_item, parent, false)
        mTextTouchListener = this
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun setData(data: ArrayList<FHEnity.Data.FHList>?) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder!!.setItemView(position)
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tv_fh_syr: TextView? = null
        var tv_fh_zcbm: TextView? = null
        var tv_fh_cphm: TextView? = null
        var tv_fh_ywlx: TextView? = null
        var tv_fh_ywry: TextView? = null
        var tv_fh_slsj: TextView? = null
        var tv_fh_fhzt: TextView? = null

        var tv_fh_yy: TextView? = null
        var tv_fh_fhr: TextView? = null
        var tv_fh_fhsj: TextView? = null
        var tv_fh_fhyj: TextView? = null

        var ll_yy: LinearLayout? = null
        var ll_fhr: LinearLayout? = null
        var ll_fhsj: LinearLayout? = null
        var ll_fhyj: LinearLayout? = null
        var ll_item: LinearLayout? = null

        init {
            tv_fh_syr = view.findViewById(R.id.tv_fh_syr)
            tv_fh_zcbm = view.findViewById(R.id.tv_fh_zcbm)
            tv_fh_cphm = view.findViewById(R.id.tv_fh_cphm)
            tv_fh_ywlx = view.findViewById(R.id.tv_fh_ywlx)
            tv_fh_ywry = view.findViewById(R.id.tv_fh_ywry)
            tv_fh_slsj = view.findViewById(R.id.tv_fh_slsj)
            tv_fh_fhzt = view.findViewById(R.id.tv_fh_fhzt)

            tv_fh_yy = view.findViewById(R.id.tv_fh_yy)
            tv_fh_fhr = view.findViewById(R.id.tv_fh_fhr)
            tv_fh_fhsj = view.findViewById(R.id.tv_fh_fhsj)
            tv_fh_fhyj = view.findViewById(R.id.tv_fh_fhyj)

            ll_yy = view.findViewById(R.id.ll_yy)
            ll_fhr = view.findViewById(R.id.ll_fhr)
            ll_fhsj = view.findViewById(R.id.ll_fhsj)
            ll_fhyj = view.findViewById(R.id.ll_fhyj)
            ll_item = view.findViewById(R.id.ll_item)

            bindTextTouchEvent()
            bindItemOnclickEvent()
        }

        /**
         * item的点击事件
         */
        private fun bindItemOnclickEvent() {
            ll_item!!.setOnClickListener {
                var zcbm = mData!!.get(adapterPosition).zcbm
                var fhzt = mData!!.get(adapterPosition).fhbj //复核标记 9-无需复核，0-待复核，1-复核通过，2-复核未通过
                var ywlx = mData!!.get(adapterPosition).ywlx
                var lsh = mData!!.get(adapterPosition).lsh
                Yw3CzActivity.zcbm = zcbm
                Yw3CzActivity.fhzt = fhzt
                mListener!!.itemClick(zcbm,ywlx,lsh,adapterPosition)
            }
        }

        /**
         * 文本内容过多，可以滑动
         */
        @SuppressLint("ClickableViewAccessibility")
        private fun bindTextTouchEvent() {
            tv_fh_yy!!.movementMethod = ScrollingMovementMethod.getInstance()
            tv_fh_fhyj!!.movementMethod = ScrollingMovementMethod.getInstance()
            tv_fh_yy!!.setOnTouchListener(mTextTouchListener)
            tv_fh_fhyj!!.setOnTouchListener(mTextTouchListener)
        }

        fun setItemView(position: Int) {

            var fhzt: String? = mData!!.get(position).fhbj // 复核标记 9-无需复核，0-待复核，1-复核通过，2-复核未通过

            tv_fh_syr!!.text = mData!!.get(position).syr
            tv_fh_zcbm!!.text = mData!!.get(position).zcbm
            tv_fh_cphm!!.text = mData!!.get(position).cph
            tv_fh_ywlx!!.text = DMZUtils.getDMSM(Constants.YWLX, mData!!.get(position).ywlx)
            tv_fh_ywry!!.text = mData!!.get(position).ywry
            tv_fh_slsj!!.text = StringUtils.longToStringData(mData!!.get(position).slsj)
            tv_fh_fhzt!!.text = FHUtil.getDMSM(mData!!.get(position).fhbj)

            tv_fh_fhr!!.text = mData!!.get(position).fhr
            tv_fh_fhyj!!.text = if (TextUtils.isEmpty(mData!!.get(position).fhbz)) "/" else mData!!.get(position).fhbz
            tv_fh_fhsj!!.text = StringUtils.longToStringData(mData!!.get(position).fhsj)

            if ("2".equals(fhzt)) {
                tv_fh_fhzt!!.setTextColor(Color.RED)
                tv_fh_yy!!.setTextColor(Color.RED)
                tv_fh_yy!!.text = mData!!.get(position).btgyy
            } else if ("1".equals(fhzt)) {
                tv_fh_fhzt!!.setTextColor(Color.GREEN)
                ViewShowUtils.showGoneView(ll_yy)
            } else if ("0".equals(fhzt)) {
                tv_fh_fhzt!!.setTextColor(Color.RED)
                ViewShowUtils.showGoneView(ll_yy, ll_fhr, ll_fhsj, ll_fhyj) //不显示
            }
        }
    }

    /**
     * 与listview防止冲突
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            v!!.parent.requestDisallowInterceptTouchEvent(true)
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            // 通知ScrollView控件不要干扰
            v!!.parent.requestDisallowInterceptTouchEvent(true)
        }
        if (event.action == MotionEvent.ACTION_UP) {
            v!!.parent.requestDisallowInterceptTouchEvent(false)
        }
        return false
    }

    fun setListener(ls : onItemListener){
        mListener = ls
    }

    interface onItemListener {
        fun itemClick(zcbm: String?, ywlx: String?,lsh: String?,position: Int)
    }
}
