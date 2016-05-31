package com.vienan.animpopup;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by vienan on 16/5/29.
 */
public class AnimPopup extends PopupWindow implements Animator.AnimatorListener{

    TextView mTextView;
    Context mContext;
    View v;
    int[] location = new int[2];
    private static AnimPopup instance=null;
    private PropertyValuesHolder pvhX,pvhY;
    private int w,h;
    private int mDistance=200;
    private ObjectAnimator objectAnimator;
    private boolean hasChanged=false;

    public static synchronized AnimPopup getInstance(Context context){
        if (instance==null) instance=new AnimPopup(context);
        return instance;
    }
    private AnimPopup(Context context){
        super(context);

        init(context);
    }


    private void init(Context context) {
        mContext=context;
        w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        initTextView();

//        reSetPopupWH();
        mTextView.measure(w, h);
        setWidth(mTextView.getMeasuredWidth());
        setHeight(mDistance + mTextView.getMeasuredHeight());
        setFocusable(false);
        setTouchable(false);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape));
        pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0.0f, 0.0f);
    }


    private AnimPopup setDistance(int distance){
        this.mDistance=distance;
        return this;
    }

    private void reSetPopupWH() {
        w = (int) mTextView.getPaint().measureText(mTextView.getText().toString());
        setWidth(w);
        setHeight(mDistance + getTextViewHeight(mTextView, w));
        Log.i("mTextView", mTextView.getMeasuredWidth() + " " + mTextView.getMeasuredHeight() + " " + mTextView.getX() + " " + mTextView.getY());
    }

    private static int getTextViewHeight(TextView textView, int width) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }


    public AnimPopup setMsg(String msg){
        mTextView.setText(msg);
        reSetPopupWH();
        return this;
    }

    public AnimPopup setTextColor(int textColor){
        mTextView.setTextColor(textColor);
        return this;
    }

    private void initTextView() {
        RelativeLayout layout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTextView=new TextView(mContext);
        mTextView.setGravity(Gravity.BOTTOM);
        mTextView.setLayoutParams(params);
        layout.addView(mTextView);
        setContentView(layout);
    }

    public AnimPopup showOnTop(View view){
        this.v=view;
        v.getLocationOnScreen(location);
        hasChanged=true;
        Log.i("height width",getHeight()+" "+getWidth());
        Log.i("px py",v.getPivotX()+" "+v.getPivotY());
        showAtLocation(v, Gravity.NO_GRAVITY, location[0]+v.getWidth()/2-getWidth()/2, location[1]-getHeight());
        pvhY = PropertyValuesHolder.ofFloat("y",getHeight()/2,
                -mDistance/2,  getHeight()/2);
        return this;
    }
    public AnimPopup showOnBottom(View view){
        this.v=view;
        v.getLocationOnScreen(location);
        hasChanged=true;
        pvhY = PropertyValuesHolder.ofFloat("y", -getHeight()/2,
                mDistance/2,  -getHeight()/2);
        showAtLocation(v, Gravity.NO_GRAVITY, location[0]+v.getWidth()/2-getWidth()/2, location[1]+v.getHeight());
        return this;
    }
    private AnimPopup showOnLeft(View view){
        this.v=view;
        v.getLocationOnScreen(location);
        hasChanged=true;
        showAtLocation(v, Gravity.NO_GRAVITY, location[0]-getWidth(), location[1]);
        pvhY = PropertyValuesHolder.ofFloat("x", getWidth()/2,
                -mDistance,  getWidth()/2);
        return this;
    }
    private AnimPopup showOnRight(View view){
        this.v=view;
        v.getLocationOnScreen(location);
        hasChanged=true;
        pvhY = PropertyValuesHolder.ofFloat("x", -getWidth()/2,
                mDistance,  -getWidth()/2);
        showAtLocation(v, Gravity.NO_GRAVITY, location[0]+getWidth(), location[1]);
        return this;
    }

    ObjectAnimator createAnimator(){
        objectAnimator=ObjectAnimator.ofPropertyValuesHolder(getContentView(),pvhX,pvhY)
                .setDuration(1500);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(this);
        hasChanged=false;
        return objectAnimator;
    }

    public void show(){
        if (objectAnimator==null||hasChanged)
            objectAnimator=createAnimator();
        objectAnimator.start();

    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mTextView.post(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        });
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
