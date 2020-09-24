package com.demo.cook.ui.user.model.data;

public class UserInfo extends User{

    //作品数   菜谱数
    //被关注数（粉丝）
    //关注数

    private int countProduct;

    private int countRecipe;

    private int countBeCared;

    private int countSubscribe;

    private boolean subscribe;

    public int getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(int countProduct) {
        this.countProduct = countProduct;
    }

    public int getCountRecipe() {
        return countRecipe;
    }

    public void setCountRecipe(int countRecipe) {
        this.countRecipe = countRecipe;
    }

    public int getCountBeCared() {
        return countBeCared;
    }

    public void setCountBeCared(int countBeCared) {
        this.countBeCared = countBeCared;
    }

    public int getCountSubscribe() {
        return countSubscribe;
    }

    public void setCountSubscribe(int countSubscribe) {
        this.countSubscribe = countSubscribe;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }
}
