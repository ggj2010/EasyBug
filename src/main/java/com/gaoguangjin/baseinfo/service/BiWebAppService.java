package com.gaoguangjin.baseinfo.service;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.dao.BiWebAppDao;
import com.gaoguangjin.baseinfo.entity.BiWebApp;

@Service
public class BiWebAppService {
	@Autowired
	private BiWebAppDao biWebAppDao;
	
	/**
	 * 功能：根据教室id查询出教室信息
	 * 
	 * @param id
	 * @return 返回教室实体类
	 */
	public BiWebApp getBiWebApp(String id) {
		return biWebAppDao.get(id);
	}
	
	/**
	 * 功能：教室分页查询
	 * 
	 * @param biWebApp
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Page getPagedBiWebApp(BiWebApp biWebApp, int pageNo, int pageSize) throws Exception {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		
		String hql = generateHQL(biWebApp, params);
		String hqlCount = "select count(*) " + hql;
		Integer totalSize = biWebAppDao.getHQLCount(hqlCount, params);
		List<?> dbList = biWebAppDao.getHQLPageList(hql, params, startIndex, endIndex);
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	/**
	 * 功能：组装hql
	 * @param biWebApp
	 * @param params
	 * @return
	 */
	private String generateHQL(BiWebApp biWebApp, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from BiWebApp where flag='Y'  ");
		
		if (null != biWebApp) {
			if (!StringUtils.isEmpty(biWebApp.getName())) {
				sql.append(" and name like '%" + biWebApp.getName() + "%'");
			}
		}
		
		return sql.toString();
	}
	
	/**
	 * 功能：删除教室信息，假删除
	 * 
	 * @param biWebApp
	 */
	public void delete(BiWebApp biWebApp) {
		String hql = "update BiWebApp set flag='N' where id=?";
		biWebAppDao.bulkUpdate(hql, new Object[] { biWebApp.getId() });
	}
	
	/**
	 * 功能：新增一个教室，并且同一校区教室名称不允许重复
	 * 
	 * @param biWebApp
	 */
	public void save(BiWebApp biWebApp) {
		biWebAppDao.save(biWebApp);
	}
	
	/**
	 * 功能：更新教室信息
	 * 
	 * @param biWebApp
	 */
	public void update(BiWebApp biWebApp) {
		biWebAppDao.update(biWebApp);
	}
}
