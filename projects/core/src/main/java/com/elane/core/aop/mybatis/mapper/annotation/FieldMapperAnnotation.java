package com.elane.core.aop.mybatis.mapper.annotation;

import org.apache.ibatis.type.JdbcType;

import java.lang.annotation.*;

/**
 * 用于描述java对象字段对应的数据库表字段的注解（数据库字段名，字段对应的jdbc类型）
 * @author Hankin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface FieldMapperAnnotation {
	public enum ForeignType {
		LEFT,
		INNER,
		RIGHT
	}
	
    /**
     * 
     * 对应数据库表的字段名称
     */
    String columnName();

    /**
     * 
     * javaBean显示名称
     */
    String propertyName() default "";
    /**
     * 
     * 
     * 字段用JDBC接口存入数据库需要设置的数据类型,Integer,Long,Short,Float,Double,String,Date ,Timestamp,Time
     */
    JdbcType jdbcType();
    /**
     * 
     * 公用页面显示字段顺序
     */
    int sort() default 999;
    /**
     * 页面需要隐藏的字段（如ID）
     */
    boolean fieldHidden() default false;
    
    /**
     * 关联表TableName
     */
    String foreignTableName() default "";
    /**
     * 关联键 dbFieldName
     */
    String foreignKey() default "";
    /**
     * 需要显示的关联表字段dbFieldName
     */
    String displayItem() default "";
    /**
     * 需要显示的关联表字段的显示名称
     */
    String displayItemName() default "";
    /**
     * 需要显示的关联表字段的数据类型
     */
    JdbcType displayItemJdbcType() default JdbcType.VARCHAR;
    /**
     * 关联关系 LEFT,INNER,RIGHT
     */
    ForeignType foreignType() default ForeignType.LEFT; 
}
