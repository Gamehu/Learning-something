package com.elane.logger.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
@Component
@Document(collection="loggingAll")
public class LogEntity {
    private static final long serialVersionUID = -2634064977259616340L;
    @Id
    private String _id;
    private String database;
    private String table;
    private String type;
    private String ts;
    private String data;
    private String app_id;
    private String company_id;
    private String user_id;
    private String old;
    private String xtransaction_id;

    public LogEntity() {
        super();
    }



    public LogEntity(String _id, String database, String table, String type, String ts,
            String data, String appid, String companyid, String userid, String old, String xtransactionid) {
        super();
        this._id = _id;
        this.database = database;
        this.table = table;
        this.type = type;
        this.ts = ts;
        this.data = data;
        this.app_id = appid;
        this.company_id = companyid;
        this.user_id = userid;
        this.old = old;
        this.xtransaction_id = xtransactionid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getApp_id() {
		return app_id;
	}
    
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

	public String getXtransaction_id() {
		return xtransaction_id;
	}

	public void setXtransaction_id(String xtransaction_id) {
		this.xtransaction_id = xtransaction_id;
	}



	@Override
    public String toString() {
        return "LogEntity [_id=" + _id + ", database=" + database + ", table=" + table + ", type=" + type + ", ts=" + ts
                + ", data=" + data + ", app_id=" + app_id + ", company_id="
                + company_id + ", user_id=" + user_id + ", old=" + old + ", xtransaction_id= "+ xtransaction_id +"]";
    }
}
