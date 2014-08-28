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
 * <b>������:</b>
 * 
 * <pre>
 * ����Controller�Ļ���
 * </pre>
 * 
 * @see
 * @since
 */
public class BaseController {
	protected String messageCode = "01";
	
	/**
	 * �����ɹ���Ϣ����
	 */
	protected static final String MSG_SUCCESS = "01";
	
	/**
	 * �����ɹ�����ӡ
	 */
	protected static final String MSG_SUCCESS_PRINT = "03";
	
	/**
	 * ����ʧ����Ϣ����
	 */
	protected static final String MSG_ERROR = "02";
	
	/**
	 * ���ò�����Ϣ��
	 * @param code
	 */
	public void setMessageCode(String code) {
		this.messageCode = code;
	}
	
	/**
	 * ��ȡ������Ϣ��
	 * @return
	 */
	public String getMessageCode() {
		return this.messageCode;
	}
	
	/**
	 * ��session��ȡ���û�pageSize����;
	 * @param request
	 * @return ���session�в����ڣ���ȡ��Ĭ��ֵ��
	 */
	protected int getCookiesPageSize(HttpServletRequest request) {
		int pageSize = Global.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isEmpty(getCookiesValue("pageSize", request))) {
			pageSize = Integer.parseInt(getCookiesValue("pageSize", request));
		}
		return pageSize;
	}
	
	/**
	 * ��cookies ��ȡ��Ӧ��ֵ������Ҳ�������''
	 * @param key
	 * @param request
	 * @return
	 */
	protected String getCookiesValue(String key, HttpServletRequest request) {
		String keyval = "";
		Cookie cookies[] = request.getCookies(); // ������Ŀ¼������Cookie���벢����cookies������
		Cookie sCookie = null;
		String sname = null;
		if (cookies == null) // ���û���κ�cookie
		{
		}
		else {
			for (int i = 0; i < cookies.length; i++) // ѭ���г����п��õ�Cookie
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
	 * ���ܣ�дcookies���ͻ���
	 * @param key
	 * @param request
	 */
	protected void setCookiesValue(String key, String value, HttpServletRequest request) {
		String keyval = "";
		Cookie cookies[] = request.getCookies(); // ������Ŀ¼������Cookie���벢����cookies������
		Cookie sCookie = null;
		String sname = null;
		if (cookies == null) // ���û���κ�cookie
		{
		}
		else {
			for (int i = 0; i < cookies.length; i++) // ѭ���г����п��õ�Cookie
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
	 * ��session����ֵ
	 * @param key
	 * @param value void
	 * 
	 */
	protected void setSessionValue(String key, String value, HttpServletRequest request) {
		request.getSession().setAttribute(key, value);
	}
	
	/**
	 * ��session����ֵ
	 * @param key
	 * @param value
	 * @param request
	 */
	protected void setSessionObj(String key, Object value, HttpServletRequest request) {
		request.getSession().setAttribute(key, value);
	}
	
	/**
	 * ��ȡsession�еĲ���ֵ
	 * @param key
	 * @return String
	 * 
	 */
	protected String getSessionValue(String key, HttpServletRequest request) {
		return (String) request.getSession().getAttribute(key);
	}
	
	/**
	 *���ܣ���·����Ϣд�뵽session
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
	 * ��ȡ�û������ַ������
	 * @param request
	 * @return
	 */
	public String getSessionListUrl(HttpServletRequest request) {
		// ������
		String url = "/main/index.do";
		// �õ������ַ
		Stat urlStat = (Stat) request.getSession().getAttribute("urlStat");
		if (null != urlStat) {
			url = urlStat.pop().toString();
		}
		return url;
	}
	
	/**
	 * ���ܣ��鿴��ǰ��¼�û���Ϣ
	 * @param request
	 * @return ����BiUserInfo����
	 */
	protected BiUserInfo getSessionUser(HttpServletRequest request) {
		return (BiUserInfo) request.getSession().getAttribute(Global.USER_INFO);
	}
	
	/**
	 * ���ܣ��鿴��ǰ��¼��λ
	 * @param request
	 * @return ����BiUserInfo����
	 */
	protected ProjectUser getRoleProjectUser(HttpServletRequest request) {
		return (ProjectUser) request.getSession().getAttribute(Global.USER_ROLE);
	}
	
}
