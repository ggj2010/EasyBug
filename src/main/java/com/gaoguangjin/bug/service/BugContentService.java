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
	 * ���ܣ������û���ɫ�õ� ��Ŀ�汾��ģ�飬�û�
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
	 * ���ܣ�ִ�б���
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
					
					// ����bug���ȡ�
					saveBugContentProgress(bugContent);
				}
			}
		}
	}
	
	/**
	 * ���ܣ��������,
	 * @param bugContent
	 */
	private void saveBugContentProgress(BugContent bugContent) {
		BugContentProgress progress = new BugContentProgress();
		progress.setCreateDate(bugContent.getCreateDate());
		// ������
		progress.setFromUser(bugContent.getCreateUser());
		// ������
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
	 * ���ܣ�����id�õ�bug����
	 * @param id
	 * @return
	 */
	public BugContent get(String id) {
		return bugContentDao.get(id);
	}
	
	/**
	 * ���ܣ�����bug
	 * @param bug
	 */
	public void update(BugContent bug) {
		// ����bug
		bugContentDao.update(bug);
		// ����bugProcess��һ����¼
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
	 * ����:ɾ��Bug
	 * @param bugContent
	 */
	public void delete(BugContent bugContent) {
		// ɾ��bug�Ľ���
		bugContentProgressDao.delete(bugContent);
		// ɾ��bug
		bugContentDao.delete(bugContent);
	}
	
	/**
	 * ���ܣ�axja��ȡbug����
	 * @param bugContent
	 * @return
	 */
	public List<BugContent> getBugList(BugContent bugContent) {
		return bugContentDao.getBugList(bugContent);
	}
	
	/**
	 * ���ܣ��õ���ҳbugContent
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedBugContent(BugContent bugContent, Integer pageNo, int pageSize) {
		return bugContentDao.getPagedBugContent(bugContent, pageNo, pageSize);
	}
	
	/**
	 * ���ܣ��õ�δ��˵�bug/δ����bug
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedAssingAndReviewBug(BugContent bugContent, Integer pageNo, int pageSize) {
		return bugContentDao.getPagedAssingAndReviewBug(bugContent, pageNo, pageSize);
	}
	
	/**
	 * ���ܣ������ѷ���bug
	 * @param bugContent
	 */
	public void saveAssing(BugContent bugContent, String isEmail) {
		BugContent bug = bugContentDao.get(bugContent.getId());
		
		bug.setUserHandler(bugContent.getUserHandler());
		// �ǿ�ֵ�Ǿ��Ǵ���
		if (StringUtils.isEmpty(bugContent.getStatus())) {
			bug.setStatus(BugContent.STATUS_OPEN);
			bug.setIsOpen(BugContent.YES);
		}
		else {
			// �ر�ԭ��
			bug.setStatus(bugContent.getStatus());
			bug.setIsClosed(BugContent.YES);
		}
		// ������ǰbug.
		bugContentDao.update(bug);
		
		if (!StringUtils.isEmpty(isEmail) && isEmail.equals(BugContent.YES)) {
			// ����email
			sendEmail(bugContentDao.get(bugContent.getId()), isEmail);
		}
		
		// ����bug���ȡ�
		saveBugContentProgress(bugContent, bug);
		
	}
	
	/**
	 * ������½���
	 * @param bugContent
	 * @param bug
	 */
	private void saveBugContentProgress(BugContent bugContent, BugContent bug) {
		BugContentProgress progress = new BugContentProgress();
		progress.setCreateDate(bugContent.getCreateDate());
		
		// ������
		progress.setFromUser(bug.getUserAssigner().getBiUserInfo());
		// �����
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
	 * ���ܣ������ʼ�
	 * @param bugContent
	 * @param isEmail
	 */
	private void sendEmail(BugContent bug, String isEmail) {
		
		List<ProjectUser> userList = projectUserDao.findByProject(bug.getProject().getId());
		
		List<String> listAddress = new ArrayList<String>();
		
		for (ProjectUser projectUser : userList) {
			listAddress.add(projectUser.getBiUserInfo().getEmail());
		}
		// �߹�� �� [Test] �������µ�BUG #389792 ����� �߹��
		
		ProjectUser user = projectUserDao.get(bug.getUserHandler().getId());
		
		String title = bug.getCreateUser().getName() + "��[" + bug.getProject().getName() + "]��Ŀ�������µ�BUG�����"
				+ user.getBiUserInfo().getName();
		StringBuffer content = new StringBuffer();
		content.append(bug.getCreateUser().getName() + " ��[" + bug.getProject().getName() + "]��Ŀ������һ���µ�BUG");
		content.append("<br>");
		content.append("���⣺" + bug.getName());
		content.append("<br>");
		content.append("�汾��" + bug.getProjectVersion().getName());
		content.append("<br>");
		content.append("ģ�飺" + bug.getProjectModule().getName());
		content.append("<br>");
		content.append("<br>");
		content.append("<br>");
		content.append("<br>");
		content.append("<br>");
		content.append("ָ�������ˣ�" + user.getName());
		
		// Mail.foreachSendMail(listAddress, title, content.toString());
	}
	
	/**
	 * ��ҳ�õ������ҵ�bug
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPageBelognToMe(BugContent bugContent, Integer pageNo, int pageSize) {
		return bugContentDao.getPageBelognToMe(bugContent, pageNo, pageSize);
	}
	
	/**
	 * ���ܣ�bug�رձ���
	 * @param bugContent
	 * @param bugContentVO
	 */
	public void saveClose(BugContent bugContent, BugContentVO bugContentVO) {
		BiUserInfo user = null;
		ProjectUser realHandler = null;
		BugContent bug = bugContentDao.get(bugContent.getId());
		// �õ���Ŀ�е�������Ա
		List<ProjectUser> userList = projectUserDao.findByProject(bug.getProject().getId());
		bug.setIsClosed("Y");
		bug.setStatus(BugContent.STATUS_FIXED);
		
		// �õ���bug�����Ľ���ߣ��������һ�ε�fromUser;
		List<BugContentProgress> processList = bugContentVO.getProcessList();
		if (processList.size() > 0) {
			int length = processList.size() - 1;
			user = processList.get(length).getFromUser();
		}
		
		// ѭ����Ŀ�������� ���ҳ�����һ���ġ�
		for (ProjectUser projectUser : userList) {
			if (projectUser.getName().equals(user.getName())) {
				realHandler = projectUser;
			}
		}
		bug.setUserHandler(realHandler);
		
		bugContentDao.update(bug);
		
	}
}
