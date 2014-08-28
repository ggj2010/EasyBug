/**
 *
 */
package com.gaoguangjin.baseinfo.entity;

import java.io.Serializable;

import com.framework.base.BaseEntity;

/**
 * @author duruxin
 * @date 9:35:30 PM
 * @description 菜单项
 */
public class BiMenu extends BaseEntity implements Serializable {
	
	public final String MENU_MU = "00";
	
	/**
	 * 菜单夹
	 */
	public static String MENU_FOLDER = "01";
	
	/**
	 * 菜单
	 */
	public static String MENU_ITEM = "02";
	/**
	 * 按钮
	 */
	public static String MENU_ANNIU = "03";
	
	private String id;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sequence;
	/**
	 * 菜单描述
	 */
	private String description;
	/**
	 * 菜单logo
	 */
	private String logo;
	/**
	 * 跳转URL
	 */
	private String url;
	/**
	 * 菜单类别 00:根菜单 01：菜单夹 02：菜单项
	 */
	private String menuType;
	/**
	 * 上级id
	 */
	private String preId;
	
	private String ButtonCode;
	
	public String getButtonCode() {
		return ButtonCode;
	}
	
	public void setButtonCode(String buttonCode) {
		ButtonCode = buttonCode;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSequence() {
		return sequence;
	}
	
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMenuType() {
		return menuType;
	}
	
	public void setMenuType(String menuType) {
		this.menuType = menuType;
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
	
}
