package com.demo.cook.ui.interaction.comment.model;

import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.interaction.comment.model.data.Comment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HttpCommentApi {

    @POST("praise/addPraise")
    Call<RtnResult> publishComment(
            @Body Comment comment
    );


}
