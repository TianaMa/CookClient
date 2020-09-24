package com.demo.cook;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.baselib.base.BaseContext;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.base.local.Storage;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

import java.util.Locale;

public class App extends BaseContext {

    @Override
    public void onCreate() {
        super.onCreate();

        initStorage();

        initAlbum();
    }

    private void initStorage(){
        Storage.setZh(false);
        Storage.setQNToken(QiNiuUtil.getToken());


    }


    private void initAlbum(){
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new AlbumLoader(){
                    @Override
                    public void load(ImageView imageView, AlbumFile albumFile) {
                        load(imageView, albumFile.getPath());
                    }

                    @Override
                    public void load(ImageView imageView, String url) {
                        Glide.with(imageView.getContext()).load(url).into(imageView);
                    }
                })
                .setLocale(Locale.getDefault())
                .build()
        );
    }

}
