package com.elane.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elane.core.aop.mybatis.page.Page;
import com.elane.core.aop.mybatis.page.Pages;

/***
 * 
 * @author hankin
 *
 */
public interface BaseDao<T> {

	/***
	 * INSERT
	 * 
	 * @param detial
	 * @return
	 */
	int insert(T detial);
	
	/***
	 * DELETE
	 * 
	 * @param condtion
	 * @return
	 */
	int delete(T detial);
	
	/***
	 * UPDATE
	 * 
	 * @param detial
	 * @return
	 */
	int update(T detial);

	/***
	 * SELECT
	 * 
	 * @param detial
	 * @return
	 */
	List<T> select(T detial);
	
	/***
	 * 分页查询
	 * 
	 * @param detial
	 * @return
	 */
	@Pages
	List<T> selectByPage(@Param("search") T search, @Param("page") Page<T> page);
}
