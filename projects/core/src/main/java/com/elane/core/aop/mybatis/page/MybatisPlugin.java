package com.elane.core.aop.mybatis.page;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elane.core.aop.mybatis.SqlBuilder;
import com.elane.core.util.Reflections;

/***
 * mybatis 插件
 * @author hankin
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MybatisPlugin implements Interceptor {
	private Logger logger = LoggerFactory.getLogger(MybatisPlugin.class);

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	private static String pageSqlId = ".*Page$"; // // 需要拦截的ID(正则匹配)
	private static String dialect = "mysql"; // 数据库方言
	private static String defaultDialect = "mysql"; // 数据库方言

	public Object intercept(Invocation invocation) throws Throwable {
		String _boundSql_sql = "";
		BoundSql boundSql = null;
		Object _parameterObject = null;

		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);
		// 分离代理对象链
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		// 分离最后一个代理对象的目标类
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		//
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		//
		Method sqlMethod = getMethodByStmt(mappedStatement);
		try {
			Validate.notNull(sqlMethod);
		} catch (Exception e) {
			return invocation.proceed();
		}

		boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		_boundSql_sql = boundSql.getSql();
		if (null == _boundSql_sql || "".equals(_boundSql_sql)) {
			_parameterObject = boundSql.getParameterObject();
			if (_parameterObject == null) {
				throw new NullPointerException("MybatisPlugin parameterObject is null!");
			}
			// 自动生成sql语句
			String newSql = "";
			String id = mappedStatement.getId();
			id = id.substring(id.lastIndexOf(".") + 1);
			if ("insert".equals(id)) {
				newSql = SqlBuilder.buildInsertSql(_parameterObject);
			} else if ("update".equals(id)) {
				newSql = SqlBuilder.buildUpdateSql(_parameterObject);
			} else if ("delete".equals(id)) {
				newSql = SqlBuilder.buildDeleteSql(_parameterObject);
			} else if ("select".equals(id)) {
				newSql = SqlBuilder.buildSelectSql(_parameterObject);
			} else if ("selectByPage".equals(id)) {
				newSql = SqlBuilder.buildPageSelectSql(_parameterObject);
			}

			logger.debug("MybatisPlugin Auto generated sql:" + newSql);
			Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
			//
			SqlSource sqlSource = buildSqlSource(configuration, newSql, _parameterObject.getClass());
			List<ParameterMapping> parameterMappings = sqlSource.getBoundSql(_parameterObject).getParameterMappings();
			metaStatementHandler.setValue("delegate.boundSql.sql", sqlSource.getBoundSql(_parameterObject).getSql());
			metaStatementHandler.setValue("delegate.boundSql.parameterMappings", parameterMappings);
		}

		// 判断是否为分页查询
		if (sqlMethod.getAnnotation(Pages.class) != null) {
			// 只处理@Pages注解的
			boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			_parameterObject = boundSql.getParameterObject();
			String oldSql = boundSql.getSql();
			if (_parameterObject == null) {
				throw new NullPointerException("MybatisPlugin parameterObject is null!");
			}

			Connection connection = (Connection) invocation.getArgs()[0];

			//
			// Configuration configuration = (Configuration)
			// metaStatementHandler.getValue("delegate.configuration");
			// Connection conn =
			// configuration.getEnvironment().getDataSource().getConnection();
			// 从数据源获取数据库类型名称
			String driverName = connection.getMetaData().getDatabaseProductName();
			// try {
			// conn.close();
			// } catch (Exception e) {
			// return invocation.proceed();
			// }
			if (driverName.toLowerCase().contains("oracle")) {
				dialect = "oracle";
			} else if (driverName.toLowerCase().contains("mysql")) {
				dialect = "mysql";
			} else if ((null == dialect) || ("".equals(dialect))) {
				logger.warn("MybatisPlugin Property dialect is not setted,use default 'mysql' ");
				dialect = defaultDialect;
			}
			//
			Object pageObj = metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
			//
			metaStatementHandler.setValue("delegate.boundSql.sql",
					buildPageSqlStep1(boundSql.getSql(), pageObj, dialect));
			metaStatementHandler.setValue("delegate.rowBounds.offset", Integer.valueOf(0));
			metaStatementHandler.setValue("delegate.rowBounds.limit", Integer.valueOf(2147483647));

			logger.debug("MybatisPlugin Auto generated page sql:" + boundSql.getSql());
			buildPageSqlStep2(connection, mappedStatement, boundSql, pageObj, oldSql);
		}
		// 传递给下一个拦截器处理
		return invocation.proceed();
	}

	/***
	 * 绑定分页参数
	 * 
	 * @param boundSql
	 * @param parameterObject
	 * @param dialect_
	 * @return
	 */
	private String buildPageSqlStep1(String boundSql, Object parameterObject, String dialect_) {
		Class parameterClass = parameterObject.getClass();
		Method[] methods = parameterClass.getDeclaredMethods();
		int pageIndex = 0;
		int pageSize = 0;
		String orderField = "";
		String order = "";
		for (Method m : methods) {
			Pages pages = (Pages) m.getAnnotation(Pages.class);
			Object result = null;
			if (pages == null)
				continue;
			try {
				result = m.invoke(parameterObject, null);
				if (result == null)
					result = "";
			} catch (Exception e) {
				logger.error("MybatisPlugin parameters reflection error!");
			}
			if (pages.pageIndex()) {
				pageIndex = ((Integer) result).intValue();
			}
			if (pages.pageSize()) {
				pageSize = ((Integer) result).intValue();
			}
			if (pages.orderField()) {
				orderField = (String) result;
			}
			if (pages.order()) {
				order = (String) result;
			}
		}

		return buildPageSql(boundSql, pageIndex, pageSize, orderField, order, dialect_);
	}

	/***
	 * 查询数据总条数
	 * 
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param pageObj
	 */
	private void buildPageSqlStep2(Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
			Object pageObj, String oldSql) {
		String countSql = "";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		int totalCount = 0;

		Class parameterClass = pageObj.getClass();
		Method[] methods = parameterClass.getDeclaredMethods();
		try {

			countSql = new StringBuilder().append("select count(0) from (").append(oldSql).append(") total").toString();
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
					boundSql.getParameterMappings(), boundSql.getParameterObject());

			Field metaParamsField = Reflections.getAccessibleField(boundSql, "metaParameters");
			if (metaParamsField != null) {
				MetaObject mo = (MetaObject) Reflections.getFieldValue(boundSql, "metaParameters");
				Reflections.setFieldValue(countBS, "metaParameters", mo);
			}
			System.out.println(boundSql.getParameterObject());
			setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
			rs = countStmt.executeQuery();
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			for (Method m : methods) {
				Pages pages = (Pages) m.getAnnotation(Pages.class);
				String mtName = m.getName();
				Class returnType = m.getReturnType();
				String setMethodName = new StringBuilder().append("set").append(mtName.substring(3)).toString();
				Object result = null;
				if (pages != null) {
					Method setMethod = null;
					try {
						result = m.invoke(pageObj, null);
						setMethod = parameterClass.getMethod(setMethodName, new Class[] { returnType });

						if (result == null) {
							result = "";
						}
					} catch (Exception e) {
						logger.error("MybatisPlugin parameters reflection error!");
					}
					if (pages.totalRecord())
						setObject(setMethod, pageObj, Integer.valueOf(totalCount));
				}
			}
		} catch (Exception e) {
			logger.error("MybatisPlugin Ignore this exception", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("MybatisPlugin Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				logger.error("MybatisPlugin Ignore this exception", e);
			}
		}
	}

	public void setObject(Method setMethod, Object pojo, Object parmeterObject) {
		try {
			setMethod.invoke(pojo, new Object[] { parmeterObject });
		} catch (Exception e) {
			logger.error("MybatisPlugin reflection errors:", e);
		}
	}

	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	private Method getMethodByStmt(MappedStatement mappedStatement) throws ClassNotFoundException {
		String methodid = mappedStatement.getId();
		String interfaceName = methodid.substring(0, methodid.lastIndexOf("."));
		String methodName = methodid.substring(methodid.lastIndexOf(".") + 1);
		Class interfaceClass = Class.forName(interfaceName);
		Method[] methods = interfaceClass.getMethods();
		for (Method method : methods) {
			if (methodName.equals(method.getName()))
				return method;
		}
		return null;
	}

	/***
	 * 根据方言选择SQL生成器
	 * 
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param orderField
	 * @param order
	 * @param dialect
	 * @return
	 */
	private String buildPageSql(String sql, int pageIndex, int pageSize, String orderField, String order,
			String dialect_) {
		if ((pageIndex != 0) && (((dialect_ != null) || (!dialect_.equals(""))))) {
			String pageSql;
			if ("mysql".equals(dialect_)) {
				pageSql = buildPageSqlForMysql(sql, pageIndex, pageSize, orderField, order, true);
			} else {
				if ("oracle".equals(dialect_))
					pageSql = buildPageSqlForOracle(sql, pageIndex, pageSize, orderField, order);
				else
					pageSql = sql;
			}
			return pageSql;
		}
		return sql;
	}

	/***
	 * Mysql分页查询SQL生成器
	 * 
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param orderField
	 * @param order
	 * @return
	 */
	public String buildPageSqlForMysql(String sql, int pageIndex, int pageSize, String orderField, String order) {
		StringBuffer pageSql = new StringBuffer();
		String beginrow = String.valueOf((pageIndex - 1) * pageSize);
		pageSql.append(sql);
		if (!orderField.equals("")) {
			pageSql.append(new StringBuilder().append(" order by ").append(orderField).toString());
		}
		if ((!orderField.equals("")) && (!order.equals(""))) {
			pageSql.append(new StringBuilder().append(" ").append(order).toString());
		}

		if (pageSize != -1) {
			pageSql.append(
					new StringBuilder().append(" limit ").append(beginrow).append(",").append(pageSize).toString());
		}

		return pageSql.toString();
	}

	/***
	 * Mysql分页查询SQL生成器
	 * 
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param orderField
	 * @param order
	 * @param chinese
	 * @return
	 */
	public String buildPageSqlForMysql(String sql, int pageIndex, int pageSize, String orderField, String order,
			boolean chinese) {
		StringBuffer pageSql = new StringBuffer();
		pageSql.append(sql);

		if (!orderField.equals("")) {
			if (chinese) {
				String orderby = new StringBuilder().append(" ORDER BY convert(").append(orderField)
						.append(" USING gbk ) COLLATE gbk_chinese_ci").toString();

				pageSql.append(orderby);
			} else {
				pageSql.append(new StringBuilder().append(" order by ").append(orderField).toString());
			}

			if (!order.equals("")) {
				pageSql.append(new StringBuilder().append(" ").append(order).toString());
			}

		}

		if (pageSize != -1) {
			String beginrow = String.valueOf((pageIndex - 1) * pageSize);
			pageSql.append(
					new StringBuilder().append(" limit ").append(beginrow).append(",").append(pageSize).toString());
		}

		return pageSql.toString();
	}

	/***
	 * Oracle分页查询SQL生成器
	 * 
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param orderField
	 * @param order
	 * @return
	 */
	public String buildPageSqlForOracle(String sql, int pageIndex, int pageSize, String orderField, String order) {
		StringBuilder pageSql = new StringBuilder(100);
		String beginrow = String.valueOf((pageIndex - 1) * pageSize);
		String endrow = String.valueOf(pageIndex * pageSize);
		pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
		pageSql.append(sql);
		pageSql.append(" ) temp where rownum <= ").append(endrow);
		if (!orderField.equals("")) {
			pageSql.append(new StringBuilder().append(" order by temp.").append(orderField).toString());
		}
		if (!order.equals("")) {
			pageSql.append(new StringBuilder().append(" ").append(order).toString());
		}
		pageSql.append(") where row_id > ").append(beginrow);
		return pageSql.toString();
	}

	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	/***
	 * 获取属性值
	 */
	public void setProperties(Properties properties) {
		String properte = "";
		try {
			properte = properties.getProperty("pageSqlId");
			if (!StringUtils.isEmpty(properte)) {
				MybatisPlugin.pageSqlId = properte;
			}
		} catch (Exception e) {
			logger.error("MybatisPlugin setProperties pageSqlId ", e);
		}
		try {
			properte = properties.getProperty("dialect");
			if (!StringUtils.isEmpty(properte)) {
				MybatisPlugin.defaultDialect = properte;
			}
		} catch (Exception e) {
			logger.error("MybatisPlugin setProperties dialect ", e);
		}
	}

	/***
	 * 
	 * @param configuration
	 * @param originalSql
	 * @param parameterType
	 * @return
	 */
	private SqlSource buildSqlSource(Configuration configuration, String originalSql, Class<?> parameterType) {
		SqlSourceBuilder builder = new SqlSourceBuilder(configuration);
		return builder.parse(originalSql, parameterType, null);
	}
}
