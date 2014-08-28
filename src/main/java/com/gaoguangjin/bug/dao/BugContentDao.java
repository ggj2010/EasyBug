package com.gaoguangjin.bug.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.bug.entity.BugContent;

@Repository
public class BugContentDao extends BaseDao<BugContent> {
	/**
	 * 功能：删除模块时，判断改模块下面是否还有bug
	 * @param bugContent.id,传的是参数的ID
	 * @return
	 */
	public boolean findByProjectParam(BugContent bugContent) {
		boolean flg = false;
		StringBuffer hql = new StringBuffer();
		hql.append(" from BugContent where flag='Y' ");
		if (null != bugContent) {
			if (null != bugContent.getUserHandler()) {
				hql.append(" and userHandler.id='" + bugContent.getId() + "' ");
			}
			if (null != bugContent.getProjectModule()) {
				hql.append(" and projectModule.id='" + bugContent.getId() + "' ");
			}
			if (null != bugContent.getProjectVersion()) {
				hql.append(" and projectVersion.id='" + bugContent.getId() + "' ");
			}
			
			List<BugContent> bugContentlist = super.find(hql.toString());
			if (null != bugContentlist) {
				if (bugContentlist.size() > 0) {
					flg = true;
				}
			}
		}
		return flg;
	}
	
	/**
	 * 功能：逻辑删除
	 * @param bugContent
	 */
	public void delete(BugContent bugContent) {
		String hql = "update BugContent set flag='N' where id=?";
		super.bulkUpdate(hql, new Object[] { bugContent.getId() });
		
	}
	
	/**
	 * 功能：ajax获取Bug名字
	 * @param biUserInfo
	 */
	public List<BugContent> getBugList(BugContent bugContent) {
		List<Object> params = new LinkedList<Object>();
		String hql = generateHQL(params, bugContent);
		return (List<BugContent>) super.getHQLPageList(hql, params, 0, 5);
	}
	
