package com.elane.logger.dao.templete;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class AbstractBaseMongoTemplete<T> {
    @Resource
    protected MongoTemplate mongoTemplate;

    private Class<T> tClass;

    @SuppressWarnings("unchecked")
	public AbstractBaseMongoTemplete() {
        super();
        tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 新增 <br>
     * ------------------------------<br>
     * 
     * @param entity
     */
    public void insert(T entity) {
        mongoTemplate.insert(entity);
    }

    /**
     * 新增 <br>
     * ------------------------------<br>
     * 
     * @param entity
     * @param collectionName
     */
    public void insert(T log, String collectionName) {
        mongoTemplate.insert(log, collectionName);
    }

    /**
     * 批量新增 <br>
     * ------------------------------<br>
     * 
     * @param entitys
     */
    public void insertAll(List<T> entitys) {
        mongoTemplate.insertAll(entitys);
    }

    /**
     * 删除,按主键id, 如果主键的值为null,删除会失败 <br>
     * ------------------------------<br>
     * 
     * @param id
     * @throws Exception
     */
    public void deleteById(String id) throws Exception {
        Object obj = Class.forName(tClass.getName()).newInstance();
        setKeyByEntity(obj, "id", id, tClass);
        mongoTemplate.remove(obj);
    }

    /**
     * 按条件删除 <br>
     * ------------------------------<br>
     * 
     * @param criteriaT
     */
    public void deleteByQuery(Query query) {
        mongoTemplate.remove(query, tClass);
    }

    /**
     * 删除全部 <br>
     * ------------------------------<br>
     */
    public void deleteAll() {
        mongoTemplate.dropCollection(tClass);
    }

    /**
     * 按主键修改, 如果文档中没有相关key 会新增 使用$set修改器 <br>
     * ------------------------------<br>
     * 
     * @param entitys
     * @throws Exception
     */
    public void updateById(T entitys) throws Exception {
        String id = (String) getKeyByEntity(entitys, "id", tClass);

        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        Update update = new Update();
        Field[] fields = tClass.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        for (Field field : fields) {
            String name = field.getName(); // 获取属性的名字
            if ("id".equals(name)) {
                continue;
            }
            update.set(name, getKeyByEntity(entitys, name, tClass));
        }

        mongoTemplate.updateFirst(query, update, tClass);
    }

    /**
     * 修改多条 <br>
     * ------------------------------<br>
     * 
     * @param query
     * @param update
     */
    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, tClass);
    }

    /**
     * 根据主键查询 <br>
     * ------------------------------<br>
     * 
     * @param id
     * @return
     */
    public T findById(String id) {
        return mongoTemplate.findById(id, tClass);
    }

    /**
     * 根据主键查询 <br>
     * ------------------------------<br>
     * 
     * @param id
     * @param collection
     * @return
     */
    public T findById(String id, String collection) {
        return mongoTemplate.findById(id, tClass, collection);
    }

    /**
     * 查询全部 <br>
     * ------------------------------<br>
     * 
     * @return
     */
    public List<T> findAll() {
        return mongoTemplate.findAll(tClass);
    }

    /**
     * 按条件查询, 分页 <br>
     * ------------------------------<br>
     * 
     * @param query
     * @param skip
     * @param limit
     * @return
     */
    public List<T> find(Query query, int skip, int limit) {
        query.skip(skip);
        query.limit(limit);
        return mongoTemplate.find(query, tClass);
    }

    /**
     * 根据条件查询出来后 再去修改 <br>
     * ------------------------------<br>
     * 
     * @param query
     *            查询条件
     * @param updateT
     *            修改的值对象
     * @return
     */
    public T findAndModify(Query query, Update update) {
        return mongoTemplate.findAndModify(query, update, tClass);
    }

    /**
     * 查询出来后 删除 <br>
     * ------------------------------<br>
     * 
     * @param query
     * @return
     */
    public T findAndRemove(Query query) {
        return mongoTemplate.findAndRemove(query, tClass);
    }

    /**
     * count <br>
     * ------------------------------<br>
     * 
     * @param query
     * @return
     */
    public long count(Query query) {
        return mongoTemplate.count(query, tClass);
    }

    /***
     * 获取对象属性值
     * 
     * @param dataObj
     * @param key
     * @param classType
     * @return
     * @throws Exception
     */
    public static <E> Object getKeyByEntity(Object dataObj, String key, Class<E> classType) throws Exception {
        Object r_obj = null;
        Object obj = Class.forName(classType.getName()).newInstance();
        Method[] methods = classType.getMethods();
        // Field[] fields = classType.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        if (methods.length > 0 && !StringUtils.isEmpty(key) && key.length() > 1) {
            String name = key.substring(0, 1).toUpperCase() + key.substring(1); // 将属性的首字符大写，方便构造get，set方法
            Method m = classType.getMethod("get" + name);
            // Field field = classType.getDeclaredField(key); // 获取实体类的所有属性，返回Field
            // String type = field.getGenericType().toString(); // 获取属性的类型
            // if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
            // String value = (String) m.invoke(obj); // 调用getter方法获取属性值
            // if (value == null) {
            // m = obj.getClass().getMethod("set" + name, String.class);
            // m.invoke(obj, "");
            // }
            // }
            r_obj = m.invoke(obj);
        }
        return r_obj;

    }

    /***
     * 设置对象属性值
     * 
     * @param dataObj
     * @param key
     * @param value
     * @param classType
     * @return
     * @throws Exception
     */
    public static <E> Object setKeyByEntity(Object dataObj, String key, String value, Class<E> classType)
            throws Exception {
        Object r_obj = null;
        Object obj = Class.forName(classType.getName()).newInstance();
        Method[] methods = classType.getMethods();
        // Field[] fields = classType.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        if (methods.length > 0 && !StringUtils.isEmpty(key) && key.length() > 1) {
            String name = key.substring(0, 1).toUpperCase() + key.substring(1); // 将属性的首字符大写，方便构造get，set方法
            Method m = classType.getMethod("set" + name);
            // Field field = classType.getDeclaredField(key); // 获取实体类的所有属性，返回Field
            // String type = field.getGenericType().toString(); // 获取属性的类型
            // if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
            // String value = (String) m.invoke(obj); // 调用getter方法获取属性值
            // if (value == null) {
            // m = obj.getClass().getMethod("set" + name, String.class);
            // m.invoke(obj, "");
            // }
            // }
            r_obj = m.invoke(obj, value);
        }
        return r_obj;

    }
}