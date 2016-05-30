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
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by vienan on 16/5/29.
 */
public class AnimPopup extends PopupWindow {

    TextView mTextView;
    Context mContext;
    View v;
    int[] location = new int[2];
    private static AnimPopup instance=null;
    private PropertyValuesHolder pvhX,pvhY;
    private int w,h;
    private int mDistance=200;

    public static synchronized AnimPopup getInstance(Context context){
        if (instance==null) instance=new AnimPopup(context);
        return instance;
    }
    public AnimPopup(Context context){
        super(context);

        init(context);
    }


    private void init(Context context) {
        mContext=context;
        w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        initTextView();

        reSetPopupWH();

        setFocusable(false);
        setTouchable(false);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape));
        pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0.0f, 0.0f);

    }

    private void initTextView() {
        RelativeLayout layout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mTextView=new TextView(mContext);
        mTextView.setGravity(Gravity.BOTTOM);
        mTextView.setText(" + 1 ");

        mTextView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(mTextView);
        setContentView(layout);
    }


    public AnimPopup setDistance(int distance){
        this.mDistance=distance;
        return this;
    }

    private void reSetPopupWH(){
        w = (int) mTextView.getPaint().measureText(mTextView.getText().toString());
        setWidth(w);
        setHeight(mDistance + getTextViewHeight(mTextView, w));
        Log.i("mTextView",mTextView.getMeasuredWidth()+" "+mTextView.getMeasuredHeight());
    }

    private static int getTextViewHeight(TextView textView, int width) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }


    public AnimPopup setMsg(String msg){
        if (mTextView==null) initTextView();
        mTextView.setText(msg);
        reSetPopupWH();
        return this;
    }

    public AnimPopup setTextColor(int textColor){
        mTextView.setTextColor(textColor);
        return this;
    }


    public AnimPopup showOnTop(View view){
        this.v=view;
        mTextView.setY(v.getPivotY());
        v.getLocationOnScreen(location);
        Log.i("height width",getHeight()+" "+getWidth());
        Log.i("px py",mTextView.getPivotX()+" "+mTextView.getPivotY());
        showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]-getHeight());
        pvhY = PropertyValuesHolder.ofFloat("y",v.getPivotY(),
                v.getPivotY()-mDistance/2,  v.getPivotY());
        return this;
    }
    public AnimPopup showOnBottom(View view){
        this.v=view;
        v.getLocationOnScreen(location);

        pvhY = PropertyValuesHolder.ofFloat("y", mTextView.getPivotY(),
                mTextView.getPivotY()+200,  mTextView.getPivotY());
        Log.i("px py",v.getPivotY()+" ");
        showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]);
        return this;
    }
    public AnimPopup showOnLeft(View view){
        this.v=view;
        v.getLocationOnScreen(location);
        pvhY = PropertyValuesHolder.ofFloat("x", v.getPivotX()-30,
                v.getPivotX()-120,  v.getPivotX()-30);
        showAtLocation(v, Gravity.NO_GRAVITY, location[0]-getWidth(), location[1]);
        return this;
    }
    public AnimPopup showOnRight(View view){
        this.v=view;
        v.getLocationOnScreen(location);
        pvhY = PropertyValuesHolder.ofFloat("x", location[0]+getWidth(),
                location[0]+getWidth()+120,  location[0]+getWidth());
        showAtLocation(v, Gravity.NO_GRAVITY, location[0]+getWidth(), location[1]);
        return this;
    }

    public void show(){
//        setContentView(mTextView);

        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(getContentView(),pvhX,pvhY)
                .setDuration(1500);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                if (popupWindow.isShowing()) popupWindow.dismiss();
                mTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

}
