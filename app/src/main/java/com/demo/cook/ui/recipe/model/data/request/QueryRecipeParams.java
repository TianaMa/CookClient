package com.demo.cook.ui.recipe.model.data.request;

import com.demo.cook.base.local.Storage;

import java.io.Serializable;

public class QueryRecipeParams implements Serializable {

    public enum Order{

        collect("collect"),praise("praise"),time("time");

        private String type;
        Order(String type){
            this.type=type;
        }

        public String getType(){
            return type;
        }

    }

    private int pageNum =1;
    private int pageSize =20;
    private String loginUserName = Storage.getUserInfo().getUsername();

    private String sortId;
    private String searchText;
    private String issuer;
    private String collector;
    private String order;

    public QueryRecipeParams() {
    }

    public QueryRecipeParams(String issuer) {
        this.issuer = issuer;
    }

    public QueryRecipeParams(Order order) {
        this.order = order.getType();
    }

    public QueryRecipeParams(String sortId,Order order) {
        this.sortId = sortId;
        this.order = order.getType();
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order.getType();
    }


}
