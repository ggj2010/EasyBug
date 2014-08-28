package com.gaoguangjin.baseinfo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.base.BaseController;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiMenu;
import com.gaoguangjin.baseinfo.entity.BiRole;
import com.gaoguangjin.baseinfo.entity.BiRoleMenu;
import com.gaoguangjin.baseinfo.service.BiMenuService;
import com.gaoguangjin.baseinfo.service.BiRoleMenuService;
import com.gaoguangjin.baseinfo.service.BiRoleService;

@Controller
@RequestMapping("/main")
public class BiRoleController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BiRoleController.class);
	
	@Autowired
	private BiRoleService biRoleService;
	
	@Autowired
	private BiMenuService biMenuService;
	@Autowired
	private BiRoleMenuService biRoleMenuService;
	
	/**
	 * 功能：BiRole查询列表
	 * @param biRole : entity,pageNo :页数,pageSize :
	 * @return 返回view
	 */
	@RequestMapping(value = "/biRole/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listBiRole(BiRole biRole, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			this.setSessionListUrl(request);
			pageNo = pageNo == null ? 1 : pageNo;
			int pageSize = this.getCookiesPageSize(request);
			Page pagedBiRole = biRoleService.getPagedBiRole(biRole, pageNo, pageSize);
			view.addObject("pagedBiRole", pagedBiRole);
			view.addObject("biRole", biRole);
			view.setViewName("/baseinfo/biRole/bi_role_list");
			
			logger.info("角色分页查询成功:" + biRole.toString());
			
		}
		catch (Exception e) {
			
			logger.error("角色分页查询失败:" + biRole.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * 功能：BiRole添加预览
	 * @param biRole : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return 返回mav
	 */
	@RequestMapping(value = "/biRole/add/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addView() throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("/baseinfo/biRole/bi_role_add");
			logger.info("角色添加预览成功");
		}
		catch (Exception e) {
			logger.error("角色添加预览失败");
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiRole添加
	 * @param biRole : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return 返回mav
	 */
	@RequestMapping(value = "/biRole/add/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addBiRole(BiRole biRole, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			biRole.setFlag("Y");
			biRoleService.save(biRole);
			mav.setViewName("/baseinfo/biRole/bi_role_add");
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			logger.info("角色添加成功:" + biRole.toString());
		}
		catch (Exception e) {
			
			logger.error("角色添加失败:" + biRole.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiRole修改预览
	 * @param biRole : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return 返回 mav
	 */
	@RequestMapping("/biRole/update/view")
	public ModelAndView updateView(BiRole biRole) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			biRole = biRoleService.getBiRole(biRole.getId());
			mav.addObject("biRole", biRole);
			mav.setViewName("/baseinfo/biRole/bi_role_update");
			logger.info("角色修改预览成功:" + biRole.getId());
		}
		catch (Exception e) {
			
			logger.error("角色修改预览失败:" + biRole.getId());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiRole修改
	 * @param biRole : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return 返回mav
	 */
	@RequestMapping("/biRole/update/save")
	public ModelAndView updateBiRole(BiRole biRole, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			BiRole param = biRoleService.getBiRole(biRole.getId());
			param.setDiscription(biRole.getDiscription());
			param.setMemo(biRole.getMemo());
			param.setName(biRole.getName());
			biRoleService.update(param);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.setViewName("/baseinfo/biRole/bi_role_update");
			mav.addObject("messageCode", this.getMessageCode());
			logger.info("角色修改成功:" + biRole.toString());
			
		}
		catch (Exception e) {
			
			logger.error("角色修改失败:" + biRole.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiRole删除
	 * @return 返回String
	 */
	@RequestMapping("/biRole/delete")
	public String delete(BiRole biRole) throws Exception {
		String targetUrl = null;
		try {
			biRoleService.delete(biRole);
			targetUrl = "/main/biRole/list.do";
			logger.info("角色删除成功:" + biRole.getId());
		}
		catch (Exception e) {
			
			logger.error("角色删除失败:" + biRole.getId());
			throw e;
		}
		return "redirect:" + targetUrl;
	}
	
	/**
	 * 功能：设置按钮视图
	 * @return
	 */
	@RequestMapping(value = "/biRole/menu/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView setupMenuView(String id, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		
		List<BiMenu> menuList = new ArrayList<BiMenu>();
		// 查到所有按钮。
		menuList = biMenuService.findAll();
		// 查询改角色下面所有按钮。
		List<BiRoleMenu> biRoleMenuList = biRoleMenuService.findMenuByRole(id);
		
		List<BiMenu> btnList = new ArrayList<BiMenu>();
		btnList = biMenuService.findBtn();
		List<BiMenu> btnGroupList = new ArrayList<BiMenu>();
		btnGroupList = biMenuService.findBtnGroup();
		
		List<BiRoleMenu> biRoleBtnList = biRoleMenuService.findBtnByRole(id);
		List<BiRoleMenu> biRoleBtnGroupList = biRoleMenuService.findBtnGroupByRole(id);
		JSONArray arrayJson = new JSONArray();
		for (BiRoleMenu biRoleMenu : biRoleMenuList) {
			JSONObject json = new JSONObject();
			json.put("menuId", biRoleMenu.getBiMenu().getId());
			arrayJson.add(json);
		}
		for (BiRoleMenu biRoleMenu : biRoleBtnList) {
			JSONObject json = new JSONObject();
			json.put("menuId", biRoleMenu.getBiMenu().getId());
			arrayJson.add(json);
		}
		for (BiRoleMenu biRoleMenu : biRoleBtnGroupList) {
			JSONObject json = new JSONObject();
			json.put("menuId", biRoleMenu.getBiMenu().getId());
			arrayJson.add(json);
		}
		view.setViewName("/baseinfo/biRole/bi_role_menu");
		
		BiRole biRole = biRoleService.getBiRole(id);
		view.addObject("biRole", biRole);
		view.addObject("idjson", arrayJson.toJSONString());
		
		view.addObject("menuList", menuList);
		view.addObject("btnList", btnList);
		view.addObject("btnGroupList", btnGroupList);
		return view;
		
	}
	
	/**
	 * 功能：角色按钮保存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/biRole/menu/save")
	public ModelAndView saveMenu(String roleId, String menuStr, HttpServletRequest request) throws Exception {
		
		try {
			biRoleMenuService.saveRoleMenu(roleId, menuStr);
			ModelAndView view = setupMenuView(roleId, request);
			view.addObject("messageCode", this.MSG_SUCCESS);
			logger.error("角色按钮添加成功:" + roleId);
			return view;
		}
		catch (Exception e) {
			logger.error("角色按钮添加失败:" + roleId);
			throw e;
		}
	}
	
}
