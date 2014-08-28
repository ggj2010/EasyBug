package com.gaoguangjin.count.vo;

import com.framework.base.BaseEntity;

public class BugCountParamVO extends BaseEntity {
	
	private String projectId;
	private String versionId;
	private String moduleId;
	
	private String bugId;
	private String userAssignerId;
	private String userHandlerId;
	private String createUserId;
	
	private String projectUserId;
	private String status;
	private String isOpen;
	private String isClosed;
	private String isSolved;
	private String isReopen;
	private String level;
	
	public String getVersionId() {
		return versionId;
	}
	
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	
	public String getModuleId() {
		return moduleId;
	}
	
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getProjectId() {
		return projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getBugId() {
		return bugId;
	}
	
	public void setBugId(String bugId) {
		this.bugId = bugId;
	}
	
	public String getUserAssignerId() {
		return userAssignerId;
	}
	
	public void setUserAssignerId(String userAssignerId) {
		this.userAssignerId = userAssignerId;
	}
	
	public String getUserHandlerId() {
		return userHandlerId;
	}
	
	public void setUserHandlerId(String userHandlerId) {
		this.userHandlerId = userHandlerId;
	}
	
	public String getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getProjectUserId() {
		return projectUserId;
	}
	
	public void setProjectUserId(String projectUserId) {
		this.projectUserId = projectUserId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getIsOpen() {
		return isOpen;
	}
	
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	public String getIsClosed() {
		return isClosed;
	}
	
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	
	public String getIsSolved() {
		return isSolved;
	}
	
	public void setIsSolved(String isSolved) {
		this.isSolved = isSolved;
	}
	
	public String getIsReopen() {
		return isReopen;
	}
	
	public void setIsReopen(String isReopen) {
		this.isReopen = isReopen;
	}
	
}
