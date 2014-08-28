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
	 * 功能：数据库备份分页查询
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
			// 将地址放到session里面
			this.setSessionListUrl(request);
			pageNo = pageNo == null ? 1 : pageNo;
			int pageSize = this.getCookiesPageSize(request);
			Page pagedData = dBbackUpService.getPagedBackUp(dBbackUp, pageNo, pageSize);
			view.addObject("pagedData", pagedData);
			view.addObject("dBbackUp", dBbackUp);
			view.setViewName("/dBbackUp/db_backup_list");
			logger.info("系统备份分页查询成功:" + dBbackUp.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("系统备份分页分页查询失败:" + dBbackUp.toString(), e);
			throw e;
		}
		return view;
	}
	
	/**
	 * 功能：添加单个员工视图
	 * @return
	 */
	@RequestMapping(value = "/dBbackUp/add/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dBbackUp/db_backup_add_view");
		return mav;
	}
	
	/**
	 * 功能：保存备份
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
			logger.info("备份成功" + dBbackUp.toString());
			return mav;
		}
		catch (Exception e) {
			logger.info("备份失败" + dBbackUp.toString());
			throw e;
		}
	}
	
	/**
	 * 功能：还原备份
	 * @param biUserInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dBbackUp/recover", method = { RequestMethod.GET, RequestMethod.POST })
	public String recover(DBbackUp dBbackUp, HttpServletRequest request) {
		dBbackUpService.recover(dBbackUp);
		logger.info("备份还原成功" + dBbackUp.toString());
		return "redirect:" + getSessionListUrl(request);
	}
	
	/**
	 * 功能：备份删除
	 * @param biUserInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dBbackUp/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(DBbackUp dBbackUp, HttpServletRequest request) {
		dBbackUpService.delete(dBbackUp);
		logger.info("备份删除成功" + dBbackUp.toString());
		return "redirect:" + getSessionListUrl(request);
	}
	
}
