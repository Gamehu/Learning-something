package com.elane.core.aop.mybatis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.elane.core.aop.mybatis.mapper.annotation.FieldMapper;
import com.elane.core.aop.mybatis.mapper.annotation.FieldMapperAnnotation;
import com.elane.core.aop.mybatis.mapper.annotation.TableMapper;
import com.elane.core.aop.mybatis.mapper.annotation.TableMapperAnnotation;



/***
 * 通过注解生成sql
 * @author Hankin
 *
 */
public class SqlBuilder {
    /**
     * 缓存TableMapper
     */
    private static HashMap<Class<?>, TableMapper> tableMapperCache = new HashMap<Class<?>, TableMapper>(128);

    /**
     * 由传入的dto对象的class构建TableMapper对象，构建好的对象存入缓存中，以后使用时直接从缓存中获取
     * 
     * @param dtoClass
     * @return TableMapper
     */
    private static TableMapper buildTableMapper(Class<?> dtoClass) {

        HashMap<String, FieldMapper> fieldMapperCache = null;
        ArrayList<FieldMapper> fieldMapperList = null;
        Field[] fields = null;

        FieldMapperAnnotation fieldMapperAnnotation = null;
        FieldMapper fieldMapper = null;
        TableMapper tableMapper = null;
        synchronized (tableMapperCache) {
            tableMapper = tableMapperCache.get(dtoClass);
            if (tableMapper != null) {
                return tableMapper;
            }
            tableMapper = new TableMapper();
            Annotation[] classAnnotations = dtoClass.getDeclaredAnnotations();
            if (classAnnotations.length == 0) {
                throw new RuntimeException("Class " + dtoClass.getName()
                        + " has no annotation, I can't build 'TableMapper' for it.");
            }
            for (Annotation an : classAnnotations) {
                if (an instanceof TableMapperAnnotation) {
                    tableMapper.setTableMapperAnnotation(an);
                }
            }
            if (tableMapper.getTableMapperAnnotation() == null) {
                throw new RuntimeException("Class " + dtoClass.getName() + " has no 'TableMapperAnnotation', "
                        + "which has the database table information," + " I can't build 'TableMapper' for it.");
            }
            fields = dtoClass.getDeclaredFields();
            fieldMapperCache = new HashMap<String, FieldMapper>();
            fieldMapperList = new ArrayList<FieldMapper>();
            Annotation[] fieldAnnotations = null;
            for (Field field : fields) {
                fieldAnnotations = field.getDeclaredAnnotations();
                if (fieldAnnotations.length == 0) {
                    continue;
                }
                for (Annotation an : fieldAnnotations) {
                    if (an instanceof FieldMapperAnnotation) {
                        fieldMapperAnnotation = (FieldMapperAnnotation) an;
                        fieldMapper = new FieldMapper();
                        fieldMapper.setPropertyName(field.getName());
                        fieldMapper.setColumnName(fieldMapperAnnotation.columnName());
                        fieldMapper.setJdbcType(fieldMapperAnnotation.jdbcType());
                        fieldMapperCache.put(fieldMapperAnnotation.columnName(), fieldMapper);
                        fieldMapperList.add(fieldMapper);
                    }
                }
            }
            tableMapper.setFieldMapperCache(fieldMapperCache);
            tableMapper.setFieldMapperList(fieldMapperList);
            tableMapperCache.put(dtoClass, tableMapper);
            return tableMapper;
        }
    }

//    /**
//     * 从注解里获取唯一键信息
//     * 
//     * @param tableMapper
//     * @return
//     */
//    private static String[] buildUniqueKey(TableMapper tableMapper) {
//        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
//        String[] uniqueKeyNames = null;
//        if (tma.uniqueKeyType().equals(UniqueKeyType.Single)) {
//            uniqueKeyNames = new String[1];
//            uniqueKeyNames[0] = tma.uniqueKey();
//        } else {
//            uniqueKeyNames = tma.uniqueKey().split(",");
//        }
//        return uniqueKeyNames;
//    }

