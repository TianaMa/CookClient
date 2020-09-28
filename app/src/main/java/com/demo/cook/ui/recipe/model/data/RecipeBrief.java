package com.demo.cook.ui.recipe.model.data;

import java.util.Date;

public class RecipeBrief {


    private String nickname;

    private String headImg;

    private int collectedCount;

    private String recipeId;

    private String recipeName;

    private String cover;

    private Date createTime;

    private int countPraise;

    private int countCollect;

    private int countComment;

    private boolean collected;

    private boolean praised;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getCollectedCount() {
        return collectedCount;
    }

    public void setCollectedCount(int collectedCount) {
        this.collectedCount = collectedCount;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCountPraise() {
        return countPraise;
    }

    public void setCountPraise(int countPraise) {
        this.countPraise = countPraise;
    }

    public int getCountCollect() {
        return countCollect;
    }

    public void setCountCollect(int countCollect) {
        this.countCollect = countCollect;
    }

    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public boolean isPraised() {
        return praised;
    }

    public void setPraised(boolean praised) {
        this.praised = praised;
    }
}
