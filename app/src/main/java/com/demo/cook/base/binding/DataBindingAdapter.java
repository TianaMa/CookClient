package com.demo.cook.base.binding;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.ui.user.model.data.User;


public class DataBindingAdapter {

    @BindingAdapter("headImg")
    public static void loadHeadImage(ImageView view, String headImg) {
        Log.e("loadHeadImage",QiNiuUtil.getNetRealPath(headImg));
        Glide.with(view)
                .load(QiNiuUtil.getNetRealPath(headImg))
                .thumbnail(Glide.with(view).load(R.drawable.user_head_0).circleCrop())
                .error(Glide.with(view).load(R.drawable.user_head_0).circleCrop())
                .circleCrop().into(view);
    }

    @BindingAdapter(value = {"srcPath","circle","placeholder","error"}, requireAll = false)
    public static void srcPath(ImageView view, String srcPath, boolean circle , Drawable placeholder, Drawable error){
        if(TextUtils.isEmpty(srcPath)){
            if(placeholder!=null){
                view.setImageDrawable(placeholder);
            }
        }else {
            //设置图片圆角角度
            RoundedCorners roundedCorners= new RoundedCorners(12);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions options= RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

            RequestBuilder<Drawable> load = Glide.with(view).load(QiNiuUtil.getNetRealPath(srcPath)).apply(options);
            if(circle){
                load.circleCrop();
            }
            if(placeholder!=null){
                load.thumbnail(Glide.with(view).load(placeholder));
            }
            if (error!=null){
                load.error(error);
            }
            load.into(view);
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
