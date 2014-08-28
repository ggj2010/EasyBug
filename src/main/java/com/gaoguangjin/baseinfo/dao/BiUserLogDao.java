package com.gaoguangjin.baseinfo.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiUserLog;

@Repository
public class BiUserLogDao extends BaseDao<BiUserLog> {
	/**
	 * 查询到没有format的数据
	 * @return
	 */
	public List<BiUserLog> findNotFormat() {
		List<BiUserLog> list = new ArrayList<BiUserLog>();
		try {
			String hql = "from BiUserLog where isFormat='N'";
			Object obj = super.getHQLPageList(hql, null, 0, 100);
			if (null != obj) {
				list = (List<BiUserLog>) obj;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 功能：查询没有格式化的日志
	 */
	public int findNotFormatCount() {
		int i = 0;
		String hql = "from BiUserLog where isFormat='N'";
		i = super.getHQLCount(hql, null);
		return i;
	}
	
	public Page getPagedBiUserLog(BiUserLog biUserLog, Integer pageNo, int pageSize) {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		
		String hql = generateHQL(params, biUserLog);
		String hqlCount = "select count(*) " + hql;
		int totalSize = super.getHQLCount(hqlCount, params);
		List<?> dbList = super.getHQLPageList(hql, params, startIndex, endIndex);
		
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	private String generateHQL(List<Object> params, BiUserLog biUserLog) {
		StringBuffer hql = new StringBuffer();
		hql.append("from BiUserLog where isFormat='Y'");
		if (biUserLog != null) {
			if (null != biUserLog.getBiUserInfo()) {
				if (!StringUtils.isEmpty(biUserLog.getBiUserInfo().getId())) {
					hql.append(" and biUserInfo.id = ?");
					params.add(biUserLog.getBiUserInfo().getId());
				}
			}
			if (null != biUserLog.getBeginDate()) {
				hql.append(" and createDate >= ? ");
				params.add(biUserLog.getBeginDate());
			}
			if (null != biUserLog.getEndDate()) {
				hql.append(" and createDate <= ? ");
				params.add(biUserLog.getEndDate());
			}
		}
		
		hql.append(" order by createDate desc");
		return hql.toString();
	}
	
}
