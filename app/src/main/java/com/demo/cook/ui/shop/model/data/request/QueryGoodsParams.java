package com.demo.cook.ui.shop.model.data.request;


public class QueryGoodsParams {

    public QueryGoodsParams() {
    }

    public QueryGoodsParams(String sortId) {
        this.sortId = sortId;
    }

    private int pageNum=1;
    private int pageSize=20;

    private String sortId;
    private String searchText;


    public int getPageNum() {
        return pageNum>0?pageNum:1;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize>0?pageSize:20;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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



}
