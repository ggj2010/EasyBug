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
		// 执行删除
		delete();
		// 执行更新
		update();
	}
	
	/**
	 * 执行更新is_format
	 */
	private void update() {
		List<BiUserLog> list = biUserLogDAO.findNotFormat();
		for (BiUserLog userLog : list) {
			String url = userLog.getUrl();
			if (null != url) {
				// url为"/main/stuEnroll/findNotover.do"或者"/tms/main/stuBaseInfo/getAjaxStu.do"
				String[] str = url.split("/main/");
				// xml里面保存的格式是这样的"mainfPayRecordall"
				String xmlValue = "main" + str[1].replace("/", "");
				xmlValue = xmlValue.substring(0, xmlValue.length() - 3);
				// 获取xml里面的汉字值
				String urlName = (UserLog.getUserLog(xmlValue) == null) ? "未知地址" : UserLog.getUserLog(xmlValue);
				
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
	 * 删除前一个月的
	 */
	
	private void delete() {
		try {
			// 得到前两个月的日期
			Date begin = new Date();
			Date end = DateUtil.getDayAfter(begin, 'M', -1);
			biUserLogDAO.executeSQL("delete from bi_user_log where create_date<?", new Object[] { end });
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
