package com.seatrend.xj.electricbicyclesalesystem.manager

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.graphics.Rect
import android.util.Log
import android.util.SparseArray
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.util.LogUtil


/**
 * Created by ly on 2019/11/11 10:53
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class MyRecycleManager(var mContext: Context?) : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler)

        val sumWidth = width
        com.seatrend.xj.electricbicyclesalesystem.util.LogUtil.getInstance().d("  总宽 = "+sumWidth)
        var curLineWidth = 0
        var curLineTop = 0
        var lastLineMaxHeight = 0
        for (i in 0 until itemCount) {
            val view = recycler!!.getViewForPosition(i)

            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            com.seatrend.xj.electricbicyclesalesystem.util.LogUtil.getInstance().d("  item"+i +" 的宽度 = "+width)
            com.seatrend.xj.electricbicyclesalesystem.util.LogUtil.getInstance().d("  item"+i +" 的高度 = "+height)
            curLineWidth += width
            com.seatrend.xj.electricbicyclesalesystem.util.LogUtil.getInstance().d(" 第"+i+"行的总宽 "+curLineWidth)
            if (curLineWidth <= sumWidth) {//不需要换行
                layoutDecorated(view, curLineWidth - width, curLineTop, curLineWidth, curLineTop + height)
                //比较当前行多有item的最大高度
                lastLineMaxHeight = Math.max(lastLineMaxHeight, height)
            } else {//换行
                curLineWidth = width
                if (lastLineMaxHeight == 0) {
                    lastLineMaxHeight = height
                }
                //记录当前行top
                curLineTop += lastLineMaxHeight

                layoutDecorated(view, 0, curLineTop, width, curLineTop + height)
                lastLineMaxHeight = height
            }
        }
    }

//    private val TAG = this.javaClass.name
//    private val cachedViews = SparseArray<View>()
//    private val layoutPoints = SparseArray<Rect>()
//    private var totalWidth: Int = 0
//    private var totalHeight: Int = 0
//    private var mContentHeight: Int = 0
//    private var mOffset: Int = 0
//    private var mIsFullyLayout: Boolean ?=null
//
//    fun FlowLayoutManager(context: Context, isFullyLayout: Boolean) {
//        mIsFullyLayout = isFullyLayout
//    }
//
//    override fun supportsPredictiveItemAnimations(): Boolean {
//        return true
//    }
//
//    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
//        for (i in 0 until itemCount) {
//            val v = cachedViews.get(i)
//            val rect = layoutPoints.get(i)
//            layoutDecorated(v, rect.left, rect.top, rect.right, rect.bottom)
//        }
//
//    }
//
//    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
//        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
//    }
//
//    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
//        return dx
//    }
//
//    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
//        var shouldOffset = 0
//        if (mContentHeight - totalHeight > 0) {
//            var targetOffset = mOffset + dy
//            if (targetOffset < 0) {
//                targetOffset = 0
//            } else if (targetOffset > mContentHeight - totalHeight) {
//                targetOffset = mContentHeight - totalHeight
//            }
//            shouldOffset = targetOffset - mOffset
//            offsetChildrenVertical(-shouldOffset)
//            mOffset = targetOffset
//        }
//
//        if (mIsFullyLayout!!) {
//            shouldOffset = dy
//        }
//        return shouldOffset
//    }
//
//    override fun canScrollHorizontally(): Boolean {
//        return true
//    }
//
//    override fun canScrollVertically(): Boolean {
//        return true
//    }
//
//    override fun onAdapterChanged(oldAdapter: RecyclerView.Adapter<*>?, newAdapter: RecyclerView.Adapter<*>?) {
//        removeAllViews()
//    }
//
//    override fun onMeasure(recycler: RecyclerView.Recycler?, state: RecyclerView.State?, widthSpec: Int, heightSpec: Int) {
//        super.onMeasure(recycler, state, widthSpec, heightSpec)
//
//        val widthMode = View.MeasureSpec.getMode(widthSpec)
//        val heightMode = View.MeasureSpec.getMode(heightSpec)
//        val widthSize = View.MeasureSpec.getSize(widthSpec)
//        val heightSize = View.MeasureSpec.getSize(heightSpec)
//
//        var height: Int
//
//        when (widthMode) {
//            View.MeasureSpec.UNSPECIFIED -> Log.d(TAG, "WidthMode is unspecified.")
//            View.MeasureSpec.AT_MOST -> {
//            }
//            View.MeasureSpec.EXACTLY -> {
//            }
//        }
//
//        removeAndRecycleAllViews(recycler)
//        recycler!!.clear()
//        cachedViews.clear()
//
//        mContentHeight = 0
//
//        totalWidth = widthSize - paddingRight - paddingLeft
//
//        var left = paddingLeft
//        var top = paddingTop
//
//        var maxTop = top
//
//        for (i in 0 until itemCount) {
//            val v = recycler.getViewForPosition(i)
//            addView(v)
//            measureChildWithMargins(v, 0, 0)
//            cachedViews.put(i, v)
//        }
//
//        for (i in 0 until itemCount) {
//            val v = cachedViews.get(i)
//
//            val w = getDecoratedMeasuredWidth(v)
//            val h = getDecoratedMeasuredHeight(v)
//
//            if (w > totalWidth - left) {
//                left = paddingLeft
//                top = maxTop
//            }
//
//            val rect = Rect(left, top, left + w, top + h)
//            layoutPoints.put(i, rect)
//
//            left = left + w
//
//            if (top + h >= maxTop) {
//                maxTop = top + h
//            }
//
//        }
//
//        mContentHeight = maxTop - paddingTop
//
//        height = mContentHeight + paddingTop + paddingBottom
//
//        when (heightMode) {
//            View.MeasureSpec.EXACTLY -> height = heightSize
//            View.MeasureSpec.AT_MOST -> if (height > heightSize) {
//                height = heightSize
//            }
//            View.MeasureSpec.UNSPECIFIED -> {
//            }
//        }
//
//        totalHeight = height - paddingTop - paddingBottom
//
//        setMeasuredDimension(widthSize, height)
//    }
}