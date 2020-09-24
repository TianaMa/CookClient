package com.demo.cook.utils;

import android.content.Intent;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.demo.baselib.base.BaseContext;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.user.login.LoginActivity;

public class LoginVerifyUtils {

    public interface VerifyCallback {
        void verifySuccess();
    }

    public static void verifyAccount(VerifyCallback verify){

        if(TextUtils.isEmpty(Storage.getUserInfo().getUsername())){
            new AlertDialog.Builder(BaseContext.getInstance().getTopActivity())
                    .setTitle("温馨提示")
                    .setMessage("请先登录账号")
                    .setPositiveButton("去登录", (dialog, which) -> {
                        Intent intentLogin = new  Intent(BaseContext.getInstance().getTopActivity(), LoginActivity.class);
                        intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        BaseContext.getInstance().getTopActivity().startActivity(intentLogin);
                    })
                    .setNegativeButton("取消", null)
                    .setCancelable(false)// 设置builder不可被取消
                    .show();
        }else {
            verify.verifySuccess();
        }
    }
}
