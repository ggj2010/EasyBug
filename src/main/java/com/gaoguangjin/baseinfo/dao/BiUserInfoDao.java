package com.gaoguangjin.baseinfo.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gaoguangjin.base.BaseDao;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.bug.entity.BugContent;

@Repository
public class BiUserInfoDao extends BaseDao<BiUserInfo> {
	
	public Page getPagedBiUserInfo(BiUserInfo biUserInfo, Integer pageNo, int pageSize) {
		List<Object> params = new LinkedList<Object>();
		// 开始下标
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		// 结束下表
		final int endIndex = pageSize;
		String hql = generateHQL(params, biUserInfo);
		// 合计多少个
		String hqlCount = "select count(*)" + hql;
		// 最大页数
		int totalSize = super.getHQLCount(hqlCount, params);
		// 得到集合
		List<?> dbList = super.getHQLPageList(hql, params, startIndex, endIndex);
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	private String generateHQL(List<Object> params, BiUserInfo biUserInfo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from BiUserInfo where flag='Y' ");
		if (null != biUserInfo) {
			if (!StringUtils.isEmpty(biUserInfo.getName())) {
				hql.append(" and ( ");
				hql.append(" name like ? ");
				params.add("%" + biUserInfo.getName() + "%");
				
				hql.append(" or email like ? ");
				params.add("%" + biUserInfo.getName() + "%");
				
				hql.append(" or phone like ? )");
				params.add("%" + biUserInfo.getName() + "%");
				
				hql.append(" or encode like ? )");
				params.add("%" + biUserInfo.getName() + "%");
			}
		}
		
		return hql.toString();
	}
	
	/**
	 * 功能：逻辑删除用户
	 * @param biUserInfo
	 */
	public void deleteUser(BiUserInfo biUserInfo) {
		String hql = "update BiUserInfo set flag='N' where id= '" + biUserInfo.getId() + "'";
		super.bulkUpdate(hql);
	}
	
	/**
	 * 功能：ajax获取用户
	 * @param biUserInfo
	 */
	public List<BiUserInfo> getUserList(BiUserInfo biUserInfo) {
		List<Object> params = new LinkedList<Object>();
		String hql = generateHQL(params, biUserInfo);
		
		return (List<BiUserInfo>) super.getHQLPageList(hql, params, 0, 5);
		
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
		hql.append("from BugContent where flag='Y'");
		if (bugContent != null) {
			// 创建人
			if (null != bugContent.getCreateUser()) {
				if (!StringUtils.isEmpty(bugContent.getCreateUser().getId())) {
					hql.append(" and createUser.id = ?");
					params.add(bugContent.getCreateUser().getId());
				}
			}
			
			// 分配人
			if (null != bugContent.getUserAssigner()) {
				if (!StringUtils.isEmpty(bugContent.getUserAssigner().getId())) {
					hql.append(" and userAssigner.id = ?");
					params.add(bugContent.getUserAssigner().getId());
				}
			}
			
			// 解决
			if (null != bugContent.getUserHandler()) {
				if (!StringUtils.isEmpty(bugContent.getUserHandler().getId())) {
					hql.append(" and userHandler.id = ?");
					params.add(bugContent.getUserHandler().getId());
				}
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
				hql.append(" and isClosed = ?");
				params.add(bugContent.getIsClosed());
			}
			
			if (!StringUtils.isEmpty(bugContent.getIsOpen())) {
				hql.append(" and isOpen = ?");
				params.add(bugContent.getIsOpen());
			}
			
			if (!StringUtils.isEmpty(bugContent.getIsReopen())) {
				hql.append(" and isReopen = ?");
				params.add(bugContent.getIsReopen());
			}
			
			if (!StringUtils.isEmpty(bugContent.getIsSolved())) {
				hql.append(" and isSolved = ?");
				params.add(bugContent.getIsSolved());
			}
			
		}
		
		hql.append(" order by createDate desc");
		return hql.toString();
	}
}
