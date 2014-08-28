package com.gaoguangjin.count.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.bug.dao.BugContentDao;
import com.gaoguangjin.bug.entity.BugContent;
import com.gaoguangjin.count.vo.BugCountReportVO;

@Service
public class BugContentReportService {
	@Autowired
	private BugContentDao bugContentDao;
	
	/**
	 * 功能：根据日期生产报表
	 * 
	 * @param bugContent
	 * @return
	 */
	public BugCountReportVO getReport(BugContent bugContent) {
		// 参数
		BugCountReportVO report = new BugCountReportVO();
		if (null != bugContent && !StringUtils.isEmpty(bugContent.getFlag())) {
			// 借用flag传参数
			// 得到level
			String levelStr = getLevelReport(bugContent);
			String statusStr = getStatusReport(bugContent);
			// String allBugStr = getAllBugReport(bugContent);
			// String moduleStr = getModuleReport(bugContent);
			// String userStr = getUserReport(bugContent);
			// 得到status
			report.setLevelStr(levelStr);
			report.setStatusStr(statusStr);
			// report.setAllBugStr(allBugStr);
			// report.setModuleStr(moduleStr);
			// report.setUserStr(userStr);
			
		}
		
		return report;
	}
	
	private String getUserReport(BugContent bugContent) {
		StringBuffer str = new StringBuffer();
		String sql = "select count(*),level from bug_content WHERE flag='Y' ";
		sql = sql + getDate(bugContent);
		sql = sql + " GROUP BY level ";
		// 低;2;false;ABDD79\n中;4;false;92C1D6\n高;5;false;FCE66C\n紧急;0;false;E74862\n严重;0;false;FEC514
		List<Object[]> obj = (List<Object[]>) bugContentDao.findBySQL(sql, null);
		for (Object[] st : obj) {
			if (st[1].equals("01")) {
				str.append("低" + ";" + st[0] + ";" + "false;ABDD79\\n");
			}
			else if (st[1].equals("02")) {
				str.append("中" + ";" + st[0] + ";" + "false;92C1D6\\n");
			}
			else if (st[1].equals("03")) {
				str.append("高" + ";" + st[0] + ";" + "false;FCE66C\\n");
			}
			else if (st[1].equals("04")) {
				str.append("紧急" + ";" + st[0] + ";" + "false;E74862\\n");
			}
			else if (st[1].equals("05")) {
				str.append("严重" + ";" + st[0] + ";" + "false;FEC514\\n");
			}
		}
		return str.toString();
	}
	
	private String getModuleReport(BugContent bugContent) {
		StringBuffer str = new StringBuffer();
		String sql = "select count(*),level from bug_content WHERE flag='Y' ";
		sql = sql + getDate(bugContent);
		sql = sql + " GROUP BY level ";
		// 低;2;false;ABDD79\n中;4;false;92C1D6\n高;5;false;FCE66C\n紧急;0;false;E74862\n严重;0;false;FEC514
		List<Object[]> obj = (List<Object[]>) bugContentDao.findBySQL(sql, null);
		for (Object[] st : obj) {
			if (st[1].equals("01")) {
				str.append("低" + ";" + st[0] + ";" + "false;ABDD79\\n");
			}
			else if (st[1].equals("02")) {
				str.append("中" + ";" + st[0] + ";" + "false;92C1D6\\n");
			}
			else if (st[1].equals("03")) {
				str.append("高" + ";" + st[0] + ";" + "false;FCE66C\\n");
			}
			else if (st[1].equals("04")) {
				str.append("紧急" + ";" + st[0] + ";" + "false;E74862\\n");
			}
			else if (st[1].equals("05")) {
				str.append("严重" + ";" + st[0] + ";" + "false;FEC514\\n");
			}
		}
		return str.toString();
	}
	
