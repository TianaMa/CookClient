package com.demo.cook.ui.interaction.comment.model.data;

import com.demo.cook.base.local.Storage;

public class Comment {

    //评论对象： 作品 或 菜谱
    private String targetId;

    //parentId 最多分两级。
    private String parentId;

    //评论内容
    private String content;

    //回复的是谁的评论
    private String replyId;

    //评论人
    private String commentUser = Storage.getUserInfo().getUsername();


    public Comment() {
        super();
    }


    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId == null ? null : replyId.trim();
    }


    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser == null ? null : commentUser.trim();
    }
}