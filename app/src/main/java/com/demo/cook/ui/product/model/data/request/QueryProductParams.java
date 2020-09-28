package com.demo.cook.ui.product.model.data.request;

import com.demo.cook.base.local.Storage;

public class QueryProductParams {

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

    private String tagId;
    private String searchText;
    private String issuer;
    private String collector;
    private String order;

    public QueryProductParams() {
    }

    public QueryProductParams(String issuer) {
        this.issuer = issuer;
    }

    public QueryProductParams(Order order) {
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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
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
