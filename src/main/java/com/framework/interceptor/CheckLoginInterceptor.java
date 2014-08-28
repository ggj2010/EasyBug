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
		long endTime = System.currentTimeMillis();// 2������ʱ��
		long beginTime = startTimeThreadLocal.get();// �õ��̰߳󶨵ľֲ���������ʼʱ�䣩
		long consumeTime = endTime - beginTime;// 3�����ĵ�ʱ��
		
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
		long beginTime = System.currentTimeMillis();// 1����ʼʱ��
		startTimeThreadLocal.set(beginTime);// �̰߳󶨱���������
		String headerStr = request.getHeader("accept");
		// ����ĵ�½�˾Ͳ��õ�½��ֱ�ӷ���true
		if (request != null && request.getSession().getAttribute(FILTERED_REQUEST) != null) {
			return true;
		}
		else {
			// �õ��û���½��Ϣ����
			BiUserInfo biUserInfo = getBiUserInfo(request);
			
			if (biUserInfo == null) {
				// ���������ĵ�ַ��/main/*.do��(/main/dologin.do)
				String toUrl = request.getServletPath().toString();
				// ��������ĵ�ַ�ǣ�/main/aa.do?a=b��
				// "a=b"==request.getQueryString()��ȡ�ύ������
				if (!StringUtils.isEmpty(request.getQueryString())) {
					toUrl += "?" + request.getQueryString();
				}
				// ��session��������ֵ��LOGIN_TO_URL�����ѵ�ַ����
				request.getSession().setAttribute(Global.LOGIN_TO_URL, toUrl);
				
				// ���ε�½��ʱ��headerStr==(text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8)
				if (headerStr.contains("json")) {
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					response.getWriter().println(
							"{\"result\":\"" + StatusCode.LOGOUT + "\",\"errorInfo\":\"�û��ỰʧЧ�������µ�¼\"}");
					return false;
				}
				else {
					// ������http://localhost:8080/tms/��,�ͻ�����web.xml���õ�<welcome-file-list>
					request.getRequestDispatcher("/").forward(request, response);
					logger.info("�û�����url:" + toUrl);
					logger.info("�û�δ��¼����ת����¼ҳ��");
					return false;
				}
			}
			else {
				// �û������ڿգ�������requset ����ֵ.
				request.getSession().setAttribute(FILTERED_REQUEST, Boolean.TRUE);
			}
		}
		return true;
	}
	
	/**
	 * �õ��û��ĵ�½��Ϣ��c��session������Global.USER_INFO
	 * @param request
	 * @return BiUserInfo����
	 */
	private BiUserInfo getBiUserInfo(HttpServletRequest request) {
		return (BiUserInfo) request.getSession().getAttribute(Global.USER_INFO);
	}
	
}
