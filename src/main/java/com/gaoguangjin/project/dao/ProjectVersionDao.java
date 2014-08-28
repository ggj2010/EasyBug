package com.gaoguangjin.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.project.entity.ProjectVersion;

@Repository
public class ProjectVersionDao extends BaseDao<ProjectVersion> {
	
	public List<ProjectVersion> findByProject(String projectId) {
		
		String hql = "from ProjectVersion where flag='Y' and project.id='" + projectId + "'";
		
		return super.find(hql);
	}
	
	/**
	 * ¹¦ÄÜ£ºÖ´ÐÐÉ¾³ý
	 * @param id
	 * @return
	 */
	public void deleteVersion(String id) {
		String hql = "update ProjectVersion set flag='N' where id='" + id + "'";
		super.bulkUpdate(hql);
	}
	
}
