package com.seatrend.xj.electricbicyclesalesystem.createview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by ly on 2019/10/21 15:41
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CircleImageView extends AppCompatImageView {

    private int mSize;
    private Paint mPaint;
    private Xfermode mPorterDuffXfermode;
    private Bitmap mBitmap = null;

    public CircleImageView(Context context) {
        this(context, null);
        setWillNotDraw(false);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setWillNotDraw(false);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mSize = Math.min(width, height);  //取宽高的最小值
        setMeasuredDimension(mSize, mSize);    //设置CircleImageView为等宽高
        Log.d("lylog", " w = " + width + "  h = " + height);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("lylog", "  onDraw");
        Bitmap sourceBitmap = mBitmap;
        if (sourceBitmap != null) {
            //对图片进行缩放，以适应控件的大小
            Bitmap bitmap = resizeBitmap(sourceBitmap, getWidth(), getHeight());
            drawCircleBitmapByXfermode(canvas, bitmap);    //(1)利用PorterDuffXfermode实现
            //drawCircleBitmapByShader(canvas,bitmap);    //(2)利用BitmapShader实现
        }
    }

    private Bitmap resizeBitmap(Bitmap sourceBitmap, int dstWidth, int dstHeight) {
        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();

        float widthScale = ((float) dstWidth) / width;
        float heightScale = ((float) dstHeight) / height;

        //取最大缩放比
        float scale = Math.max(widthScale, heightScale);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        return Bitmap.createBitmap(sourceBitmap, 0, 0, width, height, matrix, true);
    }

    private void drawCircleBitmapByXfermode(Canvas canvas, Bitmap bitmap) {
        final int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //绘制dst层
        canvas.drawCircle(mSize / 2, mSize / 2, mSize / 2, mPaint);
        //设置图层混合模式为SRC_IN
        mPaint.setXfermode(mPorterDuffXfermode);
        //绘制src层
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restoreToCount(sc);
    }

    public void setbg(Bitmap bm) {
        mBitmap = bm;
        invalidate();
    }
}