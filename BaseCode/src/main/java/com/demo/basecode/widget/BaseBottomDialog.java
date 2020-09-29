package com.demo.basecode.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;

import com.demo.basecode.R;

/**
 * 高度模式：
 * 1 固定百分比高度
 * 2 自身高度
 * 3 最大 百分比高度
 * */
public abstract class BaseBottomDialog extends Dialog {

    public BaseBottomDialog(Context context) {
        super(context,R.style.Dialog);
        getWindow().setGravity(Gravity.BOTTOM);
    }


    @LayoutRes
    protected abstract int getLayoutRes();


    public enum HeightMode {
        WRAP,FIXED,WRAP_MAX
    }
    private HeightMode heightMode = HeightMode.WRAP_MAX;

    @FloatRange(from = 0.0, to = 1.0)
    private Float heightPCT = 0.7F;


    protected View childView;

    public void setHeightMode(HeightMode heightMode){
        this.heightMode=heightMode;
    }

    public void setHeightPCT(Float heightPCT){
        this.heightPCT=heightPCT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置可取消
        setCanceledOnTouchOutside(true);
        childView = getLayoutInflater().inflate(getLayoutRes(), null);
        setContentView(childView);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(android.graphics.Color.WHITE);
        getWindow().setBackgroundDrawable(gradientDrawable);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        switch (heightMode){
            case WRAP:
                getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                break;
            case FIXED:
                getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,(int)(getContext().getResources().getDisplayMetrics().heightPixels * heightPCT));
                break;
            case WRAP_MAX:
                getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,Math.min((int) childView.getHeight(),
                        (int)(getContext().getResources().getDisplayMetrics().heightPixels * heightPCT)));
                break;
        }

        getWindow().setWindowAnimations(R.style.PickerAnim);
    }
}
