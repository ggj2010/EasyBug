package com.gaoguangjin.baseinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.dao.BiRoleDao;
import com.gaoguangjin.baseinfo.entity.BiRole;

@Service
public class BiRoleService {
	
	@Autowired
	private BiRoleDao biRoleDao;
	
	/**
	 * ���ܣ���ҳ��ѯ
	 * @param biRole
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedBiRole(BiRole biRole, Integer pageNo, int pageSize) {
		return biRoleDao.getPagedBiRole(biRole, pageNo, pageSize);
	}
	
	/**
	 * ����
	 * @param BiRole
	 */
	public String save(BiRole biRole) {
		return biRoleDao.save(biRole);
	}
	
	/**
	 * ���ܣ�����
	 * @param BiRole
	 */
	public void update(BiRole biRole) {
		biRoleDao.update(biRole);
	}
	
	/**
	 * ���ܣ���ѯȫ���н����Ľ�ɫ
	 * @return
	 */
	public List<BiRole> findBiRole() {
		String hql = "from BiRole where flag='Y'";
		return biRoleDao.find(hql);
	}
	
	/**
	 * ���ܣ��߼�ɾ��
	 * @param BiRole
	 */
	public void delete(BiRole biRole) {
		String hql = "update BiRole set flag='N' where id=?";
		biRoleDao.bulkUpdate(hql, new Object[] { biRole.getId() });
	}
	
	/**
	 * ���ܣ�����id��ȡBiRole����
	 * @param id
	 * @return BiRole����
	 */
	public BiRole getBiRole(String id) {
		return biRoleDao.get(id);
	}
	
	public void saveRoleMenu(String roleId, String menuStr) {
		// TODO Auto-generated method stub
		
	}
	
	// /**
	// * ���ܣ����ݽ�ɫ�鿴�ð汾���󶨵Ĳ˵���
	// * @param roleid
	// * @return
	// */
	// public Map<String, Object> findByRole(String roleId) {
	// String hql = "from BiRoleMenu m where m.flag='Y' and m.biRole.id=? ";
	// List<BiRoleMenu> list = biRoleMenuDao.find(hql, new Object[] { roleId });
	// Map<String, Object> menuMap = new HashMap<String, Object>();
	// for (BiRoleMenu menu : list) {
	// menuMap.put(menu.getBiMenu().getId(), menu);
	// }
	// return menuMap;
	// }
	//	
	// /**
	// *
	// * @param syVersionId
	// * @param menuStr
	// */
	// public void saveRoleMenu(String roleId, String menuStr) {
	// BiRole biRole = new BiRole();
	// biRole.setId(roleId);
	//		
	// // ��ɾ��������
	// List<BiRoleMenu> menuList = findMenuByRole(roleId);
	// biRoleMenuDao.deleteAll(menuList);
	//		
	// // ������
	// if (menuStr != null) {
	// String[] arrayMenu = menuStr.split(",");
	// for (String menuId : arrayMenu) {
	// if (menuId != null && menuId.trim().length() > 0) {
	// BiMenu biMenu = new BiMenu();
	// biMenu.setId(menuId);
	// BiRoleMenu biRoleMenu = new BiRoleMenu();
	// biRoleMenu.setBiRole(biRole);
	// biRoleMenu.setBiMenu(biMenu);
	// biRoleMenu.setFlag("Y");
	// biRoleMenuDao.save(biRoleMenu);
	// }
	// }
	// }
	// }
	//	
	
}
