package com.gaoguangjin.project.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.bug.dao.BugContentDao;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.project.dao.ProjectUserDao;
import com.gaoguangjin.project.entity.ProjectUser;

@Service
public class ProjectUserService {
	@Autowired
	private ProjectUserDao projectUserDao;
	@Autowired
	private BugContentDao bugContentDao;
	
	/**
	 * 功能：保存用户
	 * @param projectUser
	 */
	public void saveUser(ProjectUser projectUser) {
		projectUser.setCreateDate(new Date());
		projectUser.setFlag("Y");
		projectUser.setIsDefault("N");
		String id = projectUserDao.save(projectUser);
		projectUserDao.update(projectUser);
		
	}
	
	/**
	 * 功能：删除用户
	 * @param id
	 * @return
	 */
	public String deleteUser(BugContent bugContent) {
		JSONObject json = new JSONObject();
		// true 存在不能删除
		if (bugContentDao.findByProjectParam(bugContent)) {
			json.put("type", "02");
		}
		else {
			projectUserDao.deleteUser(bugContent.getId());
			json.put("type", "01");
		}
		return String.valueOf(json);
	}
	
	/**
	 * 功能：得到员工岗位
	 * @param userInfo
	 * @return
	 */
	public List<ProjectUser> findProjectUser(BiUserInfo userInfo) {
		return projectUserDao.finByUser(userInfo);
	}
	
	/**
	 * 功能：得到用户角色
	 * @param projectUser
	 * @return
	 */
	public ProjectUser getProjectUser(ProjectUser projectUser) {
		// 更新所有状态
		projectUserDao.updateDefault();
		
		return projectUserDao.get(projectUser.getId());
	}
	
	/**
	 * 功能：更新岗位
	 * @param projectUser
	 */
	public void update(ProjectUser projectUser) {
		projectUserDao.update(projectUser);
	}
	
}
