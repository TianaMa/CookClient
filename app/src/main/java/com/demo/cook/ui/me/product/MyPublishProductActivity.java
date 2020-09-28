package com.demo.cook.ui.me.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;

import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.product.fragment.ProductListFragment;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;

public class MyPublishProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_product);

        findViewById(R.id.ivMyProductBack).setOnClickListener(v -> super.onBackPressed());

        MutableLiveData<QueryProductParams> productParamsData= new MutableLiveData(new QueryProductParams(Storage.getUserInfo().getUsername()));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flMyProduct, ProductListFragment.newInstance(productParamsData));
        transaction.commit();
    }
}