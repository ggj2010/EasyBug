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
	 * ���ܣ�����roleId��ѯȫ���ܲ˵�
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findMenuByRole(String roleId) {
		return biRoleMenuDao.findMenuByRole(roleId);
	}
	
	/**
	 * ���ܣ������ɫ��ť
	 * @param roleId
	 * @param menuStr
	 */
	public void saveRoleMenu(String roleId, String menuStr) {
		BiRole biRole = new BiRole();
		biRole.setId(roleId);
		
		// ��ɾ��������
		List<BiRoleMenu> menuList = findMenuByRole(roleId);
		biRoleMenuDao.deleteAll(menuList);
		
		// ������
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
	 * ���ܣ�����roleid ��ѯȫ���ܲ˵�
	 * @param id
	 * @return
	 */
	public List<BiRoleMenu> findRoleMenuByRole(String id) {
		return biRoleMenuDao.findRoleMenuByRole(id);
	}
	
	/**
	 * ���ܣ���ѯ��ťȨ��
	 * @param string
	 * @return
	 */
	public String finRoleMenuButton(String roleId) {
		StringBuffer str = new StringBuffer();
		// �õ�һ��Ȩ�ް�ťcode
		List<BiRoleMenu> btnList = findBtnByRole(roleId);
		// �õ�����Ȩ�ް�ť�����code
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
	 * ���ܣ�����roleId��ѯһ��Ȩ�ް�ťcode
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findBtnByRole(String roleId) {
		return biRoleMenuDao.findBtnByRole(roleId);
	}
	
	/**
	 * ���ܣ�����roleId��ѯ����Ȩ�ް�ťcode
	 * @param roleId
	 * @return
	 */
	public List<BiRoleMenu> findBtnGroupByRole(String roleId) {
		return biRoleMenuDao.findBtnGroupByRole(roleId);
	}
	
}
