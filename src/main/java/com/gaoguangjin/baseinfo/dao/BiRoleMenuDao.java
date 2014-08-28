package com.gaoguangjin.baseinfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.baseinfo.entity.BiRoleMenu;

@Repository
public class BiRoleMenuDao extends BaseDao<BiRoleMenu> {
	/**
	 * 功能：根据roleId查询全功能菜单
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findMenuByRole(String roleId) {
		String hql = "from BiRoleMenu where biRole.id=? and biMenu.flag='Y' order by biMenu.sequence";
		List<BiRoleMenu> roleMenuList = super.find(hql, new Object[] { roleId });
		return roleMenuList;
	}
	
	/**
	 * 功能：查询功能菜单
	 * @param id
	 * @return
	 */
	public List<BiRoleMenu> findRoleMenuByRole(String roleId) {
		String hql = "from BiRoleMenu where biRole.id=? and biMenu.flag='Y' and ( biMenu.menuType ='02' or biMenu.menuType='01' ) order by biMenu.sequence";
		List<BiRoleMenu> roleMenuList = super.find(hql, new Object[] { roleId });
		return roleMenuList;
	}
	
	
	
	/**
	 * 功能：根据roleId查询一级权限按钮
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findBtnByRole(String roleId){
		String hql="from BiRoleMenu where biRole.id=? and biMenu.flag='Y' and biMenu.menuType='03'  order by biMenu.sequence";
		List<BiRoleMenu> roleMenuList=(List<BiRoleMenu>)super.find(hql, new Object[]{roleId});
		return roleMenuList;
	}
	
	/**
	 * 功能：根据roleId查询耳机权限按钮
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findBtnGroupByRole(String roleId){
		String hql="from BiRoleMenu where biRole.id=? and biMenu.flag='Y' and biMenu.menuType='04' order by biMenu.sequence";
		List<BiRoleMenu> roleMenuList=(List<BiRoleMenu>)super.find(hql, new Object[]{roleId});
		return roleMenuList;
	}
	
}
