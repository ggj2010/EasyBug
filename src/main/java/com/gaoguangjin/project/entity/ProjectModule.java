package com.gaoguangjin.project.entity;

import java.util.Date;

import com.framework.base.BaseEntity;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;

public class ProjectModule extends BaseEntity {
	
	private String id;
	private String name;
	private String flag;
	private Date createDate;
	private BiUserInfo createUser;
	private Project project;
	private BiUserInfo dealUser;
	
	public BiUserInfo getDealUser() {
		return dealUser;
	}
	
	public void setDealUser(BiUserInfo dealUser) {
		this.dealUser = dealUser;
	}
	
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
	
	@Override
	public Date getCreateDate() {
		return createDate;
	}
	
	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public BiUserInfo getCreateUser() {
		return createUser;
	}
	
	@Override
	public void setCreateUser(BiUserInfo createUser) {
		this.createUser = createUser;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
}
