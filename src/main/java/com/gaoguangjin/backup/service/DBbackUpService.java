package com.gaoguangjin.backup.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.constant.ConfigXML;
import com.gaoguangjin.backup.dao.DBbackUpDao;
import com.gaoguangjin.backup.entity.DBbackUp;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.util.DateUtil;

@Service
public class DBbackUpService {
	@Autowired
	DBbackUpDao dBbackUpDao;
	
	public Page getPagedBackUp(DBbackUp dBbackUp, Integer pageNo, int pageSize) {
		return dBbackUpDao.getPagedBackUp(dBbackUp, pageNo, pageSize);
	}
	
	/**
	 * 功能：保存备份
	 * @param request
	 * @param biUserInfo
	 */
	public void saveBackUp(DBbackUp dBbackUp, HttpServletRequest request) throws Exception {
		
		String date = DateUtil.convertDateToString(new Date(), DateUtil.DATE_FORMAT_yyyyMMdd);
		String path = "backup/";
		// 定义存放地址
		String clientPath = request.getSession().getServletContext().getRealPath(path) + File.separator + date
				+ dBbackUp.getName() + ".sql";
		// 获取数据库名字
		String DataBaseName = ConfigXML.getParam("dataBaseName");
		Runtime.getRuntime().exec(
				"cmd /c mysqldump -hlocalhost -uroot -proot --default-character-set=utf8 " + DataBaseName + ">"
						+ clientPath);
		
		dBbackUp.setCreateDate(new Date());
		dBbackUp.setUrl(clientPath);
		
		String id = dBbackUpDao.save(dBbackUp);
		dBbackUp.setId(id);
		dBbackUpDao.update(dBbackUp);
	}
	
	/**
	 * 功能：还原备份
	 * @param dBbackUp
	 */
	public void recover(DBbackUp dBbackUp) {
		DBbackUp backUp = dBbackUpDao.get(dBbackUp.getId());
		
		String path = backUp.getUrl();
		String DataBaseName = ConfigXML.getParam("dataBaseName");
		recover(path, DataBaseName);
	}
	
	public static void recover(String path, String dataBaseName) {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("mysql -hlocalhost -uroot -proot --default-character-set=utf8 "
					+ dataBaseName);
			OutputStream outputStream = process.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf8"));
			String str = null;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str + "\r\n");
			}
			str = sb.toString();
			System.out.println(str);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf8");
			writer.write(str);
			writer.flush();
			outputStream.close();
			br.close();
			writer.close();
			System.out.println("恢复成功!");
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * 功能：删除备份
	 * @param dBbackUp
	 */
	public void delete(DBbackUp dBbackUp) {
		DBbackUp backUp = dBbackUpDao.get(dBbackUp.getId());
		String path = backUp.getUrl();
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		// 执行删除
		dBbackUpDao.bulkUpdate("update DBbackUp set flag='N' where id='" + dBbackUp.getId() + "'");
		
	}
}
