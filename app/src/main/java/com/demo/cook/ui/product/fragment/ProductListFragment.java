package com.demo.cook.ui.product.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.FragmentProductListBinding;
import com.demo.cook.databinding.ItemLayoutCommentBriefnessBinding;
import com.demo.cook.databinding.ItemLayoutProductBinding;
import com.demo.cook.databinding.ItemLayoutProductImageBinding;
import com.demo.cook.ui.interaction.comment.model.data.Comment;
import com.demo.cook.ui.interaction.comment.view.CommentListActivity;
import com.demo.cook.ui.interaction.comment.view.CommentSendDialog;
import com.demo.cook.ui.product.model.data.ProductDetails;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;
import com.demo.cook.utils.LoginVerifyUtils;
import com.demo.cook.utils.view.SoftKeyBoardListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Arrays;
import java.util.List;

public class ProductListFragment extends BaseFragment<FragmentProductListBinding, ProductListViewModel> {

    private ProductListFragment(){

    }

    private MutableLiveData<QueryProductParams> productParamsData ;
    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    public ProductListFragment setParams(MutableLiveData<QueryProductParams> productParams){
        this.productParamsData = productParams;
        return this;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_product_list;
    }

    @Override
    protected ProductListViewModel getViewModel() {
        return new ViewModelProvider(this).get(ProductListViewModel.class);
    }

    CommentSendDialog commentSendDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CmnRcvAdapter<ProductDetails> adapter = new CmnRcvAdapter<ProductDetails>(this,R.layout.item_layout_product,mViewModel.productListData) {
            @Override
            public void convert(CmnViewHolder holder, ProductDetails productDetails, int position) {
                ItemLayoutProductBinding productBinding = DataBindingUtil.bind(holder.itemView);
                productBinding.setProductDetail(productDetails);
                productBinding.setUser(Storage.getUserInfo());

                productBinding.tvPraise.setOnClickListener(v -> {
                    LoginVerifyUtils.verifyAccount(() -> {
                        mViewModel.clickPraise(productDetails);
                    });
                });

                productBinding.tvCollect.setOnClickListener(v -> {
                    LoginVerifyUtils.verifyAccount(() -> {
                        mViewModel.clickCollect(productDetails);
                    });
                });

                productBinding.tvWriteComment.setOnClickListener(v -> {
                    Comment comment = new Comment(productDetails.getProductId(),productDetails.getProductId(),productDetails.getProductId());
                    commentSendDialog=new CommentSendDialog(getContext(), comment, (mComment) -> {
                        productDetails.setCountComment(productDetails.getCountComment()+1);
                        productDetails.getCommentList().add(0,mComment);
                        productBinding.rcvCommentListProduct.getAdapter().notifyDataSetChanged();
                    });
                    commentSendDialog.show();
                });

                productBinding.tvProductComment.setOnClickListener(v -> {
                    if(productDetails.getCountComment()>0){
                        CommentListActivity.actionStart(getContext(), productDetails.getProductId(), commentList -> {
                            productDetails.setCountComment(commentList.size());
                            productDetails.getCommentList().clear();
                            productDetails.getCommentList().addAll(commentList);
                            productBinding.rcvCommentListProduct.getAdapter().notifyDataSetChanged();
                        });
                    }else {
                        Comment comment = new Comment(productDetails.getProductId(),productDetails.getProductId(),productDetails.getProductId());
                        commentSendDialog=new CommentSendDialog(getContext(), comment, (mComment) -> {
                            productDetails.setCountComment(productDetails.getCountComment()+1);
                            productDetails.getCommentList().add(0,mComment);
                            productBinding.rcvCommentListProduct.getAdapter().notifyDataSetChanged();
                        });
                        commentSendDialog.show();
                    }
                });

                List<String> imageList = Arrays.asList(productDetails.getImages().split(","));

                productBinding.rcvProductImageList.setAdapter(new CmnRcvAdapter<String>(R.layout.item_layout_product_image,imageList) {
                    @Override
                    public void convert(CmnViewHolder holder, String s, int position) {
                        ItemLayoutProductImageBinding imageBinding = DataBindingUtil.bind(holder.itemView);
                        imageBinding.setImagePath(s);
                    }
                });

                productBinding.rcvCommentListProduct.setAdapter(new CmnRcvAdapter<Comment>(R.layout.item_layout_comment_briefness,productDetails.getCommentList()) {
                    @Override
                    public void convert(CmnViewHolder holder, Comment comment, int position) {
                        ItemLayoutCommentBriefnessBinding commentBinding =DataBindingUtil.bind(holder.itemView);
                        commentBinding.setComment(comment);
                        holder.itemView.setOnClickListener(v -> {
                            CommentListActivity.actionStart(getContext(), productDetails.getProductId(), commentList -> {
                                productDetails.setCountComment(commentList.size());
                                productDetails.getCommentList().clear();
                                productDetails.getCommentList().addAll(commentList);
                                productBinding.rcvCommentListProduct.getAdapter().notifyDataSetChanged();
                            });
                        });
                    }

                    @Override
                    public int getItemCount() {
                        return super.getItemCount()>3?3:super.getItemCount();
                    }
                });

                productBinding.llCommentProduct.setOnClickListener(v -> {
                    CommentListActivity.actionStart(getContext(), productDetails.getProductId(), commentList -> {
                        productDetails.setCountComment(commentList.size());
                        productDetails.getCommentList().clear();
                        productDetails.getCommentList().addAll(commentList);
                        productBinding.rcvCommentListProduct.getAdapter().notifyDataSetChanged();
                    });
                });
            }
        };
        //空数据视图逻辑
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout_no_data,null);
        adapter.setEmptyView(emptyView);

        mDataBinding.rcvProductList.setAdapter(adapter);

        SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(this.getActivity());
        //软键盘状态监听
        softKeyBoardListener.setListener(show -> {
            if(!show&&commentSendDialog!=null&&commentSendDialog.isShowing()){
                commentSendDialog.dismiss();
            }
        });

        mDataBinding.rflProductList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getProductList(productParamsData.getValue());
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                productParamsData.getValue().setPageNum(1);
                mViewModel.getProductList(productParamsData.getValue());
            }
        });

        mViewModel.finishAndHaveMore.observe(getViewLifecycleOwner(), aBoolean -> {
            mDataBinding.rflProductList.setNoMoreData(aBoolean);
            mDataBinding.rflProductList.closeHeaderOrFooter();
        });

        productParamsData.observe(getViewLifecycleOwner(), productParams -> {
            mViewModel.getProductList(productParamsData.getValue());
        });

    }

}