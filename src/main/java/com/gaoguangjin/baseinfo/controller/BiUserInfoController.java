package com.gaoguangjin.baseinfo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.base.BaseController;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiEmail;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.baseinfo.service.BiUserInfoService;
import com.gaoguangjin.util.MD5;
import com.gaoguangjin.util.mail.Mail;

@Controller
public class BiUserInfoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BiUserInfoController.class);
	
	@Autowired
	private BiUserInfoService biUserInfoService;
	
	/**
	 * ���ܣ���ҳ��ѯ�б�
	 * @param biUserInfo
	 * @param pageNo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/biUserInfo/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPageList(BiUserInfo biUserInfo, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			// ����ַ�ŵ�session����
			this.setSessionListUrl(request);
			// ��ҳ����
			pageNo = pageNo == null ? 1 : pageNo;
			// һҳ��ʾ������
			int pageSize = super.getCookiesPageSize(request);
			Page pageData = biUserInfoService.getPagedBiUserInfo(biUserInfo, pageNo, pageSize);
			
			mav.addObject("biUserInfo", biUserInfo);
			mav.addObject("pageData", pageData);
			mav.setViewName("baseinfo/biUserInfo/bi_userInfo_list");
			
			logger.info("Ա�������ҳ��ѯ:" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("Ա�������ҳ��ѯʧ��" + biUserInfo.toString());
			throw e;
			
		}
		return mav;
		
	}
	
	/**
	 * ���ܣ�ajax��ȡ�û�
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/biUserInfo/getAjaxUserList", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object getAjaxUserList(String key, HttpServletRequest request) {
		BiUserInfo biUserInfo = new BiUserInfo();
		biUserInfo.setName(key);
		List<BiUserInfo> biUserInfoList = biUserInfoService.getUserList(biUserInfo);
		
		return String.valueOf(JSONArray.toJSON(biUserInfoList));
	}
	
	/**
	 * ���ܣ���ӵ���Ա����ͼ
	 * @return
	 */
	@RequestMapping(value = "/main/biUserInfo/addOne/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addOneView() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("baseinfo/biUserInfo/bi_userInfo_add_one_view");
		return mav;
		
	}
	
	// /**
	// * ���ܣ���Ӷ��Ա����ͼ
	// * @return
	// */
	// @RequestMapping(value = "/biUserInfo/addMore/view", method = { RequestMethod.GET, RequestMethod.POST })
	// public ModelAndView addMoreView() {
	// ModelAndView mav = new ModelAndView();
	//		
	// mav.setViewName("baseinfo/biUserInfo/bi_userInfo_add_more_view");
	// return mav;
	//		
	// }
	
	/**
	 * ���ܣ�����Ա����Ϣ
	 * @param biUserInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/biUserInfo/addOne/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveMoreUser(BiUserInfo biUserInfo, HttpServletRequest request) throws Exception {
		
		try {
			BiUserInfo user = this.getSessionUser(request);
			biUserInfo.setCreateUser(user);
			biUserInfo.setUpdateUser(user);
			biUserInfo.setPassWord(biUserInfo.getEmail());
			biUserInfoService.saveOneUser(biUserInfo);
			
			ModelAndView mav = addOneView();
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			logger.info("ע����Ϣ����ɹ�" + biUserInfo.toString());
			return mav;
		}
		catch (Exception e) {
			logger.info("ע����Ϣ����ʧ��" + biUserInfo.toString());
			throw e;
		}
		
	}
	
	/**
	 * ���ܣ�Ա����Ϣ������ͼ
	 * @param biUserInfo
	 * @return
	 */
	@RequestMapping(value = "/main/biUserInfo/update/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateView(BiUserInfo biUserInfo) {
		
		ModelAndView mav = new ModelAndView();
		
		if (null != biUserInfo) {
			biUserInfo = biUserInfoService.get(biUserInfo);
		}
		mav.addObject("biUserInfo", biUserInfo);
		
		mav.setViewName("/baseinfo/biUserInfo/bi_userInfo_update_view");
		return mav;
		
	}
	
	/**
	 * Servi�㲻�� �õ������ֵ�ٸ��¡� ���ܣ�����Ա������
	 * @param biUserInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/main/biUserInfo/update/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateSave(BiUserInfo biUserInfo, HttpServletRequest request) {
		
		BiUserInfo user = this.getSessionUser(request);
		biUserInfo.setUpdateUser(user);
		
		// �õ���ǰ���û�����
		BiUserInfo userInfo = biUserInfoService.get(biUserInfo);
		biUserInfo.setCreateDate(userInfo.getCreateDate());
		biUserInfo.setCreateUser(userInfo.getCreateUser());
		biUserInfo.setFlag("Y");
		
		biUserInfoService.update(biUserInfo);
		
		ModelAndView mav = updateView(biUserInfo);
		this.setMessageCode(this.MSG_SUCCESS);
		mav.addObject("messageCode", this.getMessageCode());
		return mav;
	}
	
	@RequestMapping(value = "/main/biUserInfo/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(BiUserInfo biUserInfo, HttpServletRequest request) {
		// ��ȡҪɾ����Ա����Ϣ
		if (null != biUserInfo) {
			biUserInfo = biUserInfoService.get(biUserInfo);
		}
		biUserInfoService.delete(biUserInfo);
		
		return "redirect:" + getSessionListUrl(request);
	}
	
	/**
	 * ���ܣ�ע�������Ϣ��ͼ
	 * 
	 * @param biUserInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/biUserInfo/singUp/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveUserView(BiUserInfo biUserInfo, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("foreground/singUp");
			logger.info("ע����Ϣ����ɹ�" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("ע����Ϣ����ʧ��" + biUserInfo.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ����浥��ע����Ϣ
	 * @param biUserInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/biUserInfo/singUp/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveOneUser(BiUserInfo biUserInfo, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			biUserInfoService.saveOneUser(biUserInfo);
			mav.setViewName("foreground/login");
			logger.info("ע����Ϣ����ɹ�" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("ע����Ϣ����ʧ��" + biUserInfo.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ��һظ���������ͼ
	 * 
	 */
	@RequestMapping(value = "/biUserInfo/pwReset/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView findPassword(HttpSession session, BiUserInfo biUserInfo, HttpServletRequest request)
			throws Exception {
		String step = "1";
		ModelAndView mav = new ModelAndView();
		this.setMessageCode("01");
		try {
			if (null != biUserInfo) {
				if (!StringUtils.isEmpty(biUserInfo.getStep())) {
					if (biUserInfo.getStep().equals("1")) {
						String code = (String) session.getAttribute("code");
						String nowCode = request.getParameter("nowCode");
						if (code.equals(nowCode)) {
							// ��ѯ���û��Ƿ����
							BiUserInfo user = biUserInfoService.findUser(biUserInfo);
							if (null != user) {
								biUserInfo.setStep("2");
								step = "2";
							}
							else {
								// �û�������
								this.setMessageCode("02");
							}
						}
						else {
							// ��֤�벻��
							this.setMessageCode("03");
						}
					}
					else if (biUserInfo.getStep().equals("2")) {
						// �ڶ�����������
						biUserInfo.setStep("3");
						step = "3";
					}
					else if (biUserInfo.getStep().equals("3")) {
						// �����µ�����
						biUserInfoService.saveResetPw(biUserInfo);
						mav.setViewName("foreground/login");
						return mav;
					}
				}
			}
			
			mav.setViewName("foreground/pwReset");
			mav.addObject("step", step);
			mav.addObject("messagecode", this.getMessageCode());
			mav.addObject("biUserInfo", biUserInfo);
			logger.info("�һ�������ͼ��" + step + "���ɹ�" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("�һ�������ͼ��" + step + "��ʧ��" + biUserInfo.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ��һ����뱣��
	 * 
	 */
	@RequestMapping(value = "/biUserInfo/pwReset/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView resetPassword(BiUserInfo biUserInfo, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("foreground/login");
			logger.info("ע����Ϣ����ɹ�" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("ע����Ϣ����ʧ��" + biUserInfo.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * �޸�����
	 * @return
	 */
	@RequestMapping(value = "/main/baseInfo/updatePassWord/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView modifyPassWordView(HttpServletRequest request) {
		ModelAndView andView = new ModelAndView();
		BiUserInfo biUserInfo = getSessionUser(request);
		biUserInfo = biUserInfoService.get(biUserInfo);
		andView.addObject("biUserInfo", biUserInfo);
		andView.setViewName("/baseinfo/biUserInfo/bi_user_update_pwd");
		return andView;
	}
	
	/**
	 * ���ܣ��޸����뱣��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/main/baseInfo/updatePassWord/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView modifyPassWordSave(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/baseinfo/biUserInfo/bi_user_update_pwd");
		String oldPassword = request.getParameter("oldPass");
		String newPassWord = request.getParameter("newPass");
		BiUserInfo biUserInfo = getSessionUser(request);
		
		Boolean flag = false;
		biUserInfo = biUserInfoService.get(biUserInfo);
		MD5 md5 = new MD5();
		// ������
		String passwordMd5 = md5.getMD5ofStr(oldPassword);
		if (biUserInfo.getPassWord().equals(passwordMd5)) {
			flag = true;
		}
		
		if (flag) {
			biUserInfo.setPassWord(md5.getMD5ofStr(newPassWord));
			biUserInfoService.updatePass(biUserInfo);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			
		}
		else {
			mav.addObject("passStyle", "has-error");
			super.setMessageCode("�����벻��ȷ");
			mav.addObject("message", super.getMessageCode());
		}
		mav.addObject("biUserInfo", biUserInfo);
		
		return mav;
	}
	
	/**
	 * ���ܣ�ajax��ȡ��������
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/biUserInfo/getEmail", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object getEmail(HttpServletRequest request, String key) throws Exception {
		List<BiEmail> list = new ArrayList<BiEmail>();
		list = null;
		if (key.contains("@")) {
			list = biUserInfoService.findEmail(key);
		}
		return String.valueOf(JSONArray.toJSON(list));
	}
	
	/**
	 * ���ܣ�ajax��ȡ�ʼ�����֤��
	 * @param request
	 * @param email
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/biUserInfo/getEmailCode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object getEmailCode(HttpServletRequest request, String email) throws Exception {
		JSONArray arrayJson = new JSONArray();
		JSONObject ja = new JSONObject();
		String str = Math.random() * 10000000 + "";
		if (str.length() > 5) {
			str = str.substring(0, 4);
		}
		StringBuffer content = new StringBuffer();
		content.append("�װ����û���<br><br>");
		content.append("���ã���л��ʹ��BUG���������ڽ���������֤�������������֤��Ϊ��<br><br><br>");
		content.append(str + "<br><br><br><br><br><br>");
		content.append("BUG�˺��Ŷ�");
		content.append(new Date().toLocaleString());
		Mail.sendMail(email, "BUG�˺�--���������֤", content.toString());
		ja.put("code", str);
		arrayJson.add(ja);
		return String.valueOf(arrayJson);
	}
	
}
