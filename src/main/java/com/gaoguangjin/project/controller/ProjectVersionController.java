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
import com.gaoguangjin.project.entity.ProjectVO;
import com.gaoguangjin.project.entity.ProjectVersion;
import com.gaoguangjin.project.service.ProjectService;
import com.gaoguangjin.project.service.ProjectVersionService;

@Controller
@RequestMapping("/main")
public class ProjectVersionController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectVersionController.class);
	@Autowired
	ProjectVersionService projectVersionService;
	@Autowired
	ProjectService projectService;
	
	/**
	 * ���ܣ���Ӱ汾��ͼ
	 * @param projectModule
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectVersion/add/view", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addProjectVersionView(ProjectVO projectVO, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		if (null != projectVO) {
			if (!StringUtils.isEmpty(projectVO.getId())) {
				Project project = projectService.get(projectVO.getId());
				projectVO.setProject(project);
			}
		}
		
		mav.addObject("ProjectVO", projectVO);
		mav.setViewName("/project/projectVersion/version_add_view");
		
		return mav;
	}
	
	/**
	 * ���ܣ�����汾
	 * @param projectVersion
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projectVersion/add/save", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView saveProjectVersion(ProjectVersion projectVersion, HttpServletRequest request) throws Exception {
		
		try {
			
			projectVersionService.saveVersion(projectVersion);
			
			ModelAndView mav = addProjectVersionView(new ProjectVO(), request);
			mav.addObject("projectVersion", projectVersion);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			
			logger.error("projectVersion����ɹ�");
			return mav;
			
		}
		catch (Exception e) {
			logger.error("projectVersion���ʧ��");
			throw e;
		}
	}
	
	/**
	 * ���ܣ�ajaxɾ��ģ��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectVersion/ajaxDelte")
	public @ResponseBody
	Object deleteVersion(BugContent bugContent, HttpServletRequest request) throws Exception {
		try {
			String jason = projectVersionService.deleteVersion(bugContent);
			
			logger.error("projectModuleɾ���ɹ�");
			return jason;
		}
		catch (Exception e) {
			logger.error("projectModuleɾ��ʧ��");
			throw e;
		}
	}
	
}
