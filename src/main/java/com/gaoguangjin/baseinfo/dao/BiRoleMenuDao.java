package com.gaoguangjin.baseinfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.baseinfo.entity.BiRoleMenu;

@Repository
public class BiRoleMenuDao extends BaseDao<BiRoleMenu> {
	/**
	 * ���ܣ�����roleId��ѯȫ���ܲ˵�
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findMenuByRole(String roleId) {
		String hql = "from BiRoleMenu where biRole.id=? and biMenu.flag='Y' order by biMenu.sequence";
		List<BiRoleMenu> roleMenuList = super.find(hql, new Object[] { roleId });
		return roleMenuList;
	}
	
	/**
	 * ���ܣ���ѯ���ܲ˵�
	 * @param id
	 * @return
	 */
	public List<BiRoleMenu> findRoleMenuByRole(String roleId) {
		String hql = "from BiRoleMenu where biRole.id=? and biMenu.flag='Y' and ( biMenu.menuType ='02' or biMenu.menuType='01' ) order by biMenu.sequence";
		List<BiRoleMenu> roleMenuList = super.find(hql, new Object[] { roleId });
		return roleMenuList;
	}
	
	
	
	/**
	 * ���ܣ�����roleId��ѯһ��Ȩ�ް�ť
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findBtnByRole(String roleId){
		String hql="from BiRoleMenu where biRole.id=? and biMenu.flag='Y' and biMenu.menuType='03'  order by biMenu.sequence";
		List<BiRoleMenu> roleMenuList=(List<BiRoleMenu>)super.find(hql, new Object[]{roleId});
		return roleMenuList;
	}
	
	/**
	 * ���ܣ�����roleId��ѯ����Ȩ�ް�ť
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findBtnGroupByRole(String roleId){
		String hql="from BiRoleMenu where biRole.id=? and biMenu.flag='Y' and biMenu.menuType='04' order by biMenu.sequence";
		List<BiRoleMenu> roleMenuList=(List<BiRoleMenu>)super.find(hql, new Object[]{roleId});
		return roleMenuList;
	}
	
}
