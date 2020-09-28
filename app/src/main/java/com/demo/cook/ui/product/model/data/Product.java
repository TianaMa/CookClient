package com.demo.cook.ui.product.model.data;


import androidx.databinding.BaseObservable;

import com.demo.cook.base.local.Storage;

public class Product  extends BaseObservable {

    private String issuer = Storage.getUserInfo().getUsername();

    private String title;

    private String content;

    private String images;

    private String tagId;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
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


}