package com.base.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;

import com.alibaba.fastjson.JSON;
import com.base.util.Page;

/**
 * 基础controller，实现通用方法
 * 
 * @author liupeng
 */
public abstract class BaseController extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	// 分页使用
	private Page pager;
	private int startIndex = 1;
	private int pageSize = 10;

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(HttpServletResponse response, Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object,
					"yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/json;charset=utf-8");
			System.out.println(json);
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getStartIndex(HttpServletRequest request) {
		String pageOffset = request.getParameter("pager.offset");
		if (StringUtils.isNotBlank(pageOffset)) {
			try {
				startIndex = Math.max(1, Integer.parseInt(pageOffset) + 1);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page getPager() {
		return pager;
	}

	public void setPager(Page pager) {
		this.pager = pager;
	}
}