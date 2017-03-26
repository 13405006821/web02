package com.web.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.web.dao.IFunctionDao;
import com.web.entity.SystemFunction;
import com.web.service.IFunctionService;

@Service("functionService")
public class FunctionServiceImpl implements IFunctionService {
	
	@Resource
	private IFunctionDao functionDao;
	
	private String getSearcHQL(String function, Boolean complete){
		String hql = "";
		if(StringUtils.isNotBlank(function)){
			hql += " and functionContent like '%" + function + "%' ";
		}
		if(complete != null){
			hql += " and complete = " + complete;
		}
		return hql;
	}

	@Override
	public List<SystemFunction> findList(String function, Boolean complete, int start, int length) {
		String hql =  getSearcHQL(function, complete);
		hql += " order by sort asc ";
		return functionDao.query(hql, start, length);
	}

	@Override
	public void deleteFunction(Long id) {
		SystemFunction systemFunction = this.getById(id);
		if(systemFunction != null){
			functionDao.delete(systemFunction);
		}
	}

	@Override
	public SystemFunction getById(Long id) {
		return functionDao.load(id);
	}

	@Override
	public void saveOrUpdate(SystemFunction function) {
		if(function.getId() > 0){
			functionDao.update(function);
		}else{
			functionDao.save(function);
		}
	}

	@Override
	public int findCount(String function, Boolean complete) {
		String hql =  getSearcHQL(function, complete);
		return functionDao.countInt(hql);
	}
	
	@Override
	public void deleteByIds(String ids) {
		String[] idArr = ids.split(",");
		for(String id : idArr){
			functionDao.delete(Long.valueOf(id));
		}
	}

	@Override
	public void complete(String ids) {
		String[] idArr = ids.split(",");
		SystemFunction function = null;
		for(String id : idArr){
			function = functionDao.load(Long.valueOf(id));
			function.setComplete(true);
			function.setCompleteTime(new Date());
			functionDao.update(function);
		}
	}

	@Override
	public void cancel(String ids) {
		String[] idArr = ids.split(",");
		SystemFunction function = null;
		for(String id : idArr){
			function = functionDao.load(Long.valueOf(id));
			function.setComplete(false);
			function.setCompleteTime(null);
			functionDao.update(function);
		}
	}
}
