package com.demo.cook.ui.product.data;

import androidx.databinding.BaseObservable;

import java.util.Date;

public class ProductDetails extends BaseObservable {

    private String productId;



    private String title;

    private String content;

    private String images;

    private String tagId;

    private Date createTime;



    private String issuer;

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



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }










    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
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
    }

    public boolean isPraised() {
        return praised;
    }

    public void setPraised(boolean praised) {
        this.praised = praised;
        notifyChange();
    }
}
