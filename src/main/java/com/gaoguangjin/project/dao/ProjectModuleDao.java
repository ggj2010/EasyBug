package com.gaoguangjin.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.project.entity.ProjectModule;

@Repository
public class ProjectModuleDao extends BaseDao<ProjectModule> {
	
	/**
	 * ���ܣ�������Ŀid��ѯģ��
	 * @param projectId
	 * @return
	 */
	public List<ProjectModule> findByProject(String projectId) {
		String hql = "from ProjectModule where flag='Y' and project.id='" + projectId + "'";
		return super.find(hql);
		
	}
	
	/**
	 * ���ܣ�ִ��ɾ��
	 * @param id
	 * @return
	 */
	public void deleteModule(String id) {
		String hql = "update ProjectModule set flag='N' where id='" + id + "'";
		super.bulkUpdate(hql);
	}
	
}
