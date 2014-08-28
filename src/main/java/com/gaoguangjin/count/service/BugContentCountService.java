package com.gaoguangjin.count.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.base.Page;
import com.gaoguangjin.bug.dao.BugContentDao;
import com.gaoguangjin.count.vo.BugCountVO;

@Service
public class BugContentCountService {
	@Autowired
	private BugContentDao bugContentDao;
	
	/**
	 * 功能：根据"校区"统计
	 * @param tmClassStu
	 * @return
	 */
	public List findProject(BugCountVO bugCountVO) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = genProjectSql(bugCountVO, params);
		return bugContentDao.findBySQL(sql, params);
	}
	
	/**
	 * 功能：根据"状态"统计
	 * @param bugCountVO
	 * @return
	 */
	public List findStatus(BugCountVO bugCountVO) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = genStatusSql(bugCountVO, params);
		return bugContentDao.findBySQL(sql, params);
	}
	
	/**
	 * 功能：根据"解决者"统计
	 * @param bugCountVO
	 * @return
	 */
	public List findHandler(BugCountVO bugCountVO) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = genHandlerSql(bugCountVO, params);
		return bugContentDao.findBySQL(sql, params);
	}
	
	/**
	 * 功能：根据"分配者"统计
	 * @param bugCountVO
	 * @return
	 */
	public List findAssigner(BugCountVO bugCountVO) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = genAssignerSql(bugCountVO, params);
		return bugContentDao.findBySQL(sql, params);
	}
	
	/**
	 * 功能：根据"创建者"统计
	 * @param bugCountVO
	 * @return
	 */
	public List findCreate(BugCountVO bugCountVO) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = genCreateSql(bugCountVO, params);
		return bugContentDao.findBySQL(sql, params);
	}
	
	/**
	 * 功能：根据"模块"统计
	 * @param bugCountVO
	 * @return
	 */
	public List findModule(BugCountVO bugCountVO) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = genModuleSql(bugCountVO, params);
		return bugContentDao.findBySQL(sql, params);
	}
	
	/**
	 * 功能：根据"版本"统计
	 * @param bugCountVO
	 * @return
	 */
	public List findVersion(BugCountVO bugCountVO) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = genVersionSql(bugCountVO, params);
		return bugContentDao.findBySQL(sql, params);
	}
	
	/**
	 * 功能：项目sql
	 * @param bugCountVO
	 * @param params
	 * @return
	 */
	private String genProjectSql(BugCountVO bugCountVO, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t2.id as id, ");
		sql.append("(select name from project t2 where t2.id = t1.project) as projectName, ");
		sql.append("count(t1.project) as projectNumber ");
		sql.append("from bug_content t1  ");
		sql.append("INNER JOIN project t2 ON t1.project = t2.id  ");
		sql.append("INNER JOIN project_module t3 ON t1.project_module = t3.id  ");
		sql.append("INNER JOIN project_version t4 ON t1.project_version = t4.id  ");
		sql.append("INNER JOIN project_user t5 ON t1.user_assigner = t5.id where t1.flag = 'Y' ");
		sql.append(getPublicSql(bugCountVO, params));
		sql.append(" GROUP BY t1.project ");
		return sql.toString();
	}
	
	/**
	 * 功能：状态sql
	 * @param bugCountVO
	 * @return
	 */
	private String genStatusSql(BugCountVO bugCountVO, ArrayList<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.status as statusName, ");
		sql.append("t1.status as statusName, ");
		sql.append("count(t1.status) as statusNumber ");
		sql.append("from bug_content t1  ");
		sql.append("INNER JOIN project t2 ON t1.project = t2.id  ");
		sql.append("INNER JOIN project_module t3 ON t1.project_module = t3.id  ");
		sql.append("INNER JOIN project_version t4 ON t1.project_version = t4.id  ");
		sql.append("INNER JOIN project_user t5 ON t1.user_assigner = t5.id where t1.flag = 'Y' ");
		sql.append(getPublicSql(bugCountVO, params));
		sql.append(" GROUP BY t1.status ");
		return sql.toString();
	}
	
	/**
	 * 功能：状态sql
	 * @param bugCountVO
	 * @return
	 */
	private String genHandlerSql(BugCountVO bugCountVO, ArrayList<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.user_handler as id, ");
		sql.append("(select name from project_user t2 where t2.id = t1.user_handler) as userName, ");
		sql.append("count(t1.user_handler) as userNumber ");
		sql.append("from bug_content t1  ");
		sql.append("INNER JOIN project t2 ON t1.project = t2.id  ");
		sql.append("INNER JOIN project_module t3 ON t1.project_module = t3.id  ");
		sql.append("INNER JOIN project_version t4 ON t1.project_version = t4.id  ");
		sql.append("INNER JOIN project_user t5 ON t1.user_assigner = t5.id where t1.flag = 'Y' ");
		sql.append(getPublicSql(bugCountVO, params));
		
		sql.append(" and t1.is_open='Y'  and t1.is_closed='Y' ");
		sql.append(" GROUP BY t1.user_handler ");
		return sql.toString();
	}
	
	/**
	 * 功能：状态sql
	 * @param bugCountVO
	 * @return
	 */
	private String genAssignerSql(BugCountVO bugCountVO, ArrayList<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.user_assigner as id, ");
		sql.append("(select name from project_user t2 where t2.id = t1.user_assigner) as userName, ");
		sql.append("count(t1.user_assigner) as userNumber ");
		sql.append("from bug_content t1  ");
		sql.append("INNER JOIN project t2 ON t1.project = t2.id  ");
		sql.append("INNER JOIN project_module t3 ON t1.project_module = t3.id  ");
		sql.append("INNER JOIN project_version t4 ON t1.project_version = t4.id  ");
		sql.append("INNER JOIN project_user t5 ON t1.user_assigner = t5.id  where t1.flag = 'Y' ");
		sql.append(getPublicSql(bugCountVO, params));
		sql.append(" GROUP BY t1.user_assigner ");
		return sql.toString();
	}
	
	/**
	 * 功能：状态sql
	 * @param bugCountVO
	 * @return
	 */
	private String genCreateSql(BugCountVO bugCountVO, ArrayList<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.create_user as id, ");
		sql.append("(select name from bi_user_info t2 where t2.id = t1.create_user) as userName, ");
		sql.append("count(t1.create_user) as userNumber ");
		sql.append("from bug_content t1  ");
		sql.append("INNER JOIN project t2 ON t1.project = t2.id  ");
		sql.append("INNER JOIN project_module t3 ON t1.project_module = t3.id  ");
		sql.append("INNER JOIN project_version t4 ON t1.project_version = t4.id  ");
		sql.append("INNER JOIN project_user t5 ON t1.user_assigner = t5.id  where t1.flag = 'Y' ");
		sql.append(getPublicSql(bugCountVO, params));
		sql.append(" GROUP BY t1.create_user ");
		return sql.toString();
	}
	
	/**
	 * 功能：状态sql
	 * @param bugCountVO
	 * @return
	 */
	private String genModuleSql(BugCountVO bugCountVO, ArrayList<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.project_module as id, ");
		sql.append("(select name from project_module t2 where t2.id = t1.project_module) as moduleName, ");
		sql.append("count(t1.project_module) as moduleNumber ");
		sql.append("from bug_content t1  ");
		sql.append("INNER JOIN project t2 ON t1.project = t2.id  ");
		sql.append("INNER JOIN project_module t3 ON t1.project_module = t3.id  ");
		sql.append("INNER JOIN project_version t4 ON t1.project_version = t4.id  ");
		sql.append("INNER JOIN project_user t5 ON t1.user_assigner = t5.id  where t1.flag = 'Y' ");
		sql.append(getPublicSql(bugCountVO, params));
		sql.append(" GROUP BY t1.project_module ");
		return sql.toString();
	}
	
	/**
	 * 功能：状态sql
	 * @param bugCountVO
	 * @return
	 */
	private String genVersionSql(BugCountVO bugCountVO, ArrayList<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.project_version as id, ");
		sql.append("(select name from project_version t2 where t2.id = t1.project_version) as userName, ");
		sql.append("count(t1.project_version) as userNumber ");
		sql.append("from bug_content t1  ");
		sql.append("INNER JOIN project t2 ON t1.project = t2.id  ");
		sql.append("INNER JOIN project_module t3 ON t1.project_module = t3.id  ");
		sql.append("INNER JOIN project_version t4 ON t1.project_version = t4.id  ");
		sql.append("INNER JOIN project_user t5 ON t1.user_assigner = t5.id  where t1.flag = 'Y' ");
		sql.append(getPublicSql(bugCountVO, params));
		sql.append(" GROUP BY t1.project_version ");
		return sql.toString();
	}
	
	/**
	 * 功能：公共sql
	 * @param bugCountVO
	 * @param params
	 * @return
	 */
	private String getPublicSql(BugCountVO bugCountVO, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		if (null != bugCountVO) {
			if (!StringUtils.isEmpty(bugCountVO.getProjectId())) {
				sql.append(" and t1.project = ? ");
				params.add(bugCountVO.getProjectId());
			}
			if (!StringUtils.isEmpty(bugCountVO.getLevel())) {
				sql.append(" and t1.level =? ");
				params.add(bugCountVO.getLevel());
			}
			if (!StringUtils.isEmpty(bugCountVO.getStatus())) {
				sql.append(" and t1.status =? ");
				params.add(bugCountVO.getStatus());
			}
			if (!StringUtils.isEmpty(bugCountVO.getVersionId())) {
				sql.append(" and t1.project_version =? ");
				params.add(bugCountVO.getVersionId());
			}
			if (!StringUtils.isEmpty(bugCountVO.getModuleId())) {
				sql.append(" and t1.project_module =? ");
				params.add(bugCountVO.getModuleId());
			}
			// 打开的才有解决者。
			if (!StringUtils.isEmpty(bugCountVO.getUserHandlerId())) {
				sql.append(" and t1.user_handler =?   ");
				params.add(bugCountVO.getUserHandlerId());
			}
		}
		return sql.toString();
	}
	
	/**
	 * 功能：分页查询
	 * @param bugCount 查询条件
	 * @param pageNo 页码
	 * @param pageSize 行数
	 * @return Page对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page getPagedShow(BugCountVO bugCountVO, int pageNo, int pageSize) throws Exception {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		
		String hql = generateHQL(params, bugCountVO);
		String hqlCount = "select count(*) " + hql;
		int totalSize = bugContentDao.getHQLCount(hqlCount, params);
		List<?> dbList = bugContentDao.getHQLPageList(hql, params, startIndex, endIndex);
		
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	/**
	 * 功能：拼hql查询条件
	 * @param hql
	 * @param params
	 * @param bugCount
	 */
	private String generateHQL(List params, BugCountVO bugCountVO) {
		StringBuffer hql = new StringBuffer();
		hql.append("from BugContent where flag='Y' ");
		if (bugCountVO != null) {
			if (null != bugCountVO) {
				if (!StringUtils.isEmpty(bugCountVO.getProjectId())) {
					hql.append(" and project.id = ? ");
					params.add(bugCountVO.getProjectId());
				}
				
				if (!StringUtils.isEmpty(bugCountVO.getLevel())) {
					hql.append(" and level =? ");
					params.add(bugCountVO.getLevel());
				}
				if (!StringUtils.isEmpty(bugCountVO.getStatus())) {
					hql.append(" and status =? ");
					params.add(bugCountVO.getStatus());
				}
				
				if (!StringUtils.isEmpty(bugCountVO.getVersionId())) {
					hql.append(" and projectVersion.id =? ");
					params.add(bugCountVO.getVersionId());
				}
				
				if (!StringUtils.isEmpty(bugCountVO.getModuleId())) {
					hql.append(" and projectModule.id =? ");
					params.add(bugCountVO.getModuleId());
				}
				
				// 打开的才有解决者。
				if (!StringUtils.isEmpty(bugCountVO.getUserHandlerId())) {
					hql.append(" and userHandler.id =?  and isOpen='Y'  and isClosed='Y' ");
					params.add(bugCountVO.getUserHandlerId());
				}
				// 打开的才有解决者。
				if (!StringUtils.isEmpty(bugCountVO.getCreateUserId())) {
					hql.append(" and createUser.id =? ");
					params.add(bugCountVO.getCreateUserId());
				}
				// 打开的才有解决者。
				if (!StringUtils.isEmpty(bugCountVO.getUserAssignerId())) {
					hql.append(" and userAssignerer.id =?  ");
					params.add(bugCountVO.getUserAssignerId());
				}
				
				hql.append("  order by createDate desc");
			}
		}
		return hql.toString();
	}
	
}
