package com.gaoguangjin.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.project.entity.Project;
import com.gaoguangjin.project.entity.ProjectVO;
import com.gaoguangjin.project.service.ProjectService;

@Controller
@RequestMapping("/main")
public class ProjectController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value = "/project/index.do")
	public ModelAndView indexView() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/project/project/project_index");
		
		return mav;
	}
	
	/**
	 * ���ܣ�����������Դ
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/project/getTreeData")
	public @ResponseBody
	String getTreeData(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		String str = projectService.getTreeData();
		
		return str;
	}
	
	/**
	 * ���ܣ��鿴��Ŀҳ��
	 * @param project
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/project/show")
	public ModelAndView show(Project project, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		ProjectVO projectVO = projectService.findProjectVO(project);
		
		mav.addObject("projectVO", projectVO);
		mav.setViewName("/project/project/project_show");
		
		return mav;
	}
	
	/**
	 * ���ܣ������Ŀ��ͼ
	 * @param projectVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/project/add/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addProjectView(ProjectVO projectVO, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("projectVO", projectVO);
		mav.setViewName("/project/project/project_add_view");
		return mav;
	}
	
	/**
	 * ���ܣ�������Ŀ
	 * @param projectVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/project/add/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveProject(ProjectVO projectVO, HttpServletRequest request) throws Exception {
		try {
			
			BiUserInfo userinfo = getSessionUser(request);
			projectVO.getProject().setCreateUser(userinfo);
			projectVO = projectService.saveProject(projectVO);
			
			ModelAndView mav = addProjectView(projectVO, request);
			mav.addObject("projectVO", projectVO);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			
			logger.error("Project��ӳɹ�");
			return mav;
			
		}
		catch (Exception e) {
			logger.error("Project���ʧ��");
			throw e;
		}
	}
	
	/**
	 * ���ܣ���Ŀ������ͼ
	 * @param biUserInfo
	 * @return
	 */
	@RequestMapping(value = "/project/update/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateView(Project project) {
		
		ModelAndView mav = new ModelAndView();
		
		if (null != project) {
			project = projectService.get(project.getId());
		}
		mav.addObject("project", project);
		
		mav.setViewName("/project/project/project_update_view");
		return mav;
		
	}
	
	/**
	 * ���ܣ�������Ŀ
	 * @param project
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/project/update/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateSave(Project project, HttpServletRequest request) {
		
		BiUserInfo user = this.getSessionUser(request);
		project.setUpdateUser(user);
		
		// �õ���ǰ���û�����
		Project pro = projectService.get(project.getId());
		project.setCreateDate(pro.getCreateDate());
		project.setCreateUser(pro.getCreateUser());
		project.setFlag("Y");
		
		projectService.update(project);
		
		ModelAndView mav = updateView(project);
		this.setMessageCode(this.MSG_SUCCESS);
		mav.addObject("messageCode", this.getMessageCode());
		return mav;
	}
	
	/**
	 * ���ܣ�ɾ����Ŀ
	 * @param project
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/project/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(Project project, HttpServletRequest request) {
		// ��ȡҪɾ����Ա����Ϣ
		if (null != project) {
			project = projectService.get(project.getId());
		}
		
		projectService.delete(project);
		
		return "redirect:" + "/main/project/index.do";
	}
	
	/**
	 * ���ܣ�ajax��ȡ��Ŀ�����ģ�飬�汾����Ա
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/getProjectParam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object getProjectParam(HttpServletRequest request, Project project) throws Exception {
		String jason = projectService.getProjectParm(project);
		return jason;
	}
}
