package com.demo.cook.ui.shop.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;

import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.ActivitySearchGoodsBinding;
import com.demo.cook.ui.recipe.fragment.RecipeListFragment;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;
import com.demo.cook.ui.shop.fragment.GoodsListFragment;
import com.demo.cook.ui.shop.model.data.request.QueryGoodsParams;

public class SearchGoodsActivity extends AppCompatActivity {

    ActivitySearchGoodsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_search_goods);

        MutableLiveData<QueryGoodsParams> paramsData = new MutableLiveData(new QueryGoodsParams());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flSearchGoods, GoodsListFragment.newInstance().setParams(paramsData))
                .commit();

        mBinding.ivSearchBack.setOnClickListener(v -> super.onBackPressed());

        mBinding.tvSearchGoods.setOnClickListener(v -> {
            QueryGoodsParams params = paramsData.getValue();
            params.setPageNum(1);
            params.setSearchText(mBinding.etSearchGoods.getText().toString());
            paramsData.postValue(params);
        });

    }
}