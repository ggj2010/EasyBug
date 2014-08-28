/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.gaoguangjin.baseinfo.entity;

import com.framework.base.BaseEntity;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

public class BiRoleMenu extends BaseEntity {
	private static final long serialVersionUID = 5454155825314635342L;
	
	// columns START
	private String id;
	private BiMenu biMenu;
	private BiRole biRole;
	
	// columns END
	
	public BiRoleMenu() {
	}
	
	public BiRoleMenu(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public BiRole getBiRole() {
		return biRole;
	}
	
	public void setBiRole(BiRole biRole) {
		this.biRole = biRole;
	}
	
	public BiMenu getBiMenu() {
		return biMenu;
	}
	
	public void setBiMenu(BiMenu biMenu) {
		this.biMenu = biMenu;
	}
	
}
