package com.gaoguangjin.bug.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.bug.dao.BugContentDao;
import com.gaoguangjin.bug.dao.BugContentProgressDao;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.bug.entity.BugContentProgress;
import com.gaoguangjin.bug.entity.BugContentVO;
import com.gaoguangjin.project.dao.ProjectDao;
import com.gaoguangjin.project.dao.ProjectModuleDao;
import com.gaoguangjin.project.dao.ProjectUserDao;
import com.gaoguangjin.project.dao.ProjectVersionDao;
import com.gaoguangjin.project.entity.Project;
import com.gaoguangjin.project.entity.ProjectModule;
import com.gaoguangjin.project.entity.ProjectUser;
import com.gaoguangjin.project.entity.ProjectVO;
import com.gaoguangjin.project.entity.ProjectVersion;
import com.gaoguangjin.util.DateUtil;

@Service
public class BugContentService {
	
	@Autowired
	private BugContentDao bugContentDao;
	@Autowired
	private BugContentProgressDao bugContentProgressDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private ProjectUserDao projectUserDao;
	@Autowired
	private ProjectModuleDao projectModuleDao;
	@Autowired
	private ProjectVersionDao projectVersionDao;
	
	/**
	 * 功能：根据用户角色得到 项目版本，模块，用户
	 * @param projectUser
	 * @return
	 */
	public ProjectVO getAddView(ProjectUser projectUser) {
		ProjectVO projectVO = new ProjectVO();
		
		if (null != projectUser && null != projectUser.getProject()) {
			Project project = projectDao.get(projectUser.getProject().getId());
			
			List<ProjectUser> userList = projectUserDao.findByProject(project.getId());
			List<ProjectModule> moduleList = projectModuleDao.findByProject(project.getId());
			List<ProjectVersion> versionList = projectVersionDao.findByProject(project.getId());
			
			projectVO.setId(project.getId());
			projectVO.setProject(project);
			projectVO.setModuleList(moduleList);
			projectVO.setUserList(userList);
			projectVO.setVersionList(versionList);
			
		}
		return projectVO;
	}
	
	/**
	 * 功能：执行保存
	 * @param bugContentVO
	 */
	public void saveBugContent(BugContentVO bugContentVO) {
		
		if (null != bugContentVO) {
			List<BugContent> bugList = bugContentVO.getBugContentList();
			if (null != bugList) {
				for (BugContent bugContent : bugList) {
					
					bugContent.setCreateDate(bugContentVO.getCreateDate());
					bugContent.setCreateUser(bugContentVO.getCreateUser());
					bugContent.setMonth(DateUtil.convertDateToString(bugContentVO.getCreateDate(),
							DateUtil.DATE_FORMAT_yyyyMMdd));
					bugContent.setStatus(BugContent.STATUS_NEW);
					bugContent.setProject(bugContentVO.getProject());
					bugContent.setFlag("Y");
					bugContent.setIsClosed("N");
					bugContent.setIsOpen("N");
					bugContent.setIsSolved("N");
					bugContent.setIsReopen("N");
					
					String id = bugContentDao.save(bugContent);
					
					bugContent.setId(id);
					bugContentDao.update(bugContent);
					
					// 保存bug进度。
					saveBugContentProgress(bugContent);
				}
			}
		}
	}
	
	/**
	 * 功能：保存进度,
	 * @param bugContent
	 */
	private void saveBugContentProgress(BugContent bugContent) {
		BugContentProgress progress = new BugContentProgress();
		progress.setCreateDate(bugContent.getCreateDate());
		// 创建者
		progress.setFromUser(bugContent.getCreateUser());
		// 分配者
		progress.setToUser(projectUserDao.get(bugContent.getUserAssigner().getId()).getBiUserInfo());
		progress.setFlag("Y");
		progress.setStatus(bugContent.getStatus());
		progress.setProject(bugContent.getProject());
		progress.setBugContent(bugContent);
		progress.setMemo(bugContent.getDescribe().trim());
		
		String id = bugContentProgressDao.save(progress);
		progress.setId(id);
		bugContentProgressDao.update(progress);
	}
	
	/**
	 * 功能：根据id得到bug对象
	 * @param id
	 * @return
	 */
	public BugContent get(String id) {
		return bugContentDao.get(id);
	}
	
	/**
	 * 功能：更新bug
	 * @param bug
	 */
	public void update(BugContent bug) {
		// 更新bug
		bugContentDao.update(bug);
		// 更新bugProcess第一个记录
		List<BugContentProgress> progressList = bugContentProgressDao.findBugProgress(bug);
		if (null != progressList) {
			if (progressList.size() > 0) {
				BugContentProgress progress = progressList.get(0);
				progress.setBugContent(bug);
				progress.setMemo(bug.getDescribe());
				bugContentProgressDao.update(progress);
			}
		}
	}
	
	/**
	 * 功能:删除Bug
	 * @param bugContent
	 */
	public void delete(BugContent bugContent) {
		// 删除bug的进度
		bugContentProgressDao.delete(bugContent);
		// 删除bug
		bugContentDao.delete(bugContent);
	}
	
	/**
	 * 功能：axja获取bug名字
	 * @param bugContent
	 * @return
	 */
	public List<BugContent> getBugList(BugContent bugContent) {
		return bugContentDao.getBugList(bugContent);
	}
	
