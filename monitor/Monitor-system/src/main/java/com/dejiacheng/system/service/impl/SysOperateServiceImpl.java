package com.dejiacheng.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dejiacheng.common.utils.dahua.DahuaApp;
import com.dejiacheng.system.domain.SysChannelInfo;
import com.dejiacheng.system.domain.SysDeviceInfo;
import com.dejiacheng.system.domain.SysServiceInfo;
import com.dejiacheng.system.service.ISysChannelInfoService;
import com.dejiacheng.system.service.ISysOperateService;
import com.dejiacheng.system.service.ISysServiceInfoService;
import com.dh.DpsdkCore.Enc_Channel_Info_Ex_t;


/**
 * @author pcy
 * 功能操作业务实现
 * @version 创建时间:2019年9月24日-下午12:01:21
 */
@Service
public class SysOperateServiceImpl implements ISysOperateService{
	
	@Autowired 
	private DahuaApp dahuaApp;
	
	@Autowired 
	private ISysServiceInfoService serviceInfoServiceImpl;
	
	@Autowired
	private ISysChannelInfoService channelinfoServiceImpl;

	/**
	 * 初始化大华平台*/
	@Override
	public int OnCreate() {
		// TODO Auto-generated method stub
		return dahuaApp.OnCreate();
	}

	/**
	 * 登录大华平台*/
	@Override 
	public int OnLogin() {
		// TODO Auto-generated method stub+
		SysServiceInfo serviceInfo = serviceInfoServiceImpl.selectSysServiceInfoById(1);
//		String m_strIp = "192.168.2.254";
//		int m_nPort = 9000;
//		String m_strUser = "system";
//		String m_strPassword = "admin123";
		return dahuaApp.OnLogin(serviceInfo.getServiceIp(), serviceInfo.getServicePort(), serviceInfo.getServiceName(), serviceInfo.getServicePassword());
	}

	/**
	 * 加载组织树*/
	@Override
	public int LoadAllGroup() {
		// TODO Auto-generated method stub
		return dahuaApp.LoadAllGroup();
	}
	
	
	
	/**
	 * 获取HLS拉流方式视频
	 * @param m_strRealCamareID 视频通道
	 * @return 视频流地址
	 */
	@Override
	public String GetExternUrlHLS(String m_strRealCamareID) {
		// TODO Auto-generated method stub
		return dahuaApp.GetExterUrlHLS(m_strRealCamareID);
	}

	/**
	 * @param m_strRealCamareID 视频通道
	 * @param nDirect 云台控制方向
	 * @param nStep 步长
	 * @return 视频流地址
	 */
	@Override
	public int OnYuntaiStart(String m_strRealCamareID, int nDirect, int nStep) {
		// TODO Auto-generated method stub
		return dahuaApp.OnYuntaiStart(m_strRealCamareID, nDirect, nStep);
	}

	/**
	 * @param m_strRealCamareID 视频通道
	 * @param nDirect 云台控制方向
	 * @param nStep 步长
	 * @return 视频流地址
	 */
	@Override
	public int OnYuntaiStop(String m_strRealCamareID, int nDirect, int nStep) {
		// TODO Auto-generated method stub
		return dahuaApp.OnYuntaiStop(m_strRealCamareID, nDirect, nStep);
	}

	
	@Override
	public void updateInfo() {
		// TODO Auto-generated method stub
		Enc_Channel_Info_Ex_t[]  channels = dahuaApp.updateInfo();
//		SysChannelInfo channelinfo = new SysChannelInfo();
//		for (Enc_Channel_Info_Ex_t enc_Channel_Info_Ex_t : channels) {
//			channelinfo.setChannelcode(channels[0].nCameraType);
//			channelinfoServiceImpl.updateSysChannelInfo(channelinfo);
//		}
	}

}
