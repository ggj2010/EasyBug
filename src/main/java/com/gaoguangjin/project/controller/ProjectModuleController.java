package com.gaoguangjin.project.controller;

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
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.project.entity.Project;
import com.gaoguangjin.project.entity.ProjectModule;
import com.gaoguangjin.project.entity.ProjectVO;
import com.gaoguangjin.project.service.ProjectModuleService;
import com.gaoguangjin.project.service.ProjectService;

@Controller
@RequestMapping("/main")
public class ProjectModuleController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectModuleController.class);
	@Autowired
	ProjectModuleService projectModuleService;
	@Autowired
	ProjectService projectService;
	
	/**
	 * 功能：添加模块视图
	 * @param projectModule
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectModule/add/view", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addProjectModulView(ProjectVO projectVO, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (null != projectVO) {
			if (!StringUtils.isEmpty(projectVO.getId())) {
				Project project = projectService.get(projectVO.getId());
				projectVO.setProject(project);
			}
		}
		
		mav.addObject("ProjectVO", projectVO);
		mav.setViewName("/project/projectModule/module_add_view");
		
		return mav;
	}
	
	/**
	 * 功能：保存模块
	 * @param projectModule
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projectModule/add/save", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView saveProjectModule(ProjectModule projectModule, HttpServletRequest request) throws Exception {
		try {
			
			projectModuleService.saveModule(projectModule);
			
			ModelAndView mav = addProjectModulView(new ProjectVO(), request);
			mav.addObject("projectModule", projectModule);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			
			logger.error("projectModule保存成功");
			return mav;
		}
		catch (Exception e) {
			logger.error("projectModule添加失败");
			throw e;
		}
	}
	
	/**
	 * 功能：ajax删除模块
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectModule/ajaxDelte")
	public @ResponseBody
	Object deleteModule(BugContent bugContent, HttpServletRequest request) throws Exception {
		try {
			String jason = projectModuleService.deleteModule(bugContent);
			
			logger.error("projectModule删除成功");
			return jason;
		}
		catch (Exception e) {
			logger.error("projectModule删除失败");
			throw e;
		}
	}
	
}
