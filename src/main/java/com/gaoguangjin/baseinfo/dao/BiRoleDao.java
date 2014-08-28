package com.gaoguangjin.baseinfo.dao;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiRole;

@Repository
public class BiRoleDao extends BaseDao<BiRole> {
	
	/**
	 * 功能：分页查询
	 * @param BiSysConfig 查询条件
	 * @param pageNo 页码
	 * @param pageSize 行数
	 * @return Page对象
	 * @throws Exception
	 */
	public Page getPagedBiRole(BiRole biRole, Integer pageNo, int pageSize) {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		
		String hql = generateHQL(params, biRole);
		String hqlCount = "select count(*) " + hql;
		int totalSize = super.getHQLCount(hqlCount, params);
		List<?> dbList = super.getHQLPageList(hql, params, startIndex, endIndex);
		
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	/**
	 * 功能：拼hql查询条件
	 * @param hql
	 * @param params
	 * @param BiSysConfig
	 */
	private String generateHQL(List params, BiRole biRole) {
		StringBuffer hql = new StringBuffer();
		hql.append("from BiRole where flag='Y' order by name  ");
		return hql.toString();
	}
	
}
