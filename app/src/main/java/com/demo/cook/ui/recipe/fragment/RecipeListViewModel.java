package com.demo.cook.ui.recipe.fragment;


import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.http.PageInfo;
import com.demo.cook.ui.recipe.model.HttpRecipeApi;
import com.demo.cook.ui.recipe.model.data.RecipeBrief;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;

import java.util.ArrayList;
import java.util.List;

public class RecipeListViewModel extends BaseViewModel {

    HttpRecipeApi recipeApi = HttpConfig.getHttpServe(HttpRecipeApi.class);


    MutableLiveData<Boolean> finishAndHaveMore = new MutableLiveData<>();//数据请求完成 是否有更多数据

    MutableLiveData<List<RecipeBrief>> recipeListData = new MutableLiveData();

    void getRecipeList(QueryRecipeParams recipeParams){

        recipeApi.queryRecipeList(recipeParams).enqueue(new HttpCallback<PageInfo<RecipeBrief>>() {
            @Override
            public void onSuccess(PageInfo<RecipeBrief> data) {
                List<RecipeBrief> listData = recipeListData.getValue();
                listData=listData==null?new ArrayList():listData;
                if(data.getPageNum()==1){
                    listData.clear();
                }
                listData.addAll(data.getList());
                recipeListData.setValue(listData);
                recipeParams.setPageSize(data.getNextPage());
                finishAndHaveMore.setValue(data.isIsLastPage());
            }

            @Override
            public void finallyCall() {
                super.finallyCall();
                finishAndHaveMore.setValue(true);
            }
        });

    }


}