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
	 * ���ܣ�BiWebApp��ѯ�б�
	 * @param biWebApp : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
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
			logger.info("moduleName��ҳ��ѯ�ɹ�:" + biWebApp.toString());
			
		}
		catch (Exception e) {
			
			logger.error("moduleName��ҳ��ѯʧ��:" + biWebApp.toString(), e);
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�BiWebApp�鿴����
	 * @param id
	 * @return ����BiWebApp����
	 */
	@RequestMapping(value = "/biWebApp/{id}")
	public ModelAndView show(BiWebApp biWebApp, @PathVariable java.lang.String id) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			biWebApp = biWebAppService.getBiWebApp(id);
			mav.addObject("biWebApp", biWebApp);
			mav.setViewName("/baseinfo/biWebApp/bi_web_app_show");
			logger.info("moduleName��ϸ��ѯ�ɹ�:" + biWebApp.toString());
		}
		catch (Exception e) {
			
			logger.error("moduleName��ϸ��ѯʧ��:" + biWebApp.toString(), e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiWebApp���Ԥ��
	 * @param biWebApp : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ����mav
	 */
	@RequestMapping(value = "/biWebApp/add/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addView(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			mav.setViewName("/baseinfo/biWebApp/bi_web_app_add_view");
			
			logger.info("moduleName���Ԥ���ɹ�");
		}
		catch (Exception e) {
			logger.error("moduleName���Ԥ��ʧ��", e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiWebApp���
	 * @param biWebApp : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ����mav
	 */
	@RequestMapping(value = "/biWebApp/add/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addBiWebApp(BiWebApp biWebApp, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			String clientPath = "";
			String path = "themes/uploadImage/";
			String fileName = "";
			// �������·���
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
					.getServletContext());
			
			// ���form�Ƿ���enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// ��CommonsMultipartFile�̳ж���,ӵ������ķ���.
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
			
			logger.info("moduleName��ӳɹ�:" + biWebApp.toString());
		}
		catch (Exception e) {
			
			logger.error("moduleName���ʧ��:" + biWebApp.toString(), e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiWebApp�޸�Ԥ��
	 * @param biWebApp : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ����mav
	 */
	@RequestMapping("/biWebApp/update/view")
	public ModelAndView updateView(BiWebApp biWebApp, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			biWebApp = biWebAppService.getBiWebApp(biWebApp.getId());
			mav.addObject("biWebApp", biWebApp);
			
			mav.setViewName("/baseinfo/biWebApp/bi_web_app_update");
			logger.info("moduleName�޸�Ԥ���ɹ�:" + biWebApp.getId());
		}
		catch (Exception e) {
			
			logger.error("moduleName�޸�Ԥ��ʧ��:" + biWebApp.getId(), e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiWebApp�޸�
	 * @param biWebApp : entity
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ����mav
	 */
	@RequestMapping("/biWebApp/update/save")
	public ModelAndView updateBiWebApp(BiWebApp biWebApp, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			
			biWebAppService.update(biWebApp);
			
			mav = updateView(biWebApp, request);
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			logger.info("moduleName�޸ĳɹ�:" + biWebApp.toString());
			
		}
		catch (Exception e) {
			
			logger.error("moduleName�޸�ʧ��:" + biWebApp.toString(), e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * ���ܣ�BiWebAppɾ��
	 * @return ����String
	 */
	@RequestMapping("/biWebApp/delete")
	public String delete(BiWebApp biWebApp, HttpServletRequest request) throws Exception {
		try {
			biWebAppService.delete(biWebApp);
			logger.info("moduleNameɾ���ɹ�,��ת��ַ:" + LIST_ACTION);
			return "redirect:" + LIST_ACTION;
			
		}
		catch (Exception e) {
			
			logger.error("moduleNameɾ��ʧ��:" + biWebApp.getId(), e);
			throw e;
		}
	}
	
}
