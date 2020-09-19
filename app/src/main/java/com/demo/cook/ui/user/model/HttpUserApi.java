package com.demo.cook.ui.user.model;


import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.user.model.data.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpUserApi {

    @POST("user/login")
    Call<RtnResult<User>> login(
            @Query("username") String username,
            @Query("password") String password
    );

    @POST("user/register")
    Call<RtnResult<User>> register(
            @Body User username
    );


    @POST("user/updateUserInfo")
    Call<RtnResult<User>> updateUserInfo(
            @Body User username
    );



}
