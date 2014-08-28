package com.gaoguangjin.baseinfo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.framework.constant.Global;
import com.gaoguangjin.baseinfo.entity.BiRoleMenu;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.baseinfo.service.BiRoleMenuService;
import com.gaoguangjin.baseinfo.service.BiUserInfoService;
import com.gaoguangjin.count.service.BugContentCountService;
import com.gaoguangjin.count.vo.BugCountVO;
import com.gaoguangjin.project.entity.ProjectUser;
import com.gaoguangjin.project.service.ProjectUserService;
import com.gaoguangjin.util.MD5;

@Controller
public class LoginController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	final static String USER_USERNAME_NOT_FOUND = "01";// δ��ƥ����û���
	final static String USER_PASSWORD_FAIL = "02"; // �������
	
	@Autowired
	private BiUserInfoService biUserInfoService;
	
	@Autowired
	private ProjectUserService projectUserService;
	
	@Autowired
	private BiRoleMenuService biRoleMenuService;
	@Autowired
	private BugContentCountService bugCountService;
	
	private final MD5 md5 = new MD5();
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/dologin", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView doLogin(HttpSession session, BiUserInfo biUserInfo, HttpServletRequest request)
			throws Exception {
		ModelAndView view = new ModelAndView();
		String viewName = "/foreground/login";
		// ���ھͷ���trues
		BiUserInfo user = biUserInfoService.findUser(biUserInfo);
		
		// �˻�������
		if (null != user) {
			if (!md5.getMD5ofStr(biUserInfo.getPassWord()).equals(user.getPassWord())) {
				// ���벻ƥ��
				super.setMessageCode(USER_PASSWORD_FAIL);
				view.addObject("passStyle", "has-error");
				view.addObject("email", user.getEmail());
			}
			else {
				viewName = "index";
				session.setAttribute(Global.USER_INFO, user);
				// ����session��ֵ
				setSession(session, request);
			}
		}
		else {
			// �û�������
			super.setMessageCode(USER_USERNAME_NOT_FOUND);
			view.addObject("emailStyle", "has-error");
		}
		
		view.addObject("messagecode", super.getMessageCode());
		view.setViewName(viewName);
		return view;
	}
	
	private void setSession(HttpSession session, HttpServletRequest request) {
		// ��ť��
		List<BiRoleMenu> allMenuList = new ArrayList<BiRoleMenu>();
		// Ȩ�ް�ť�ַ���
		String buttonString = "123";
		// �û���ǰ��λ��ɫ.
		ProjectUser roleProjectUser = new ProjectUser();
		
		// ��ѯԱ����������,����д��session
		BiUserInfo userInfo = super.getSessionUser(request);
		
		// �õ��û��ĸ�λ
		List<ProjectUser> projectUserList = projectUserService.findProjectUser(userInfo);
		// �ж��û��Ƿ����ø�λ�����û�У����һ���ο͸�λ��
		if (projectUserList.size() < 1) {
			// �ο͵�id��д���ġ�
			allMenuList = biRoleMenuService.findRoleMenuByRole("402881e74573f9e20145743446b00060");
			buttonString = biRoleMenuService.finRoleMenuButton("402881e74573f9e20145743446b00060");
		}
		else {
			roleProjectUser = projectUserList.get(0);
			allMenuList = biRoleMenuService.findMenuByRole(projectUserList.get(0).getBiRole().getId());
			buttonString = biRoleMenuService.finRoleMenuButton(projectUserList.get(0).getId());
			// ѭ���Ƿ���ڸ�λ��Ĭ�ϵ�
			for (ProjectUser userProject : projectUserList) {
				if (userProject.getIsDefault().equals("Y")) {
					roleProjectUser = userProject;
					allMenuList = biRoleMenuService.findMenuByRole(userProject.getBiRole().getId());
					buttonString = biRoleMenuService.finRoleMenuButton(userProject.getBiRole().getId());
				}
			}
			
			// ���ݸ�λ���bug
			BugCountVO bugCountVO = new BugCountVO();
			bugCountVO.setProjectId(roleProjectUser.getProject().getId());
			List<String[]> sumList = bugCountService.findStatus(bugCountVO);
			int sum = 0;
			int notFixed = 0;
			int solved = 0;
			int fixed = 0;
			for (int i = 0; i < sumList.size(); i++) {
				Object[] str = sumList.get(i);
				sum += Integer.parseInt(str[2].toString());
				if (str[1].toString().equals("fixed")) {
					fixed += Integer.parseInt(str[2].toString());
				}
				else if (str[1].toString().equals("solved")) {
					solved += Integer.parseInt(str[2].toString());
				}
				else if (str[1].toString().equals("open") || str[1].toString().equals("resolved")) {
					notFixed += Integer.parseInt(str[2].toString());
				}
			}
			
			session.setAttribute("sum", sum);
			session.setAttribute("notFixed", notFixed);
			session.setAttribute("solved", solved);
			session.setAttribute("fixed", fixed);
			
		}
		
		session.setAttribute(Global.USER_ALL_MENU, allMenuList);
		session.setAttribute(Global.USER_ROLE, roleProjectUser);
		session.setAttribute(Global.USER_BUTTON_STR, buttonString);
		session.setAttribute(Global.USER_ROLE_LIST, projectUserList);
		
	}
	
	/**
	 * ���ܣ���ת����ҳ
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/main/index", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpSession session, HttpServletRequest request) {
		ModelAndView andView = new ModelAndView("index");
		andView.addObject("today", new Date());
		return andView;
	}
	
	/**
	 * ���ܣ��л��û�
	 * @return
	 */
	@RequestMapping(value = "/main/changeRole/save", method = RequestMethod.GET)
	public ModelAndView doChangeRole(HttpServletRequest request, ProjectUser projectUser, HttpSession session)
			throws Exception {
		ModelAndView view = null;
		try {
			view = new ModelAndView();
			// ִ�и���
			ProjectUser user = projectUserService.getProjectUser(projectUser);
			user.setIsDefault(ProjectUser.IS_DEFAULT);
			projectUserService.update(user);
			setSession(session, request);
			
			view.setViewName("index");
			logger.info("�л���λ�ɹ�");
		}
		catch (Exception e) {
			logger.error("�л���λʧ��", e);
			throw e;
		}
		return view;
		
	}
	
	/**
	 * ���ܣ��Ƴ�ϵͳ
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/dologout", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView doLogout(HttpSession session) {
		ModelAndView view = new ModelAndView();
		session.removeAttribute(Global.USER_INFO);
		session.removeAttribute(Global.USER_ALL_MENU);
		session.removeAttribute(Global.USER_ROLE);
		session.removeAttribute(Global.USER_BUTTON_STR);
		session.removeAttribute(Global.USER_ROLE_LIST);
		session.removeAttribute("sum");
		session.removeAttribute("notFixed");
		session.removeAttribute("solved");
		session.removeAttribute("fixed");
		view.setViewName("/foreground/login");
		return view;
	}
	
}
