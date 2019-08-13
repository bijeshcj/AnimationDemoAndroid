package com.bijesh.animationdemo.samples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bijesh.animationdemo.R;

/**
 * Created by Bijesh C J on 08,August,2019
 */
public class RippleEffect extends View {

    private BitmapDrawable verfier_logo;
    private Bitmap mVerifierRippleLogo;
    private Canvas mRippleCanvas;
    private int mWidth = this.getResources().getDisplayMetrics().widthPixels;
    private int mHeight = this.getResources().getDisplayMetrics().heightPixels;
    private BitmapDrawable ripple,ripple1;

    public RippleEffect(Context context) {
        super(context);
    }

    public RippleEffect(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleEffect(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RippleEffect(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init(){
        verfier_logo = (BitmapDrawable)getResources().getDrawable(R.drawable.circle_no_ripple);
        Bitmap btMap = verfier_logo.getBitmap();
        mVerifierRippleLogo = btMap.copy(Bitmap.Config.ARGB_8888, true);
        mRippleCanvas = new Canvas(mVerifierRippleLogo);
        ripple =  (BitmapDrawable)getResources().getDrawable(R.drawable.circle_two_ripple);
        ripple1 = (BitmapDrawable)getResources().getDrawable(R.drawable.circle_one_ripple);
    }

    private int rippleState = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circleX = ((verfier_logo.getBitmap().getWidth()/2));
        float circleY = ( (verfier_logo.getBitmap().getHeight() * 1.15f))-25;
        canvas.drawBitmap(verfier_logo.getBitmap(),((mWidth/2)-70f),mHeight/2,null);
        canvas.drawLine(circleX+400,circleY,circleX+400,
                circleY * 20,getMarkerPaint(true,true));
        if(rippleState < 100)
            canvas.drawBitmap(verfier_logo.getBitmap(),((mWidth/2)-70f),mHeight/2,null);
        else if(rippleState >= 100 && rippleState <200){
            canvas.drawBitmap(ripple1.getBitmap(),((mWidth/2)-70f),mHeight/2,null);
        }else if(rippleState >=200 && rippleState < 300)
            canvas.drawBitmap(ripple.getBitmap(),((mWidth/2)-70f),mHeight/2,null);

        rippleState++;
        if(rippleState >= 300)
            rippleState = 0;
        invalidate();

    }

    private Paint getMarkerPaint(boolean isFill, boolean isDarker) {
        Paint paint = new Paint();
        if(isFill) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }else{
            paint.setStyle(Paint.Style.STROKE);
        }
//        if(isCircle) {
        paint.setColor(Color.parseColor("#039BE5"));//339933
//        }else{
//            paint.setColor(Color.parseColor("#CD5C5C"));
//        }
        if(isDarker)
            paint.setStrokeWidth(16f);
        else
            paint.setStrokeWidth(6f);
        return paint;
    }
}
