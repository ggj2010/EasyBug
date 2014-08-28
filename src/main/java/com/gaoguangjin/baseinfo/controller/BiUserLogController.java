package com.gaoguangjin.baseinfo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiUserLog;
import com.gaoguangjin.baseinfo.service.BiUserLogService;

@Controller
@RequestMapping("/main")
public class BiUserLogController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BiUserLogController.class);
	@Autowired
	BiUserLogService biUserLogService;
	
	@RequestMapping(value = "biUserLog/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listBiUserLog(BiUserLog biUserLog, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			
			pageNo = pageNo == null ? 1 : pageNo;
			int pageSize = this.getCookiesPageSize(request);
			Page pagedData = biUserLogService.getPagedBiUserLog(biUserLog, pageNo, pageSize);
			view.addObject("pagedData", pagedData);
			view.addObject("biUserLog", biUserLog);
			view.setViewName("/baseinfo/biUserLog/bi_user_log_list");
			
			logger.info("系统日志查询成功:" + biUserLog.toString());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("系统日志分页查询失败:" + biUserLog.toString(), e);
			throw e;
		}
		return view;
	}
	
}
