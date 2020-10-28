package com.demo.cook.ui.shop.model.data;


import java.math.BigDecimal;

public class ShoppingCartDetails extends ShoppingCart {

    private String goodsName;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private String cover;

    private String goodsDescribe;

    private boolean isCheck;

    public ShoppingCartDetails() {
    }
    public ShoppingCartDetails(Goods goods) {
        this.setGoodsId(goods.getGoodsId());
        this.goodsName= goods.getGoodsName();
        this.price = goods.getPrice();
        this.cover = goods.getCover();
        this.goodsDescribe= goods.getGoodsDescribe();
    }


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        notifyChange();
    }
}