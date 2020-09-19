package com.demo.cook.ui.me.recipe;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import com.demo.baselib.design.BaseViewModel;

import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.publish.recipe.model.HttpRecipeApi;
import com.demo.cook.ui.publish.recipe.model.data.MyPublishRecipe;

import java.util.ArrayList;
import java.util.List;

public class MyPublishRecipeViewModel extends BaseViewModel {

    HttpRecipeApi httpRecipeApi = HttpConfig.getHttpServe(HttpRecipeApi.class);

    public MutableLiveData<List<MyPublishRecipe>> myPublishRecipeListData = new MutableLiveData(new ArrayList());

    public void queryMuPublishRecipe(){

        String username = Storage.getUser().getUsername();
        if(!TextUtils.isEmpty(username)){
            httpRecipeApi.queryMyPublish(username).enqueue(new HttpCallback<List<MyPublishRecipe>>() {
                @Override
                public void onSuccess(List<MyPublishRecipe> data) {
                    List<MyPublishRecipe> list = myPublishRecipeListData.getValue();
                    list.clear();
                    list.addAll(data);
                    myPublishRecipeListData.postValue(list);
                }
            });
        }


    }
}