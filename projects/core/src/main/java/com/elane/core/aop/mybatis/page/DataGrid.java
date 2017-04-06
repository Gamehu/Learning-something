package com.elane.core.aop.mybatis.page;

import java.util.ArrayList;
import java.util.List;


public class DataGrid implements java.io.Serializable {

	private static final long serialVersionUID = -1522880613778925092L;
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

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer(0);
		buffer.append("DataGrid [");
		buffer.append("total=").append(total).append(",");
		buffer.append("rows=").append(rows).append(",");
		buffer.append("footer=").append(footer);
		buffer.append("]");
		return buffer.toString();
	}

	
}
