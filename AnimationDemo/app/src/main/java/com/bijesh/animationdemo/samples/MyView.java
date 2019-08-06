package com.bijesh.animationdemo.samples;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.bijesh.animationdemo.R;
import com.bijesh.animationdemo.animate.AnimatePoint;
import com.bijesh.animationdemo.animate.AnimationUtils;
import com.bijesh.animationdemo.animate.GoidAnimationType;
import com.bijesh.animationdemo.indrive.animations.AnimationType;
import com.bijesh.animationdemo.indrive.animations.TutorialPoint;


/**
 * Created by Bijesh C J on 05,August,2019
 */
public class MyView extends View {


    private static final String COLOR_HEX = "#E74300";
    private  Paint drawPaint;
    private  float size = 5f;
    private Handler mHandler;
    private GoidAnimationType mAnimationType = GoidAnimationType.SHOW_CIRCLE_CENTER;
    private int mWidth = this.getResources().getDisplayMetrics().widthPixels;
    private int mHeight = this.getResources().getDisplayMetrics().heightPixels;
    private int handCounter = 0;
    private BitmapDrawable verfier_logo,verfier_circle;
    private boolean isPointsInit = false;
    private AnimatePoint[] mMoveVerifierUpPoints;
    private static final int FRAME_RATE = 5,FRAME_RATE_MOVE_UP = 1;

    public MyView(Context context) {
        super(context);
        // start the animation:

    }

    public MyView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyView(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mHandler = new Handler();
        size = 15f;
        drawPaint = new Paint();
        drawPaint.setColor(Color.parseColor(COLOR_HEX));
        drawPaint.setAntiAlias(true);
        setOnMeasureCallback();
        verfier_logo = (BitmapDrawable)getResources().getDrawable(R.drawable.verifier_icon);
        verfier_circle = (BitmapDrawable)getResources().getDrawable(R.drawable.verifier_circle);
    }

    private void setOnMeasureCallback() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(this);
//                size = getMeasuredWidth() / 2;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void removeOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    private void initPoints(){
        mMoveVerifierUpPoints = AnimationUtils.getVerifierUpwardMovementPoints(mWidth/2,mHeight/2);
        isPointsInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isPointsInit){
            initPoints();
        }
        switch (mAnimationType){
            case SHOW_CIRCLE_CENTER:{
                showCircle(canvas);
                break;
            }case SHOW_VERFIER_LOGO:{
                showVerifierLogo(canvas);
                break;
            }
            case MOVE_VERIFIER_LOGO_UP:{
                moveVerifier(canvas,verfier_logo,new AnimatePoint(mWidth/2,mHeight/2),mMoveVerifierUpPoints);
                break;
            }
        }

    }

    private void moveVerifier(Canvas canvas, BitmapDrawable img, AnimatePoint startPoint, AnimatePoint[] points) {
        if (handCounter < getIterationLength(points)) {
            AnimatePoint animatePoint = getShapePoints(points, handCounter);
            canvas.drawBitmap(img.getBitmap(), animatePoint.x, animatePoint.y, null);
//            canvas.drawCircle(animatePoint.x,animatePoint.y,105f,getMarkerPaint(true));
            handCounter++;
        }
        if(handCounter < getIterationLength(points)){
            mHandler.postDelayed(gotoNavigation,FRAME_RATE_MOVE_UP);
        }
//        if (mCounter < getIterationLength(mDragHandToNavigationDrawer)) {
////            block which draws shape
//            mHandler.postDelayed(gotoNavigation, FRAME_RATE);
//        } else if (mCounter == getIterationLength(mDragHandToNavigationDrawer)) {
//            showSelectedAnim(canvas, img, mDragHandToNavigationDrawer);
//            mCounter = 0;
//            handCounter = 0;
////            isHandDrawnToNavigationDrawer = true;
////            if(mTutorialType == TutorialType.HOME_SCREEN_TUTORIAL) {
//            mAnimationType = AnimationType.FLASH_CIRCLE;
//            h.postDelayed(flashRunnable, FRAME_RATE);
////            }
//        }
    }

    private Runnable gotoNavigation = new Runnable() {
        @Override
        public void run() {
//
//            appendPoints(mDragHandToNavigationDrawer);
            invalidate();
        }
    };

    private int getIterationLength(AnimatePoint[] points){
        return points.length;
    }

    private AnimatePoint getShapePoints(AnimatePoint[] points, int whichOrdinal) {
        return points[whichOrdinal];
    }



    private void showVerifierLogo(Canvas canvas){
//        canvas.drawCircle(mWidth/2,mHeight/2,105f,getMarkerPaint(true));
//        BitmapDrawable verfier_logo = (BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher_round);
        canvas.drawBitmap(verfier_logo.getBitmap(),((mWidth/2)-70f),mHeight/2,null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimationType = GoidAnimationType.MOVE_VERIFIER_LOGO_UP;
                invalidate();
            }
        },2000);
    }

    private void showCircle(final Canvas canvas){
//        canvas.drawCircle(mWidth/2,mHeight/2,55f,getMarkerPaint(true));
        canvas.drawBitmap(verfier_circle.getBitmap(),((mWidth/2)-70f),mHeight/2,null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimationType = GoidAnimationType.SHOW_VERFIER_LOGO;
                invalidate();
            }
        }, 2000);
    }


    private Paint getMarkerPaint(boolean isFill) {
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
        paint.setStrokeWidth(6f);
        return paint;
    }







}
