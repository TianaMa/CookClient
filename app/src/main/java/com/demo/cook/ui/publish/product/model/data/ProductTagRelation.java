package com.demo.cook.ui.publish.product.model.data;

public class ProductTagRelation {
    private String productId;

    private String productTypeId;

    public ProductTagRelation(String productId, String productTypeId) {
        this.productId = productId;
        this.productTypeId = productTypeId;
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

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId == null ? null : productTypeId.trim();
    }
}