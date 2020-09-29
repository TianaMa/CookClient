package com.demo.cook.ui.interaction.comment.model;

import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.interaction.comment.model.data.Comment;
import com.demo.cook.ui.interaction.comment.model.data.CommentDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpCommentApi {

    @POST("comment/publish")
    Call<RtnResult> publishComment(
            @Body Comment comment
    );

    @GET("comment/queryCommentList")
    Call<RtnResult<List<CommentDetails>>> queryCommentList(
            @Query("targetId") String targetId,@Query("loginUserName") String loginUserName
    );


}
