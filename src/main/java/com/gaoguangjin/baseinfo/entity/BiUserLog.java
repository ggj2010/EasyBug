package com.gaoguangjin.baseinfo.entity;

import com.framework.base.BaseEntity;

public class BiUserLog extends BaseEntity {
	private String id;
	private BiUserInfo biUserInfo;
	private String title;
	private String content;
	private String customIP;
	private String isFormat;
	private Long times;
	private String url;
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public BiUserInfo getBiUserInfo() {
		return biUserInfo;
	}
	
	public void setBiUserInfo(BiUserInfo biUserInfo) {
		this.biUserInfo = biUserInfo;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCustomIP() {
		return customIP;
	}
	
	public void setCustomIP(String customIP) {
		this.customIP = customIP;
	}
	
	public Long getTimes() {
		return times;
	}
	
	public void setTimes(Long times) {
		this.times = times;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getIsFormat() {
		return isFormat;
	}
	
	public void setIsFormat(String isFormat) {
		this.isFormat = isFormat;
	}
	
}
