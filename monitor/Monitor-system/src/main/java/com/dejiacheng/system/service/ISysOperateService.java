package com.dejiacheng.system.service;

import com.dejiacheng.system.domain.SysDeviceInfo;

/**
 * @author pcy
 * 功能操作业务层
 * @version 创建时间:2019年9月23日-下午7:50:00
 */
public interface ISysOperateService {
	
	/**
	 * 创建DPSDK*/
	public int OnCreate();
	
	/**
	 * 登录*/
	public int OnLogin();
	
	/**
	 * 加载所有组织树*/
	public int LoadAllGroup();
	
	/**
	 * 更新数据库信息
	 */
	public void updateInfo();
	
	/**
	 * 获取视频URL*/
	public String GetExternUrlHLS(String m_strRealCamareID);
	
	
	/**
	 * 云台控制开始*/
	public int OnYuntaiStart(String m_strRealCamareID,int nDirect,int nStep);
	
	/**
	 * 云台控制结束*/
	public int OnYuntaiStop(String m_strRealCamareID,int nDirect,int nStep);

	/**
	 * 录像计划*/
	
	
}
