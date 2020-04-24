package com.seatrend.xj.electricbicyclesalesystem.createview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.seatrend.xj.electricbicyclesalesystem.R;

/**
 * Created by ly on 2020/4/10 17:28
 */
public class ClipperView extends View {

    private static String mPicturePath = ""; //原始图片的路径

    private static RectF mRect; //框内矩形图片
    private static RectF mParentRect; //父类的范围矩形
    private static RectF mRect1 = new RectF(); //框内矩形图片(测试 getBitmap())

    private static Bitmap cacheBitmap;

    private float mViewH, mViewW; // 自定义view 的高宽

    float left, right, top, bottom; //矩形框的长宽高

    float vfocusX, vfocusY; //中心点

    int defaultDis = 30;  //这个目的是为了可以缩小放大，要有间隔才行

    float mHWDis = 0;  //这个目的是为了适配图片是在父控件的间隙，间隙要么宽间隙，要么高间隙

    static Bitmap bmp; //图片bitmap对象

    float mSwidth, mSheight; //屏幕宽高

    float roat; // 图片宽高比率  w/h

    public ClipperView(Context context) {
        super(context);
        init(context);
    }


    public ClipperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClipperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (null != windowManager) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            mSwidth = outMetrics.widthPixels;
            mSheight = outMetrics.heightPixels;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewH = getMeasuredHeight();
        mViewW = getMeasuredWidth();
        defaultUi();
    }

    @SuppressLint({"DrawAllocation", "ResourceAsColor"})
    @Override
    protected void onDraw(Canvas canvas) {
        mViewH = getMeasuredHeight();
        mViewW = getMeasuredWidth();

//        showLog(" rect left =  " + left + "  top = " + top + "  right = " + right + "  bottom = " + bottom);
        //画框
        Paint mKuangPaint;
        mKuangPaint = new Paint();
        mKuangPaint.setColor(Color.RED);
        mKuangPaint.setStrokeWidth(5);
        mKuangPaint.setStyle(Paint.Style.STROKE);//空心矩形框
        Rect r1 = new Rect((int) left, (int) top, (int) right, (int) bottom);
        canvas.drawRect(r1, mKuangPaint);

        //画个中心点
        Paint pointP = new Paint();
        pointP.setColor(Color.BLACK);
        pointP.setStrokeWidth(5);
        vfocusX = left + (right - left) / 2;
        vfocusY = top + (bottom - top) / 2;
        canvas.drawPoint(vfocusX, vfocusY, pointP);

        float bh = bmp.getHeight();// bh是父类控件的大小 是不变的，那么宽度是有空隙的情况
        float bw = bmp.getWidth();  // 图片的比例 是一定的 那么尺寸也要做相应的改变

        float l, t, r, b;  // 实际图片ROI区域

        if (judgeAdapterHOrW()) {  //以高度为标准 宽度有间隙
            float currentBitmapWidth = roat * mViewH;

            l = (left - mHWDis) / currentBitmapWidth * bw;
            t = top / mViewH * bh;
            r = (right - mHWDis) / currentBitmapWidth * bw;
            b = bottom / mViewH * bh;
        } else {//以宽度为标准 高度有间隙
            float currentBitmapHeight = mViewW / roat;

            l = left / mViewW * bw;
            t = (top - mHWDis) / currentBitmapHeight * bh;
            r = right / mViewW * bw;
            b = (bottom - mHWDis) / currentBitmapHeight * bh;
        }

        mRect = new RectF(l, t, r, b);  // 相对于图片  做比例转化

        //相对于视图做比例迁移
        mRect1.left = left;
        mRect1.right = right;
        mRect1.top = top;
        mRect1.bottom = bottom;

        //画模糊层
        Paint shadowP = new Paint();
        shadowP.setStyle(Paint.Style.FILL);
        shadowP.setColor(R.color.shudowgray);
        canvas.drawRect(0, 0, mViewW, top, shadowP);
        canvas.drawRect(0, top, left, bottom, shadowP);
        canvas.drawRect(right, top, mViewW, bottom, shadowP);
        canvas.drawRect(0, bottom, mViewW, mViewH, shadowP);

        super.onDraw(canvas);
    }

    // 初始框的大小
    private void defaultUi() {


        float bh = bmp.getHeight();// bh是父类控件的大小 是不变的，那么宽度是有空隙的情况
        float bw = bmp.getWidth();  // 图片的比例 是一定的 那么尺寸也要做相应的改变


        roat = bw / bh;

        if (judgeAdapterHOrW()) {  //以高度为标准(因为高度矮了，那么宽度就缩小存在间隙)
            mHWDis = (mViewW - mViewH * roat) / 2;//一半的差距  (宽度两边间隙 )
            left = mHWDis + defaultDis;
            right = (int) (mViewW - mHWDis - defaultDis);
            top = (int) (mViewH / 2 - mViewH / 8);
            bottom = (int) (mViewH / 2 + mViewH / 8);
        } else {//以宽度为标准 那么高度有空隙
            mHWDis = (mViewH - mViewW / roat) / 2;
            left = defaultDis;
            right = (int) (mViewW - defaultDis);
            top = (int) (mViewH / 2 - mViewH / 8);
            bottom = (int) (mViewH / 2 + mViewH / 8);
        }
        showLog(" " + (judgeAdapterHOrW() ? "以高度适配" : "以宽度适配"));
        showLog(" mHWDis = " + mHWDis);
        showLog(" roat = " + bh / bw);
//        Log.d("lylog", "onMeasure mViewW = " + mViewW + "  mViewH = " + mViewH);
//        Log.d("lylog", "onMeasure mSwidth = " + mSwidth + "  mSheight = " + mSheight);
    }


    float dl, dr, dt, db;  //  中心点到left right top bottom的边距离

    float dw, dh;  // 矩形的宽和高，当按下屏幕的时候 固定他

    private double nLenStart = 0;//监听两指手势

    private boolean twoFingle = false;//监听两指手势flag 防止与ACTION_MOVE冲突

    double dis = 30; // 滑动阈值

    float down_x, down_y; // 初始x,y

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int nCnt = event.getPointerCount();
        if (nCnt == 2) {
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) {
                twoFingle = true;
                int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
                int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));
                nLenStart = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
            } else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
                int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
                int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));
                double nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
                if (nLenEnd > nLenStart)//通过两个手指开始距离和结束距离，来判断放大缩小
                {
                    showLog("放大");
                } else {
                    showLog("缩小");
                }


                twoFingle = false;

            }
            return true;
        }


        switch (event.getAction()) {


            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                int pointCount = event.getPointerCount();
                if (pointCount < 2) {
                    showLog(" Point x =  " + x + "  y = " + y);
                    if (judgePiontOnView(x, y)) {

                        if (judgeRectMove()) {
                            setViewOnFocus(x, y);
                        }

                    } else {

                        if (x > vfocusX) {
                            right = (int) (x - defaultDis);

                            if (judgeAdapterHOrW()) { // 以高度适配  宽度要注意间隙问题
                                if (right > mViewW - mHWDis - defaultDis) {
                                    right = mViewW - mHWDis - defaultDis;
                                }
                            }
                            // else 不做处理
                        } else {
                            left = (int) (x + defaultDis);

                            if (judgeAdapterHOrW()) { // 以高度适配  宽度要注意间隙问题
                                if (left < mHWDis + defaultDis) {
                                    left = mHWDis + defaultDis;
                                }
                            }
                            // else 不做处理
                        }

                        if (y > vfocusY) {
                            bottom = (int) (y - defaultDis);
                            if (!judgeAdapterHOrW()) { // 以宽度适配高度要注意间隙
                                if (bottom > mViewH - mHWDis - defaultDis) {
                                    bottom = mViewH - mHWDis - defaultDis;
                                }
                            }
                        } else {
                            if (top > defaultDis) {
                                top = (int) (y + defaultDis);// 父控件上边有title
                            } else {
                                top = defaultDis;
                            }
                            if (!judgeAdapterHOrW()) { // 以宽度适配高度要注意间隙
                                if (top < mHWDis + defaultDis) {
                                    top = defaultDis + mHWDis;
                                }
                            }

                        }
                    }
                }

                break;

            case MotionEvent.ACTION_DOWN:

                down_x = event.getX();
                down_y = event.getY();
                if (event.getPointerCount() < 2) {
                    dl = event.getX() - left;
                    dr = right - event.getX();

                    dt = event.getY() - top;
                    db = bottom - event.getY();


                    //触摸点与中心点的距离不变

                    dw = right - left;
                    dh = bottom - top;
                } else {
                    showLog(" ACTION_DOWN PointerCount  =  " + event.getPointerCount());

                }

                break;

            case MotionEvent.ACTION_UP:
                dw = right - left;
                dh = bottom - top;
                break;
        }
        invalidate();
        return true;
    }

    // 防止一按下屏幕就监听滑动了
    private boolean isCanMove(float down_x, float down_y, float x, float y) {
        float xl = Math.abs(x - down_x);
        float yl = Math.abs(y - down_y);
        double dxy = Math.sqrt(xl * xl + yl * yl);
        if (dxy > dis) {
            return true;
        }
        return false;
    }

    private void setViewOnFocus(float x, float y) {
//        Log.d("lylog", " setViewOnFocus ");
        //X方向
        setLeftAndRight(x);
        //Y方向
        setTopAndBottom(y);
    }

    private void setTopAndBottom(float y) {
        if (judgeAdapterHOrW()) {  // 以高为标准
            if (bottom != mViewH - defaultDis) {
                if (top > defaultDis) {
                    top = (int) (y - dt);
                    if (top < defaultDis) {
                        top = defaultDis;
                    }
                } else if (top == defaultDis) {
                    if (y - dt > defaultDis) {
                        top = (int) (y - dt);
                    }
                    if (top < defaultDis) {
                        top = defaultDis;
                    }
                } else {
                    top = defaultDis;
                }
            }


            if (top != defaultDis) {
                if (bottom < mViewH - defaultDis) {
                    bottom = (int) (y + db);
                    if (bottom > mViewH - defaultDis) {
                        bottom = mViewH - defaultDis;
                    }
                } else if (bottom == (int) (mViewH - defaultDis)) {
                    if (y + db < (int) (mViewH - defaultDis)) {
                        bottom = (int) (y + db);
                    }
                    if (bottom > mViewH - defaultDis) {
                        bottom = mViewH - defaultDis;
                    }
                } else {
                    bottom = (int) (mViewH - defaultDis);
                }
            }
        } else {
//            showLog("bottom = "+bottom);
//            showLog("mViewH = "+mViewH);
//            showLog("defaultDis = "+defaultDis);
//            showLog("mHWDis = "+mHWDis);
//            showLog("top = "+top);
            if ((int)bottom != (int)(mViewH - defaultDis - mHWDis)) {
                if (top > defaultDis + mHWDis) {
                    top = (int) (y - dt);
                    if (top < defaultDis + mHWDis) {
                        top = defaultDis + mHWDis;
                    }
                } else if (top == defaultDis + mHWDis) {
                    if (y - dt > defaultDis + mHWDis) {
                        top = (int) (y - dt);
                    }
                    if (top < defaultDis + mHWDis) {
                        top = defaultDis + mHWDis;
                    }
                } else {
                    top = defaultDis + mHWDis;
                }
            }

            if (top != defaultDis + mHWDis) {
                if (bottom < mViewH - defaultDis - mHWDis) {
                    bottom = (int) (y + db);
                    if (bottom > mViewH - defaultDis - mHWDis) {
                        bottom = mViewH - defaultDis - mHWDis;
                    }
                } else if (bottom == (int) (mViewH - defaultDis - mHWDis)) {
                    if (y + db < (int) (mViewH - defaultDis - mHWDis)) {
                        bottom = (int) (y + db);
                    }
                    if (bottom > mViewH - defaultDis - mHWDis) {
                        bottom = mViewH - defaultDis - mHWDis;
                    }
                } else {
                    bottom = (int) (mViewH - defaultDis - mHWDis);
                }
            }
        }
    }

    private void setLeftAndRight(float x) {
        if (judgeAdapterHOrW()) {  // 以高度为标准  考虑间隙问题
            if (right != mViewW - defaultDis - mHWDis) {
                if (left > defaultDis) {
                    left = (int) (x - dl);
                    if (left < defaultDis + mHWDis) {
                        left = defaultDis + mHWDis;
                    }
                } else if (left == defaultDis) {
                    if (x - dl > defaultDis + mHWDis) {
                        left = (int) (x - dl);
                    }
                    if (left < defaultDis + mHWDis) {
                        left = defaultDis + mHWDis;
                    }
                } else {
                    left = defaultDis + mHWDis;
                }
            }

        } else {

            if (right != mViewW - defaultDis) {
                if (left > defaultDis) {
                    left = (int) (x - dl);
                    if (left < defaultDis) {
                        left = defaultDis;
                    }
                } else if (left == defaultDis) {
                    if (x - dl > defaultDis) {
                        left = (int) (x - dl);
                    }
                    if (left < defaultDis) {
                        left = defaultDis;
                    }
                } else {
                    left = defaultDis;
                }
            }


            if (left != defaultDis) {
                if (right < mViewW - defaultDis) {
                    right = (int) (x + dr);
                    if (right > mViewW - defaultDis) {
                        right = mViewW - defaultDis;
                    }
                } else if (right == (int) (mViewW - defaultDis)) {
                    if (x + dr < (int) (mViewW - defaultDis)) {
                        right = (int) (x + dr);
                    }
                    if (right > mViewW - defaultDis) {
                        right = mViewW - defaultDis;
                    }
                } else {
                    right = (int) (mViewW - defaultDis);
                }
            }
        }

    }

    //    static Bitmap bmp;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    boolean judgePiontOnView(float x, float y) {

        return x >= left && x <= right && y >= top && y <= bottom;
    }

    //判断宽高以什么味标准  true 是以高为标准  false 以宽为标准(如果比率为roat 1.0 相当于一宽度为标准)


    boolean judgeAdapterHOrW() {
        return mViewH / mViewW < bmp.getHeight() / bmp.getWidth();
    }

    // 触摸点是否在框内
    boolean judgeRectMove() {
        if (right <= mViewW - defaultDis && left >= defaultDis && top >= defaultDis && bottom <= mViewH - defaultDis) {
            return right >= defaultDis && left <= mViewW - defaultDis / 2 && top <= mViewH - defaultDis && bottom >= defaultDis;
        }
//        Log.d("lylog", " left = " + left + "  top = " + top + "  right = " + right + "  bottom = " + bottom);
        return false;
    }

    public void setImagePath(String path) {
        mPicturePath = path;
    }

    public void setBitmap(Bitmap b) {
        bmp = b;
    }


    public static RectF getBitmapRect() {
        return mRect;
    }

    public void setParentScaleRect(RectF r) {
        mParentRect = r;
    }

    public static Bitmap getBitmap() {
        //这个地方注意临界值
        return Bitmap.createBitmap(bmp, (int) mRect.left, (int) mRect.top, (int) mRect.right - (int) mRect.left, (int) mRect.bottom - (int) mRect.top);
    }

    private void showLog(String msg) {
        Log.d("lylog", "  " + msg);
    }
}
