package com.gaoguangjin.bug.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.bug.entity.BugContentVO;
import com.gaoguangjin.bug.service.BugContentProcessService;
import com.gaoguangjin.bug.service.BugContentService;
import com.gaoguangjin.project.entity.ProjectUser;
import com.gaoguangjin.project.entity.ProjectVO;

@Controller
@RequestMapping("/main")
public class BugContentProgressController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BugContentProgressController.class);
	@Autowired
	private BugContentProcessService bugContentProcessService;
	@Autowired
	private BugContentService bugContentService;
	
	/**
	 * 功能：Bug分配视图
	 * @param biUserInfo
	 * @return
	 */
	@RequestMapping(value = "/bugContentProcess/process/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processView(BugContent bugContent, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// 得到岗位
		ProjectUser projectUser = super.getRoleProjectUser(request);
		// 得到传参版本，项目，模块，员工。
		ProjectVO projectVO = bugContentService.getAddView(projectUser);
		
		if (null != bugContent) {
			bugContent = bugContentService.get(bugContent.getId());
		}
		// 得到bug进度
		BugContentVO bugContentVO = bugContentProcessService.getBugProcess(bugContent);
		mav.addObject("bugContent", bugContent);
		mav.addObject("projectVO", projectVO);
		mav.addObject("bugContentVO", bugContentVO);
		
		mav.setViewName("/bug/bugContentProcess/bug_content_process_view");
		return mav;
	}
	
	/**
	 * 功能：Bug进度保存
	 * @param bugContent
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bugContent/process/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView assingSave(BugContent bugContent, HttpServletRequest request) {
		
		bugContent.setCreateDate(new Date());
		bugContent.setCreateUser(super.getSessionUser(request));
		
		bugContentProcessService.saveProcess(bugContent);
		
		ModelAndView mav = processView(bugContent, request);
		this.setMessageCode(this.MSG_SUCCESS);
		mav.addObject("messageCode", this.getMessageCode());
		return mav;
	}
	
}
