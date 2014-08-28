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
	 * ���ܣ�����bugId��ѯ���н���
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
	 * ���ܣ��߼�ɾ��bug��Ӧ���Ӽ�
	 * @param bugContent
	 */
	public void delete(BugContent bugContent) {
		String hql = "update BugContentProgress set flag='N' where bugContent.id=?";
		super.bulkUpdate(hql, new Object[] { bugContent.getId() });
		
	}
	
	/**
	 * �õ���һ��id
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
