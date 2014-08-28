package com.gaoguangjin.bug.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.framework.base.BaseController;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.baseinfo.service.BiUserInfoService;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.bug.entity.BugContentVO;
import com.gaoguangjin.bug.service.BugContentProcessService;
import com.gaoguangjin.bug.service.BugContentService;
import com.gaoguangjin.project.entity.ProjectUser;
import com.gaoguangjin.project.entity.ProjectVO;

@Controller
@RequestMapping("/main")
public class BugContentCotroller extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BugContentCotroller.class);
	
	@Autowired
	private BiUserInfoService biUserInfoService;
	
	@Autowired
	private BugContentService bugContentService;
	
	@Autowired
	private BugContentProcessService bugContentProcessService;
	
	/**
	 * bug分页列表
	 * @param bugContent
	 * @param pageNo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bugContent/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPageList(BugContent bugContent, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			// 将地址放到session里面
			this.setSessionListUrl(request);
			// 分页参数
			pageNo = pageNo == null ? 1 : pageNo;
			// 一页显示多少条
			int pageSize = super.getCookiesPageSize(request);
			ProjectUser roleProjectUser = super.getRoleProjectUser(request);
			bugContent.setProject(roleProjectUser.getProject());
			Page pageData = bugContentService.getPagedBugContent(bugContent, pageNo, pageSize);
			
			ProjectUser projectUser = super.getRoleProjectUser(request);
			// 得到传参版本，项目，模块，员工。
			ProjectVO projectVO = bugContentService.getAddView(projectUser);
			mav.addObject("projectVO", projectVO);
			
			mav.addObject("bugContent", bugContent);
			mav.addObject("pageData", pageData);
			mav.setViewName("bug/bugContent/bug_content_list");
			
			logger.info("Bug分页查询:" + bugContent.toString());
		}
		catch (Exception e) {
			logger.info("Bug分页查询失败" + bugContent.toString());
			throw e;
		}
		return mav;
		
	}
	
	@RequestMapping(value = "/bugContent/createByMe/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPageCreateByMeList(BugContent bugContent, Integer pageNo, HttpServletRequest request)
			throws Exception {
		try {
			BiUserInfo userinfo = getSessionUser(request);
			bugContent.setCreateUser(userinfo);
			
			ModelAndView mav = getPageList(bugContent, null, request);
			logger.info("我创建的Bug分页查询失败" + bugContent.toString());
			return mav;
		}
		catch (Exception e) {
			logger.info("我创建的Bug分页查询失败" + bugContent.toString());
			throw e;
		}
		
	}
	
	/**
	 * 功能：bug添加视图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bugContent/add/view")
	public ModelAndView addView(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			// 得到岗位
			ProjectUser projectUser = super.getRoleProjectUser(request);
			// 得到传参版本，项目，模块，员工。
			ProjectVO projectVO = bugContentService.getAddView(projectUser);
			mav.setViewName("/bug/bugContent/bug_content_add_view");
			mav.addObject("projectVO", projectVO);
			logger.info("Bug添加视图成功");
		}
		catch (Exception e) {
			logger.info("Bug添加视图失败");
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：bug批量添加bug视图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bugContent/add/more/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addMoreView(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			// 得到岗位
			ProjectUser projectUser = super.getRoleProjectUser(request);
			// 得到传参版本，项目，模块，员工。
			ProjectVO projectVO = bugContentService.getAddView(projectUser);
			// 一次可以添加的bug次数。
			projectVO.setDepth("10");
			mav.addObject("projectVO", projectVO);
			mav.setViewName("/bug/bugContent/bug_content_add_view");
			logger.info("Bug添加视图成功");
		}
		catch (Exception e) {
			logger.info("Bug添加视图是不");
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：bug项目
	 * @param projectVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/bugContent/add/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveBugContent(BugContentVO bugContentVO, HttpServletRequest request) throws Exception {
		try {
			
			BiUserInfo userinfo = getSessionUser(request);
			bugContentVO.setCreateUser(userinfo);
			bugContentVO.setCreateDate(new Date());
			// 执行保存
			bugContentService.saveBugContent(bugContentVO);
			
			ModelAndView mav = addView(request);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			
			logger.error("Project添加成功");
			return mav;
			
		}
		catch (Exception e) {
			logger.error("Project添加失败");
			throw e;
		}
	}
	
	/**
	 * 功能：Bug更新视图
	 * @param biUserInfo
	 * @return
	 */
	@RequestMapping(value = "/bugContent/update/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateView(BugContent bugContent, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		// 得到岗位
		ProjectUser projectUser = super.getRoleProjectUser(request);
		// 得到传参版本，项目，模块，员工。
		ProjectVO projectVO = bugContentService.getAddView(projectUser);
		
		if (null != bugContent) {
			bugContent = bugContentService.get(bugContent.getId());
		}
		
		mav.addObject("bugContent", bugContent);
		mav.addObject("projectVO", projectVO);
		
		mav.setViewName("/bug/bugContent/bug_content_update_view");
		return mav;
		
	}
	
	/**
	 * 功能：更新项目
	 * @param bugContent
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bugContent/update/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateSave(BugContent bugContent, HttpServletRequest request) {
		
		BiUserInfo user = this.getSessionUser(request);
		bugContent.setUpdateUser(user);
		
		// 得到以前的用户对象
		BugContent bug = bugContentService.get(bugContent.getId());
		
		bugContent.setCreateDate(bug.getCreateDate());
		bugContent.setCreateUser(bug.getCreateUser());
		bugContent.setStatus(bug.getStatus());
		bugContent.setProject(bug.getProject());
		bugContent.setFlag(bug.getFlag());
		bugContent.setIsClosed(bug.getIsClosed());
		bugContent.setIsOpen(bug.getIsOpen());
		bugContent.setIsSolved(bug.getIsSolved());
		bugContent.setIsReopen(bug.getIsReopen());
		
		bugContentService.update(bugContent);
		
		ModelAndView mav = updateView(bugContent, request);
		this.setMessageCode(this.MSG_SUCCESS);
		mav.addObject("messageCode", this.getMessageCode());
		return mav;
	}
	
	/**
	 * 功能：删除bug
	 * @param bugContent
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bugContent/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(BugContent bugContent, HttpServletRequest request) {
		// 获取要删除的Bug信息
		if (null != bugContent) {
			bugContent = bugContentService.get(bugContent.getId());
		}
		bugContentService.delete(bugContent);
		return "redirect:" + "/main/bugContent/list.do";
	}
	
	/**
	 * 功能：ajax获取用户
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bugContent/getAjaxBugList", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Object getAjaxBugList(String key, HttpServletRequest request) {
		BugContent bugContent = new BugContent();
		bugContent.setName(key);
		List<BugContent> bugList = bugContentService.getBugList(bugContent);
		// 清空描述，这样才能查到
		for (BugContent bug : bugList) {
			bug.setDescribe(null);
		}
		return String.valueOf(JSONArray.toJSON(bugList));
	}
	
	/**
	 * 未分配bug分页列表
	 * @param bugContent
	 * @param pageNo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bugContent/assign/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPageAssingList(BugContent bugContent, Integer pageNo, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			// 将地址放到session里面
			this.setSessionListUrl(request);
			// 分页参数
			pageNo = pageNo == null ? 1 : pageNo;
			// 一页显示多少条
			int pageSize = super.getCookiesPageSize(request);
			// 设置分配人的角色。
			ProjectUser user = this.getRoleProjectUser(request);
			bugContent.setUserAssigner(user);
			bugContent.setProject(user.getProject());
			
			Page pageData = bugContentService.getPagedAssingAndReviewBug(bugContent, pageNo, pageSize);
			
			mav.addObject("bugContent", bugContent);
			mav.addObject("pageData", pageData);
			mav.setViewName("bug/bugContent/bug_content_assign_list");
			
			logger.info("Bug分页查询:" + bugContent.toString());
		}
		catch (Exception e) {
			logger.info("Bug分页查询失败" + bugContent.toString());
			throw e;
		}
		return mav;
		
	}
	
	/**
	 * 未审核bug分页列表
	 * @param bugContent
	 * @param pageNo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bugContent/review/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPageReviewList(BugContent bugContent, Integer pageNo, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			// 将地址放到session里面
			this.setSessionListUrl(request);
			// 分页参数
			pageNo = pageNo == null ? 1 : pageNo;
			// 一页显示多少条
			int pageSize = super.getCookiesPageSize(request);
			// 设置分配人的角色。
			ProjectUser user = this.getRoleProjectUser(request);
			bugContent.setUserAssigner(user);
			bugContent.setProject(user.getProject());
			bugContent.setIsSolved("Y");
			Page pageData = bugContentService.getPagedAssingAndReviewBug(bugContent, pageNo, pageSize);
			
			mav.addObject("bugContent", bugContent);
			mav.addObject("pageData", pageData);
			mav.setViewName("bug/bugContent/bug_content_review_list");
			
			logger.info("Bug分页查询:" + bugContent.toString());
		}
		catch (Exception e) {
			logger.info("Bug分页查询失败" + bugContent.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：Bug分配视图
	 * @param biUserInfo
	 * @return
	 */
	@RequestMapping(value = "/bugContent/assign/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView assingView(BugContent bugContent, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// 得到岗位
		ProjectUser projectUser = super.getRoleProjectUser(request);
		// 得到传参版本，项目，模块，员工。
		ProjectVO projectVO = bugContentService.getAddView(projectUser);
		
		if (null != bugContent) {
			bugContent = bugContentService.get(bugContent.getId());
		}
		mav.addObject("bugContent", bugContent);
		mav.addObject("projectVO", projectVO);
		
		mav.setViewName("/bug/bugContent/bug_content_assign_view");
		return mav;
	}
	
	/**
	 * 功能：Bug分配保存
	 * @param bugContent
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bugContent/assign/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView assingSave(BugContent bugContent, String isEmail, HttpServletRequest request) {
		
		bugContent.setCreateDate(new Date());
		bugContent.setCreateUser(super.getSessionUser(request));
		
		bugContentService.saveAssing(bugContent, isEmail);
		
		ModelAndView mav = assingView(bugContent, request);
		this.setMessageCode(this.MSG_SUCCESS);
		mav.addObject("messageCode", this.getMessageCode());
		return mav;
	}
	
	/**
	 * 未分配bug分页列表
	 * @param bugContent
	 * @param pageNo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bugContent/belognToMe/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPageBelognToMeList(BugContent bugContent, Integer pageNo, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			// 将地址放到session里面
			this.setSessionListUrl(request);
			// 分页参数
			pageNo = pageNo == null ? 1 : pageNo;
			// 一页显示多少条
			int pageSize = super.getCookiesPageSize(request);
			
			// 设置当前用户的角色。
			ProjectUser user = this.getRoleProjectUser(request);
			bugContent.setUserHandler(user);
			
			Page pageData = bugContentService.getPageBelognToMe(bugContent, pageNo, pageSize);
			
			mav.addObject("bugContent", bugContent);
			mav.addObject("pageData", pageData);
			mav.setViewName("bug/bugContent/bug_content_belogntome_list");
			
			logger.info("Bug分页查询:" + bugContent.toString());
		}
		catch (Exception e) {
			logger.info("Bug分页查询失败" + bugContent.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：Bug关闭保存
	 * @param bugContent
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bugContent/close/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveClose(BugContent bugContent, HttpServletRequest request) {
		BugContentVO bugContentVO = bugContentProcessService.getBugProcess(bugContent);
		bugContentService.saveClose(bugContent, bugContentVO);
		
		ModelAndView mav = assingView(bugContent, request);
		this.setMessageCode(this.MSG_SUCCESS);
		mav.addObject("messageCode", this.getMessageCode());
		return mav;
	}
	
}
