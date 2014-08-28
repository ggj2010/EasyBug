package com.framework.base;

import java.util.Date;

import com.gaoguangjin.util.DateUtil;


public class BeginAndEndDate {
	private Date beginDate;
	private Date endDate;
	
	public String getBeginDateString() {
		String date = "";
		if(null!=getBeginDate()){
			date = DateUtil.convertDateToString(getBeginDate(), DateUtil.DATE_FORMAT_yyyyMMdd);
		}
		return date;
	}
	
	public void setBeginDateString(String value) {
		if(null!=value && !"".equals(value)){
			setBeginDate(DateUtil.convertStringToDate(value, DateUtil.DATE_FORMAT_yyyyMMdd));
		}
	}
	
	public String getEndDateString() {
		String date = "";
		if(null!=getEndDate()){
			date = DateUtil.convertDateToString(getEndDate(), DateUtil.DATE_FORMAT_yyyyMMdd);
		}
		return date;
	}
	
	public void setEndDateString(String value) {
		if(null!=value && !"".equals(value)){
			setEndDate(DateUtil.convertStringToDate(value, DateUtil.DATE_FORMAT_yyyyMMdd));
		}
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
