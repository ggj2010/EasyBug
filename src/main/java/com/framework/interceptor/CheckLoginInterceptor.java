package com.framework.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.framework.constant.Global;
import com.framework.constant.StatusCode;
import com.gaoguangjin.baseinfo.dao.BiUserLogDao;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.baseinfo.entity.BiUserLog;

public class CheckLoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private BiUserLogDao biUserLogDao;
	
	private static final String FILTERED_REQUEST = "@@session_context_filtered_request";
	private static final Logger logger = LoggerFactory.getLogger(CheckLoginInterceptor.class);
	
	private final NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		long endTime = System.currentTimeMillis();// 2、结束时间
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long consumeTime = endTime - beginTime;// 3、消耗的时间
		
		String ip = request.getHeader("x-forwarded-for");
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		Map map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		
		BiUserLog bul = new BiUserLog();
		bul.setBiUserInfo((BiUserInfo) request.getSession().getAttribute(Global.USER_INFO));
		bul.setCustomIP(ip);
		bul.setTimes(consumeTime);
		bul.setUrl(request.getRequestURI());
		bul.setTitle(request.getRequestURI());
		bul.setContent(String.valueOf(JSONArray.toJSON(map)));
		bul.setCreateDate(new Date());
		bul.setIsFormat("N");
		biUserLogDao.save(bul);
		
	}
	
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView mav)
			throws Exception {
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数
		String headerStr = request.getHeader("accept");
		// 如果的登陆了就不用登陆，直接返回true
		if (request != null && request.getSession().getAttribute(FILTERED_REQUEST) != null) {
			return true;
		}
		else {
			// 得到用户登陆信息对象。
			BiUserInfo biUserInfo = getBiUserInfo(request);
			
			if (biUserInfo == null) {
				// 浏览器输入的地址（/main/*.do）(/main/dologin.do)
				String toUrl = request.getServletPath().toString();
				// 比如输入的地址是（/main/aa.do?a=b）
				// "a=b"==request.getQueryString()获取提交的内容
				if (!StringUtils.isEmpty(request.getQueryString())) {
					toUrl += "?" + request.getQueryString();
				}
				// 向session里面设置值“LOGIN_TO_URL”，把地址给他
				request.getSession().setAttribute(Global.LOGIN_TO_URL, toUrl);
				
				// 初次登陆的时候，headerStr==(text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8)
				if (headerStr.contains("json")) {
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					response.getWriter().println(
							"{\"result\":\"" + StatusCode.LOGOUT + "\",\"errorInfo\":\"用户会话失效，需重新登录\"}");
					return false;
				}
				else {
					// 跳到（http://localhost:8080/tms/）,就会跳到web.xml配置的<welcome-file-list>
					request.getRequestDispatcher("/").forward(request, response);
					logger.info("用户请求url:" + toUrl);
					logger.info("用户未登录，跳转到登录页面");
					return false;
				}
			}
			else {
				// 用户不等于空，向拦截requset 设置值.
				request.getSession().setAttribute(FILTERED_REQUEST, Boolean.TRUE);
			}
		}
		return true;
	}
	
	/**
	 * 得到用户的登陆信息，c从session里面获得Global.USER_INFO
	 * @param request
	 * @return BiUserInfo对象
	 */
	private BiUserInfo getBiUserInfo(HttpServletRequest request) {
		return (BiUserInfo) request.getSession().getAttribute(Global.USER_INFO);
	}
	
}
