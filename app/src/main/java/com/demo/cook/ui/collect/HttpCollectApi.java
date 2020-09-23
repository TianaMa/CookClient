package com.demo.cook.ui.collect;

import com.demo.cook.base.http.RtnResult;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpCollectApi {

    @POST("collect/addCollect")
    Call<RtnResult> addCollect(
            @Query("username") String username,
            @Query("targetId") String targetId
    );

    @POST("collect/cancelCollect")
    Call<RtnResult> cancelCollect(
            @Query("username") String username,
            @Query("targetId") String targetId
    );
}
