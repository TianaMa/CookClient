package com.demo.cook.base.binding;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.ui.user.model.data.User;


public class DataBindingAdapter {

    @BindingAdapter("resName")
    public static void loadLocalImage(ImageView view, String resName) {
        int resId= TextUtils.isEmpty(resName)?R.drawable.vector_ic_recipe_sort_more:
        view.getContext().getResources().getIdentifier(resName,"drawable", view.getContext().getPackageName());
        view.setImageResource(resId);
    }

    @BindingAdapter(value = {"srcPath","circle","placeholder","error","ratio"}, requireAll = false)
    public static void srcPath(ImageView imageView, String srcPath, boolean circle , Drawable placeholder, Drawable error,Float ratio){
        if(TextUtils.isEmpty(srcPath)){
            if(placeholder!=null){
                imageView.setImageDrawable(placeholder);
            }
        }else {
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions options= RequestOptions.bitmapTransform(new RoundedCorners(12)).override(300, 300);

            RequestBuilder<Drawable> load = Glide.with(imageView).load(QiNiuUtil.getNetRealPath(srcPath)).apply(options);

            if(ratio != null){
                load.listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        int vh = (int) (vw/ ratio);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                });
            }
            if(circle){
                load.circleCrop();
            }
            if(placeholder!=null){
                load.thumbnail(Glide.with(imageView).load(placeholder));
            }
            if (error!=null){
                load.error(error);
            }
            load.into(imageView);
        }
    }


    @BindingAdapter("userInfo")
    public static void loadUserInfo(TextView view, User user) {
        String info="";
        if(!TextUtils.isEmpty(user.getGender())){
            info+=user.getGender()+" · ";
        }
        if(!TextUtils.isEmpty(user.getRegisterDate())){
            info+=user.getRegisterDate()+" join";
        }
        if(!TextUtils.isEmpty(user.getProfession())){
            info+=" · ";
            info+=user.getProfession();
        }
        info+="\nhometown: ";
        if(TextUtils.isEmpty(user.getHometown())){
            info+="not filled";
        }else {
            info+=user.getHometown();
        }
        info+=" · address: ";
        if(TextUtils.isEmpty(user.getAddress())){
            info+="not filled";
        }else {
            info+=user.getAddress();
        }
        info+="\n";

        if (TextUtils.isEmpty(user.getSignature())){
            info+="Add a personal profile to get to know you better";
        }else {
            info+=user.getSignature();
        }
        view.setText(info);
    }

}
