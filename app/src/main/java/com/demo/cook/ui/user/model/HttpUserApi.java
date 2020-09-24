package com.demo.cook.ui.user.model;


import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.user.model.data.Register;
import com.demo.cook.ui.user.model.data.User;
import com.demo.cook.ui.user.model.data.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpUserApi {

    @POST("user/login")
    Call<RtnResult<UserInfo>> login(
            @Query("username") String username,
            @Query("password") String password
    );

    @POST("user/register")
    Call<RtnResult<UserInfo>> register(
            @Body Register register
    );


    @POST("user/updateUserInfo")
    Call<RtnResult<UserInfo>> updateUserInfo(
            @Body User user
    );

    @POST("user/recommend")
    Call<RtnResult<List<UserInfo>>> getRecommendFriends(
            @Query("username") String username
    );

    @POST("user/mySubscribe")
    Call<RtnResult<PageInfo<UserInfo>>> getMySubscribeFriends(
            @Query("username") String username,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );





}
