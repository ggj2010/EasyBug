package com.gaoguangjin.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.project.entity.Project;

@Repository
public class ProjectDao extends BaseDao<Project> {
	
	/**
	 * ���ܣ���ѯȫ������
	 * @return
	 */
	public List<Project> findAll() {
		String hql = "from Project where flag='Y'";
		return super.find(hql);
		
	}
	
	/**
	 * ���ܣ���ѯ�ӻ���
	 * @return
	 */
	public List<Project> findProject() {
		String hql = "from Project where flag='Y' and depth<>'1' ";
		return super.find(hql);
		
	}
	
	/**
	 * ���ܣ���ѯ�ӻ�������
	 * @param id
	 * @return
	 */
	public int getChildCount(String id) {
		String hql = "select count(*) from Project where flag='Y' and preId=?";
		return Integer.parseInt(super.find(hql, new Object[] { id }).get(0).toString());
	}
	
	/**
	 * ���ܣ�ɾ����Ŀ
	 * @param project
	 */
	public void deleteProject(Project project) {
		String hql = "update Project set flag='N' where id= '" + project.getId() + "'";
		super.bulkUpdate(hql);
		
	}
	
}
