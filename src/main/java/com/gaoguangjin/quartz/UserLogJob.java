package com.gaoguangjin.quartz;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.constant.UserLog;
import com.gaoguangjin.baseinfo.dao.BiUserLogDao;
import com.gaoguangjin.baseinfo.entity.BiUserLog;
import com.gaoguangjin.util.DateUtil;

public class UserLogJob {
	@Autowired
	private BiUserLogDao biUserLogDAO;
	
	@Autowired
	public void work() throws ParseException {
		// ִ��ɾ��
		delete();
		// ִ�и���
		update();
	}
	
	/**
	 * ִ�и���is_format
	 */
	private void update() {
		List<BiUserLog> list = biUserLogDAO.findNotFormat();
		for (BiUserLog userLog : list) {
			String url = userLog.getUrl();
			if (null != url) {
				// urlΪ"/main/stuEnroll/findNotover.do"����"/tms/main/stuBaseInfo/getAjaxStu.do"
				String[] str = url.split("/main/");
				// xml���汣��ĸ�ʽ��������"mainfPayRecordall"
				String xmlValue = "main" + str[1].replace("/", "");
				xmlValue = xmlValue.substring(0, xmlValue.length() - 3);
				// ��ȡxml����ĺ���ֵ
				String urlName = (UserLog.getUserLog(xmlValue) == null) ? "δ֪��ַ" : UserLog.getUserLog(xmlValue);
				
				userLog.setUrl(urlName);
				userLog.setIsFormat("Y");
				biUserLogDAO.update(userLog);
			}
		}
		if (list.size() > 0) {
			update();
		}
	}
	
	/**
	 * ɾ��ǰһ���µ�
	 */
	
	private void delete() {
		try {
			// �õ�ǰ�����µ�����
			Date begin = new Date();
			Date end = DateUtil.getDayAfter(begin, 'M', -1);
			biUserLogDAO.executeSQL("delete from bi_user_log where create_date<?", new Object[] { end });
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
