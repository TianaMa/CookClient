package com.demo.cook.ui.me;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.demo.baselib.base.BaseContext;
import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.ActivityMyInfoBinding;
import com.demo.cook.ui.user.login.LoginActivity;
import com.demo.cook.utils.upload.UpLoadUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyInfoActivity extends BaseActivity<ActivityMyInfoBinding,MyInfoViewModel> {



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_my_info;
    }

    @Override
    protected MyInfoViewModel getViewModel() {
        return new ViewModelProvider(this).get(MyInfoViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding.setMViewModel(mViewModel);



        mDataBinding.ivMyInfoHead.setOnClickListener(
                v->UpLoadUtils.upLoadSingleImage(
                        this,
                        QiNiuUtil.Prefix.IMAGE_HEAD,
                        path -> {
                            mViewModel.user.getValue().setHeadImg(path.get(0));
                            mViewModel.user.postValue(mViewModel.user.getValue());
                        })
        );
        mDataBinding.tvMyInfoGender.setOnClickListener(v->{
            List<String> options1Items=new ArrayList(){{ add(getString(R.string.text_man));add(getString(R.string.text_woman)); }};
            //条件选择器
            OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v1) -> {
                //返回的分别是三个级别的选中位置
                mDataBinding.tvMyInfoGender.setText(options1Items.get(options1));
                mViewModel.user.getValue().setGender(options1Items.get(options1));
            }).build();
            pvOptions.setPicker(options1Items, null, null);
            pvOptions.show();
        });

        mDataBinding.tvMyInfoBirthday.setOnClickListener(v->{
            Calendar startDate = Calendar.getInstance();
            startDate.set(1900,0,1);
            //时间选择器
            TimePickerView pvTime = new TimePickerBuilder(this, (date, v12) -> {
                String birthday = new SimpleDateFormat("yyyy-MM-dd").format(date);
                mDataBinding.tvMyInfoBirthday.setText(birthday);
                mViewModel.user.getValue().setBirthday(birthday);
            }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                    .setSubmitText(getString(R.string.text_confirm))//确认按钮文字
                    .setCancelText(getString(R.string.text_cancel))
                    .setRangDate(startDate,Calendar.getInstance())//起始终止年月日设定
                    .setLabel("Y","M","D","H","m","s")//默认设置为年月日时分秒
                    .build();

            pvTime.show();

        });
        mDataBinding.tvMyInfoProfession.setOnClickListener(v->{

        });

        mDataBinding.tvMyInfoHometown.setOnClickListener(v->{

        });
        mDataBinding.tvMyInfoAddress.setOnClickListener(v->{

        });

        mDataBinding.btMyInfoLogout.setOnClickListener(v -> {
            Storage.setUserInfo(null);
            Intent intentLogin = new  Intent(BaseContext.getInstance().getTopActivity(), LoginActivity.class);
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseContext.getInstance().getTopActivity().startActivity(intentLogin);
        });



    }
}