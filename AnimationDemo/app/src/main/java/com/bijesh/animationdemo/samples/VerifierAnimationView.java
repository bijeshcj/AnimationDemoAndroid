package com.bijesh.animationdemo.samples;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
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

import static com.bijesh.animationdemo.animate.GoidAnimationType.MOVE_CIRCLE_DOWN;
import static com.bijesh.animationdemo.animate.GoidAnimationType.VERIFIER_LOGO_DOWN_WITH_SMALL_CIRCLE;
import static com.bijesh.animationdemo.animate.GoidAnimationType.VERIFIER_LOGO_UP_WITH_SMALL_CIRCLE;


/**
 * Created by Bijesh C J on 05,August,2019
 */
public class VerifierAnimationView extends View {


    private static final String COLOR_HEX = "#E74300";
    private  Paint drawPaint;
    private  float size = 5f;
    private Handler mHandler;
    private GoidAnimationType mAnimationType = GoidAnimationType.SHOW_CIRCLE_CENTER;
    private int mWidth = this.getResources().getDisplayMetrics().widthPixels;
    private int mHeight = this.getResources().getDisplayMetrics().heightPixels;
    private int handCounter = 0,moveDownCircleCounter=0;
    private BitmapDrawable verfier_logo,verfier_circle,ripple,ripple1;
    private boolean isPointsInit = false;
    private AnimatePoint[] mMoveVerifierUpPoints,circleMoveDownPoints;
    private static final int FRAME_RATE = 5,FRAME_RATE_MOVE_UP = 1;
    private float newPointx , newPointy;
    private boolean renderCirlceOnTop = true;
    private Canvas mRippleCanvas;
    private Bitmap mVerifierRippleLogo;

    public VerifierAnimationView(Context context) {
        super(context);
        // start the animation:

    }

