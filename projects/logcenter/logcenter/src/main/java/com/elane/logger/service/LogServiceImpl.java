package com.elane.logger.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.elane.logger.dao.LogDaoImpl;
import com.elane.logger.dao.entity.LogEntity;
import com.elane.logger.dao.templete.Page;
import com.elane.logger.dao.vo.LogSearch;

@Service
public class LogServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
    
    @Resource
    private LogDaoImpl logDao;

    public void insert(LogSearch log) {
        LogEntity logdb = new LogEntity();
        BeanUtils.copyProperties(log, logdb);
        logDao.insert(logdb);
    }

    public void insert(LogSearch log, String collectionName) {
        LogEntity logdb = new LogEntity();
        BeanUtils.copyProperties(log, logdb);
        logDao.insert(logdb, collectionName);
    }

    public void insertAll(List<LogSearch> logs) {

    }

    public void deleteById(String id) {

    }

    public void deleteByUserName(LogSearch criteriaLogEntity) {

    }

    public void deleteAll() {

    }

    public void updateById(LogSearch log) {

    }

    public void update(LogSearch criteriaLogEntity, LogSearch log) {

    }

    public LogSearch findById(String id) {
        LogSearch logVo = new LogSearch();
        LogEntity logdb = logDao.findById(id);
        BeanUtils.copyProperties(logdb, logVo);
        return logVo;
    }

    public LogSearch findById(String id, String collection) {
        LogSearch logVo = new LogSearch();
        LogEntity logdb = logDao.findById(id, collection);
        BeanUtils.copyProperties(logdb, logVo);
        return logVo;
    }

    public List<LogSearch> findAll() {
        return null;
    }

    public List<LogEntity> findByPage(LogSearch criteriaLogEntity, Page page) {
        if (criteriaLogEntity == null) {
            criteriaLogEntity = new LogSearch();
        }
        if (page == null) {
            page = new Page();
        }
        Query query = getQuery(criteriaLogEntity,true,page);
        List<LogEntity> list = logDao.find(query, page.getSkip(), page.getPageSize());
        
        return list;
    }
    
    public LogSearch findAndModify(LogSearch criteriaLogEntity, LogSearch updateLogEntity) {
        return null;
    }

    public LogSearch findAndRemove(LogSearch criteriaLogEntity) {
        return null;
    }

    /***
     * 检索结果集总条数
     */
    public long count(LogSearch criteriaLogEntity) {
        Query query = getQuery(criteriaLogEntity,false,null);
        return logDao.count(query);
    }

    /***
     * 拼接查询条件
     * 
     * @param criteriaLogEntity
     * @return
     */
    private Query getQuery(LogSearch criteriaLogEntity, boolean isOrderby,Page page) {
        Query query = new Query();
        
        // where
        if (criteriaLogEntity.getAppid().size() > 0) {
            Criteria criteria = Criteria.where("app_id").in(criteriaLogEntity.getAppid());
            query.addCriteria(criteria);
        }
        if (!StringUtils.isEmpty(criteriaLogEntity.getCompanyid())) {
            Criteria criteria = Criteria.where("company_id").is(criteriaLogEntity.getCompanyid());
            query.addCriteria(criteria);
        }
        if (!StringUtils.isEmpty(criteriaLogEntity.getDatabase())) {
            Criteria criteria = Criteria.where("database").is(criteriaLogEntity.getDatabase());
            query.addCriteria(criteria);
        }
        if (!StringUtils.isEmpty(criteriaLogEntity.getTable())) {
            Criteria criteria = Criteria.where("table").is(criteriaLogEntity.getTable());
            query.addCriteria(criteria);
        }
        if (!StringUtils.isEmpty(criteriaLogEntity.getUserid())) {
            Criteria criteria = Criteria.where("user_id").is(criteriaLogEntity.getUserid());
            query.addCriteria(criteria);
        }
        if (!StringUtils.isEmpty(criteriaLogEntity.getXtransactionid())) {
            Criteria criteria = Criteria.where("xtransaction_id").is(criteriaLogEntity.getXtransactionid());
            query.addCriteria(criteria);
        }
	    if ((criteriaLogEntity.getTsBegin() != null && criteriaLogEntity.getTsBegin() > 0) || (criteriaLogEntity.getTsEnd() != null && criteriaLogEntity.getTsEnd() > 0)) {
	    	Criteria criteria = Criteria.where("ts");
	    	if(criteriaLogEntity.getTsBegin() != null && criteriaLogEntity.getTsBegin() > 0){
	    		criteria.gte(criteriaLogEntity.getTsBegin());
	    	}
	    	if(criteriaLogEntity.getTsEnd() != null && criteriaLogEntity.getTsEnd() > 0){
	    		criteria.lte(criteriaLogEntity.getTsEnd());
	    	}
	        query.addCriteria(criteria);
	    }
	    
        // orderby
        if(isOrderby && page!=null && !StringUtils.isEmpty(page.getOrderBy())){
            Sort sort = page.getSort();
            if(sort != null){
                query.with(sort);
            }
        }
        logger.info(query.toString());
        return query;
    }

}
