package com.bijesh.animationdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.support.v7.widget.AppCompatImageView;


/**
 * Created by Bijesh C J on 05,August,2019
 */
public class AnimateVerificationView extends AppCompatImageView {


    public AnimateVerificationView(Context context) {
        super(context);
    }

    public AnimateVerificationView(Context context,  AttributeSet attrs) {
        super(context, attrs);

    }

    public AnimateVerificationView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private Paint getLinePaint(boolean isCircle) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        if (isCircle) {
            paint.setColor(Color.parseColor("#339933"));
        } else {
            paint.setColor(Color.parseColor("#CD5C5C"));
        }
        paint.setStrokeWidth(6f);
        return paint;
    }


}
