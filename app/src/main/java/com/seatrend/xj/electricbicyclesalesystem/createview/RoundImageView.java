package com.seatrend.xj.electricbicyclesalesystem.createview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Administrator on 2017/12/4.
 */

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView {

    private float width, height;
    private int roundSize = 20;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {


        if (width > roundSize && height > roundSize) {
            Path path = new Path();
            path.moveTo(roundSize, 0);
            path.lineTo(width - roundSize, 0);
            path.quadTo(width, 0, width, roundSize);
            path.lineTo(width, height - roundSize);
            path.quadTo(width, height, width - roundSize, height);
            path.lineTo(roundSize, height);
            path.quadTo(0, height, 0, height - roundSize);
            path.lineTo(0, roundSize);
            path.quadTo(0, 0, roundSize, 0);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);


    }
}
