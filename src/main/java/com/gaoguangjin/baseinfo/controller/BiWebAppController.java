package com.gaoguangjin.baseinfo.controller;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiWebApp;
import com.gaoguangjin.baseinfo.service.BiWebAppService;

@Controller
@RequestMapping("/main")
public class BiWebAppController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BiWebAppController.class);
	
	@Autowired
	private BiWebAppService biWebAppService;
	
	private final String LIST_ACTION = "/main/biWebApp/list.do";
	
	/**
	 * 功能：BiWebApp查询列表
	 * @param biWebApp : entity,pageNo :页数,pageSize :
	 * @return 返回view
	 */
	@RequestMapping(value = "/biWebApp/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listBiWebApp(BiWebApp biWebApp, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			this.setSessionListUrl(request);
			pageNo = pageNo == null ? 1 : pageNo;
			int pageSize = this.getCookiesPageSize(request);
			Page pagedData = biWebAppService.getPagedBiWebApp(biWebApp, pageNo, pageSize);
			view.addObject("pagedData", pagedData);
			view.addObject("biWebApp", biWebApp);
			view.setViewName("/baseinfo/biWebApp/bi_web_app_list");
			logger.info("moduleName分页查询成功:" + biWebApp.toString());
			
		}
		catch (Exception e) {
			
			logger.error("moduleName分页查询失败:" + biWebApp.toString(), e);
			throw e;
		}
		return view;
	}
	
	/**
	 * 功能：BiWebApp查看详情
	 * @param id
	 * @return 返回BiWebApp对象
	 */
	@RequestMapping(value = "/biWebApp/{id}")
	public ModelAndView show(BiWebApp biWebApp, @PathVariable java.lang.String id) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			biWebApp = biWebAppService.getBiWebApp(id);
			mav.addObject("biWebApp", biWebApp);
			mav.setViewName("/baseinfo/biWebApp/bi_web_app_show");
			logger.info("moduleName详细查询成功:" + biWebApp.toString());
		}
		catch (Exception e) {
			
			logger.error("moduleName详细查询失败:" + biWebApp.toString(), e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiWebApp添加预览
	 * @param biWebApp : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return 返回mav
	 */
	@RequestMapping(value = "/biWebApp/add/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addView(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("/baseinfo/biWebApp/bi_web_app_add_view");
			
			logger.info("moduleName添加预览成功");
		}
		catch (Exception e) {
			logger.error("moduleName添加预览失败", e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiWebApp添加
	 * @param biWebApp : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return 返回mav
	 */
	@RequestMapping(value = "/biWebApp/add/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addBiWebApp(BiWebApp biWebApp, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			String clientPath = "";
			String path = "themes/uploadImage/";
			String fileName = "";
			// 设置上下方文
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
					.getServletContext());
			
			// 检查form是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 由CommonsMultipartFile继承而来,拥有上面的方法.
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						fileName = file.getOriginalFilename();
						clientPath = request.getSession().getServletContext().getRealPath(path) + File.separator
								+ fileName;
						File localFile = new File(clientPath);
						file.transferTo(localFile);
					}
				}
			}
			biWebApp.setFlag("Y");
			biWebApp.setImage("/" + path + fileName);
			biWebAppService.save(biWebApp);
			
			mav = addView(request);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			
			logger.info("moduleName添加成功:" + biWebApp.toString());
		}
		catch (Exception e) {
			
			logger.error("moduleName添加失败:" + biWebApp.toString(), e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiWebApp修改预览
	 * @param biWebApp : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return 返回mav
	 */
	@RequestMapping("/biWebApp/update/view")
	public ModelAndView updateView(BiWebApp biWebApp, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			biWebApp = biWebAppService.getBiWebApp(biWebApp.getId());
			mav.addObject("biWebApp", biWebApp);
			
			mav.setViewName("/baseinfo/biWebApp/bi_web_app_update");
			logger.info("moduleName修改预览成功:" + biWebApp.getId());
		}
		catch (Exception e) {
			
			logger.error("moduleName修改预览失败:" + biWebApp.getId(), e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiWebApp修改
	 * @param biWebApp : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return 返回mav
	 */
	@RequestMapping("/biWebApp/update/save")
	public ModelAndView updateBiWebApp(BiWebApp biWebApp, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			
			biWebAppService.update(biWebApp);
			
			mav = updateView(biWebApp, request);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			logger.info("moduleName修改成功:" + biWebApp.toString());
			
		}
		catch (Exception e) {
			
			logger.error("moduleName修改失败:" + biWebApp.toString(), e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * 功能：BiWebApp删除
	 * @return 返回String
	 */
	@RequestMapping("/biWebApp/delete")
	public String delete(BiWebApp biWebApp, HttpServletRequest request) throws Exception {
		try {
			biWebAppService.delete(biWebApp);
			logger.info("moduleName删除成功,跳转地址:" + LIST_ACTION);
			return "redirect:" + LIST_ACTION;
			
		}
		catch (Exception e) {
			
			logger.error("moduleName删除失败:" + biWebApp.getId(), e);
			throw e;
		}
	}
	
}
