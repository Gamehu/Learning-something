package com.elane.core.aop.mybatis.mapper.annotation;

import java.lang.annotation.*;

/**
 * 描述Java对象与数据库表映射关系的注解类（对应的数据库表名、唯一键类型、唯一键名称）
 * @author Hankin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface TableMapperAnnotation {
    /**
     * 
     * @return Dto对应的数据库表的表名
     */
    public String tableName() default "";

    /**
     * 
     * @return 数据库表唯一键类型：UniqueKeyType.Single（单个字段的唯一键）,UniqueKeyType.Union（几个字段组合起来的唯一键）
     */
    public UniqueKeyType uniqueKeyType();

    /**
     * 
     * @return 唯一键名称，多个唯一键用逗号隔开,只能用数据库字段名
     */
    public String uniqueKey();
    
    /**
     * @return 公共查询显示大标题
     */
    public String title() default "";
}
