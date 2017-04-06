package com.elane.logger.dao.vo;

import java.io.Serializable;

public class LogSearch implements Serializable {
	private String database;
	private String table;
	private String type;
	private String appid;
	private String companyid;
	private String userid;
	private int tsBegin;
	private int tsEnd;
	
	public LogSearch() {
		super();
	}

	public LogSearch(String database, String table, String type, String appid,
			String companyid, String userid, int tsBegin, int tsEnd) {
		super();
		this.database = database;
		this.table = table;
		this.type = type;
		this.appid = appid;
		this.companyid = companyid;
		this.userid = userid;
		this.tsBegin = tsBegin;
		this.tsEnd = tsEnd;
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

	public int getTsBegin() {
		return tsBegin;
	}

	public void setTsBegin(int tsBegin) {
		this.tsBegin = tsBegin;
	}

	public int getTsEnd() {
		return tsEnd;
	}

	public void setTsEnd(int tsEnd) {
		this.tsEnd = tsEnd;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	@Override
	public String toString() {
		return "LogSearch [ database=" + database + ", table=" + table + ", type=" + type 
				+ ", appid=" + appid + ", companyid="
                + companyid + ", userid=" + userid + "]";
	}
	
	
}
