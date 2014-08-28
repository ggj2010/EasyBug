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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.base.BaseController;
import com.gaoguangjin.baseinfo.entity.BiMenu;
import com.gaoguangjin.baseinfo.service.BiMenuService;

@Controller
@RequestMapping("/main")
public class BiMenuController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BiMenuController.class);
	
	@Autowired
	private BiMenuService biMenuService;
	
	/**
	 * 功能：菜单管理主界面view
	 * @return
	 */
	@RequestMapping(value = "/biMenu/index", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView mainView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/baseinfo/biMenu/bi_menu_index");
		return mav;
	}
	
	/**
	 * 功能：机构树数据源
	 * @param request
	 * @return
	 */
	@RequestMapping("/biMenu/getTreeData")
	public @ResponseBody
	String getTreeData(HttpServletRequest request) {
		List<BiMenu> menuList = new ArrayList<BiMenu>();
		// 初始化根节点
		if (biMenuService.getCount() == 0) {
			BiMenu rootMenu = new BiMenu();
			rootMenu.setName("主菜单");
			rootMenu.setPreId("-1");
			rootMenu.setFlag("Y");
			rootMenu.setMenuType(BiMenu.MENU_FOLDER);
			biMenuService.saveBiMenu(rootMenu);
			menuList = biMenuService.findAll();
		}
		else {
			menuList = biMenuService.findAll();
		}
		
		JSONArray arrayJson = new JSONArray();
		for (BiMenu biMenu : menuList) {
			int childCount = biMenuService.getChildCount(biMenu.getId());
			JSONObject json = new JSONObject();
			json.put("id", biMenu.getId());
			json.put("name", biMenu.getName());
			json.put("isParent", childCount > 0 ? true : false);
			json.put("pId", biMenu.getPreId());
			json.put("menuType", biMenu.getMenuType());
			arrayJson.add(json);
		}
		logger.info(arrayJson.toJSONString());
		return arrayJson.toJSONString();
	}
	
	/**
	 * 功能：BiOrgan查看详情
	 * @param id
	 * @return 返回BiOrgan对象
	 */
	@RequestMapping(value = "/biMenu/show")
	public ModelAndView show(BiMenu biMenu) throws Exception {
		logger.info("查看菜单详情，查询条件：", biMenu.toString());
		ModelAndView mav = new ModelAndView();
		BiMenu preBiMenu;
		try {
			biMenu = biMenuService.getBiMenu(biMenu.getId());
			preBiMenu = biMenuService.getBiMenu(biMenu.getPreId());
		}
		catch (Exception e) {
			logger.error("查看菜单详细信息失败", e);
			throw e;
		}
		mav.addObject("biMenu", biMenu);
		mav.addObject("preBiMenu", preBiMenu);
		mav.setViewName("/baseinfo/biMenu/bi_menu_show");
		return mav;
	}
	
	/**
	 * to Menu add jsp
	 * @return
	 */
	@RequestMapping(value = "/biMenu/add/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addView(BiMenu biMenu) {
		BiMenu preBiMenu = biMenuService.getBiMenu(biMenu.getId());
		ModelAndView mav = new ModelAndView();
		mav.addObject("preBiMenu", preBiMenu);
		mav.setViewName("/baseinfo/biMenu/bi_menu_add");
		return mav;
	}
	
	/**
	 * to Menu edit jsp
	 * @return
	 */
	@RequestMapping(value = "/biMenu/update/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateView(String id) {
		ModelAndView mav = new ModelAndView();
		BiMenu biMenu = biMenuService.getBiMenu(id);
		BiMenu preBiMenu = biMenuService.getBiMenu(biMenu.getPreId());
		mav.addObject("biMenu", biMenu);
		mav.addObject("preBiMenu", preBiMenu);
		mav.setViewName("/baseinfo/biMenu/bi_menu_update");
		return mav;
	}
	
	/**
	 * add Menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/biMenu/add/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addBiMenu(BiMenu biMenu, HttpServletRequest request) throws Exception {
		biMenu.setFlag("Y");
		biMenu.setId(biMenuService.saveBiMenu(biMenu));
		
		ModelAndView mav = show(biMenu);
		this.setMessageCode(this.MSG_SUCCESS);
		mav.addObject("messageCode", this.getMessageCode());
		return mav;
	}
	
	/**
	 * edit Menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/biMenu/update/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateBiMenu(BiMenu biMenu, HttpServletRequest request) throws Exception {
		biMenu.setFlag("Y");
		biMenuService.updateBiMenu(biMenu);
		
		ModelAndView mav = show(biMenu);
		this.setMessageCode(this.MSG_SUCCESS);
		mav.addObject("messageCode", this.getMessageCode());
		return mav;
	}
	
	/**
	 * 功能：删除按钮
	 * @param project
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/biMenu/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(BiMenu biMenu, HttpServletRequest request) {
		// 获取要删除的员工信息
		biMenu = biMenuService.getBiMenu(biMenu.getId());
		biMenu.setFlag("N");
		biMenuService.updateBiMenu(biMenu);
		
		return "redirect:" + "/main/biMenu/index.do";
	}
}
