package com.demo.cook.ui.publish.product.model.data;

public class ProductTagRelation {
    private String productId;

    private String productTagId;

    public ProductTagRelation(String productId, String productTagId) {
        this.productId = productId;
        this.productTagId = productTagId;
    }

    public ProductTagRelation() {
        super();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductTagId() {
        return productTagId;
    }

    public void setProductTagId(String productTagId) {
        this.productTagId = productTagId == null ? null : productTagId.trim();
    }
}