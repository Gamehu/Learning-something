package com.elane.logger.dao.templete;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class Page implements Serializable {
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	protected int pageNo = 1;

	protected int skip = 0;

	protected int pageSize = 10;

	protected String orderBy = null;

	protected String order = null;

	protected Sort sort = null;

	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1)
			this.pageNo = 1;
	}

	public int getSkip() {
		this.skip = (getPageNo() - 1 > -1 ? getPageNo() - 1 : 0)
				* getPageSize();
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
		if (skip < 1)
			this.skip = 1;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	//不允许通过参数动态修改pageSize
	/*public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}*/

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public static String getAsc() {
		return ASC;
	}

	public static String getDesc() {
		return DESC;
	}

	public Sort getSort() {
		String orderStr = "asc";
		if (!StringUtils.isEmpty(getOrder())) {
			orderStr = getOrder().toLowerCase();
		}
		if (StringUtils.equals("desc", orderStr)
				&& !StringUtils.isEmpty(getOrderBy())) {
			sort = new Sort(Direction.DESC, getOrderBy());
		}

		if (StringUtils.equals("asc", orderStr)
				&& !StringUtils.isEmpty(getOrderBy())) {
			sort = new Sort(Direction.ASC, getOrderBy());
		}
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

}
