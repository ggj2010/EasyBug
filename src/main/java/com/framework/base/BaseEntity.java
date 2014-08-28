package com.framework.base;

import java.util.Date;

import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.util.DateUtil;

public abstract class BaseEntity {
	
	public static String YES = "Y";
	public static String NO = "N";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String flag;
	private Date createDate;
	private BiUserInfo createUser;
	private Date updateDate;
	private Date beginDate;
	
	private Date endDate;
	private BiUserInfo updateUser;
	private String updateMemo;
	// 拼音
	private String encode;
	// 找回密码步骤
	private String step;
	private String sort;
	private String sortSequence;
	private String memo;
	
	// page column
	
	public String getEncode() {
		return encode;
	}
	
	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	public String getStep() {
		return step;
	}
	
	public void setStep(String step) {
		this.step = step;
	}
	
	public String getSort() {
		return sort;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	// 将数据库的日期"2014-1-22 19:46:50"转换成字符串格式"2014-01-22"
	public String getCreateDateString() {
		String date = "";
		if (null != getCreateDate()) {
			date = DateUtil.convertDateToString(getCreateDate(), DateUtil.DATE_FORMAT_yyyyMMdd);
		}
		return date;
	}
	
	// 将页面字符串日期"Wed Jan 22 19:46:50 CST 2014"转换成日期类型"2014-1-22 19:46:50"，存到数据库
	public void setCreateDateString(String value) {
		if (null != value && !"".equals(value)) {
			setCreateDate(DateUtil.convertStringToDate(value, DateUtil.DATE_FORMAT_yyyyMMdd));
		}
	}
	
	public String getUpdateDateString() {
		String date = "";
		if (null != getUpdateDate()) {
			date = DateUtil.convertDateToString(getUpdateDate(), DateUtil.DATE_FORMAT_yyyyMMdd);
		}
		return date;
	}
	
	public void setUpdateDateString(String value) {
		if (null != value && !"".equals(value)) {
			setUpdateDate(DateUtil.convertStringToDate(value, DateUtil.DATE_FORMAT_yyyyMMdd));
		}
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public void setCreateUser(BiUserInfo createUser) {
		this.createUser = createUser;
	}
	
	public void setUpdateUser(BiUserInfo updateUser) {
		this.updateUser = updateUser;
	}
	
	public BiUserInfo getCreateUser() {
		return createUser;
	}
	
	public BiUserInfo getUpdateUser() {
		return updateUser;
	}
	
	public void setSortSequence(String sortSequence) {
		this.sortSequence = sortSequence;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUpdateMemo() {
		return updateMemo;
	}
	
	public void setUpdateMemo(String updateMemo) {
		this.updateMemo = updateMemo;
	}
	
	public String getSortSequence() {
		return sortSequence;
	}
	
	public String getBeginDateString() {
		String date = "";
		if (null != getBeginDate()) {
			date = DateUtil.convertDateToString(getBeginDate(), DateUtil.DATE_FORMAT_yyyyMMdd);
		}
		return date;
	}
	
	public void setBeginDateString(String value) {
		if (null != value && !"".equals(value)) {
			setBeginDate(DateUtil.convertStringToDate(value, DateUtil.DATE_FORMAT_yyyyMMdd));
		}
	}
	
	public String getEndDateString() {
		String date = "";
		if (null != getEndDate()) {
			date = DateUtil.convertDateToString(getEndDate(), DateUtil.DATE_FORMAT_yyyyMMdd);
		}
		return date;
	}
	
	public void setEndDateString(String value) {
		if (null != value && !"".equals(value)) {
			setEndDate(DateUtil.convertStringToDate(value, DateUtil.DATE_FORMAT_yyyyMMdd));
		}
	}
	
}
