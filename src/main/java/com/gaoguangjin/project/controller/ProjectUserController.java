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
	 * 功能：添加员工视图
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
		// 查询所有角色
		List<BiRole> roleList = biRoleService.findBiRole();
		
		mav.addObject("roleList", roleList);
		mav.addObject("ProjectVO", projectVO);
		mav.setViewName("/project/projectUser/user_add_view");
		
		return mav;
	}
	
	/**
	 * 功能：保存用户
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
			
			logger.error("projectUser保存成功");
			return mav;
			
		}
		catch (Exception e) {
			logger.error("projectUser添加失败");
			throw e;
		}
	}
	
	/**
	 * 功能：ajax删除项目用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectUser/ajaxDelte")
	public @ResponseBody
	Object deleteVersion(BugContent bugContent, HttpServletRequest request) throws Exception {
		try {
			String jason = projectUserService.deleteUser(bugContent);
			
			logger.error("projectModule删除成功");
			return jason;
		}
		catch (Exception e) {
			logger.error("projectModule删除失败");
			throw e;
		}
	}
	
	/**
	 * 功能：返回教室信息的分页信息
	 * 
	 * @param pageNo
	 * @param biClassRoom
	 * @param request
	 * @return 返回查询出的教室信息的ModelAndView
	 */
	@RequestMapping(value = "/projectUser/changeRole/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView changeRoleView(HttpServletRequest request) throws Exception {
		
		ModelAndView view = new ModelAndView();
		try {
			// List<BiUserRole> roleList=getSessionBiUserRoleList(request);
			
			// 查询员工姓名对象,将其写入session
			BiUserInfo biUserInfo = super.getSessionUser(request);
			
			// 得到用户的岗位
			List<ProjectUser> roleList = projectUserService.findProjectUser(biUserInfo);
			view.addObject("roleList", roleList);
			view.addObject("biUserInfo", biUserInfo);
			view.setViewName("/project/projectUser/user_change_role");
			
			logger.info("切换岗位查询成功");
		}
		catch (Exception e) {
			logger.error("切换岗位查询失败", e);
			
			throw e;
		}
		return view;
	}
	
}
