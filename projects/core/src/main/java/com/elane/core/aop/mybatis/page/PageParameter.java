package com.elane.core.aop.mybatis.page;

import java.io.Serializable;

import org.apache.ibatis.type.JdbcType;

/**
 * 分页参数类
 * 
 * @author Hankin
 */
@Deprecated
public class PageParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize = 10; // 每页显示条数
    private int currentPage;
    private int prePage;
    private int nextPage;
    private int totalPage;
    private int totalCount;
    private String orderBy = "";
    private String sort = "ASC";
    private String orderJdbcType = JdbcType.VARCHAR.name();

    public PageParameter() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.nextPage = currentPage + 1;
        this.prePage = currentPage - 1;
    }

    /**
     * 
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.nextPage = currentPage + 1;
        this.prePage = currentPage - 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.nextPage = currentPage + 1;
        this.prePage = currentPage - 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (pageSize == 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize
                : totalCount / pageSize + 1;
        this.totalCount = totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrderJdbcType() {
        return orderJdbcType;
    }

    public void setOrderJdbcType(String orderJdbcType) {
        this.orderJdbcType = orderJdbcType;
    }

}
