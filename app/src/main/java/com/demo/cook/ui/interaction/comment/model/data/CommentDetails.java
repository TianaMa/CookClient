package com.demo.cook.ui.interaction.comment.model.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDetails  extends Comment{

    private String commentId;

    private Date createTime;

    private String commentUsername;

    private String commentUserHeadImg;

    private String commentUserNickName;

    private String targetUsername;

    private String targetUserHeadImg;

    private String targetUserNickName;

    private int countPraise;

    private boolean praised;

    private List<CommentDetails> commentList = new ArrayList<>();


    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCommentUsername() {
        return commentUsername;
    }

    public void setCommentUsername(String commentUsername) {
        this.commentUsername = commentUsername;
    }

    public String getCommentUserHeadImg() {
        return commentUserHeadImg;
    }

    public void setCommentUserHeadImg(String commentUserHeadImg) {
        this.commentUserHeadImg = commentUserHeadImg;
    }

    public String getCommentUserNickName() {
        return commentUserNickName;
    }

    public void setCommentUserNickName(String commentUserNickName) {
        this.commentUserNickName = commentUserNickName;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getTargetUserHeadImg() {
        return targetUserHeadImg;
    }

    public void setTargetUserHeadImg(String targetUserHeadImg) {
        this.targetUserHeadImg = targetUserHeadImg;
    }

    public String getTargetUserNickName() {
        return targetUserNickName;
    }

    public void setTargetUserNickName(String targetUserNickName) {
        this.targetUserNickName = targetUserNickName;
    }

    public int getCountPraise() {
        return countPraise;
    }

    public void setCountPraise(int countPraise) {
        this.countPraise = countPraise;
        notifyChange();
    }

    public boolean isPraised() {
        return praised;
    }

    public void setPraised(boolean praised) {
        this.praised = praised;
        notifyChange();
    }

    public List<CommentDetails> getCommentList() {
        return commentList;
    }

}
