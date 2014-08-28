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
		// ��ʼ�±�
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		// �����±�
		final int endIndex = pageSize;
		String hql = generateHQL(params, biUserInfo);
		// �ϼƶ��ٸ�
		String hqlCount = "select count(*)" + hql;
		// ���ҳ��
		int totalSize = super.getHQLCount(hqlCount, params);
		// �õ�����
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
	 * ���ܣ��߼�ɾ���û�
	 * @param biUserInfo
	 */
	public void deleteUser(BiUserInfo biUserInfo) {
		String hql = "update BiUserInfo set flag='N' where id= '" + biUserInfo.getId() + "'";
		super.bulkUpdate(hql);
	}
	
	/**
	 * ���ܣ�ajax��ȡ�û�
	 * @param biUserInfo
	 */
	public List<BiUserInfo> getUserList(BiUserInfo biUserInfo) {
		List<Object> params = new LinkedList<Object>();
		String hql = generateHQL(params, biUserInfo);
		
		return (List<BiUserInfo>) super.getHQLPageList(hql, params, 0, 5);
		
	}
	
	/**
	 * ���ܣ��õ���ҳbugContent
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
			// ������
			if (null != bugContent.getCreateUser()) {
				if (!StringUtils.isEmpty(bugContent.getCreateUser().getId())) {
					hql.append(" and createUser.id = ?");
					params.add(bugContent.getCreateUser().getId());
				}
			}
			
			// ������
			if (null != bugContent.getUserAssigner()) {
				if (!StringUtils.isEmpty(bugContent.getUserAssigner().getId())) {
					hql.append(" and userAssigner.id = ?");
					params.add(bugContent.getUserAssigner().getId());
				}
			}
			
			// ���
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