	/**
	 * 功能：得到分页bugContent
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedBugContent(BugContent bugContent, Integer pageNo, int pageSize) {
		return bugContentDao.getPagedBugContent(bugContent, pageNo, pageSize);
	}
	
	/**
	 * 功能：得到未审核的bug/未分配bug
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedAssingAndReviewBug(BugContent bugContent, Integer pageNo, int pageSize) {
		return bugContentDao.getPagedAssingAndReviewBug(bugContent, pageNo, pageSize);
	}
	
	/**
	 * 功能：保存已分配bug
	 * @param bugContent
	 */
	public void saveAssing(BugContent bugContent, String isEmail) {
		BugContent bug = bugContentDao.get(bugContent.getId());
		
		bug.setUserHandler(bugContent.getUserHandler());
		// 是空值那就是打开了
		if (StringUtils.isEmpty(bugContent.getStatus())) {
			bug.setStatus(BugContent.STATUS_OPEN);
			bug.setIsOpen(BugContent.YES);
		}
		else {
			// 关闭原因
			bug.setStatus(bugContent.getStatus());
			bug.setIsClosed(BugContent.YES);
		}
		// 更新以前bug.
		bugContentDao.update(bug);
		
		if (!StringUtils.isEmpty(isEmail) && isEmail.equals(BugContent.YES)) {
			// 发送email
			sendEmail(bugContentDao.get(bugContent.getId()), isEmail);
		}
		
		// 保存bug进度。
		saveBugContentProgress(bugContent, bug);
		
	}
	
	/**
	 * 保存更新进度
	 * @param bugContent
	 * @param bug
	 */
	private void saveBugContentProgress(BugContent bugContent, BugContent bug) {
		BugContentProgress progress = new BugContentProgress();
		progress.setCreateDate(bugContent.getCreateDate());
		
		// 创建者
		progress.setFromUser(bug.getUserAssigner().getBiUserInfo());
		// 解决者
		if (null != bugContent.getUserHandler()) {
			progress.setToUser(projectUserDao.get(bugContent.getUserHandler().getId()).getBiUserInfo());
		}
		else {
			progress.setToUser(projectUserDao.get(bug.getUserAssigner().getId()).getBiUserInfo());
		}
		
		progress.setFlag("Y");
		progress.setStatus(bug.getStatus());
		progress.setProject(bug.getProject());
		progress.setBugContent(bug);
		progress.setMemo(bugContent.getDescribe().trim());
		progress.setPreid(bugContentProgressDao.getPreId(bug));
		
		String id = bugContentProgressDao.save(progress);
		progress.setId(id);
		bugContentProgressDao.update(progress);
	}
	
	/**
	 * 功能：发送邮件
	 * @param bugContent
	 * @param isEmail
	 */
	private void sendEmail(BugContent bug, String isEmail) {
		
		List<ProjectUser> userList = projectUserDao.findByProject(bug.getProject().getId());
		
		List<String> listAddress = new ArrayList<String>();
		
		for (ProjectUser projectUser : userList) {
			listAddress.add(projectUser.getBiUserInfo().getEmail());
		}
		// 高广金 在 [Test] 创建了新的BUG #389792 分配给 高广金
		
		ProjectUser user = projectUserDao.get(bug.getUserHandler().getId());
		
		String title = bug.getCreateUser().getName() + "在[" + bug.getProject().getName() + "]项目创建了新的BUG分配给"
				+ user.getBiUserInfo().getName();
		StringBuffer content = new StringBuffer();
		content.append(bug.getCreateUser().getName() + " 在[" + bug.getProject().getName() + "]项目创建了一个新的BUG");
		content.append("<br>");
		content.append("标题：" + bug.getName());
		content.append("<br>");
		content.append("版本：" + bug.getProjectVersion().getName());
		content.append("<br>");
		content.append("模块：" + bug.getProjectModule().getName());
		content.append("<br>");
		content.append("<br>");
		content.append("<br>");
		content.append("<br>");
		content.append("<br>");
		content.append("指定处理人：" + user.getName());
		
		// Mail.foreachSendMail(listAddress, title, content.toString());
	}
	
	/**
	 * 分页得到属于我的bug
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPageBelognToMe(BugContent bugContent, Integer pageNo, int pageSize) {
		return bugContentDao.getPageBelognToMe(bugContent, pageNo, pageSize);
	}
	
	/**
	 * 功能：bug关闭保存
	 * @param bugContent
	 * @param bugContentVO
	 */
	public void saveClose(BugContent bugContent, BugContentVO bugContentVO) {
		BiUserInfo user = null;
		ProjectUser realHandler = null;
		BugContent bug = bugContentDao.get(bugContent.getId());
		// 得到项目中的所有人员
		List<ProjectUser> userList = projectUserDao.findByProject(bug.getProject().getId());
		bug.setIsClosed("Y");
		bug.setStatus(BugContent.STATUS_FIXED);
		
		// 得到改bug真正的解决者，就是最后一次的fromUser;
		List<BugContentProgress> processList = bugContentVO.getProcessList();
		if (processList.size() > 0) {
			int length = processList.size() - 1;
			user = processList.get(length).getFromUser();
		}
		
		// 循环项目中所有人 ，找出姓名一样的。
		for (ProjectUser projectUser : userList) {
			if (projectUser.getName().equals(user.getName())) {
				realHandler = projectUser;
			}
		}
		bug.setUserHandler(realHandler);
		
		bugContentDao.update(bug);
		
	}
}
