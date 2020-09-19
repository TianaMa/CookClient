package com.demo.cook.ui.me;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.demo.cook.R;
import com.google.android.material.appbar.AppBarLayout;

public class MyBehavior extends AppBarLayout.ScrollingViewBehavior{

    public MyBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        Log.e("onNestedPreScroll","getTop"+dependency.getTop());

        Toolbar scroll_tb_header=dependency.findViewById(R.id.scroll_tb_header);

        float top =-dependency.getTop();
        float headerBarOffsetY = 250;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - top) / headerBarOffsetY, 0f);
        Log.e("onDependentViewChanged","top=="+top+"--offset=="+offset);

        int color = (int) new ArgbEvaluator().evaluate(offset,
                Color.parseColor("#002E8BD9"),
                Color.parseColor("#FF2E8BD9"));
        scroll_tb_header.setBackgroundColor(color);

        return super.onDependentViewChanged(parent, child, dependency);
    }
}
