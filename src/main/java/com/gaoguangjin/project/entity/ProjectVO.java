package com.gaoguangjin.project.entity;

import java.util.List;

public class ProjectVO {
	
	private String id;
	
	private String depth;
	private Project project;
	private List<ProjectModule> moduleList;
	private List<ProjectUser> userList;
	private List<ProjectVersion> versionList;
	private List<Project> projectList;
	
	public List<Project> getProjectList() {
		return projectList;
	}
	
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDepth() {
		return depth;
	}
	
	public void setDepth(String depth) {
		this.depth = depth;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public List<ProjectModule> getModuleList() {
		return moduleList;
	}
	
	public void setModuleList(List<ProjectModule> moduleList) {
		this.moduleList = moduleList;
	}
	
	public List<ProjectUser> getUserList() {
		return userList;
	}
	
	public void setUserList(List<ProjectUser> userList) {
		this.userList = userList;
	}
	
	public List<ProjectVersion> getVersionList() {
		return versionList;
	}
	
	public void setVersionList(List<ProjectVersion> versionList) {
		this.versionList = versionList;
	}
}
