package com.gaoguangjin.count.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.count.service.BugContentReportService;
import com.gaoguangjin.count.vo.BugCountReportVO;
import com.gaoguangjin.util.DateUtil;

@Controller
@RequestMapping("/main")
public class BugContentReportController {
	private static final Logger logger = LoggerFactory.getLogger(BugContentReportController.class);
	
	@Autowired
	private BugContentReportService bugContentReportService;
	
	/**
	 * 
	 * @param bugContent
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/count/report/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getReport(BugContent bugContent, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			
			if (null == bugContent.getBeginDate()) {
				bugContent.setBeginDate(DateUtil.convertStringToDate(DateUtil.getSystemDateByyyyyMMdd(), null));
			}
			if (null == bugContent.getEndDate()) {
				bugContent.setEndDate(DateUtil.convertStringToDate(DateUtil.getSystemDateByyyyyMMdd(), null));
			}
			String beginDate = bugContent.getBeginDateString() + " 00:00:00";
			String endDate = bugContent.getEndDateString() + " 23:59:59";
			bugContent.setBeginDate(DateUtil.convertStringToDate(beginDate, DateUtil.DATE_FORMAT_yyyyMMddhhmmss));
			bugContent.setEndDate(DateUtil.convertStringToDate(endDate, DateUtil.DATE_FORMAT_yyyyMMddhhmmss));
			
			BugCountReportVO report = bugContentReportService.getReport(bugContent);
			
			mav.addObject("bugContent", bugContent);
			mav.addObject("report", report);
			mav.setViewName("bugCount/bug_report");
			
			logger.info("Bug报表生成成功:" + bugContent.toString());
		}
		catch (Exception e) {
			logger.info("Bug报表生成失败" + bugContent.toString());
			throw e;
		}
		return mav;
		
	}
}
