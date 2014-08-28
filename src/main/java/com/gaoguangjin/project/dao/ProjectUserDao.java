package com.gaoguangjin.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.project.entity.ProjectUser;

@Repository
public class ProjectUserDao extends BaseDao<ProjectUser> {
	
	/**
	 * ���ܣ�������Ŀid ��ѯ�û�
	 * @param projectId
	 * @return
	 */
	public List<ProjectUser> findByProject(String projectId) {
		String hql = "from ProjectUser where flag='Y' and project.id='" + projectId + "'";
		return super.find(hql);
	}
	
	/**
	 * ���ܣ�ִ��ɾ��
	 * @param id
	 * @return
	 */
	public void deleteUser(String id) {
		String hql = "update ProjectUser set flag='N' where id='" + id + "'";
		super.bulkUpdate(hql);
	}
	
	/**
	 * ���ܣ������û�id ��ѯ��λ��
	 * @param userInfo
	 * @return
	 */
	public List<ProjectUser> finByUser(BiUserInfo userInfo) {
		String hql = "from ProjectUser where flag='Y' and biUserInfo.id='" + userInfo.getId() + "'";
		return super.find(hql);
	}
	
	/**
	 * ���ܣ���������Ĭ��״̬
	 */
	public void updateDefault() {
		String hql = "update ProjectUser set isDefault='N'";
		super.bulkUpdate(hql);
	}
	
}
