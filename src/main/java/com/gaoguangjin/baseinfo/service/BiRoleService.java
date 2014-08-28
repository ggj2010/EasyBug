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
	 * 功能：分页查询
	 * @param biRole
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedBiRole(BiRole biRole, Integer pageNo, int pageSize) {
		return biRoleDao.getPagedBiRole(biRole, pageNo, pageSize);
	}
	
	/**
	 * 保存
	 * @param BiRole
	 */
	public String save(BiRole biRole) {
		return biRoleDao.save(biRole);
	}
	
	/**
	 * 功能：更新
	 * @param BiRole
	 */
	public void update(BiRole biRole) {
		biRoleDao.update(biRole);
	}
	
	/**
	 * 功能：查询全部有交往的角色
	 * @return
	 */
	public List<BiRole> findBiRole() {
		String hql = "from BiRole where flag='Y'";
		return biRoleDao.find(hql);
	}
	
	/**
	 * 功能：逻辑删除
	 * @param BiRole
	 */
	public void delete(BiRole biRole) {
		String hql = "update BiRole set flag='N' where id=?";
		biRoleDao.bulkUpdate(hql, new Object[] { biRole.getId() });
	}
	
	/**
	 * 功能：根据id获取BiRole对象
	 * @param id
	 * @return BiRole对象
	 */
	public BiRole getBiRole(String id) {
		return biRoleDao.get(id);
	}
	
	public void saveRoleMenu(String roleId, String menuStr) {
		// TODO Auto-generated method stub
		
	}
	
	// /**
	// * 功能：根据角色查看该版本所绑定的菜单；
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
	// // 先删除再增加
	// List<BiRoleMenu> menuList = findMenuByRole(roleId);
	// biRoleMenuDao.deleteAll(menuList);
	//		
	// // 再增加
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
