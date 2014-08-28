package com.gaoguangjin.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.project.entity.ProjectModule;

@Repository
public class ProjectModuleDao extends BaseDao<ProjectModule> {
	
	/**
	 * 功能：根据项目id查询模块
	 * @param projectId
	 * @return
	 */
	public List<ProjectModule> findByProject(String projectId) {
		String hql = "from ProjectModule where flag='Y' and project.id='" + projectId + "'";
		return super.find(hql);
		
	}
	
	/**
	 * 功能：执行删除
	 * @param id
	 * @return
	 */
	public void deleteModule(String id) {
		String hql = "update ProjectModule set flag='N' where id='" + id + "'";
		super.bulkUpdate(hql);
	}
	
}