    public VerifierAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerifierAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VerifierAnimationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        verfier_logo = (BitmapDrawable)getResources().getDrawable(R.drawable.circle_no_ripple);
        ripple =  (BitmapDrawable)getResources().getDrawable(R.drawable.circle_two_ripple);
        ripple1 = (BitmapDrawable)getResources().getDrawable(R.drawable.circle_one_ripple);
        verfier_circle = (BitmapDrawable)getResources().getDrawable(R.drawable.verifier_circle);
        Bitmap btMap = verfier_logo.getBitmap();
        mVerifierRippleLogo = btMap.copy(Bitmap.Config.ARGB_8888, true);
        mRippleCanvas = new Canvas(mVerifierRippleLogo);
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
        circleMoveDownPoints = AnimationUtils.getCircleDownwardMovementPoints(mMoveVerifierUpPoints[mMoveVerifierUpPoints.length-1].x,
                mMoveVerifierUpPoints[mMoveVerifierUpPoints.length-1].y);
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
            }case VERIFIER_LOGO_UP_WITH_SMALL_CIRCLE:{
                verifierWithSmalCircle(canvas,verfier_logo,circleMoveDownPoints);
                break;
            }case MOVE_CIRCLE_DOWN:{
                moveLineWithCircleDownward(canvas,verfier_logo,circleMoveDownPoints);
                break;
            }case VERIFIER_LOGO_DOWN_WITH_SMALL_CIRCLE:{
                verifierDownWithSmalCircle(canvas,verfier_logo,circleMoveDownPoints);
                break;
            }
        }

    }

    private boolean isAnimate = true;
    private float animatePointY;
    private float resetAnimatePointY;
    private float circlePoint1Y,circlePoint2Y,circlePoint3Y,circlePoint4Y,circlePoint5Y,circlePoint6Y;
    private void verifierDownWithSmalCircle(Canvas canvas,BitmapDrawable img, AnimatePoint[] points){
        AnimatePoint animatePoint = mMoveVerifierUpPoints[mMoveVerifierUpPoints.length-1];
        float circleX = animatePoint.x+(img.getBitmap().getWidth()/2);
        float circleY = animatePoint.y + (img.getBitmap().getHeight() * 1.15f);

//        mRippleCanvas.drawRect(0,0,mRippleCanvas.getWidth(),mRippleCanvas.getHeight(),getMarkerPaint(true,false));
//        mRippleCanvas.drawArc(0,0,mRippleCanvas.getWidth(),mRippleCanvas.getHeight(),0f,360f,false,getRipplePaint1());
//         mRippleCanvas.drawCircle(0,0,mRippleCanvas.getWidth(),getRipplePaint(false,false));
        if(rippleState < 100)
          canvas.drawBitmap(img.getBitmap(),animatePoint.x,animatePoint.y,null);
        else if(rippleState >= 100 && rippleState <200){
            canvas.drawBitmap(ripple1.getBitmap(),animatePoint.x,animatePoint.y,null);
        }else if(rippleState >=200 && rippleState < 300)
            canvas.drawBitmap(ripple.getBitmap(),animatePoint.x,animatePoint.y,null);
        canvas.drawLine(circleX,circleY-25f,circleX,newPointy,getMarkerPaint(true,true));
        canvas.drawCircle(circleX,newPointy-25,25f,getMarkerPaint(true,false));
        canvas.drawBitmap(img.getBitmap(),animatePoint.x,newPointy,null);

//        canvas.drawArc(img.getBitmap().get);

        if(isAnimate){
            if(circleY < circlePoint1Y)
              canvas.drawCircle(circleX,circlePoint1Y,25f,getMarkerPaint(true,false));
            else
              circlePoint1Y = resetAnimatePointY;
            if(circleY < circlePoint2Y-70f)
              canvas.drawCircle(circleX,circlePoint2Y-70f,25f,getMarkerPaint(true,false));
            else
                circlePoint2Y = resetAnimatePointY;
            if(circleY < circlePoint3Y-140f)
              canvas.drawCircle(circleX,circlePoint3Y-140f,25f,getMarkerPaint(true,false));
            else
                circlePoint3Y = resetAnimatePointY;
            if(circleY < circlePoint4Y-210f)
              canvas.drawCircle(circleX,circlePoint4Y-210f,25f,getMarkerPaint(true,false));
            if(circleY < circlePoint5Y-280f)
                canvas.drawCircle(circleX,circlePoint5Y-280f,25f,getMarkerPaint(true,false));
            if(circleY < circlePoint6Y-350f)
                canvas.drawCircle(circleX,circlePoint6Y-350f,25f,getMarkerPaint(true,false));
            animatePointY = animatePointY - 5f;
//            if(animatePointY == circleY)
//                isAnimate = false;
            setAnimPoints(-5f);
            rippleState++;
            if(rippleState >= 300)
                rippleState = 0;
            invalidate();
        }

    }
    private int rippleState = 0;

    private void moveLineWithCircleDownward(Canvas canvas,BitmapDrawable img,AnimatePoint[] points){
        AnimatePoint animatePoint = mMoveVerifierUpPoints[mMoveVerifierUpPoints.length-1];
        float circleX = (animatePoint.x+(img.getBitmap().getWidth()/2));
        float circleY = (animatePoint.y + (img.getBitmap().getHeight() * 1.15f))-25;
        canvas.drawBitmap(img.getBitmap(),animatePoint.x,animatePoint.y,null);
        if(renderCirlceOnTop)
           canvas.drawCircle(circleX,circleY,25f,getMarkerPaint(true,false));

        if(moveDownCircleCounter < getIterationLength(points) && moveDownCircleCounter == 0){
            AnimatePoint animatePoint1 = getShapePoints(points,moveDownCircleCounter);
            canvas.drawLine(circleX,circleY,circleX,circleY,getMarkerPaint(true,true));
            canvas.drawCircle(circleX,circleY,25f,getMarkerPaint(true,false));
            moveDownCircleCounter++;
            newPointx = circleX - 5;
            newPointy = circleY - 5;
            renderCirlceOnTop = false;
            invalidate();
        }else if(moveDownCircleCounter < getIterationLength(points)){
            canvas.drawLine(circleX,circleY,circleX,newPointy,getMarkerPaint(true,true));
            canvas.drawCircle(circleX,newPointy,25f,getMarkerPaint(true,false));
            moveDownCircleCounter++;
//             newPointx = newPointx - 5;
            newPointy = newPointy + 5;
            renderCirlceOnTop = false;
            invalidate();
        }else{
            canvas.drawLine(circleX,circleY,circleX,newPointy,getMarkerPaint(true,true));
            canvas.drawCircle(circleX,newPointy+25,25f,getMarkerPaint(true,false));
            canvas.drawBitmap(img.getBitmap(),animatePoint.x,newPointy,null);
            renderCirlceOnTop = false;
            animatePointY = newPointy -25f;
            resetAnimatePointY = animatePointY;
            setAnimPoints(animatePointY);
            mAnimationType = VERIFIER_LOGO_DOWN_WITH_SMALL_CIRCLE;
            invalidate();
        }
    }

    private void setAnimPoints(float animatePoint){
        circlePoint1Y = circlePoint1Y+animatePoint;
        circlePoint2Y = circlePoint2Y+animatePoint;
        circlePoint3Y = circlePoint3Y+animatePoint;
        circlePoint4Y = circlePoint4Y+animatePoint;
        circlePoint5Y = circlePoint5Y+animatePoint;
        circlePoint6Y = circlePoint6Y+animatePoint;
    }

    private void verifierWithSmalCircle(Canvas canvas,BitmapDrawable img, AnimatePoint[] points){
         AnimatePoint animatePoint = mMoveVerifierUpPoints[mMoveVerifierUpPoints.length-1];
         float circleX = animatePoint.x+(img.getBitmap().getWidth()/2);
         float circleY = animatePoint.y + (img.getBitmap().getHeight() * 1.15f);
         canvas.drawBitmap(img.getBitmap(),animatePoint.x,animatePoint.y,null);
         canvas.drawCircle(circleX,circleY,25f,getMarkerPaint(true,false));
         mAnimationType = MOVE_CIRCLE_DOWN;
         invalidate();
//         if(moveDownCircleCounter < getIterationLength(points) && moveDownCircleCounter == 0){
//             AnimatePoint animatePoint1 = getShapePoints(points,moveDownCircleCounter);
//             canvas.drawLine(circleX,circleY,circleX,circleY,getMarkerPaint(true));
//             moveDownCircleCounter++;
//             newPointx = circleX - 5;
//             newPointy = circleY - 5;
//             invalidate();
//         }else if(moveDownCircleCounter < getIterationLength(points)){
//             canvas.drawLine(circleX,circleY,circleX,newPointy,getMarkerPaint(true));
//             moveDownCircleCounter++;
////             newPointx = newPointx - 5;
//             newPointy = newPointy + 5;
//             invalidate();
//         }
//         canvas.drawLine(circleX,circleY,circleX,circleY+300f,getMarkerPaint(true));
         System.out.println("animatePoint.x "+animatePoint.x+" animatePoint.y "+animatePoint.y+ " img.getBitmap().getWidth() "+img.getBitmap().getWidth()+
                 " img.getBitmap().getHeight() "+img.getBitmap().getHeight()+" drawn at "+(img.getBitmap().getWidth()+animatePoint.x)+ " and "+
                 (img.getBitmap().getHeight()+animatePoint.y));
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
        }else if(handCounter >= getIterationLength(points)){
            mAnimationType = VERIFIER_LOGO_UP_WITH_SMALL_CIRCLE;
            invalidate();
//            AnimatePoint endPoint = points[getIterationLength(points)-1];
//            canvas.drawCircle(img.getBitmap().getWidth(),img.getBitmap().getHeight(),25f,getMarkerPaint(true));
//            canvas.drawCircle(img.getBitmap());
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


    private Paint getMarkerPaint(boolean isFill,boolean isDarker) {
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

    private Paint getRipplePaint(boolean isFill,boolean isDarker) {
        Paint paint = new Paint();
        if(isFill) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }else{
            paint.setStyle(Paint.Style.STROKE);
        }
//        if(isCircle) {
        paint.setColor(Color.parseColor("#FF0000"));//339933
        paint.setStrokeCap(Paint.Cap.ROUND);
//        }else{
//            paint.setColor(Color.parseColor("#CD5C5C"));
//        }
        if(isDarker)
            paint.setStrokeWidth(45f);
        else
            paint.setStrokeWidth(6f);
        return paint;
    }

    private Paint getRipplePaint1(){
        Paint mFillColor = new Paint();
        mFillColor.setColor(Color.parseColor("#FF0000"));
        mFillColor.setStrokeWidth(10f);
        mFillColor.setStyle(Paint.Style.FILL_AND_STROKE);
        mFillColor.setStrokeCap(Paint.Cap.ROUND);
        return mFillColor;
    }







}
