package com.gaoguangjin.project.entity;

import java.util.Date;

import com.gaoguangjin.baseinfo.entity.BiUserInfo;

public class ProjectVersion {
	private String id;
	private String name;
	private String flag;
	private Date createDate;
	private BiUserInfo createUser;
	private Project project;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public BiUserInfo getCreateUser() {
		return createUser;
	}
	
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
