package com.demo.cook.ui.interaction.comment.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.basecode.widget.BaseBottomDialog;
import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.databinding.ViewDialogCommentPublishBinding;
import com.demo.cook.ui.interaction.comment.model.HttpCommentApi;
import com.demo.cook.ui.interaction.comment.model.data.Comment;
import com.demo.cook.utils.LoginVerifyUtils;

public class CommentSendDialog extends BaseBottomDialog {

    public interface CommentCallback{
        void commentSuccess(Comment comment);
    }

    HttpCommentApi commentApi = HttpConfig.getHttpServe(HttpCommentApi.class);

    private Comment comment;
    private CommentCallback callback;

    public CommentSendDialog(Context context, @NonNull Comment comment, CommentCallback callback) {
        super(context);
        this.comment= comment;
        this.callback=callback;
    }

    private String hintText;
    public CommentSendDialog setHint(String hintText){
        this.hintText=hintText;
        return this;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_dialog_comment_publish;
    }

    ViewDialogCommentPublishBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.bind(childView);
        if(!TextUtils.isEmpty(hintText)){
            mBinding.tvCommentSend.setHint(hintText);
        }

        mBinding.tvCommentSend.setOnClickListener(v -> {

            LoginVerifyUtils.verifyAccount(()->{
                String commentContent = mBinding.etCommentContent.getText().toString();
                if(TextUtils.isEmpty(commentContent)){
                    ToastyUtils.showInfo(R.string.text_content_not_empty);return;
                }

                comment.setContent(commentContent);

                commentApi.publishComment(comment).enqueue(new HttpCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        ToastyUtils.show(R.string.text_comment_success);
                        dismiss();
                        if (callback!=null){
                            callback.commentSuccess(comment);
                        }
                    }
                });
            });

        });

    }
}
