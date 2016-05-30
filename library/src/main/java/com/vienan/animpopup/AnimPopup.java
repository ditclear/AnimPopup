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
import android.widget.PopupWindow;
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
        mTextView=new TextView(mContext);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(20);
//        mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0.0f, 0f);

    }

    public AnimPopup setMsg(String msg){
        mTextView.setText(msg);
        return this;
    }

    public AnimPopup setTextColor(int textColor){
        mTextView.setTextColor(textColor);
        return this;
    }

    public AnimPopup showOnTop(View view){
        this.v=view;
        v.getLocationOnScreen(location);
        setHeight(v.getHeight()); setWidth(v.getWidth());
        Log.i("location",location[0]+" "+location[1]);
        Log.i("height width",getHeight()+" "+getWidth());
        Log.i("px py",v.getPivotX()+" "+v.getPivotY());
        showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1]-getHeight()/2);
        pvhY = PropertyValuesHolder.ofFloat("y",mTextView.getPivotY(),
                mTextView.getPivotY()-v.getHeight(),  mTextView.getPivotY());
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
        setContentView(mTextView);

        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(getContentView(),pvhX,pvhY)
                .setDuration(1500);
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
