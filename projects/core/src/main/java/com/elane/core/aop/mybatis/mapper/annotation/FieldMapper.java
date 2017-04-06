package com.elane.core.aop.mybatis.mapper.annotation;

import org.apache.ibatis.type.JdbcType;

/***
 * 字段映射类，用于描述java对象字段和数据库表字段之间的对应关系
 * @author Hankin
 *
 */
public class FieldMapper {
    /**
     * Java对象字段名
     */
    private String propertyName;
    /**
     * 数据库表字段名
     */
    private String columnName;
    
    /**
     * 数据库字段对应的jdbc类型
     */
    private JdbcType jdbcType;
    

	public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
