package com.demo.cook.utils.upload;

import android.Manifest;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.basecode.utility.PermitsUtil;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.utils.view.DialogUtils;
import com.yanzhenjie.album.Album;

import org.json.JSONException;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UpLoadUtils {

    public interface UploadImgCallBack{

        void success(String path);
    }

    public static void upLoadSingleImage(Activity activity, QiNiuUtil.Prefix prefix,
                                         UploadImgCallBack callBack){

        PermitsUtil.getInstance().requestPermissions(activity,true,
                data -> Album.image(activity)
                        .singleChoice()//.singleChoice() 多选/单选
                        .camera(true)
                        .columnCount(3)
                        .onResult(result -> {
                            String filePath= result.get(0).getPath();
                            Log.e("filePath==",filePath);
                            String targetDir =activity.getExternalCacheDir().getAbsolutePath()+"";
                            Log.e("targetDir==",targetDir);
                            Luban.with(activity)
                                    .load(filePath)
                                    .ignoreBy(30)
                                    .setTargetDir(targetDir)
                                    .filter(path -> !TextUtils.isEmpty(path))
                                    .setCompressListener(new OnCompressListener() {
                                        @Override
                                        public void onStart() {
                                            DialogUtils.getInstance().showLoading(activity,activity.getString(R.string.text_upload_image));
                                        }

                                        @Override
                                        public void onSuccess(File file) {
                                            Log.e("file==",file.getAbsolutePath());
                                            QiNiuUtil.put(prefix,file.getAbsolutePath(),(key, info, response) -> {
                                                DialogUtils.getInstance().closeLoading();
                                                if (info.statusCode==200){
                                                    try {
                                                        String fileNetPath= response.getString("key");
                                                        callBack.success(fileNetPath);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        ToastyUtils.show(R.string.text_upload_image_fail);
                                                    }
                                                }else {
                                                    ToastyUtils.show(R.string.text_upload_image_fail);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            DialogUtils.getInstance().closeLoading();
                                            ToastyUtils.show(R.string.text_upload_image_fail);
                                        }
                                    }).launch();


                        }).start(),
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
        );
    }
}