	/**
	 * 功能：得到分页bugContent
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedBugContent(BugContent bugContent, Integer pageNo, int pageSize) {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		
		String hql = generateHQL(params, bugContent);
		String hqlCount = "select count(*) " + hql;
		int totalSize = super.getHQLCount(hqlCount, params);
		List<?> dbList = super.getHQLPageList(hql, params, startIndex, endIndex);
		
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	private String generateHQL(List<Object> params, BugContent bugContent) {
		StringBuffer hql = new StringBuffer();
		hql.append("from BugContent where flag='Y' ");
		if (bugContent != null) {
			// 创建人
			if (null != bugContent.getCreateUser()) {
				if (!StringUtils.isEmpty(bugContent.getCreateUser().getId())) {
					hql.append(" and createUser.id = ? ");
					params.add(bugContent.getCreateUser().getId());
				}
			}
			
			// 项目
			if (null != bugContent.getProject()) {
				if (!StringUtils.isEmpty(bugContent.getProject().getId())) {
					hql.append(" and project.id = ? ");
					params.add(bugContent.getProject().getId());
				}
			}
			
			// 分配人
			if (null != bugContent.getUserAssigner()) {
				if (!StringUtils.isEmpty(bugContent.getUserAssigner().getId())) {
					hql.append(" and userAssigner.id = ? ");
					params.add(bugContent.getUserAssigner().getId());
				}
			}
			
			// 解决
			if (null != bugContent.getUserHandler()) {
				if (!StringUtils.isEmpty(bugContent.getUserHandler().getId())) {
					hql.append(" and userHandler.id = ? and isClosed='Y' ");
					params.add(bugContent.getUserHandler().getId());
				}
			}
			
			if (null != bugContent.getProjectModule()) {
				if (!StringUtils.isEmpty(bugContent.getProjectModule().getId())) {
					hql.append(" and projectModule.id = ? ");
					params.add(bugContent.getProjectModule().getId());
				}
			}
			
			if (null != bugContent.getProjectVersion()) {
				if (!StringUtils.isEmpty(bugContent.getProjectVersion().getId())) {
					hql.append(" and projectVersion.id = ? ");
					params.add(bugContent.getProjectVersion().getId());
				}
			}
			
			if (null != bugContent.getUserHandler()) {
				if (!StringUtils.isEmpty(bugContent.getUserHandler().getId())) {
					hql.append(" and userHandler.id = ? ");
					params.add(bugContent.getUserHandler().getId());
				}
			}
			
			if (!StringUtils.isEmpty(bugContent.getLevel())) {
				hql.append("  and level = ? ");
				params.add(bugContent.getLevel());
			}
			
			if (null != bugContent.getBeginDate()) {
				hql.append(" and createDate >= ? ");
				params.add(bugContent.getBeginDate());
			}
			if (null != bugContent.getEndDate()) {
				hql.append(" and createDate <= ? ");
				params.add(bugContent.getEndDate());
			}
			
			if (!StringUtils.isEmpty(bugContent.getIsClosed())) {
				hql.append("  and isClosed = ? ");
				params.add(bugContent.getIsClosed());
			}
			
			if (!StringUtils.isEmpty(bugContent.getIsOpen())) {
				hql.append("  and isOpen = ? ");
				params.add(bugContent.getIsOpen());
			}
			
			if (!StringUtils.isEmpty(bugContent.getIsReopen())) {
				hql.append("  and isReopen = ? ");
				params.add(bugContent.getIsReopen());
			}
			
			if (!StringUtils.isEmpty(bugContent.getIsSolved())) {
				hql.append("  and isSolved = ? ");
				params.add(bugContent.getIsSolved());
			}
			
			if (!StringUtils.isEmpty(bugContent.getStatus())) {
				hql.append("  and status = ? ");
				params.add(bugContent.getStatus());
			}
			
			if (!StringUtils.isEmpty(bugContent.getName())) {
				hql.append(" and name like ? ");
				params.add("%" + bugContent.getName() + "%");
			}
			
		}
		
		hql.append(" order by createDate desc ");
		return hql.toString();
	}
	
	/**
	 * 功能：得到未分配的bug
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedAssingAndReviewBug(BugContent bugContent, Integer pageNo, int pageSize) {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		String hql = generateAssingAndReviewHQL(params, bugContent);
		String hqlCount = "select count(*) " + hql;
		int totalSize = super.getHQLCount(hqlCount, params);
		List<?> dbList = super.getHQLPageList(hql, params, startIndex, endIndex);
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	private String generateAssingAndReviewHQL(List<Object> params, BugContent bugContent) {
		StringBuffer hql = new StringBuffer();
		hql.append("from BugContent where flag='Y'");
		if (null != bugContent) {
			if (null != bugContent.getUserAssigner()) {
				if (!StringUtils.isEmpty(bugContent.getUserAssigner().getId())) {
					hql.append(" and userAssigner.id = ? ");
					params.add(bugContent.getUserAssigner().getId());
				}
			}
			if (!StringUtils.isEmpty(bugContent.getLevel())) {
				hql.append(" and level=? ");
				params.add(bugContent.getLevel());
			}
			
			if (null != bugContent.getProject()) {
				if (!StringUtils.isEmpty(bugContent.getProject().getId())) {
					hql.append(" and project.id=? ");
					params.add(bugContent.getProject().getId());
				}
			}
			
			// 是否分配
			if (!StringUtils.isEmpty(bugContent.getIsOpen())) {
				// 已分配
				if (bugContent.getIsOpen().equals("01")) {
					hql.append(" and isOpen='Y' ");
				}
				else if (bugContent.getIsOpen().equals("02")) {
					// 未分配
					hql.append(" and isOpen='N' and isClosed='N'");
				}
			}
			
			// 是否审核,
			if (!StringUtils.isEmpty(bugContent.getIsClosed())) {
				// 已分配
				if (bugContent.getIsClosed().equals("01")) {
					hql.append(" and isClosed='Y' ");
				}
				else if (bugContent.getIsClosed().equals("02")) {
					// 未分配
					hql.append(" and isClosed='N'");
				}
			}
			
			if (!StringUtils.isEmpty(bugContent.getIsSolved())) {
				hql.append(" and isSolved='Y'");
			}
			
		}
		return hql.toString();
	}
	
	/**
	 * 功能：分页得到属于我的bug
	 * @param bugContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPageBelognToMe(BugContent bugContent, Integer pageNo, int pageSize) {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		
		StringBuffer hql = new StringBuffer();
		hql.append("from BugContent where flag='Y'");
		if (null != bugContent) {
			if (null != bugContent.getUserHandler()) {
				if (!StringUtils.isEmpty(bugContent.getUserHandler().getId())) {
					hql.append(" and userHandler.id = ? ");
					params.add(bugContent.getUserHandler().getId());
				}
			}
			if (!StringUtils.isEmpty(bugContent.getLevel())) {
				hql.append(" and level=? ");
				params.add(bugContent.getLevel());
			}
			if (null != bugContent.getProject()) {
				if (!StringUtils.isEmpty(bugContent.getProject().getId())) {
					hql.append(" and project.id=? ");
					params.add(bugContent.getProject().getId());
				}
			}
		}
		String hqls = hql.toString();
		String hqlCount = "select count(*) " + hqls;
		int totalSize = super.getHQLCount(hqlCount, params);
		List<?> dbList = super.getHQLPageList(hqls, params, startIndex, endIndex);
		
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
}
