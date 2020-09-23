package com.demo.cook.utils.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DialogUtils {

    private static class DialogUtilHolder {
        private static final DialogUtils INSTANCE = new DialogUtils();
    }

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        return DialogUtilHolder.INSTANCE;
    }

    public void showAlertDialog(Context context, String title, String alertMsg) {
        // 创建一个Builder对象，因为Builder是静态内部类，所以它的实例化方式为 外部类.静态内部类
        new AlertDialog.Builder(context)
                .setTitle(title)// 设置标题
                .setMessage(alertMsg)// 设置消息
                .setPositiveButton("我知道了", null)
                .setCancelable(false)
                .show();// 显示Builder
    }

    public void showConfirmDialog(Context context, String title, String confirmMsg, DialogInterface.OnClickListener clickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(confirmMsg)
                .setPositiveButton("确定", clickListener)
                .setNegativeButton("取消", null)
                .setCancelable(false)// 设置builder不可被取消
                .show();
    }

    public void showChooseDialog(Context context, String title, String[] items, DialogInterface.OnClickListener clickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setNegativeButton("取消", null)
                .setItems(items, clickListener)
                .show();
    }

    public void showDataDialog(Context context, int style, String title, DatePickerDialog.OnDateSetListener listener) {
        // 通过静态方法getInstance()从指定时区 Locale.CHINA 获得一个日期实例
        Calendar d = Calendar.getInstance(Locale.CHINA);
        d.setTime(new Date());// 设置当前时间

        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);

        //初始化默认日期year, month, day
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context, style, listener, year, month, day);
        datePickerDialog.setMessage(title);
        datePickerDialog.show();
    }

    public void showTimeDialog(Context context, int style, String title, TimePickerDialog.OnTimeSetListener listener) {
        // 通过静态方法getInstance()从指定时区 Locale.CHINA 获得一个日期实例
        Calendar d = Calendar.getInstance(Locale.CHINA);
        d.setTime(new Date());// 创建一个Date实例

        int hour = d.get(Calendar.HOUR_OF_DAY);
        int minute = d.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, style,
                listener, hour, minute, true);
        timePickerDialog.setMessage(title);
        timePickerDialog.show();
    }


    private ProgressDialog progress;

    public void showProgress(Context context, @Nullable String title, String content) {
        closeProgress();
        progress = new ProgressDialog(context);
        progress.setTitle(title);
        progress.setMessage(content);
        progress.setCancelable(false);// 设置builder不可被取消
        progress.show();
    }

    public void closeProgress() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
            progress = null;
        }
    }


    private Dialog loading;


    public void showLoading(Context context, @StringRes int msgId){
        showLoading(context,context.getString(msgId));
    }

    public void showLoading(Context context, String msg) {
        closeLoading();

        GradientDrawable drawable = new GradientDrawable();//描述布局背景shape 圆角和背景色
        drawable.setColor(Color.parseColor("#CC444444"));
        drawable.setCornerRadius(50);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        LinearLayout layout = new LinearLayout(context);//生成布局
        layout.setLayoutParams(layoutParams);//设置宽高
        layout.setGravity(Gravity.CENTER);
        layout.setBackground(drawable);//背景
        layout.setPadding(40, 50, 40, 50);//padding
        layout.setOrientation(LinearLayout.VERTICAL);//方向

        ProgressBar progressBar = new ProgressBar(context);
        layout.addView(progressBar);

        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setText(msg);
        layout.addView(textView);

        loading = new Dialog(context, android.R.style.Theme_Material_Dialog_NoActionBar);//R.style.progress_dialog
        loading.setContentView(layout);
        loading.setCancelable(false);
        loading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loading.getWindow().setDimAmount(0f);//去掉背景遮蔽蒙版
        loading.show();
    }

    public void closeLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
            loading = null;
        }
    }


    private BottomSheetDialog dialog;
    private DialogInterface.OnClickListener mListener;

    public void showOptionDialog(Context context, String[] items, DialogInterface.OnClickListener clickListener){
        if(dialog==null){
            dialog=new BottomSheetDialog(context);
        }
        mListener=clickListener;
        //获取屏幕的高，根据比例计算按钮的高来达到适配的目的
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        //生成popupView
        LinearLayout dialogView =new LinearLayout(context);// 声明PopupWindow对应的视图
        dialogView.setOrientation(LinearLayout.VERTICAL);

        //动态添加popupView的子view
        for(int i=0;i<=items.length;i++){
            TextView button=new TextView(context);
            button.setHeight(metrics.heightPixels/11);
            button.setGravity(Gravity.CENTER);
            button.setText(i<items.length?items[i]:"取消");
            button.setTextColor(Color.BLACK);
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                        view.setBackgroundColor(Color.parseColor("#EEEEEE"));
                    }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                        view.setBackgroundColor(Color.WHITE);
                    }
                    return false;
                }
            });
            button.setTag(i);
            if(i==items.length){
                TextView cutLine =new TextView(context);
                cutLine.setHeight(metrics.heightPixels/96);
                cutLine.setBackgroundColor(Color.parseColor("#DDDDDD"));
                dialogView.addView(cutLine);
                button.setOnClickListener(view -> dialog.dismiss());
            }else{
                button.setOnClickListener(view -> {
                    dialog.dismiss();
                    if(mListener!=null){
                        mListener.onClick(dialog,(int)view.getTag());
                    }
                });
            }
            dialogView.addView(button);
            if(i<items.length){
                View line=new View(context);
                line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                line.setBackgroundColor(Color.parseColor("#DDDDDD"));
                dialogView.addView(line);
            }
        }
        dialog.setContentView(dialogView);
        dialog.show();
    }

}
