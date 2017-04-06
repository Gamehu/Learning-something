package com.elane.core.aop.mybatis.page;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class Page<T> implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    
    public static final String ASC = "asc";
	public static final String DESC = "desc";
	protected int pageNo = 1;

	protected int offset = 0;

	protected int pageSize = 10;

	protected String orderBy = null;

	protected String order = null;

	protected boolean autoCount = true;

	protected List<T> result = Lists.newArrayList();
	protected long totalRecords = -1L;

	protected long totalPages = 1L;

	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	
	/**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Pages(pageIndex = true, order = false, orderField = false, pageSize = false, totalPage = false, totalRecord = false)
	public int getPageNo() {
        pageNo = getOffset() / getPageSize() + 1;
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1)
			this.pageNo = 1;
	}

	public Page<T> pageNo(int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	@Pages(pageSize = true, order = false, orderField = false, pageIndex = false, totalPage = false, totalRecord = false)
	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page<T> pageSize(int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	public int getFirst() {
		return (this.pageNo - 1) * this.pageSize + 1;
	}

	@Pages(orderField = true, order = false, pageIndex = false, pageSize = false, totalPage = false, totalRecord = false)
	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Page<T> orderBy(String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	@Pages(order = true, orderField = false, pageIndex = false, pageSize = false, totalPage = false, totalRecord = false)
	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		String lowcaseOrder = StringUtils.lowerCase(order);

		String[] orders = StringUtils.split(lowcaseOrder, ',');
		for (String orderStr : orders) {
			if ((!StringUtils.equals("desc", orderStr)) && (!StringUtils.equals("asc", orderStr))) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = lowcaseOrder;
	}

	public Page<T> order(String theOrder) {
		setOrder(theOrder);
		return this;
	}

	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(this.orderBy)) && (StringUtils.isNotBlank(this.order));
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public Page<T> autoCount(boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	@Pages(totalRecord = true, order = false, orderField = false, pageIndex = false, pageSize = false, totalPage = false)
	public long getTotalRecords() {
		return this.totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public long getTotalPages() {
		if (this.totalRecords < 0L) {
			return -1L;
		}

		long count = this.totalRecords / this.pageSize;
		if (this.totalRecords % this.pageSize > 0L) {
			count += 1L;
		}

		return count;
	}

	public boolean isHasNext() {
		return this.pageNo + 1 <= getTotalPages();
	}

	public int getNextPage() {
		if (isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}

	public boolean isHasPre() {
		return this.pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (isHasPre()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}
}
