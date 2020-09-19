package com.demo.basecode.ui;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.demo.baselib.base.BaseContext;

import es.dmoral.toasty.Toasty;


/**
 * 作者：吕振鹏
 * E-mail:lvzhenpeng@pansoft.com
 * 创建时间：2019年03月28日
 * 时间：19:24
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class ToastyUtils {


    enum ToastyType{
        TOASTY_TYPE_NORMAL,TOASTY_TYPE_INFO,TOASTY_TYPE_WARNING,TOASTY_TYPE_SUCCESS,TOASTY_TYPE_ERROR
    }





    public static void show( String msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_NORMAL);
    }

    public static void showInfo( String msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_INFO);
    }

    public static void showWring( String msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_WARNING);
    }


    public static void showSuccess(String msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_SUCCESS);
    }

    public static void showError(String msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_ERROR);
    }



    public static void show( @StringRes int  msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_NORMAL);
    }

    public static void showInfo( @StringRes int  msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_INFO);
    }

    public static void showWring( @StringRes int  msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_WARNING);
    }


    public static void showSuccess(@StringRes int  msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_SUCCESS);
    }

    public static void showError(@StringRes int  msg) {
        showToasty(BaseContext.getInstance(), msg, ToastyType.TOASTY_TYPE_ERROR);
    }


    private static void showToasty(Context context, String msg, ToastyType toastyType) {
        Handler handler = new Handler(context.getMainLooper());
        handler.post(() -> {
            int duration = Toast.LENGTH_SHORT;
            Toast toast;
            switch (toastyType) {
                case TOASTY_TYPE_ERROR:
                    toast = Toasty.error(context, msg, duration);
                    break;
                case TOASTY_TYPE_NORMAL:
                    toast = Toasty.normal(context, msg, duration);
                    break;
                case TOASTY_TYPE_SUCCESS:
                    toast = Toasty.success(context, msg, duration);
                    break;
                case TOASTY_TYPE_INFO:
                    toast = Toasty.info(context, msg, duration);
                    break;
                case TOASTY_TYPE_WARNING:
                    toast = Toasty.warning(context, msg, duration);
                    break;
                default:
                    toast = Toast.makeText(context, msg, duration);
            }
            toast.show();
        });

    }


    private static void showToasty(Context context, @StringRes int msg, ToastyType toastyType) {
        Handler handler = new Handler(context.getMainLooper());
        handler.post(() -> {
            int duration = Toast.LENGTH_SHORT;
            Toast toast;
            switch (toastyType) {
                case TOASTY_TYPE_ERROR:
                    toast = Toasty.error(context, msg, duration);
                    break;
                case TOASTY_TYPE_NORMAL:
                    toast = Toasty.normal(context, msg, duration);
                    break;
                case TOASTY_TYPE_SUCCESS:
                    toast = Toasty.success(context, msg, duration);
                    break;
                case TOASTY_TYPE_INFO:
                    toast = Toasty.info(context, msg, duration);
                    break;
                case TOASTY_TYPE_WARNING:
                    toast = Toasty.warning(context, msg, duration);
                    break;
                default:
                    toast = Toast.makeText(context, msg, duration);
            }
            toast.show();
        });

    }


}
