package com.gaoguangjin.backup.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gaoguangjin.backup.entity.DBbackUp;
import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.base.Page;

@Repository
public class DBbackUpDao extends BaseDao<DBbackUp> {
	
	public Page getPagedBackUp(DBbackUp dBbackUp, Integer pageNo, int pageSize) {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		
		String hql = generateHQL(params, dBbackUp);
		String hqlCount = "select count(*) " + hql;
		int totalSize = super.getHQLCount(hqlCount, params);
		List<?> dbList = super.getHQLPageList(hql, params, startIndex, endIndex);
		
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	private String generateHQL(List<Object> params, DBbackUp dBbackUp) {
		StringBuffer hql = new StringBuffer();
		hql.append("from DBbackUp where flag='Y'");
		if (dBbackUp != null) {
			if (null != dBbackUp.getCreateUser()) {
				if (!StringUtils.isEmpty(dBbackUp.getCreateUser().getId())) {
					hql.append(" and createUser.id = ?");
					params.add(dBbackUp.getCreateUser().getId());
				}
			}
			if (null != dBbackUp.getBeginDate()) {
				hql.append(" and createDate >= ? ");
				params.add(dBbackUp.getBeginDate());
			}
			if (null != dBbackUp.getEndDate()) {
				hql.append(" and createDate <= ? ");
				params.add(dBbackUp.getEndDate());
			}
		}
		
		hql.append(" order by createDate desc");
		return hql.toString();
	}
}
