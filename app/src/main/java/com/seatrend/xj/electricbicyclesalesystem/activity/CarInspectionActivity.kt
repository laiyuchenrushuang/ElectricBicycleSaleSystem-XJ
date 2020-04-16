package com.seatrend.xj.electricbicyclesalesystem.activity

import android.support.v4.content.ContextCompat
import android.view.GestureDetector
import android.view.ViewDebug
import android.widget.CompoundButton
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.fragment.CYActualMsgFragment
import com.seatrend.xj.electricbicyclesalesystem.fragment.CYProjectJudgeFragment
import com.seatrend.xj.electricbicyclesalesystem.holder.FragmentGestureDetector
import kotlinx.android.synthetic.main.activity_inspection.*
import android.text.method.Touch.onTouchEvent
import android.view.MotionEvent
import kotlinx.android.synthetic.main.bottom_button.*


/**
 * Created by ly on 2020/4/3 11:05
 */
class CarInspectionActivity: BaseActivity() {

    var mCarScFG :CYActualMsgFragment ? =null
    var mCarPdFG :CYProjectJudgeFragment ? =null
    var mDetector: GestureDetector? = null

    override fun initView() {

        setPageTitle(resources.getString(R.string.home_clcy))
        
        getData()

        bindEvent()

    }

    private fun getData() {
        mCarScFG = CYActualMsgFragment()
        mCarPdFG = CYProjectJudgeFragment()

        supportFragmentManager.beginTransaction().replace(R.id.carmsg_fl, mCarScFG).commit()
        rb_scxx.isChecked = true
    }

    private fun bindEvent() {
        rb_scxx.setOnCheckedChangeListener { _: CompoundButton, check: Boolean ->
            if (check) {
                rb_scxx!!.setTextColor(ContextCompat.getColor(this, R.color.theme_color))
                rb_xmpd!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                switchFragment(mCarScFG)
            } else {
                rb_xmpd!!.setTextColor(ContextCompat.getColor(this, R.color.theme_color))
                rb_scxx!!.setTextColor(ContextCompat.getColor(this, R.color.black))
                switchFragment(mCarPdFG)
            }
        }

        mDetector = GestureDetector(this,FragmentGestureDetector(object :FragmentGestureDetector.GestureListener{
            override fun toLeft() {
                if(mCarScFG!!.isVisible){
                    rb_xmpd.isChecked = true
                }
            }

            override fun toRight() {
                if(mCarPdFG!!.isVisible){
                    rb_scxx.isChecked = true
                }
            }
        }))

        bt_next.setOnClickListener {

        }
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return mDetector!!.onTouchEvent(event)
//    }

    override fun getLayout(): Int {
        return  R.layout.activity_inspection
    }
}