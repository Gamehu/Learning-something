package com.elane.core.aop.mybatis.page;

import java.util.List;

public class SearchResult<T> {

	private Long totalRowCount;
	private List<T> rows;

	public Long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
