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
	 * ���ܣ�BiRole��ѯ�б�
	 * @param biRole : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
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
			
			logger.info("��ɫ��ҳ��ѯ�ɹ�:" + biRole.toString());
			
		}
		catch (Exception e) {
			
			logger.error("��ɫ��ҳ��ѯʧ��:" + biRole.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�BiRole���Ԥ��
	 * @param biRole : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ����mav
	 */
	@RequestMapping(value = "/biRole/add/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addView() throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("/baseinfo/biRole/bi_role_add");
			logger.info("��ɫ���Ԥ���ɹ�");
		}
		catch (Exception e) {
			logger.error("��ɫ���Ԥ��ʧ��");
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiRole���
	 * @param biRole : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ����mav
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
			logger.info("��ɫ��ӳɹ�:" + biRole.toString());
		}
		catch (Exception e) {
			
			logger.error("��ɫ���ʧ��:" + biRole.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiRole�޸�Ԥ��
	 * @param biRole : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ���� mav
	 */
	@RequestMapping("/biRole/update/view")
	public ModelAndView updateView(BiRole biRole) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			biRole = biRoleService.getBiRole(biRole.getId());
			mav.addObject("biRole", biRole);
			mav.setViewName("/baseinfo/biRole/bi_role_update");
			logger.info("��ɫ�޸�Ԥ���ɹ�:" + biRole.getId());
		}
		catch (Exception e) {
			
			logger.error("��ɫ�޸�Ԥ��ʧ��:" + biRole.getId());
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiRole�޸�
	 * @param biRole : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ����mav
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
			logger.info("��ɫ�޸ĳɹ�:" + biRole.toString());
			
		}
		catch (Exception e) {
			
			logger.error("��ɫ�޸�ʧ��:" + biRole.toString());
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiRoleɾ��
	 * @return ����String
	 */
	@RequestMapping("/biRole/delete")
	public String delete(BiRole biRole) throws Exception {
		String targetUrl = null;
		try {
			biRoleService.delete(biRole);
			targetUrl = "/main/biRole/list.do";
			logger.info("��ɫɾ���ɹ�:" + biRole.getId());
		}
		catch (Exception e) {
			
			logger.error("��ɫɾ��ʧ��:" + biRole.getId());
			throw e;
		}
		return "redirect:" + targetUrl;
	}
	
	/**
	 * ���ܣ����ð�ť��ͼ
	 * @return
	 */
	@RequestMapping(value = "/biRole/menu/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView setupMenuView(String id, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		
		List<BiMenu> menuList = new ArrayList<BiMenu>();
		// �鵽���а�ť��
		menuList = biMenuService.findAll();
		// ��ѯ�Ľ�ɫ�������а�ť��
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
	 * ���ܣ���ɫ��ť����
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
			logger.error("��ɫ��ť��ӳɹ�:" + roleId);
			return view;
		}
		catch (Exception e) {
			logger.error("��ɫ��ť���ʧ��:" + roleId);
			throw e;
		}
	}
	
}
