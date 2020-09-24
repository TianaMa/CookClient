package com.demo.cook.ui.subscribe;

import com.demo.cook.base.http.RtnResult;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpSubscribeApi {

    @POST("subscribe/addSubscribe")
    Call<RtnResult> addSubscribe(
            @Query("username") String username,
            @Query("targetUser") String targetUser
    );

    @POST("subscribe/cancelSubscribe")
    Call<RtnResult> cancelSubscribe(
            @Query("username") String username,
            @Query("targetUser") String targetUser
    );

}
