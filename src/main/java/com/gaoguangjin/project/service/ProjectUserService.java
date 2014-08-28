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
	 * ���ܣ������û�
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
	 * ���ܣ�ɾ���û�
	 * @param id
	 * @return
	 */
	public String deleteUser(BugContent bugContent) {
		JSONObject json = new JSONObject();
		// true ���ڲ���ɾ��
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
	 * ���ܣ��õ�Ա����λ
	 * @param userInfo
	 * @return
	 */
	public List<ProjectUser> findProjectUser(BiUserInfo userInfo) {
		return projectUserDao.finByUser(userInfo);
	}
	
	/**
	 * ���ܣ��õ��û���ɫ
	 * @param projectUser
	 * @return
	 */
	public ProjectUser getProjectUser(ProjectUser projectUser) {
		// ��������״̬
		projectUserDao.updateDefault();
		
		return projectUserDao.get(projectUser.getId());
	}
	
	/**
	 * ���ܣ����¸�λ
	 * @param projectUser
	 */
	public void update(ProjectUser projectUser) {
		projectUserDao.update(projectUser);
	}
	
}
