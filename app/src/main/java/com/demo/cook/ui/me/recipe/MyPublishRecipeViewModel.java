package com.demo.cook.ui.me.recipe;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import com.demo.baselib.design.BaseViewModel;

import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.recipe.model.HttpRecipeApi;
import com.demo.cook.ui.recipe.model.data.RecipeBrief;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;

import java.util.ArrayList;
import java.util.List;

public class MyPublishRecipeViewModel extends BaseViewModel {

    HttpRecipeApi httpRecipeApi = HttpConfig.getHttpServe(HttpRecipeApi.class);

    public MutableLiveData<List<RecipeBrief>> myPublishRecipeListData = new MutableLiveData(new ArrayList());

    public void queryMuPublishRecipe(){

        String username = Storage.getUserInfo().getUsername();
        if(!TextUtils.isEmpty(username)){
            QueryRecipeParams params = new QueryRecipeParams(username);
            params.setOrder(QueryRecipeParams.Order.time);
            httpRecipeApi.queryRecipeList(params).enqueue(new HttpCallback<PageInfo<RecipeBrief>>() {
                @Override
                public void onSuccess(PageInfo<RecipeBrief> data) {
                    myPublishRecipeListData.setValue(data.getList());
                }
            });
        }


    }
}