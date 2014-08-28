package com.gaoguangjin.backup.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.gaoguangjin.backup.entity.DBbackUp;
import com.gaoguangjin.backup.service.DBbackUpService;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;

@Controller
@RequestMapping("/main")
public class DBbackUpController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DBbackUpController.class);
	@Autowired
	DBbackUpService dBbackUpService;
	
	/**
	 * ���ܣ����ݿⱸ�ݷ�ҳ��ѯ
	 * @param dBbackUp
	 * @param pageNo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dBbackUp/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listBiUserLog(DBbackUp dBbackUp, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			// ����ַ�ŵ�session����
			this.setSessionListUrl(request);
			pageNo = pageNo == null ? 1 : pageNo;
			int pageSize = this.getCookiesPageSize(request);
			Page pagedData = dBbackUpService.getPagedBackUp(dBbackUp, pageNo, pageSize);
			view.addObject("pagedData", pagedData);
			view.addObject("dBbackUp", dBbackUp);
			view.setViewName("/dBbackUp/db_backup_list");
			logger.info("ϵͳ���ݷ�ҳ��ѯ�ɹ�:" + dBbackUp.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("ϵͳ���ݷ�ҳ��ҳ��ѯʧ��:" + dBbackUp.toString(), e);
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ���ӵ���Ա����ͼ
	 * @return
	 */
	@RequestMapping(value = "/dBbackUp/add/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dBbackUp/db_backup_add_view");
		return mav;
	}
	
	/**
	 * ���ܣ����汸��
	 * @param biUserInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dBbackUp/add/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveMoreUser(DBbackUp dBbackUp, HttpServletRequest request) throws Exception {
		
		try {
			BiUserInfo user = this.getSessionUser(request);
			dBbackUp.setCreateUser(user);
			dBbackUpService.saveBackUp(dBbackUp, request);
			
			ModelAndView mav = addView();
			this.setMessageCode(this.MSG_SUCCESS);
			mav.addObject("messageCode", this.getMessageCode());
			logger.info("���ݳɹ�" + dBbackUp.toString());
			return mav;
		}
		catch (Exception e) {
			logger.info("����ʧ��" + dBbackUp.toString());
			throw e;
		}
	}
	
	/**
	 * ���ܣ���ԭ����
	 * @param biUserInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dBbackUp/recover", method = { RequestMethod.GET, RequestMethod.POST })
	public String recover(DBbackUp dBbackUp, HttpServletRequest request) {
		dBbackUpService.recover(dBbackUp);
		logger.info("���ݻ�ԭ�ɹ�" + dBbackUp.toString());
		return "redirect:" + getSessionListUrl(request);
	}
	
	/**
	 * ���ܣ�����ɾ��
	 * @param biUserInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dBbackUp/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(DBbackUp dBbackUp, HttpServletRequest request) {
		dBbackUpService.delete(dBbackUp);
		logger.info("����ɾ���ɹ�" + dBbackUp.toString());
		return "redirect:" + getSessionListUrl(request);
	}
	
}
