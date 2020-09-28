package com.demo.cook.ui.product.model.data;


import java.util.Date;

public class ProductDetails extends Product  {

    private String productId;

    private Date createTime;

    private String tagName;

    private String nickname;

    private String headImg;

    private int countPraise;

    private int countCollect;

    private int countComment;

    private boolean collected;

    private boolean praised;




    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

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


    public int getCountPraise() {
        return countPraise;
    }

    public void setCountPraise(int countPraise) {
        this.countPraise = countPraise;
        notifyChange();
    }

    public int getCountCollect() {
        return countCollect;
    }

    public void setCountCollect(int countCollect) {
        this.countCollect = countCollect;
        notifyChange();
    }


    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
        notifyChange();
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
        notifyChange();
    }

    public boolean isPraised() {
        return praised;
    }

    public void setPraised(boolean praised) {
        this.praised = praised;
        notifyChange();
    }
}
