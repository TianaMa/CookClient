package com.demo.basecode.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * shouldShowRequestPermissionRationale:
 * 第一次打开App时	false
 * 上次弹出权限点击了禁止（但没有勾选“下次不在询问”）	true
 * 上次选择禁止并勾选：下次不在询问	false
 *
 *
 * 传入需要申请的权限 和申请成功的方法
 * 1 先去申请所需的权限：都有了 ——> 执行
 * 2 有缺少的：
 *   如果有不提示的  ——> 提示去设置缺少的权限3
 *        取消    ——> toast("取消")
 *   如果没有不提示的否 ——> toast("取消")
 *
 */
public enum PermitsUtil {

    instance;

    public static PermitsUtil getInstance() {
        return instance;
    }

    /**
     * Request permissions.
     */
    public void requestPermissions(final Activity activity, Boolean needful, @NonNull Action<List<String>> granted, String... permissions) {

        final Context context =activity.getApplicationContext();

        AndPermission.with(activity).runtime().permission(permissions)
                .onGranted(granted)
                .onDenied(needful? (Action<List<String>>) permissions1 -> {

                    List<String> alwaysDeniedPms = new ArrayList<>();//获取总是拒绝的权限
                    for (String permission : permissions1) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                            alwaysDeniedPms.add(permission);
                        }
                    }

                    if (alwaysDeniedPms.size() > 0) {

                        new AlertDialog.Builder(activity)
                                .setTitle("warm prompt")
                                .setMessage("This feature requires access" + Permission.transformText(activity, alwaysDeniedPms).toString() + "permission" + "Please grant it to “application info -> permissions!”")
                                .setNegativeButton("quit", (dialogInterface, i) -> Toast.makeText(context.getApplicationContext(), "You have revoked the authorization", Toast.LENGTH_SHORT).show())
                                .setPositiveButton("To set up", (dialog, which) -> {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    context.getApplicationContext().startActivity(intent);
                                }).show();
                    }else {
                        Toast.makeText(context.getApplicationContext(), "You denied the authorization", Toast.LENGTH_SHORT).show();
                    }

                } :granted)
                .start();
    }


    /**
     * 返回所需权限列表中缺少的权限列表
     * */
    public Boolean hasAllPermissions(Context context,String... permissions) {
        for(String permission: permissions){
            if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

}
