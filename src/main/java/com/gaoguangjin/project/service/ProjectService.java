package com.gaoguangjin.project.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaoguangjin.project.dao.ProjectDao;
import com.gaoguangjin.project.dao.ProjectModuleDao;
import com.gaoguangjin.project.dao.ProjectUserDao;
import com.gaoguangjin.project.dao.ProjectVersionDao;
import com.gaoguangjin.project.entity.Project;
import com.gaoguangjin.project.entity.ProjectModule;
import com.gaoguangjin.project.entity.ProjectUser;
import com.gaoguangjin.project.entity.ProjectVO;
import com.gaoguangjin.project.entity.ProjectVersion;

@Service
public class ProjectService {
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private ProjectUserDao ProjectUserDao;
	@Autowired
	private ProjectModuleDao ProjectModuleDao;
	@Autowired
	private ProjectVersionDao ProjectVersionDao;
	
	/*
	 * 
	 */
	public String getTreeData() {
		
		List<Project> listProject = projectDao.findAll();
		// һ����û���Զ���������
		if (listProject.size() == 0) {
			Project project = new Project();
			project.setName("���Ǹ�����");
			project.setFlag("Y");
			project.setTypeCode(project.TYPE_CODE_FL);
			project.setDepth(1);
			String id = projectDao.save(project);
			project.setId(id);
			projectDao.update(project);
		}
		
		JSONArray arrayJson = new JSONArray();
		for (Project project : listProject) {
			int childCount = projectDao.getChildCount(project.getId());
			JSONObject json = new JSONObject();
			json.put("id", project.getId());
			json.put("name", project.getName());
			json.put("pId", project.getPreId());
			json.put("isParent", childCount > 0 ? true : false);
			json.put("type", 2);
			arrayJson.add(json);
		}
		return arrayJson.toJSONString();
	}
	
	/**
	 * ���ܣ��鿴��Ŀҳ��
	 * @param project
	 * @return
	 */
	public ProjectVO findProjectVO(Project project) {
		ProjectVO projectVO = new ProjectVO();
		project = projectDao.get(project.getId());
		// ����Id
		projectVO.setId(project.getId());
		
		// �жϴ�id�Ǹ�Ŀ¼������Ŀ
		if (null != project) {
			if (!StringUtils.isEmpty(project.getDepth() + "")) {
				// ��Ŀ¼
				if (project.getDepth() == 1) {
					projectVO.setDepth("1");
				}
				else {
					// ��Ŀ
					List<ProjectUser> userList = ProjectUserDao.findByProject(project.getId());
					List<ProjectModule> moduleList = ProjectModuleDao.findByProject(project.getId());
					List<ProjectVersion> versionList = ProjectVersionDao.findByProject(project.getId());
					
					projectVO.setProject(project);
					projectVO.setModuleList(moduleList);
					projectVO.setUserList(userList);
					
					projectVO.setVersionList(versionList);
					projectVO.setDepth("2");
				}
			}
		}
		return projectVO;
	}
	
	/**
	 * ���ܣ������Ŀ
	 * @return
	 */
	public ProjectVO saveProject(ProjectVO projectVO) {
		Project project = projectVO.getProject();
		project.setDepth(2);
		project.setFlag("Y");
		project.setCreateDate(new Date());
		project.setPreId(projectVO.getId());
		
		String id = projectDao.save(project);
		projectDao.update(project);
		projectVO.setId(id);
		
		return projectVO;
	}
	
	/**
	 * ���ܣ�����id�õ���Ŀ����
	 * @param project
	 * @return
	 */
	public Project get(String id) {
		return projectDao.get(id);
	}
	
	/**
	 * ���ܣ�������Ŀ
	 * @param project
	 */
	public void update(Project project) {
		projectDao.update(project);
	}
	
	/**
	 * ���ܣ�ɾ���û�
	 * @param project
	 */
	public void delete(Project project) {
		projectDao.deleteProject(project);
		
	}
	
	/**
	 * ���ܣ��õ�������Ŀ����
	 * @return
	 */
	public ProjectVO getProject() {
		ProjectVO projectVO = new ProjectVO();
		List<Project> projectList = projectDao.findProject();
		projectVO.setProjectList(projectList);
		return projectVO;
	}
	
	/**
	 * ���ܣ�ajax��ȡ��Ŀ�����ģ��Ͱ汾
	 * @param key
	 */
	public String getProjectParm(Project project) {
		JSONArray arrayJson = new JSONArray();
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();
		
		JSONArray versionJason = new JSONArray();
		JSONArray userJason = new JSONArray();
		JSONArray moduleJason = new JSONArray();
		
		List<ProjectUser> userList = ProjectUserDao.findByProject(project.getId());
		for (ProjectUser projectUser : userList) {
			JSONObject user = new JSONObject();
			user.put("id", projectUser.getId());
			user.put("name", projectUser.getName());
			userJason.add(user);
			
		}
		List<ProjectModule> moduleList = ProjectModuleDao.findByProject(project.getId());
		for (ProjectModule projectModule : moduleList) {
			
			JSONObject module = new JSONObject();
			module.put("id", projectModule.getId());
			module.put("name", projectModule.getName());
			moduleJason.add(module);
		}
		List<ProjectVersion> versionList = ProjectVersionDao.findByProject(project.getId());
		for (ProjectVersion projectVersion : versionList) {
			JSONObject version = new JSONObject();
			version.put("id", projectVersion.getId());
			version.put("name", projectVersion.getName());
			versionJason.add(version);
		}
		
		json1.put("type", "version");
		json1.put("version", versionJason);
		
		json2.put("type", "user");
		json2.put("user", userJason);
		
		json3.put("type", "module");
		json3.put("module", moduleJason);
		
		arrayJson.add(json1);
		arrayJson.add(json2);
		arrayJson.add(json3);
		
		return String.valueOf(arrayJson);
	}
}
