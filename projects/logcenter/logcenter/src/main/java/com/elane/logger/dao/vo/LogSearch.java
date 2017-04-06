package com.elane.logger.dao.vo;

import java.io.Serializable;
import java.util.List;

public class LogSearch implements Serializable {
	private String database;
	private String table;
	private String type;
	private List<String> appid;
	private String companyid;
	private String userid;
	private Integer tsBegin;
	private Integer tsEnd;
	private String xtransactionid;
	
	public LogSearch() {
		super();
	}
	
	public LogSearch(Integer tsBegin, Integer tsEnd) {
		super();
		this.tsBegin = tsBegin;
		this.tsEnd = tsEnd;
	}

	public LogSearch(String database, String table, List<String> appid,
			String companyid, String userid, Integer tsBegin, Integer tsEnd) {
		super();
		this.database = database;
		this.table = table;
		this.appid = appid;
		this.companyid = companyid;
		this.tsBegin = tsBegin;
		this.tsEnd = tsEnd;
		this.userid = userid;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getTsBegin() {
		return tsBegin;
	}

	public void setTsBegin(Integer tsBegin) {
		this.tsBegin = tsBegin;
	}

	public Integer getTsEnd() {
		return tsEnd;
	}

	public void setTsEnd(Integer tsEnd) {
		this.tsEnd = tsEnd;
	}

	public List<String> getAppid() {
		return appid;
	}

	public void setAppid(List<String> appid) {
		this.appid = appid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getXtransactionid() {
		return xtransactionid;
	}

	public void setXtransactionid(String xtransactionid) {
		this.xtransactionid = xtransactionid;
	}

	@Override
	public String toString() {
		return "LogSearch [ database=" + database + ", table=" + table + ", type=" + type 
				+ ", appid=" + appid + ", companyid="
                + companyid + ", userid=" + userid + ", xtransactionid=" + xtransactionid + "]";
	}
	
	
}
