package com.seatrend.xj.electricbicyclesalesystem.util;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.seatrend.xj.electricbicyclesalesystem.R;


/**
 * Created by seatrend on 2018/8/28.
 * */



public class LoadingDialog {


    private static LoadingDialog mLoadingDialog;
    private LoadingDialog(){}
    private  Dialog mDialog;

    public static LoadingDialog getInstance(){
        if(mLoadingDialog==null){
            mLoadingDialog=new LoadingDialog();
        }
        return mLoadingDialog;
    }



    public  void showLoadDialog(Context context){
        mDialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_animation, null);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

//    public  void showLoadDialog(Context context){
//        mDialog = new Dialog(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
//        mDialog.setContentView(view);
//        mDialog.setCanceledOnTouchOutside(false);
//
//        ImageView ivBall=view.findViewById(R.id.iv_ball);
//        ImageView ivBicycle=view.findViewById(R.id.iv_bicycle);
//        RotateAnimation  rotateAnimation = new RotateAnimation(0,
//                360,Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//        rotateAnimation.setDuration(1000);
//        rotateAnimation.setFillAfter(true);
//        rotateAnimation.setRepeatMode(Animation.RESTART);
//        rotateAnimation.setInterpolator(new LinearInterpolator());
//        rotateAnimation.setRepeatCount(-1);
//        ivBall.setAnimation(rotateAnimation);
//
//
//        TranslateAnimation translateAni = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
//                0, Animation.RELATIVE_TO_SELF, 0,
//                Animation.RELATIVE_TO_SELF, 0.2f);
//
//        translateAni.setDuration(1000);
//        translateAni.setRepeatCount(-1);
//        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
//        translateAni.setRepeatMode(Animation.REVERSE);
//        ivBicycle.startAnimation(translateAni);
//
//        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//
//            }
//        });
//        mDialog.show();
//
//    }

    public  void dismissLoadDialog(){
        if(mDialog!=null){
            mDialog.dismiss();
        }
    }
    public boolean dialogShowing(){
        if(mDialog!=null && mDialog.isShowing()){
          return true;
        }
        return false;
    }
}
