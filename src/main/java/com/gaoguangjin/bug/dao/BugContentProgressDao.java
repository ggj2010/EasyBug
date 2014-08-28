package com.gaoguangjin.bug.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.bug.entity.BugContentProgress;

@Repository
public class BugContentProgressDao extends BaseDao<BugContentProgress> {
	
	/**
	 * 功能：根据bugId查询所有进度
	 * @param bug
	 */
	public List<BugContentProgress> findBugProgress(BugContent bug) {
		List<Object> params = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" from BugContentProgress where flag='Y' ");
		
		if (null != bug) {
			if (!StringUtils.isEmpty(bug.getId()))
				hql.append(" and bugContent.id =? ");
			params.add(bug.getId());
		}
		hql.append(" order by createDate ASC ");
		
		return (List<BugContentProgress>) super.getHQLList(hql.toString(), params);
		
	}
	
	/**
	 * 功能：逻辑删除bug对应的子集
	 * @param bugContent
	 */
	public void delete(BugContent bugContent) {
		String hql = "update BugContentProgress set flag='N' where bugContent.id=?";
		super.bulkUpdate(hql, new Object[] { bugContent.getId() });
		
	}
	
	/**
	 * 得到上一级id
	 * @param bugContent
	 */
	public BugContentProgress getPreId(BugContent bugContent) {
		BugContentProgress process = null;
		List<BugContentProgress> processList = findBugProgress(bugContent);
		if (null != processList) {
			if (processList.size() > 0) {
				process = processList.get((processList.size() - 1));
			}
		}
		return process;
	}
}
