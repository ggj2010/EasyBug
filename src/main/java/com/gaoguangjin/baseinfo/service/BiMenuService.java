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
	 * ���ܣ�����˵�
	 * @param biMenu
	 */
	public String saveBiMenu(BiMenu biMenu) {
		return biMenuDao.save(biMenu);
	}
	
	/**
	 * ���ܣ�����
	 * @param biMenu
	 */
	public void updateBiMenu(BiMenu biMenu) {
		biMenuDao.saveOrUpdate(biMenu);
	}
	
	/**
	 * ���ܣ�����id��ò˵�ʵ�����
	 * @param id
	 * @return BiMenu
	 */
	public BiMenu getBiMenu(String id) {
		return biMenuDao.get(id);
	}
	
	/**
	 * ���ܣ�����menucode��ѯ����
	 * @param menuCode
	 * @return
	 */
	public BiMenu getBiMenuByMenuCode(String menuCode) {
		return biMenuDao.getBiMenu(menuCode);
	}
	
	/**
	 * ���ܣ����ȫ���˵�
	 * @return
	 */
	public List<BiMenu> findAll() {
		return biMenuDao.findAll();
	}
	
	/**
	 * ���ܣ��������
	 * @return
	 */
	public int getCount() {
		String hql = "select count(*) from BiMenu where flag='Y'";
		return Integer.parseInt(biMenuDao.find(hql, new Object[] {}).get(0).toString());
	}
	
	/**
	 * ���ܣ�����id�鿴���Ӷ������
	 * @param id
	 * @return
	 */
	public int getChildCount(String id) {
		String hql = "select count(*) from BiMenu where preId=?";
		return Integer.parseInt(biMenuDao.find(hql, new Object[] { id }).get(0).toString());
	}
	
	/**
	 * ���ܣ�����roleId��ѯȫ���ܲ˵�
	 * @param roleId
	 * @return
	 */
	public List<BiMenu> findBtn() {
		String hql = "from BiMenu where flag='Y' and menuType='03'  order by sequence";
		List<BiMenu> menuList = biMenuDao.find(hql);
		return menuList;
	}
	
	/**
	 * ���ܣ�����roleId��ѯȫ���ܲ˵�
	 * @param roleId
	 * @return
	 */
	public List<BiMenu> findMenu() {
		String hql = "from BiMenu where flag='Y' and ( menuType ='02' or menuType='01' ) order by sequence";
		List<BiMenu> menuList = biMenuDao.find(hql);
		return menuList;
	}
	
	/**
	 * ���ܣ�����roleId��ѯȫ���ܲ˵�
	 * @param roleId
	 * @return
	 */
	public List<BiMenu> findBtnGroup() {
		String hql = "from BiMenu where flag='Y' and menuType='04'  order by sequence";
		List<BiMenu> menuList = biMenuDao.find(hql);
		return menuList;
	}
	
}
