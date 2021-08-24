package com.cqupt.master_helper.utils;

import java.util.List;

public class Page<T> {

    /**
     * 所有数据
     */
    private List<T> allData;
    /**
     * 每页条目
     */
    private int perPage = 10;
    /**
     * 当前页
     */
    private int currentPage = 1;
    /**
     * 总页码
     */
    private int pageNum = 1;
    /**
     * 子数据
     */
    private List<T> childData;
    /**
     * 总共条目
     */
    private int allNum;

    /**
     * 构造函数
     *
     * @param datas   数据集合
     * @param perPage 每页条目
     */
    public Page(List<T> datas, int perPage) {
        this.allData = datas;
        if (perPage > 0) {
            this.perPage = perPage;
        }
        //如果数据大于10条
        if (allData != null && allData.size() > perPage) {
            childData = allData.subList(0, perPage - 1);
        }
        allNum = allData.size();
        //如果总数能除端perPage，页数救赎余数，否则+1
        pageNum = allNum % perPage == 0 ? (allNum / perPage) : (allNum / perPage + 1);
    }

    public int getCount() {
        return this.allNum;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPerPage() {
        return perPage;
    }

    /**
     * 页面跳转
     *
     * @param n 去第n页
     */
    public void gotoPage(int n) {
        currentPage = n > pageNum ? pageNum : (Math.max(n, 1));
    }

    /**
     * 是否有下一页
     *
     * @return 是/否
     */
    public boolean hasNextPage() {
        return currentPage < pageNum;
    }

    /**
     * 是否有前一页
     *
     * @return 是/否
     */
    public boolean hasPrePage() {
        return currentPage > 1;
    }

    /**
     * 第一页
     */
    public void headPage() {
        currentPage = 1;
    }

    /**
     * 最后一页
     */
    public void lastPage() {
        currentPage = pageNum;
    }

    /**
     * 下一页
     */
    public void nextPage() {
        currentPage = hasNextPage() ? currentPage + 1 : pageNum;
    }

    /**
     * 前一页
     */
    public void prePage() {
        currentPage = hasPrePage() ? currentPage - 1 : 1;
    }

    /**
     * 设置每页条目
     *
     * @param perPage 每页条目
     */
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    /**
     * 获得当前数据
     *
     * @return
     */
    public List<T> currentList() {
        if (currentPage == 1) {
            childData = allData.subList(0, perPage);
        } else if (currentPage == pageNum) {
            childData = allData.subList(perPage * (pageNum - 1), allNum);
        } else {
            childData = allData.subList(perPage * (currentPage - 1), perPage * currentPage);
        }
        return childData;
    }

    /**
     * 设置当前页
     *
     * @param currentPage 当前页
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
