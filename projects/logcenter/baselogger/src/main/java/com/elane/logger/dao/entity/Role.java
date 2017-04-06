package com.elane.logger.dao.entity;

import org.apache.ibatis.type.JdbcType;

import com.elane.core.aop.mybatis.mapper.annotation.FieldMapperAnnotation;
import com.elane.core.aop.mybatis.mapper.annotation.TableMapperAnnotation;
import com.elane.core.aop.mybatis.mapper.annotation.UniqueKeyType;


/**
 * 角色
 * role entity.
 */
@TableMapperAnnotation(tableName = "role", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "id")
public class Role {
	
	@FieldMapperAnnotation(columnName = "id", propertyName = "id", jdbcType = JdbcType.INTEGER)
	private Integer id;

	@FieldMapperAnnotation(columnName = "role", propertyName = "role", jdbcType = JdbcType.VARCHAR)
	private String role;

	@FieldMapperAnnotation(columnName = "remark", propertyName = "remark", jdbcType = JdbcType.VARCHAR)
	private String remark;
	
	public Role(String role, String remark) {
		super();
		this.role = role;
		this.remark = remark;
	}
	
	public Role(String role) {
		super();
		this.role = role;
	}

	public Role() {
		super();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + ", remark=" + remark + "]";
	}
	
}

