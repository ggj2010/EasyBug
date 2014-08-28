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
	 * 功能：分页查询列表
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
			// 将地址放到session里面
			this.setSessionListUrl(request);
			// 分页参数
			pageNo = pageNo == null ? 1 : pageNo;
			// 一页显示多少条
			int pageSize = super.getCookiesPageSize(request);
			Page pageData = biUserInfoService.getPagedBiUserInfo(biUserInfo, pageNo, pageSize);
			
			mav.addObject("biUserInfo", biUserInfo);
			mav.addObject("pageData", pageData);
			mav.setViewName("baseinfo/biUserInfo/bi_userInfo_list");
			
			logger.info("员工管理分页查询:" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("员工管理分页查询失败" + biUserInfo.toString());
			throw e;
			
		}
		return mav;
		
	}
	
	/**
	 * 功能：ajax获取用户
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
	 * 功能：添加单个员工视图
	 * @return
	 */
	@RequestMapping(value = "/main/biUserInfo/addOne/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addOneView() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("baseinfo/biUserInfo/bi_userInfo_add_one_view");
		return mav;
		
	}
	
	// /**
	// * 功能；添加多个员工视图
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
	 * 功能：保存员工信息
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
			logger.info("注册信息保存成功" + biUserInfo.toString());
			return mav;
		}
		catch (Exception e) {
			logger.info("注册信息保存失败" + biUserInfo.toString());
			throw e;
		}
		
	}
	
	/**
	 * 功能：员工信息更新视图
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
	 * Servi层不能 得到对象的值再更新。 功能：保存员工更新
	 * @param biUserInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/main/biUserInfo/update/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateSave(BiUserInfo biUserInfo, HttpServletRequest request) {
		
		BiUserInfo user = this.getSessionUser(request);
		biUserInfo.setUpdateUser(user);
		
		// 得到以前的用户对象
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
		// 获取要删除的员工信息
		if (null != biUserInfo) {
			biUserInfo = biUserInfoService.get(biUserInfo);
		}
		biUserInfoService.delete(biUserInfo);
		
		return "redirect:" + getSessionListUrl(request);
	}
	
	/**
	 * 功能：注册个人信息视图
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
			logger.info("注册信息保存成功" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("注册信息保存失败" + biUserInfo.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：保存单个注册信息
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
			logger.info("注册信息保存成功" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("注册信息保存失败" + biUserInfo.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：找回个人密码视图
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
							// 查询该用户是否存在
							BiUserInfo user = biUserInfoService.findUser(biUserInfo);
							if (null != user) {
								biUserInfo.setStep("2");
								step = "2";
							}
							else {
								// 用户不存在
								this.setMessageCode("02");
							}
						}
						else {
							// 验证码不对
							this.setMessageCode("03");
						}
					}
					else if (biUserInfo.getStep().equals("2")) {
						// 第二步不做处理
						biUserInfo.setStep("3");
						step = "3";
					}
					else if (biUserInfo.getStep().equals("3")) {
						// 保存新的密码
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
			logger.info("找回密码视图第" + step + "步成功" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("找回密码视图第" + step + "步失败" + biUserInfo.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：找回密码保存
	 * 
	 */
	@RequestMapping(value = "/biUserInfo/pwReset/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView resetPassword(BiUserInfo biUserInfo, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("foreground/login");
			logger.info("注册信息保存成功" + biUserInfo.toString());
		}
		catch (Exception e) {
			logger.info("注册信息保存失败" + biUserInfo.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 修改密码
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
	 * 功能：修改密码保存
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
		// 旧密码
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
			super.setMessageCode("旧密码不正确");
			mav.addObject("message", super.getMessageCode());
		}
		mav.addObject("biUserInfo", biUserInfo);
		
		return mav;
	}
	
	/**
	 * 功能：ajax获取邮箱类型
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
	 * 功能：ajax获取邮件的验证码
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
		content.append("亲爱的用户：<br><br>");
		content.append("您好！感谢您使用BUG服务，您正在进行邮箱验证，本次请求的验证码为：<br><br><br>");
		content.append(str + "<br><br><br><br><br><br>");
		content.append("BUG账号团队");
		content.append(new Date().toLocaleString());
		Mail.sendMail(email, "BUG账号--邮箱身份验证", content.toString());
		ja.put("code", str);
		arrayJson.add(ja);
		return String.valueOf(arrayJson);
	}
	
}
