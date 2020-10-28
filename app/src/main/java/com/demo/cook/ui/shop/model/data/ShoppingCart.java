package com.demo.cook.ui.shop.model.data;

import androidx.databinding.BaseObservable;

public class ShoppingCart  extends BaseObservable {
    private String username;

    private String goodsId;

    private int buyCount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
        notifyChange();
    }
}