package com.demo.cook.ui.interaction.praise;

import com.demo.cook.base.http.RtnResult;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpPraiseApi {

    @POST("praise/addPraise")
    Call<RtnResult> addPraise(
            @Query("username") String username,
            @Query("targetId") String targetId
    );

    @POST("praise/cancelPraise")
    Call<RtnResult> cancelPraise(
            @Query("username") String username,
            @Query("targetId") String targetId
    );
}
