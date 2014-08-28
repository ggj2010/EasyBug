package com.gaoguangjin.bug.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.bug.dao.BugContentDao;
import com.gaoguangjin.bug.dao.BugContentProgressDao;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.bug.entity.BugContentProgress;
import com.gaoguangjin.bug.entity.BugContentVO;
import com.gaoguangjin.project.dao.ProjectUserDao;

@Service
public class BugContentProcessService {
	
	@Autowired
	private BugContentDao bugContentDao;
	@Autowired
	private ProjectUserDao projectUserDao;
	@Autowired
	private BugContentProgressDao bugContentProgressDao;
	
	/**
	 * bug���Ȳ�ѯ
	 * @param bugContent
	 * @return
	 */
	public BugContentVO getBugProcess(BugContent bugContent) {
		BiUserInfo lastHandler = null;
		BugContentVO bugContentVO = new BugContentVO();
		List<BugContentProgress> processList = bugContentProgressDao.findBugProgress(bugContent);
		if (processList.size() > 0) {
			int length = processList.size() - 1;
			lastHandler = processList.get(length).getToUser();
		}
		
		bugContentVO.setLastHandLer(lastHandler);
		bugContentVO.setProcessList((processList));
		return bugContentVO;
	}
	
	/**
	 * ���ܣ��������
	 * @param bugContent
	 */
	public void saveProcess(BugContent bugContent) {
		
		BugContent bug = bugContentDao.get(bugContent.getId());
		
		// �����ڿ�������
		if (null != bugContent.getUserHandler()) {
			bug.setUserHandler(bugContent.getUserHandler());
			// �ѽ����ת��
			if (bug.getIsSolved().equals("Y")) {
				bug.setIsSolved("N");
				// �����ٽ��
				bug.setStatus(BugContent.STATUS_RESOLVED);
			}
			
		}
		else {
			// �Ѿ�����ͷ����������
			bug.setUserHandler(bug.getUserAssigner());
		}
		
		// ���ǿ����Ǿ����ѽ��
		if (!StringUtils.isEmpty(bugContent.getStatus())) {
			bug.setStatus(bugContent.getStatus());
			bug.setIsSolved(BugContent.YES);
		}
		// ������ǰbug.
		bugContentDao.update(bug);
		
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
		progress.setFromUser(bugContent.getCreateUser());
		// �����
		if (null != bugContent.getUserHandler()) {
			if (!StringUtils.isEmpty(bugContent.getUserHandler().getId())) {
				progress.setToUser(projectUserDao.get(bugContent.getUserHandler().getId()).getBiUserInfo());
			}
		}
		else {
			
			progress.setToUser(projectUserDao.get(bug.getUserHandler().getId()).getBiUserInfo());
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
}
