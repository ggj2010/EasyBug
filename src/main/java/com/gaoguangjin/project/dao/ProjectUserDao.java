package com.gaoguangjin.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.project.entity.ProjectUser;

@Repository
public class ProjectUserDao extends BaseDao<ProjectUser> {
	
	/**
	 * 功能：根据项目id 查询用户
	 * @param projectId
	 * @return
	 */
	public List<ProjectUser> findByProject(String projectId) {
		String hql = "from ProjectUser where flag='Y' and project.id='" + projectId + "'";
		return super.find(hql);
	}
	
	/**
	 * 功能：执行删除
	 * @param id
	 * @return
	 */
	public void deleteUser(String id) {
		String hql = "update ProjectUser set flag='N' where id='" + id + "'";
		super.bulkUpdate(hql);
	}
	
	/**
	 * 功能：根据用户id 查询岗位。
	 * @param userInfo
	 * @return
	 */
	public List<ProjectUser> finByUser(BiUserInfo userInfo) {
		String hql = "from ProjectUser where flag='Y' and biUserInfo.id='" + userInfo.getId() + "'";
		return super.find(hql);
	}
	
	/**
	 * 功能：更新所有默认状态
	 */
	public void updateDefault() {
		String hql = "update ProjectUser set isDefault='N'";
		super.bulkUpdate(hql);
	}
	
}
