package com.seatrend.xj.electricbicyclesalesystem.holder

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Created by ly on 2020/4/3 11:40
 */
class FragmentGestureDetector(val listener: GestureListener) : GestureDetector.OnGestureListener {

    private val FLIP_DISTANCE = 50

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if (e1!!.x - e2!!.x > FLIP_DISTANCE) {
            Log.i("MYTAG", "向左滑...")
            listener.toLeft()
            return true
        }
        if (e2.x - e1.x > FLIP_DISTANCE) {
            Log.i("MYTAG", "向右滑...")
            listener.toRight()
            return true
        }
        if (e1.y - e2.y > FLIP_DISTANCE) {
            Log.i("MYTAG", "向上滑...")
            return true
        }
        if (e2.y - e1.y > FLIP_DISTANCE) {
            Log.i("MYTAG", "向下滑...")
            return true
        }

        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
    }

    interface GestureListener{
        fun toLeft()
        fun toRight()
    }
}