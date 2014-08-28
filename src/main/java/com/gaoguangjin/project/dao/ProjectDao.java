package com.gaoguangjin.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.project.entity.Project;

@Repository
public class ProjectDao extends BaseDao<Project> {
	
	/**
	 * 功能：查询全部机构
	 * @return
	 */
	public List<Project> findAll() {
		String hql = "from Project where flag='Y'";
		return super.find(hql);
		
	}
	
	/**
	 * 功能：查询子机构
	 * @return
	 */
	public List<Project> findProject() {
		String hql = "from Project where flag='Y' and depth<>'1' ";
		return super.find(hql);
		
	}
	
	/**
	 * 功能：查询子机构数量
	 * @param id
	 * @return
	 */
	public int getChildCount(String id) {
		String hql = "select count(*) from Project where flag='Y' and preId=?";
		return Integer.parseInt(super.find(hql, new Object[] { id }).get(0).toString());
	}
	
	/**
	 * 功能：删除项目
	 * @param project
	 */
	public void deleteProject(Project project) {
		String hql = "update Project set flag='N' where id= '" + project.getId() + "'";
		super.bulkUpdate(hql);
		
	}
	
}
