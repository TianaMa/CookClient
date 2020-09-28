package com.demo.cook.base.http;

import java.util.List;

public class PageInfo<T> {


    /**
     * total : 2
     * list : []
     * pageNum : 1
     * pageSize : 1
     * size : 1
     * startRow : 1
     * endRow : 1
     * pages : 2
     * prePage : 0
     * nextPage : 2
     * isFirstPage : true
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : true
     * navigatePages : 8
     * navigateFirstPage : 1
     * navigateLastPage : 2
     */

    private int total;//总条数
    private int pages;//总页数
    private int pageNum=1;//第几页
    private int pageSize=20;//每页几条
    private int startRow; //从第几条
    private int endRow; //到第几条
    private int prePage;
    private int nextPage =1;
    private boolean isFirstPage;
    private boolean isLastPage;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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


    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
