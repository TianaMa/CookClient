package com.demo.cook.ui.interaction.comment.view;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;

import androidx.databinding.DataBindingUtil;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.basecode.widget.BaseBottomDialog;
import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.ItemLayoutCommentBinding;
import com.demo.cook.databinding.ItemLayoutCommentReplyBinding;
import com.demo.cook.databinding.ViewDialogCommentLsitBinding;
import com.demo.cook.ui.interaction.comment.model.HttpCommentApi;
import com.demo.cook.ui.interaction.comment.model.data.Comment;
import com.demo.cook.ui.interaction.comment.model.data.CommentDetails;
import com.demo.cook.ui.interaction.praise.HttpPraiseApi;

import java.util.ArrayList;
import java.util.List;

public class CommentListDialog  extends BaseBottomDialog {

    HttpPraiseApi praiseApi = HttpConfig.getHttpServe(HttpPraiseApi.class);

    String targetId;

    public CommentListDialog(Context context,String targetId) {
        super(context);
        this.targetId = targetId;
        setHeightMode(HeightMode.FIXED);
        setHeightPCT(0.8F);
    }

    ViewDialogCommentLsitBinding mBinding;
    @Override
    protected int getLayoutRes() {
        return R.layout.view_dialog_comment_lsit;
    }

    HttpCommentApi commentApi = HttpConfig.getHttpServe(HttpCommentApi.class);
    List<CommentDetails> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除半透明阴影
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.dimAmount = 0.0f;
        getWindow().setAttributes(layoutParams);

        mBinding= DataBindingUtil.bind(childView);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        mBinding.ivCommentListClose.setOnClickListener(v -> dismiss());
        mBinding.llCommentListSend.setOnClickListener(v -> {
            Comment comment = new Comment(targetId,targetId,targetId);
            new CommentSendDialog(getContext(), comment, () -> {
                queryCommentList();
            }).show();
        });
        mBinding.rcvCommentList.setAdapter(new CmnRcvAdapter<CommentDetails>(R.layout.item_layout_comment,commentList) {
            @Override
            public void convert(CmnViewHolder holder, CommentDetails commentDetails, int position) {
                ItemLayoutCommentBinding commentBinding = DataBindingUtil.bind(holder.itemView);
                commentBinding.setComment(commentDetails);
                commentBinding.tvCommentPraise.setOnClickListener(v -> {
                    if (((CheckBox) v).isChecked()){
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
                commentBinding.llReply.setOnClickListener(v -> {
                    ToastyUtils.show("查看详情");
                });
                holder.itemView.setOnClickListener(v -> {
                    Comment comment = new Comment(commentDetails.getTargetId(),commentDetails.getCommentId(),commentDetails.getCommentId());
                    new CommentSendDialog(getContext(), comment, () -> {
                        queryCommentList();
                    }).setHint(getContext().getString(R.string.text_reply)+commentDetails.getCommentUserNickName()).show();
                });
                commentBinding.rcvCommentReply.setAdapter(new CmnRcvAdapter<CommentDetails>(R.layout.item_layout_comment_reply,commentDetails.getCommentList()){
                    @Override
                    public int getItemCount() {
                        return super.getItemCount();
                    }

                    @Override
                    public void convert(CmnViewHolder holder, CommentDetails replyDetails, int position) {
                        ItemLayoutCommentReplyBinding replyBinding = DataBindingUtil.bind(holder.itemView);
                        replyBinding.setComment(replyDetails);
                        replyBinding.tvReplyPraise.setOnClickListener(v -> {
                            if (((CheckBox) v).isChecked()){
                                praiseApi.addPraise(Storage.getUserInfo().getUsername(), replyDetails.getCommentId()).enqueue(new HttpCallback(){
                                    @Override
                                    public void onSuccess(Object data) {
                                        replyDetails.setCountPraise(replyDetails.getCountPraise()+1);
                                        replyDetails.setPraised(true);
                                    }
                                });
                            }else {
                                praiseApi.cancelPraise(Storage.getUserInfo().getUsername(), replyDetails.getCommentId()).enqueue(new HttpCallback(){
                                    @Override
                                    public void onSuccess(Object data) {
                                        replyDetails.setCountPraise(replyDetails.getCountPraise()-1);
                                        replyDetails.setPraised(false);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        queryCommentList();



    }


    private void queryCommentList(){
        commentApi.queryCommentList(targetId, Storage.getUserInfo().getUsername()).enqueue(new HttpCallback<List<CommentDetails>>() {
            @Override
            public void onSuccess(List<CommentDetails> data) {
                commentList.clear();
                for (CommentDetails details:data){
                    if (targetId.equals(details.getParentId())){
                        commentList.add(details);
                    }
                }
                for (CommentDetails comment:commentList){
                    for (CommentDetails details:data){
                        if(details.getParentId().equals(comment.getCommentId())){
                            comment.getCommentList().add(details);
                        }
                    }
                }
                mBinding.tvCommentCount.setText(data.size()+getContext().getString(R.string.text_comment_size));
                mBinding.rcvCommentList.getAdapter().notifyDataSetChanged();
            }
        });
    }
}
