package com.gaoguangjin.project.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gaoguangjin.bug.dao.BugContentDao;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.project.dao.ProjectVersionDao;
import com.gaoguangjin.project.entity.ProjectVersion;

@Service
public class ProjectVersionService {
	
	@Autowired
	private ProjectVersionDao projectVersionDao;
	@Autowired
	private BugContentDao bugContentDao;
	
	/**
	 * 功能：版本模块
	 * @param projectVersion
	 */
	public void saveVersion(ProjectVersion projectVersion) {
		
		projectVersion.setCreateDate(new Date());
		projectVersion.setFlag("Y");
		
		String id = projectVersionDao.save(projectVersion);
		projectVersionDao.update(projectVersion);
	}
	
	/**
	 * 功能：删除模块
	 * @param id
	 * @return
	 */
	public String deleteVersion(BugContent bugContent) {
		JSONObject json = new JSONObject();
		// true 存在不能删除
		if (bugContentDao.findByProjectParam(bugContent)) {
			json.put("type", "02");
		}
		else {
			projectVersionDao.deleteVersion(bugContent.getId());
			json.put("type", "01");
		}
		return String.valueOf(json);
	}
	
}
