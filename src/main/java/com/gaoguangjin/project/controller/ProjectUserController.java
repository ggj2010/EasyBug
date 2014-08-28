package com.gaoguangjin.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.gaoguangjin.baseinfo.entity.BiRole;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.baseinfo.service.BiRoleService;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.project.entity.Project;
import com.gaoguangjin.project.entity.ProjectUser;
import com.gaoguangjin.project.entity.ProjectVO;
import com.gaoguangjin.project.service.ProjectService;
import com.gaoguangjin.project.service.ProjectUserService;

@Controller
@RequestMapping("/main")
public class ProjectUserController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectUserController.class);
	@Autowired
	ProjectUserService projectUserService;
	@Autowired
	ProjectService projectService;
	
	@Autowired
	BiRoleService biRoleService;
	
	/**
	 * ���ܣ����Ա����ͼ
	 * @param projectModule
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectUser/add/view", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addProjectUserView(ProjectVO projectVO, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		if (null != projectVO) {
			if (!StringUtils.isEmpty(projectVO.getId())) {
				Project project = projectService.get(projectVO.getId());
				projectVO.setProject(project);
			}
		}
		// ��ѯ���н�ɫ
		List<BiRole> roleList = biRoleService.findBiRole();
		
		mav.addObject("roleList", roleList);
		mav.addObject("ProjectVO", projectVO);
		mav.setViewName("/project/projectUser/user_add_view");
		
		return mav;
	}
	
	/**
	 * ���ܣ������û�
	 * @param projectModule
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projectUser/add/save", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView saveProjectUser(ProjectUser projectUser, HttpServletRequest request) throws Exception {
		
		try {
			projectUserService.saveUser(projectUser);
			
			ModelAndView mav = addProjectUserView(new ProjectVO(), request);
			mav.addObject("projectUser", projectUser);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			
			logger.error("projectUser����ɹ�");
			return mav;
			
		}
		catch (Exception e) {
			logger.error("projectUser���ʧ��");
			throw e;
		}
	}
	
	/**
	 * ���ܣ�ajaxɾ����Ŀ�û�
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectUser/ajaxDelte")
	public @ResponseBody
	Object deleteVersion(BugContent bugContent, HttpServletRequest request) throws Exception {
		try {
			String jason = projectUserService.deleteUser(bugContent);
			
			logger.error("projectModuleɾ���ɹ�");
			return jason;
		}
		catch (Exception e) {
			logger.error("projectModuleɾ��ʧ��");
			throw e;
		}
	}
	
	/**
	 * ���ܣ����ؽ�����Ϣ�ķ�ҳ��Ϣ
	 * 
	 * @param pageNo
	 * @param biClassRoom
	 * @param request
	 * @return ���ز�ѯ���Ľ�����Ϣ��ModelAndView
	 */
	@RequestMapping(value = "/projectUser/changeRole/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView changeRoleView(HttpServletRequest request) throws Exception {
		
		ModelAndView view = new ModelAndView();
		try {
			// List<BiUserRole> roleList=getSessionBiUserRoleList(request);
			
			// ��ѯԱ����������,����д��session
			BiUserInfo biUserInfo = super.getSessionUser(request);
			
			// �õ��û��ĸ�λ
			List<ProjectUser> roleList = projectUserService.findProjectUser(biUserInfo);
			view.addObject("roleList", roleList);
			view.addObject("biUserInfo", biUserInfo);
			view.setViewName("/project/projectUser/user_change_role");
			
			logger.info("�л���λ��ѯ�ɹ�");
		}
		catch (Exception e) {
			logger.error("�л���λ��ѯʧ��", e);
			
			throw e;
		}
		return view;
	}
	
}
