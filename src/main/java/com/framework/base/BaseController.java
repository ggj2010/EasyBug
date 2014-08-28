package com.framework.base;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.framework.constant.Global;
import com.framework.utils.Stat;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.project.entity.ProjectUser;

/**
 * 
 * <br>
 * <b>类描述:</b>
 * 
 * <pre>
 * 所有Controller的基类
 * </pre>
 * 
 * @see
 * @since
 */
public class BaseController {
	protected String messageCode = "01";
	
	/**
	 * 操作成功消息代码
	 */
	protected static final String MSG_SUCCESS = "01";
	
	/**
	 * 操作成功并打印
	 */
	protected static final String MSG_SUCCESS_PRINT = "03";
	
	/**
	 * 操作失败消息代码
	 */
	protected static final String MSG_ERROR = "02";
	
	/**
	 * 设置操作消息码
	 * @param code
	 */
	public void setMessageCode(String code) {
		this.messageCode = code;
	}
	
	/**
	 * 获取操作消息码
	 * @return
	 */
	public String getMessageCode() {
		return this.messageCode;
	}
	
	/**
	 * 从session中取得用户pageSize数据;
	 * @param request
	 * @return 如果session中不存在，则取出默认值；
	 */
	protected int getCookiesPageSize(HttpServletRequest request) {
		int pageSize = Global.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isEmpty(getCookiesValue("pageSize", request))) {
			pageSize = Integer.parseInt(getCookiesValue("pageSize", request));
		}
		return pageSize;
	}
	
	/**
	 * 从cookies 上取相应的值，如果找不到返回''
	 * @param key
	 * @param request
	 * @return
	 */
	protected String getCookiesValue(String key, HttpServletRequest request) {
		String keyval = "";
		Cookie cookies[] = request.getCookies(); // 将适用目录下所有Cookie读入并存入cookies数组中
		Cookie sCookie = null;
		String sname = null;
		if (cookies == null) // 如果没有任何cookie
		{
		}
		else {
			for (int i = 0; i < cookies.length; i++) // 循环列出所有可用的Cookie
			{
				sCookie = cookies[i];
				sname = sCookie.getName();
				if (sname.equals(key)) {
					keyval = sCookie.getValue();
				}
			}
		}
		
		return keyval;
	}
	
	/**
	 * 功能：写cookies到客户机
	 * @param key
	 * @param request
	 */
	protected void setCookiesValue(String key, String value, HttpServletRequest request) {
		String keyval = "";
		Cookie cookies[] = request.getCookies(); // 将适用目录下所有Cookie读入并存入cookies数组中
		Cookie sCookie = null;
		String sname = null;
		if (cookies == null) // 如果没有任何cookie
		{
		}
		else {
			for (int i = 0; i < cookies.length; i++) // 循环列出所有可用的Cookie
			{
				sCookie = cookies[i];
				sname = sCookie.getName();
				if (sname.equals(key)) {
					keyval = sCookie.getValue();
				}
			}
		}
	}
	
	/**
	 * 向session设置值
	 * @param key
	 * @param value void
	 * 
	 */
	protected void setSessionValue(String key, String value, HttpServletRequest request) {
		request.getSession().setAttribute(key, value);
	}
	
	/**
	 * 向session设置值
	 * @param key
	 * @param value
	 * @param request
	 */
	protected void setSessionObj(String key, Object value, HttpServletRequest request) {
		request.getSession().setAttribute(key, value);
	}
	
	/**
	 * 获取session中的参数值
	 * @param key
	 * @return String
	 * 
	 */
	protected String getSessionValue(String key, HttpServletRequest request) {
		return (String) request.getSession().getAttribute(key);
	}
	
	/**
	 *功能：把路径信息写入到session
	 * @param request
	 */
	public void setSessionListUrl(HttpServletRequest request) {
		String uri = request.getServletPath().toString();
		String queryString = request.getQueryString();
		String queryUrl = (uri + (null != queryString ? ("?" + queryString) : ""));
		Stat urlStat = (Stat) request.getSession().getAttribute("urlStat");
		if (null != urlStat) {
			urlStat.push(queryUrl);
		}
		else {
			urlStat = new Stat();
			urlStat.push(queryUrl);
		}
		request.getSession().setAttribute("urlStat", urlStat);
	}
	
	/**
	 * 获取用户请求地址及参数
	 * @param request
	 * @return
	 */
	public String getSessionListUrl(HttpServletRequest request) {
		// 主界面
		String url = "/main/index.do";
		// 得到这个地址
		Stat urlStat = (Stat) request.getSession().getAttribute("urlStat");
		if (null != urlStat) {
			url = urlStat.pop().toString();
		}
		return url;
	}
	
	/**
	 * 功能：查看当前登录用户信息
	 * @param request
	 * @return 返回BiUserInfo对象
	 */
	protected BiUserInfo getSessionUser(HttpServletRequest request) {
		return (BiUserInfo) request.getSession().getAttribute(Global.USER_INFO);
	}
	
	/**
	 * 功能：查看当前登录岗位
	 * @param request
	 * @return 返回BiUserInfo对象
	 */
	protected ProjectUser getRoleProjectUser(HttpServletRequest request) {
		return (ProjectUser) request.getSession().getAttribute(Global.USER_ROLE);
	}
	
}
