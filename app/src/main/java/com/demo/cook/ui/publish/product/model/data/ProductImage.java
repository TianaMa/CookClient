package com.demo.cook.ui.publish.product.model.data;

public class ProductImage {
    private String productId;

    private String imagePath;

    private Integer orderIndex;

    public ProductImage(String productId, String imagePath, Integer orderIndex) {
        this.productId = productId;
        this.imagePath = imagePath;
        this.orderIndex = orderIndex;
    }

    public ProductImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}