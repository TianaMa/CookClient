package com.demo.cook.ui.publish.product.model.data;

import com.demo.cook.base.local.Storage;

import java.util.Date;
import java.util.List;

public class Product {

    private String productId;

    private String issuer = Storage.getUser().getUsername();

    private String title;

    private String content;

    private Date createTime;

    private List<ProductImage> productImageList;

    private List<String> productTagList;


    public Product(String productId, String title, String content, Date createTime) {
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }

    public Product() {
        super();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getIssuer() {
        return issuer;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }

    public List<String> getProductTagList() {
        return productTagList;
    }

    public void setProductTagList(List<String> productTagList) {
        this.productTagList = productTagList;
    }

}