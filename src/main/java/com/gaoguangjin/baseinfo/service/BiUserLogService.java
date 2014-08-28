package com.gaoguangjin.baseinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.dao.BiUserLogDao;
import com.gaoguangjin.baseinfo.entity.BiUserLog;

@Service
public class BiUserLogService {
	@Autowired
	BiUserLogDao biUserLogDao;
	
	/**
	 * ���ܣ���ҳ��ѯ
	 */
	public Page getPagedBiUserLog(BiUserLog biUserLog, Integer pageNo, int pageSize) {
		
		return biUserLogDao.getPagedBiUserLog(biUserLog, pageNo, pageSize);
	}
	
}
