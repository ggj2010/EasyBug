package com.gaoguangjin.project.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gaoguangjin.bug.dao.BugContentDao;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.project.dao.ProjectModuleDao;
import com.gaoguangjin.project.entity.ProjectModule;

@Service
public class ProjectModuleService {
	
	@Autowired
	private ProjectModuleDao projectModuleDao;
	
	@Autowired
	private BugContentDao bugContentDao;
	
	/**
	 * 功能：保存模块
	 * @param projectModule
	 */
	public void saveModule(ProjectModule projectModule) {
		projectModule.setCreateDate(new Date());
		projectModule.setFlag("Y");
		
		String id = projectModuleDao.save(projectModule);
		projectModuleDao.update(projectModule);
	}
	
	/**
	 * 功能：删除模块
	 * @param id
	 * @return
	 */
	public String deleteModule(BugContent bugContent) {
		JSONObject json = new JSONObject();
		// true 存在不能删除
		if (bugContentDao.findByProjectParam(bugContent)) {
			json.put("type", "02");
		}
		else {
			projectModuleDao.deleteModule(bugContent.getId());
			json.put("type", "01");
		}
		return String.valueOf(json);
	}
}
