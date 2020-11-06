package com.demo.cook.ui.interaction.comment.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityCommentListBinding;
import com.demo.cook.databinding.ItemLayoutCommentBinding;
import com.demo.cook.databinding.ItemLayoutCommentReplyBinding;
import com.demo.cook.ui.interaction.comment.model.data.Comment;
import com.demo.cook.ui.interaction.comment.model.data.CommentDetails;
import com.demo.cook.utils.view.SoftKeyBoardListener;

import java.util.ArrayList;
import java.util.List;


public class CommentListActivity extends BaseActivity<ActivityCommentListBinding,CommentListViewModel> {


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_comment_list;
    }

    @Override
    protected CommentListViewModel getViewModel() {
        return new ViewModelProvider(this).get(CommentListViewModel.class);
    }


    static CommentCallback commentCallback;

    private static final String EXTRA_TARGET_ID="targetId";
    public static void actionStart(Context context,String targetId,CommentCallback commentCallback){
        Intent intent = new Intent(context,CommentListActivity.class);
        intent.putExtra(EXTRA_TARGET_ID,targetId);
        CommentListActivity.commentCallback = commentCallback;
        context.startActivity(intent);
    }

    public static void actionStart(Context context,String targetId){
        Intent intent = new Intent(context,CommentListActivity.class);
        intent.putExtra(EXTRA_TARGET_ID,targetId);
        CommentListActivity.commentCallback =null;
        context.startActivity(intent);
    }

    String targetId;
    CommentSendDialog commentSendDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetId= getIntent().getStringExtra(EXTRA_TARGET_ID);
        mViewModel.setTargetId(targetId);
        mDataBinding.setMViewModel(mViewModel);

        mDataBinding.llCommentListSend.setOnClickListener(v -> {
            Comment comment = new Comment(targetId,targetId,targetId);
            commentSendDialog =new CommentSendDialog(this, comment, (comment1) -> {
                mViewModel.queryCommentList();
            });
            commentSendDialog.show();
        });

        mDataBinding.ivCloseCommentList.setOnClickListener(v -> onBackPressed());

        SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(this);
        //软键盘状态监听
        softKeyBoardListener.setListener(show -> {
            if(!show&&commentSendDialog!=null&&commentSendDialog.isShowing()){
                commentSendDialog.dismiss();
            }
        });


        mDataBinding.rcvCommentList.setAdapter(new CmnRcvAdapter<CommentDetails>(this,R.layout.item_layout_comment,mViewModel.commentList) {
            @Override
            public void convert(CmnViewHolder holder, CommentDetails commentDetails, int position) {
                ItemLayoutCommentBinding commentBinding = DataBindingUtil.bind(holder.itemView);
                commentBinding.setComment(commentDetails);
                commentBinding.setMViewModel(mViewModel);

                holder.itemView.setOnClickListener(v -> {
                    Comment comment = new Comment(commentDetails.getTargetId(),commentDetails.getCommentId(),commentDetails.getCommentId());
                    commentSendDialog=new CommentSendDialog(v.getContext(), comment, (comment1) -> {
                        mViewModel.queryCommentList();
                    });
                    commentSendDialog.show();
                });
                commentBinding.rcvCommentReply.setAdapter(new CmnRcvAdapter<CommentDetails>(R.layout.item_layout_comment_reply,commentDetails.getCommentList()){
                    @Override
                    public void convert(CmnViewHolder holder, CommentDetails replyDetails, int position) {
                        ItemLayoutCommentReplyBinding replyBinding = DataBindingUtil.bind(holder.itemView);
                        replyBinding.setComment(replyDetails);
                        replyBinding.setMViewModel(mViewModel);
                    }
                });
            }
        });

        mViewModel.queryCommentList();
    }

    @Override
    public void onBackPressed() {
        if(commentCallback!=null){
            List<Comment> commentList = new ArrayList<>();
            commentList.addAll(mViewModel.originalCommentList);
            commentCallback.callback(commentList);
        }
        super.onBackPressed();
    }

    public interface CommentCallback{
        void callback(List<Comment> commentList);
    }


}