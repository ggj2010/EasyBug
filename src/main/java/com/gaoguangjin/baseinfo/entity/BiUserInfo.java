package com.gaoguangjin.baseinfo.entity;

import com.framework.base.BaseEntity;

public class BiUserInfo extends BaseEntity

{
	private String passWord;
	private String sex;
	private String phone;
	private String email;
	private String isReceiveEmail;
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getIsReceiveEmail() {
		return isReceiveEmail;
	}
	
	public void setIsReceiveEmail(String isReceiveEmail) {
		this.isReceiveEmail = isReceiveEmail;
	}
	
}
