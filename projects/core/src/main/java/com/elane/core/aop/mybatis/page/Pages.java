package com.elane.core.aop.mybatis.page;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Pages {
	public abstract boolean pageSize() default false;

	public abstract boolean totalPage() default false;

	public abstract boolean totalRecord() default false;

	public abstract boolean pageIndex() default false;

	public abstract boolean orderField() default false;

	public abstract boolean order() default false;
}