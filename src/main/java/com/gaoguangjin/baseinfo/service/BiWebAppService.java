package com.gaoguangjin.baseinfo.service;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.dao.BiWebAppDao;
import com.gaoguangjin.baseinfo.entity.BiWebApp;

@Service
public class BiWebAppService {
	@Autowired
	private BiWebAppDao biWebAppDao;
	
	/**
	 * ���ܣ����ݽ���id��ѯ��������Ϣ
	 * 
	 * @param id
	 * @return ���ؽ���ʵ����
	 */
	public BiWebApp getBiWebApp(String id) {
		return biWebAppDao.get(id);
	}
	
	/**
	 * ���ܣ����ҷ�ҳ��ѯ
	 * 
	 * @param biWebApp
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Page getPagedBiWebApp(BiWebApp biWebApp, int pageNo, int pageSize) throws Exception {
		List<Object> params = new LinkedList<Object>();
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		final int endIndex = pageSize;
		
		String hql = generateHQL(biWebApp, params);
		String hqlCount = "select count(*) " + hql;
		Integer totalSize = biWebAppDao.getHQLCount(hqlCount, params);
		List<?> dbList = biWebAppDao.getHQLPageList(hql, params, startIndex, endIndex);
		return new Page(startIndex, totalSize, pageSize, dbList);
	}
	
	/**
	 * ���ܣ���װhql
	 * @param biWebApp
	 * @param params
	 * @return
	 */
	private String generateHQL(BiWebApp biWebApp, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from BiWebApp where flag='Y'  ");
		
		if (null != biWebApp) {
			if (!StringUtils.isEmpty(biWebApp.getName())) {
				sql.append(" and name like '%" + biWebApp.getName() + "%'");
			}
		}
		
		return sql.toString();
	}
	
	/**
	 * ���ܣ�ɾ��������Ϣ����ɾ��
	 * 
	 * @param biWebApp
	 */
	public void delete(BiWebApp biWebApp) {
		String hql = "update BiWebApp set flag='N' where id=?";
		biWebAppDao.bulkUpdate(hql, new Object[] { biWebApp.getId() });
	}
	
	/**
	 * ���ܣ�����һ�����ң�����ͬһУ���������Ʋ������ظ�
	 * 
	 * @param biWebApp
	 */
	public void save(BiWebApp biWebApp) {
		biWebAppDao.save(biWebApp);
	}
	
	/**
	 * ���ܣ����½�����Ϣ
	 * 
	 * @param biWebApp
	 */
	public void update(BiWebApp biWebApp) {
		biWebAppDao.update(biWebApp);
	}
}
