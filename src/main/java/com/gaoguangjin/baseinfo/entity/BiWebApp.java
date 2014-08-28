package com.gaoguangjin.baseinfo.entity;

import java.util.Date;

import com.framework.base.BaseEntity;

public class BiWebApp extends BaseEntity {
	private String id;
	private String name;
	private String flag;
	private String url;
	private String describes;
	private String image;
	private String briefContent;
	private BiUserInfo createUser;
	private Date createDate;
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getFlag() {
		return flag;
	}
	
	@Override
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getBriefContent() {
		return briefContent;
	}
	
	public void setBriefContent(String briefContent) {
		this.briefContent = briefContent;
	}
	
	@Override
	public BiUserInfo getCreateUser() {
		return createUser;
	}
	
	@Override
	public void setCreateUser(BiUserInfo createUser) {
		this.createUser = createUser;
	}
	
	@Override
	public Date getCreateDate() {
		return createDate;
	}
	
	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getDescribes() {
		return describes;
	}
	
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	
}
