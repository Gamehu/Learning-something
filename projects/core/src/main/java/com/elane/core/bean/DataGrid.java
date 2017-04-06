package com.elane.core.bean;

import java.util.ArrayList;
import java.util.List;

/***
 * 
 * @author hankin
 *
 */
public class DataGrid implements java.io.Serializable {
    /**
     * easyui datagrid 数据统计总数
     */
    private Long total = 0L;
    /**
     * easyui datagrid 数据list集合
     */
    private List<?> rows = new ArrayList<Object>();
    /**
     * eayui datagrid 数据合计行
     */
    private List<?> footer = new ArrayList<Object>();

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public List<?> getFooter() {
        return footer;
    }

    public void setFooter(List<?> footer) {
        this.footer = footer;
    }

}
