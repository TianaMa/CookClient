package com.demo.cook.ui.publish.recipe;

import android.text.Editable;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import com.demo.baselib.design.BaseViewModel;

public class PublishRecipeNameViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> canNext=new MutableLiveData(false);

    public String recipeName="";

    public void afterTextChanged(Editable s) {
        canNext.postValue((!TextUtils.isEmpty(s.toString())));
    }



}
