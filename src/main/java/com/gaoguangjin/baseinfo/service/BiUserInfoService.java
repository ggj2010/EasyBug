package com.gaoguangjin.baseinfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoguangjin.base.Page;
import com.gaoguangjin.baseinfo.dao.BiEmailDao;
import com.gaoguangjin.baseinfo.dao.BiUserInfoDao;
import com.gaoguangjin.baseinfo.entity.BiEmail;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.util.MD5;
import com.gaoguangjin.util.PinyinUtil;

@Service
public class BiUserInfoService {
	
	@Autowired
	private BiUserInfoDao biUserInfoDao;
	@Autowired
	private BiEmailDao biEmailDao;
	
	/**
	 * 功能：分页查询
	 * @param biUserInfo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedBiUserInfo(BiUserInfo biUserInfo, Integer pageNo, int pageSize) {
		return biUserInfoDao.getPagedBiUserInfo(biUserInfo, pageNo, pageSize);
	}
	
	/**
	 * 功能：保存 单个用户
	 * @param biUserInfo
	 * @throws Exception
	 */
	public void saveOneUser(BiUserInfo biUserInfo) throws Exception {
		String id = biUserInfoDao.save(biUserInfo);
		MD5 md5 = new MD5();
		biUserInfo.setPassWord(md5.getMD5ofStr(biUserInfo.getPassWord()));
		// StringUtils是判断字符串为null也不会报异常的~
		if (!StringUtils.isEmpty(biUserInfo.getName())) {
			PinyinUtil hanyu = new PinyinUtil();
			String strPinyin = hanyu.getPinYin(biUserInfo.getName()) + hanyu.getFirstPinYin(biUserInfo.getName());
			biUserInfo.setEncode(strPinyin);
		}
		
		biUserInfo.setId(biUserInfo.getId());
		biUserInfo.setCreateDate(new Date());
		biUserInfo.setIsReceiveEmail("Y");
		biUserInfo.setCreateDate(new Date());
		biUserInfo.setUpdateDate(new Date());
		biUserInfo.setFlag("Y");
		
		// // 注册用户的创建者就是自己
		// if (null == biUserInfo.getCreateUser()) {
		// biUserInfo.setCreateUser(biUserInfo);
		// biUserInfo.setUpdateUser(biUserInfo);
		// }
		
		biUserInfoDao.update(biUserInfo);
	}
	
	/**
	 * 功能：获取邮箱类型
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<BiEmail> findEmail(String key) throws Exception {
		String hql = "from BiEmail where flag='Y'";
		if (!key.endsWith("@")) {
			String str = key.split("@")[1];
			hql += " and email like " + " '%" + str + "%'";
		}
		List<BiEmail> list = (List<BiEmail>) biEmailDao.getHQLPageList(hql, null, 0, 5);
		return list;
	}
	
	/*
	 * 功能：判断用户是否存在
	 */
	public BiUserInfo findUser(BiUserInfo biUserInfo) throws Exception {
		BiUserInfo user = null;
		// 要放object
		List<Object> param = new ArrayList<Object>();
		String hql = "from BiUserInfo where flag='Y'";
		if (!StringUtils.isEmpty(biUserInfo.getEmail())) {
			hql += " and email= ?";
			param.add(biUserInfo.getEmail());
		}
		
		List<BiUserInfo> list = biUserInfoDao.find(hql, param);
		if (list.size() > 0) {
			user = list.get(0);
		}
		// 防止用户直接输入/Bug_01/dologin.do,这时候如果email没有值 会执行hql查询
		if (StringUtils.isEmpty(biUserInfo.getEmail())) {
			user = null;
		}
		return user;
	}
	
	/**
	 *功能：找回密码修改保存
	 * @param biUserInfo
	 * @throws Exception
	 */
	public void saveResetPw(BiUserInfo biUserInfo) throws Exception {
		List<BiUserInfo> userList = biUserInfoDao.find("from BiUserInfo where flag='Y' and email ='"
				+ biUserInfo.getEmail() + "'");
		if (userList.size() > 0) {
			BiUserInfo bi = userList.get(0);
			MD5 md5 = new MD5();
			bi.setPassWord(md5.getMD5ofStr(biUserInfo.getPassWord()));
			biUserInfoDao.update(bi);
		}
	}
	
	/**
	 * 功能：根据id返回对象
	 * @param biUserInfo
	 * @return
	 */
	public BiUserInfo get(BiUserInfo biUserInfo) {
		return biUserInfoDao.get(biUserInfo.getId());
	}
	
	/**
	 * 功能：保存更新的用户信息
	 * @param biUserInfo
	 */
	public void update(BiUserInfo biUserInfo) {
		
		MD5 md5 = new MD5();
		biUserInfo.setPassWord(md5.getMD5ofStr(biUserInfo.getPassWord()));
		// StringUtils是判断字符串为null也不会报异常的~
		if (!StringUtils.isEmpty(biUserInfo.getName())) {
			PinyinUtil hanyu = new PinyinUtil();
			String strPinyin = hanyu.getPinYin(biUserInfo.getName()) + hanyu.getFirstPinYin(biUserInfo.getName());
			biUserInfo.setEncode(strPinyin);
		}
		biUserInfo.setUpdateDate(new Date());
		
		biUserInfoDao.update(biUserInfo);
	}
	
	/**
	 * 功能：删除用户
	 * @param biUserInfo
	 */
	public void delete(BiUserInfo biUserInfo) {
		biUserInfoDao.deleteUser(biUserInfo);
	}
	
	/**
	 * 功能：ajax获取用户
	 * @param biUserInfo
	 */
	public List<BiUserInfo> getUserList(BiUserInfo biUserInfo) {
		return biUserInfoDao.getUserList(biUserInfo);
	}
	
	public void updatePass(BiUserInfo biUserInfo) {
		biUserInfoDao.update(biUserInfo);
		
	}
	
}
