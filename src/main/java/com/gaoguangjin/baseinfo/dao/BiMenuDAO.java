package com.gaoguangjin.baseinfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.baseinfo.entity.BiMenu;

@Repository
public class BiMenuDAO extends BaseDao<BiMenu> {
	
	/**
	 * ���ܣ����ȫ���˵�
	 * @return
	 */
	public List<BiMenu> findAll() {
		String hql = "from BiMenu where flag='Y' order by sequence";
		return this.find(hql);
	}
	
	/**
	 * ���ܣ�����menucode��ѯ����
	 * @param menuCode
	 * @return
	 */
	public BiMenu getBiMenu(String menuCode) {
		BiMenu biMenu = null;
		String hql = " from BiMenu where menuCode=? and flag='Y'";
		List<BiMenu> list = find(hql, new Object[] { menuCode });
		if (list.size() == 0) {
			biMenu = list.get(0);
		}
		return biMenu;
	}
}
