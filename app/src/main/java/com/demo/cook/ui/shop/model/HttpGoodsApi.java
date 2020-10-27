package com.demo.cook.ui.shop.model;

import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.shop.model.data.Goods;
import com.demo.cook.ui.shop.model.data.ShoppingCartDetails;
import com.demo.cook.ui.shop.model.data.request.QueryGoodsParams;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpGoodsApi {

    @POST("goods/queryGoodsList")
    Call<RtnResult<PageInfo<Goods>>> queryGoodsList(
            @Body QueryGoodsParams params
    );


    @GET("goods/queryGoodsDetails")
    Call<RtnResult<Goods>> queryGoodsDetails(
            @Query("goodsId") String goodsId
    );

    @POST("shoppingCart/add")
    Call<RtnResult<Integer>> addShoppingCart(
            @Query("username") String username,
            @Query("goodsId") String goodsId
    );

    @GET("shoppingCart/queryCount")
    Call<RtnResult<Integer>> queryCount(
            @Query("username") String username
    );

    @GET("shoppingCart/query")
    Call<RtnResult<List<ShoppingCartDetails>>> queryShoppingCart(
            @Query("username") String username
    );





}
