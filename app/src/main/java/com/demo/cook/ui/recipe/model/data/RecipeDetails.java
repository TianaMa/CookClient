package com.demo.cook.ui.recipe.model.data;


import java.util.Date;


//详情信息
public class RecipeDetails extends Recipe{

    private Date createTime;

    private String nickname;

    private String headImg;

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
