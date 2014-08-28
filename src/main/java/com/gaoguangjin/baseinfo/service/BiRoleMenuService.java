package com.gaoguangjin.baseinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.baseinfo.dao.BiRoleMenuDao;
import com.gaoguangjin.baseinfo.entity.BiMenu;
import com.gaoguangjin.baseinfo.entity.BiRole;
import com.gaoguangjin.baseinfo.entity.BiRoleMenu;

@Service
public class BiRoleMenuService {
	
	@Autowired
	private BiRoleMenuDao biRoleMenuDao;
	
	/**
	 * 功能：根据roleId查询全功能菜单
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findMenuByRole(String roleId) {
		return biRoleMenuDao.findMenuByRole(roleId);
	}
	
	/**
	 * 功能：保存角色按钮
	 * @param roleId
	 * @param menuStr
	 */
	public void saveRoleMenu(String roleId, String menuStr) {
		BiRole biRole = new BiRole();
		biRole.setId(roleId);
		
		// 先删除再增加
		List<BiRoleMenu> menuList = findMenuByRole(roleId);
		biRoleMenuDao.deleteAll(menuList);
		
		// 再增加
		if (menuStr != null) {
			String[] arrayMenu = menuStr.split(",");
			for (String menuId : arrayMenu) {
				if (menuId != null && menuId.trim().length() > 0) {
					BiMenu biMenu = new BiMenu();
					biMenu.setId(menuId);
					BiRoleMenu biRoleMenu = new BiRoleMenu();
					biRoleMenu.setBiRole(biRole);
					biRoleMenu.setBiMenu(biMenu);
					biRoleMenu.setFlag("Y");
					biRoleMenuDao.save(biRoleMenu);
				}
			}
		}
	}
	
	/**
	 * 功能：根据roleid 查询全功能菜单
	 * @param id
	 * @return
	 */
	public List<BiRoleMenu> findRoleMenuByRole(String id) {
		return biRoleMenuDao.findRoleMenuByRole(id);
	}
	
	/**
	 * 功能：查询按钮权限
	 * @param string
	 * @return
	 */
	public String finRoleMenuButton(String roleId) {
		StringBuffer str = new StringBuffer();
		// 得到一级权限按钮code
		List<BiRoleMenu> btnList = findBtnByRole(roleId);
		// 得到二级权限按钮下面的code
		List<BiRoleMenu> btn1List = findBtnGroupByRole(roleId);
		for (BiRoleMenu biRoleMenu : btnList) {
			str.append(biRoleMenu.getBiMenu().getButtonCode());
		}
		for (BiRoleMenu biRoleMenu : btn1List) {
			str.append(biRoleMenu.getBiMenu().getButtonCode());
		}
		return str.toString();
	}
	
	/**
	 * 功能：根据roleId查询一级权限按钮code
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findBtnByRole(String roleId) {
		return biRoleMenuDao.findBtnByRole(roleId);
	}
	
	/**
	 * 功能：根据roleId查询二级权限按钮code
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findBtnGroupByRole(String roleId) {
		return biRoleMenuDao.findBtnGroupByRole(roleId);
	}
	
}
