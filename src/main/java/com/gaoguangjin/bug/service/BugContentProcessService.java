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
	 * bug进度查询
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
	 * 功能：保存进度
	 * @param bugContent
	 */
	public void saveProcess(BugContent bugContent) {
		
		BugContent bug = bugContentDao.get(bugContent.getId());
		
		// 不等于空再设置
		if (null != bugContent.getUserHandler()) {
			bug.setUserHandler(bugContent.getUserHandler());
			// 已解决的转发
			if (bug.getIsSolved().equals("Y")) {
				bug.setIsSolved("N");
				// 重新再解决
				bug.setStatus(BugContent.STATUS_RESOLVED);
			}
			
		}
		else {
			// 已经解决就分配给创建者
			bug.setUserHandler(bug.getUserAssigner());
		}
		
		// 不是控制那就是已解决
		if (!StringUtils.isEmpty(bugContent.getStatus())) {
			bug.setStatus(bugContent.getStatus());
			bug.setIsSolved(BugContent.YES);
		}
		// 更新以前bug.
		bugContentDao.update(bug);
		
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
		progress.setFromUser(bugContent.getCreateUser());
		// 解决者
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
