package com.vienan.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.vienan.animpopup.AnimPopup;

public class MainActivity extends AppCompatActivity {

    AnimPopup mAnimPopup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnimPopup=AnimPopup.getInstance(this);
    }

    void startAnim(View v){
        Log.i("v","v:"+v.getX()+" "+v.getY());
        if (mAnimPopup.isShowing()) return;
        mAnimPopup.setMsg("啊哒哒哒哒。。。")
                .setTextColor(Color.RED)
                .showOnBottom(v)
                .show();
    }

    void startAnim1(View v){
        if (mAnimPopup.isShowing()) return;
        mAnimPopup
                .setMsg("   +1  ")
                .setTextColor(Color.RED)
                .showOnBottom(v)
                .show();
    }
    void startAnim2(View v){
        if (mAnimPopup.isShowing()) return;
        mAnimPopup
                .setMsg("   +1  ")
                .setTextColor(Color.MAGENTA)
                .showOnBottom(v)
                .show();
    }
    void startAnim3(View v){
        if (mAnimPopup.isShowing()) return;
        mAnimPopup
                .setMsg("   +1  ")
                .setTextColor(Color.BLUE)
                .showOnTop(v)
                .show();
    }
    void startAnim4(View v){
        if (mAnimPopup.isShowing()) return;
        mAnimPopup
                .setMsg("   +1  ")
                .setTextColor(Color.GRAY)
                .showOnTop(v)
                .show();
    }

}