	/**
	 * 功能：得到count 等级
	 * 
	 * @return
	 */
	public String getLevelReport(BugContent bugContent) {
		StringBuffer str = new StringBuffer();
		String sql = "select count(*),level from bug_content WHERE flag='Y' ";
		sql = sql + getDate(bugContent);
		sql = sql + " GROUP BY level ";
		// 低;2;false;ABDD79\n中;4;false;92C1D6\n高;5;false;FCE66C\n紧急;0;false;E74862\n严重;0;false;FEC514
		List<Object[]> obj = (List<Object[]>) bugContentDao.findBySQL(sql, null);
		for (Object[] st : obj) {
			if (st[1].equals("01")) {
				str.append("低" + ";" + st[0] + ";" + "false;ABDD79\\n");
			}
			else if (st[1].equals("02")) {
				str.append("中" + ";" + st[0] + ";" + "false;92C1D6\\n");
			}
			else if (st[1].equals("03")) {
				str.append("高" + ";" + st[0] + ";" + "false;FCE66C\\n");
			}
			else if (st[1].equals("04")) {
				str.append("紧急" + ";" + st[0] + ";" + "false;E74862\\n");
			}
			else if (st[1].equals("05")) {
				str.append("严重" + ";" + st[0] + ";" + "false;FEC514\\n");
			}
		}
		return str.toString();
	}
	
	private String getDate(BugContent bugContent) {
		StringBuffer hql = new StringBuffer();
		if (null != bugContent.getBeginDate()) {
			hql.append(" and create_date >='" + bugContent.getBeginDate().toLocaleString() + "' ");
		}
		if (null != bugContent.getEndDate()) {
			hql.append(" and create_date <='" + bugContent.getEndDate().toLocaleString() + "' ");
		}
		return hql.toString();
	}
	
	/**
	 * 功能：得到count 等级
	 * 
	 * @return
	 */
	public String getStatusReport(BugContent bugContent) {
		StringBuffer str = new StringBuffer();
		// 打开了，统计未修复
		String sql = "select count(*),status from bug_content WHERE flag='Y' ";
		sql = sql + getDate(bugContent);
		sql = sql + " GROUP BY status ";
		
		// 未修复;27;false;FEC514\n待审核;0;false;AD81D9\n已解决;4;false;ADD981\n
		List<Object[]> obj = (List<Object[]>) bugContentDao.findBySQL(sql, null);
		for (Object[] st : obj) {
			if (st[1].equals("open") || st[1].equals("resolved")) {
				str.append("未修复" + ";" + st[0] + ";" + "false;FEC514\\n");
			}
			else if (st[1].equals("fixed")) {
				str.append("已解决" + ";" + st[0] + ";" + "false;ADD981\\n");
			}
			else if (st[1].equals("solved")) {
				str.append("待审核" + ";" + st[0] + ";" + "false;AD81D9\\n");
			}
			else if (st[1].equals("new")) {
				str.append("新录入" + ";" + st[0] + ";" + "false;FEC514\\n");
			}
		}
		return str.toString();
	}
	
	// <chart><graphs><graph gid=\"0\">"
	// "<point x=\"2014-01-16\" y=\"29\"></point>" +
	// "<point x=\"2014-01-17\" y=\"29\"></point><point x=\"2014-01-18\" y=\"32\"></point><point x=\"2014-01-19\"
	// y=\"33\"></point><point x=\"2014-01-20\" y=\"57\"></point></graph></graphs></chart>
	/**
	 * gongn得到所有bug
	 */
	private String getAllBugReport(BugContent bugContent) {
		StringBuffer str = new StringBuffer();
		int memer = 0;
		// 打开了，统计未修复
		String sql = "	SELECT COUNT(*) as number,month FROM bug_content WHERE flag='Y' ";
		sql = sql + getDate(bugContent);
		sql = sql + " GROUP BY month  ORDER BY month";
		
		List<Object[]> obj = (List<Object[]>) bugContentDao.findBySQL(sql, null);
		
		str.append("<chart><graphs><graph gid=\\\"0\\\">");
		for (Object[] st : obj) {
			// str.append("<point x=\\" + st[1] + "\\ y=\\" + st[0] + "\\></point>");
			str.append("<point x=\\\"2014-01-20\\\" y=\\\"57\\\"></point>");
		}
		str.append(" </graph></graphs></chart>");
		
		return str.toString();
	}
}
