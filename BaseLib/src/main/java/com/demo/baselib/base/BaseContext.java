package com.demo.baselib.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 作者：吕振鹏
 * E-mail:lvzhenpeng@pansoft.com
 * 创建时间：2019年03月27日
 * 时间：15:04
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class BaseContext extends Application {

    private static BaseContext sInstances;

    public static BaseContext getInstance() {
        return sInstances;
    }

    /**
     * 栈顶Activity
     * */
    private Activity topActivity ;

    public Activity getTopActivity() {
        return topActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstances = this;
        registerActivity();
    }

    private void registerActivity() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                topActivity=activity;
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }
}
