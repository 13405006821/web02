package com.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.controller.BaseController;
import com.base.util.Json;
import com.web.entity.SystemFunction;
import com.web.service.IFunctionService;

/**
 * ϵͳ����action
 * 
 * @author Liup
 */
@Controller
@RequestMapping("/systemFunction")
public class SystemFunctionController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	private List<SystemFunction> list = new ArrayList<SystemFunction>();
	
	@Resource
	private IFunctionService functionService;
	
	@RequestMapping
	public String execute() throws Exception {
		return "function/function_list";
	}
	
	@RequestMapping("/table")
	public String table(HttpServletRequest request, String functionContent, Boolean complete, Boolean index){
		list = functionService.findList(functionContent, complete, getStartIndex(request), getPageSize());
		request.setAttribute("list", list);
		request.setAttribute("pageSize", getPageSize());
		request.setAttribute("count", functionService.findCount(functionContent, complete));
		request.setAttribute("index", index);
		return "function/function_table";
	}

	@RequestMapping("/systemFunctionInfo")
	public String systemFunctionInfo(HttpServletRequest request, long id) throws Exception {
		request.setAttribute("systemFunction", functionService.getById(id));
		return "function/function_info";
	}
	
	@RequestMapping("/saveOrUpdate")
	public void saveOrUpdate(HttpServletResponse response, SystemFunction systemFunction) throws Exception {
		Json json = new Json();
		try {
			functionService.saveOrUpdate(systemFunction);
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeJson(response, json);
	}
	
	@RequestMapping("/delete")
	public void delete(HttpServletResponse response, long id) throws Exception {
		Json json = new Json();
		try {
			functionService.deleteFunction(id);
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeJson(response, json);
	}
	
	@RequestMapping("/deleteByIds")
	public void deleteByIds(HttpServletResponse response, String ids) throws Exception {
		Json json = new Json();
		try {
			functionService.deleteByIds(ids);
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeJson(response, json);
	}
	
	@RequestMapping("/complete")
	public void complete(HttpServletResponse response, String ids) throws Exception {
		Json json = new Json();
		try {
			functionService.complete(ids);
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeJson(response, json);
	}
	
	@RequestMapping("/cancel")
	public void cancel(HttpServletResponse response, String ids) throws Exception {
		Json json = new Json();
		try {
			functionService.cancel(ids);
			json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeJson(response, json);
	}
}