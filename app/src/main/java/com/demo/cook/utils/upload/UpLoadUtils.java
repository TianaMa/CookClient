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
import com.yanzhenjie.album.AlbumFile;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UpLoadUtils {

    public interface UploadImgCallBack{

        void success(ArrayList<String> pathList);
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
                                                        ArrayList<String> fileNetPathList = new ArrayList<>();
                                                        fileNetPathList.add(fileNetPath);
                                                        callBack.success(fileNetPathList);
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



    public static void upLoadMultiImage(Activity activity, QiNiuUtil.Prefix prefix,int maxCount,
                                         UploadImgCallBack callBack){

        PermitsUtil.getInstance().requestPermissions(activity,true,
                data -> Album.image(activity)
                        .multipleChoice()//.singleChoice() 多选/单选
                        .camera(true)
                        .columnCount(3)
                        .selectCount(maxCount)
                        .onResult(result -> {
                            String targetDir =activity.getExternalCacheDir().getAbsolutePath()+"";

                            AtomicInteger count = new AtomicInteger(1);
                            ArrayList<String> imageNetPathList = new ArrayList<>();
                            //创建一个上游 Observable：
                            Observable<String> observable = Observable.create(emitter -> {

                                for (AlbumFile file:result){
                                    Luban.with(activity).load(file.getPath()).ignoreBy(30)
                                            .setTargetDir(targetDir)
                                            .filter(path -> !TextUtils.isEmpty(path))
                                            .setCompressListener(new OnCompressListener() {
                                                @Override
                                                public void onStart() { }

                                                @Override
                                                public void onSuccess(File file) {
                                                    QiNiuUtil.put(prefix,file.getAbsolutePath(),(key, info, response) -> {
                                                        DialogUtils.getInstance().closeLoading();
                                                        if (info.statusCode==200){
                                                            try {
                                                                String fileNetPath= response.getString("key");
                                                                emitter.onNext(fileNetPath);
                                                                int andIncrement = count.getAndIncrement();
                                                                if(andIncrement==result.size()){
                                                                    emitter.onComplete();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                                emitter.onError(e);
                                                            }
                                                        }else {
                                                            emitter.onError(new Exception(response.toString()));
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    emitter.onError(e);
                                                }
                                            }).launch();

                                }



                            });


                            //创建一个下游 Observer
                            Observer<String> observer = new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    DialogUtils.getInstance().showLoading(activity,activity.getString(R.string.text_upload_image));
                                }

                                @Override
                                public void onNext(String value) {
                                    imageNetPathList.add(value);
                                    Log.e("onNext",value);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("onNext",e.getMessage());
                                    ToastyUtils.show(R.string.text_upload_image_fail);
                                }

                                @Override
                                public void onComplete() {
                                    Log.e("onComplete","onComplete");
                                    DialogUtils.getInstance().closeLoading();

                                    callBack.success(imageNetPathList);
                                }
                            };
                            //建立连接
                            observable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(observer);


                        }).start(),
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
        );

    }
}
