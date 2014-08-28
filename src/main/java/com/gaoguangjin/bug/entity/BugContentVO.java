package com.gaoguangjin.bug.entity;

import java.util.List;

import com.framework.base.BaseEntity;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.project.entity.Project;

public class BugContentVO extends BaseEntity {
	
	private List<BugContent> bugContentList;
	
	private List<BugContentProgress> processList;
	private Project project;
	private BiUserInfo lastHandLer;
	
	public BiUserInfo getLastHandLer() {
		return lastHandLer;
	}
	
	public void setLastHandLer(BiUserInfo lastHandLer) {
		this.lastHandLer = lastHandLer;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public List<BugContent> getBugContentList() {
		return bugContentList;
	}
	
	public void setBugContentList(List<BugContent> bugContentList) {
		this.bugContentList = bugContentList;
	}
	
	public List<BugContentProgress> getProcessList() {
		return processList;
	}
	
	public void setProcessList(List<BugContentProgress> processList) {
		this.processList = processList;
	}
	
}
