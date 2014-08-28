package com.gaoguangjin.bug.entity;

import java.util.Date;

import com.framework.base.BaseEntity;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.project.entity.Project;

public class BugContentProgress extends BaseEntity {
	private String id;
	private String memo;
	private String flag;
	private BiUserInfo fromUser;
	private BiUserInfo toUser;
	private Date createDate;
	private BugContent bugContent;
	private Project project;
	private String status;
	private BugContentProgress preid;
	
	public BugContentProgress getPreid() {
		return preid;
	}
	
	public void setPreid(BugContentProgress preid) {
		this.preid = preid;
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
	public String getMemo() {
		return memo;
	}
	
	@Override
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public String getFlag() {
		return flag;
	}
	
	@Override
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public BiUserInfo getFromUser() {
		return fromUser;
	}
	
	public void setFromUser(BiUserInfo fromUser) {
		this.fromUser = fromUser;
	}
	
	public BiUserInfo getToUser() {
		return toUser;
	}
	
	public void setToUser(BiUserInfo toUser) {
		this.toUser = toUser;
	}
	
	@Override
	public Date getCreateDate() {
		return createDate;
	}
	
	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public BugContent getBugContent() {
		return bugContent;
	}
	
	public void setBugContent(BugContent bugContent) {
		this.bugContent = bugContent;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
