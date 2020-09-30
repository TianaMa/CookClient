package com.demo.cook.ui.interaction.comment.view;

import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.interaction.comment.model.HttpCommentApi;
import com.demo.cook.ui.interaction.comment.model.data.CommentDetails;
import com.demo.cook.ui.interaction.praise.HttpPraiseApi;
import com.demo.cook.utils.LoginVerifyUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentListViewModel extends BaseViewModel {

    private String targetId;

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    HttpPraiseApi praiseApi = HttpConfig.getHttpServe(HttpPraiseApi.class);
    HttpCommentApi commentApi = HttpConfig.getHttpServe(HttpCommentApi.class);
    public MutableLiveData<List<CommentDetails>> commentList = new MutableLiveData(new ArrayList());

    void queryCommentList(){
        commentApi.queryCommentList(targetId, Storage.getUserInfo().getUsername()).enqueue(new HttpCallback<List<CommentDetails>>() {
            @Override
            public void onSuccess(List<CommentDetails> data) {
                commentList.getValue().clear();
                for (CommentDetails details:data){
                    if (targetId.equals(details.getParentId())){
                        commentList.getValue().add(details);
                    }
                }
                for (CommentDetails comment:commentList.getValue()){
                    for (CommentDetails details:data){
                        if(details.getParentId().equals(comment.getCommentId())){
                            comment.getCommentList().add(details);
                        }
                    }
                }
                commentList.setValue(commentList.getValue());
            }
        });
    }

    public void clickPraise(CommentDetails commentDetails){
        LoginVerifyUtils.verifyAccount(() -> {
            if (!commentDetails.isPraised()){
                praiseApi.addPraise(Storage.getUserInfo().getUsername(), commentDetails.getCommentId()).enqueue(new HttpCallback(){
                    @Override
                    public void onSuccess(Object data) {
                        commentDetails.setCountPraise(commentDetails.getCountPraise()+1);
                        commentDetails.setPraised(true);
                    }
                });
            }else {
                praiseApi.cancelPraise(Storage.getUserInfo().getUsername(), commentDetails.getCommentId()).enqueue(new HttpCallback(){
                    @Override
                    public void onSuccess(Object data) {
                        commentDetails.setCountPraise(commentDetails.getCountPraise()-1);
                        commentDetails.setPraised(false);
                    }
                });
            }
        });

    }

    public void close(){
        finishActivity();
    }
}
