package com.demo.cook.ui.shop.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentGoodsListBinding;
import com.demo.cook.databinding.ItemLayoutGoodsBinding;
import com.demo.cook.ui.shop.details.GoodsDetailsActivity;
import com.demo.cook.ui.shop.model.data.Goods;
import com.demo.cook.ui.shop.model.data.request.QueryGoodsParams;

public class GoodsListFragment extends BaseFragment<FragmentGoodsListBinding,GoodsListViewModel> {

    private GoodsListFragment(){

    }

    public static GoodsListFragment newInstance() {
        return new GoodsListFragment();
    }

    private MutableLiveData<QueryGoodsParams>  ParamsData;

    public GoodsListFragment setParams(MutableLiveData<QueryGoodsParams> paramsMutableLiveData){
        this.ParamsData=paramsMutableLiveData;
        return this;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_goods_list;
    }

    @Override
    protected GoodsListViewModel getViewModel() {
        return new ViewModelProvider(this).get(GoodsListViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CmnRcvAdapter<Goods> goodsAdapter= new CmnRcvAdapter<Goods>(this,R.layout.item_layout_goods,mViewModel.goodsListData) {
            @Override
            public void convert(CmnViewHolder holder, Goods goods, int position) {
                ItemLayoutGoodsBinding binding = DataBindingUtil.bind(holder.itemView);
                binding.setGoods(goods);
                holder.itemView.setOnClickListener(v -> GoodsDetailsActivity.actionStart(v.getContext(),goods.getGoodsId()));
            }
        };
        mDataBinding.rcvGoodsList.setAdapter(goodsAdapter);

        mDataBinding.rflGoodsList.setEnableRefresh(false);
        mDataBinding.rflGoodsList.setOnLoadMoreListener(refreshLayout -> mViewModel.getGoodsList(ParamsData.getValue()));

        mViewModel.finishAndHaveMore.observe(getViewLifecycleOwner(), aBoolean -> {
            mDataBinding.rflGoodsList.setNoMoreData(aBoolean);
            mDataBinding.rflGoodsList.closeHeaderOrFooter();
        });

        ParamsData.observe(getViewLifecycleOwner(), productParams -> mViewModel.getGoodsList(ParamsData.getValue()));


    }
}