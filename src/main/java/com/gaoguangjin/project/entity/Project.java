package com.gaoguangjin.project.entity;

import java.util.Date;

import com.framework.base.BaseEntity;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;

public class Project extends BaseEntity {
	
	public final static String TYPE_CODE_FL = "01";
	final static String TYPE_CODE_XM = "02";
	
	private String id;
	private String name;
	private String flag;
	private Date createDate;
	private Date beginDate;
	
	private Date endDate;
	private BiUserInfo createUser;
	// 项目负责人
	private BiUserInfo managerUser;
	// 项目代号
	private String codeNumber;
	
	private String content;
	private String preId;
	private String typeCode;
	private int depth;;
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getPreId() {
		return preId;
	}
	
	public void setPreId(String preId) {
		this.preId = preId;
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public Date getEndDate() {
		return endDate;
	}
	
	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public BiUserInfo getManagerUser() {
		return managerUser;
	}
	
	public void setManagerUser(BiUserInfo managerUser) {
		this.managerUser = managerUser;
	}
	
	public String getCodeNumber() {
		return codeNumber;
	}
	
	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}
	
	@Override
	public Date getBeginDate() {
		return beginDate;
	}
	
	@Override
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
}