    /***
     * 拼接Where语句
     * @param object
     * @param objectkey (分页用，如：objectkey.id)
     * @return
     * @throws Exception
     */
    public static String buildWhereSql(Object object,String objectkey) throws Exception {
        StringBuffer whereSql = new StringBuffer("");
        Map dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        boolean isWhere = false;
        for (Object key :tableMapper.getFieldMapperCache().keySet()) {
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(key);
            String columnName = fieldMapper.getColumnName();
            Object propertyName = fieldMapper.getPropertyName();
            if(StringUtils.isEmpty(columnName)){
            	continue;
            }
            if(propertyName==null){
                propertyName = columnName;
            }
            
            Object value = dtoFieldMap.get(propertyName);
            if (value == null) {
                continue;
            }
        	if(!isWhere){
            	whereSql = new StringBuffer(" where ");
            	isWhere = true;
        	}
            whereSql.append(columnName);
            if(!StringUtils.isEmpty(objectkey)){
                propertyName = objectkey + "." +propertyName;
            }
            whereSql.append("=#{").append(propertyName).append(",").append("jdbcType=")
            .append(fieldMapper.getJdbcType().toString()).append("} and ");
        }
        if(whereSql.length()>3){
            whereSql.delete(whereSql.lastIndexOf("and"), whereSql.lastIndexOf("and") + 3);
        }
        return whereSql.toString();
    }
    /**
     * 由传入的对象生成insert sql语句
     * 
     * @param tableMapper
     * @param dto
     * @return sql
     * @throws Exception
     */
    public static String buildInsertSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        Map dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();
        StringBuffer tableSql = new StringBuffer();
        StringBuffer valueSql = new StringBuffer();

        tableSql.append("insert into ").append(tableName).append("(");
        valueSql.append("values(");

        boolean allFieldNull = true;
        for (String columnName : tableMapper.getFieldMapperCache().keySet()) {
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(columnName);
            String propertyName = fieldMapper.getPropertyName();
            Object value = dtoFieldMap.get(propertyName);
            if (value == null) {
                continue;
            }
            allFieldNull = false;
            tableSql.append(columnName).append(",");
            valueSql.append("#{").append(propertyName).append(",").append("jdbcType=")
                    .append(fieldMapper.getJdbcType().toString()).append("},");
        }
        if (allFieldNull) {
            throw new RuntimeException("Are you joking? Object " + object.getClass().getName()
                    + "'s all fields are null, how can i build sql for it?!");
        }
        tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
        valueSql.delete(valueSql.lastIndexOf(","), valueSql.lastIndexOf(",") + 1);
        return tableSql.append(") ").append(valueSql).append(")").toString();
    }

    /**
     * 由传入的对象生成update sql语句
     * 
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildUpdateSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        Map dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();

        StringBuffer tableSql = new StringBuffer();

        tableSql.append("update ").append(tableName).append(" set ");

        boolean allFieldNull = true;

        for (String columnName : tableMapper.getFieldMapperCache().keySet()) {
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(columnName);
            String propertyName = fieldMapper.getPropertyName();
            Object value = dtoFieldMap.get(propertyName);
            if (value == null) {
                continue;
            }
            allFieldNull = false;
            tableSql.append(columnName).append("=#{").append(propertyName).append(",").append("jdbcType=").append(fieldMapper.getJdbcType().toString()).append("},");
        }
        if (allFieldNull) {
            throw new RuntimeException("Are you joking? Object " + object.getClass().getName()
                    + "'s all fields are null, how can i build sql for it?!");
        }

        tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
        //
        return tableSql.append(buildWhereSql(object,"")).toString();
    }

    /**
     * 由传入的对象生成update sql语句
     * 
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildDeleteSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();

        StringBuffer sql = new StringBuffer();

        sql.append("delete from ").append(tableName).append(buildWhereSql(object,""));

        return sql.toString();
    }

    /**
     * 由传入的对象生成query sql语句
     * 
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildSelectSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();

        StringBuffer selectSql = new StringBuffer();
        selectSql.append("select ");
        for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
            selectSql.append(dbFieldName).append(",");
        }
        selectSql.delete(selectSql.lastIndexOf(","), selectSql.lastIndexOf(",") + 3);
        selectSql.append(" from ").append(tableName);

        return selectSql.append(buildWhereSql(object,"")).toString();
    }

    /**
     * 由传入的对象生成query 分页sql语句<br>
     * 只做参数设置，不配置分页
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildPageSelectSql(Object object) throws Exception {
    	Object objectSeach = null;
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        Map objectMap = (Map)object;
        if(objectMap.containsKey("search")){
        	objectSeach = objectMap.get("search");
        }else{
        	objectSeach = object;
        }
//    	return buildSelectSql(objectSeach);

        TableMapper tableMapper = buildTableMapper(objectSeach.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();

        StringBuffer selectSql = new StringBuffer();
        selectSql.append("select ");
        for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
            selectSql.append(dbFieldName).append(",");
        }
        selectSql.delete(selectSql.lastIndexOf(","), selectSql.lastIndexOf(",") + 3);
        selectSql.append(" from ").append(tableName);

        return selectSql.append(buildWhereSql(objectSeach,"search")).toString();
    }
}
