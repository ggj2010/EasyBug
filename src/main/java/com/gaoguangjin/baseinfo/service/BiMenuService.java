package com.gaoguangjin.baseinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.baseinfo.dao.BiMenuDAO;
import com.gaoguangjin.baseinfo.entity.BiMenu;

@Service
public class BiMenuService {
	@Autowired
	private BiMenuDAO biMenuDao;
	
	/**
	 * 功能：保存菜单
	 * @param biMenu
	 */
	public String saveBiMenu(BiMenu biMenu) {
		return biMenuDao.save(biMenu);
	}
	
	/**
	 * 功能：更新
	 * @param biMenu
	 */
	public void updateBiMenu(BiMenu biMenu) {
		biMenuDao.saveOrUpdate(biMenu);
	}
	
	/**
	 * 功能：根据id获得菜单实体对象
	 * @param id
	 * @return BiMenu
	 */
	public BiMenu getBiMenu(String id) {
		return biMenuDao.get(id);
	}
	
	/**
	 * 功能：根据menucode查询对象
	 * @param menuCode
	 * @return
	 */
	public BiMenu getBiMenuByMenuCode(String menuCode) {
		return biMenuDao.getBiMenu(menuCode);
	}
	
	/**
	 * 功能：获得全部菜单
	 * @return
	 */
	public List<BiMenu> findAll() {
		return biMenuDao.findAll();
	}
	
	/**
	 * 功能：获得数量
	 * @return
	 */
	public int getCount() {
		String hql = "select count(*) from BiMenu where flag='Y'";
		return Integer.parseInt(biMenuDao.find(hql, new Object[] {}).get(0).toString());
	}
	
	/**
	 * 功能：根据id查看其子对象个数
	 * @param id
	 * @return
	 */
	public int getChildCount(String id) {
		String hql = "select count(*) from BiMenu where preId=?";
		return Integer.parseInt(biMenuDao.find(hql, new Object[] { id }).get(0).toString());
	}
	
	/**
	 * 功能：根据roleId查询全功能菜单
	 * @param roleId
	 * @return
	 */
	public List<BiMenu> findBtn() {
		String hql = "from BiMenu where flag='Y' and menuType='03'  order by sequence";
		List<BiMenu> menuList = biMenuDao.find(hql);
		return menuList;
	}
	
	/**
	 * 功能：根据roleId查询全功能菜单
	 * @param roleId
	 * @return
	 */
	public List<BiMenu> findMenu() {
		String hql = "from BiMenu where flag='Y' and ( menuType ='02' or menuType='01' ) order by sequence";
		List<BiMenu> menuList = biMenuDao.find(hql);
		return menuList;
	}
	
	/**
	 * 功能：根据roleId查询全功能菜单
	 * @param roleId
	 * @return
	 */
	public List<BiMenu> findBtnGroup() {
		String hql = "from BiMenu where flag='Y' and menuType='04'  order by sequence";
		List<BiMenu> menuList = biMenuDao.find(hql);
		return menuList;
	}
	
}
