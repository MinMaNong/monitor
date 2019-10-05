package com.dejiacheng.common.utils.dahua;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.dejiacheng.common.exception.dahua.DahuaException;
import com.dejiacheng.common.utils.MapDataUtil;
import com.dh.DpsdkCore.Device_Info_Ex_t;
import com.dh.DpsdkCore.Enc_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_Channel_Info_t;
import com.dh.DpsdkCore.Get_Dep_Count_Info_t;
import com.dh.DpsdkCore.Get_Dep_Info_t;
import com.dh.DpsdkCore.Get_ExternalRealStreamUrl_Info_t;
import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Login_Info_t;
import com.dh.DpsdkCore.Ptz_Direct_Info_t;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.dh.DpsdkCore.dpsdk_dev_type_e;
import com.dh.DpsdkCore.dpsdk_protocol_version_e;
import com.dh.DpsdkCore.dpsdk_retval_e;
import com.dh.DpsdkCore.dpsdk_sdk_type_e;
import com.dh.DpsdkCore.fDPSDKDevStatusCallback;
import com.dh.DpsdkCore.fDPSDKGeneralJsonTransportCallback;
import com.dh.DpsdkCore.fDPSDKNVRChnlStatusCallback;

/**
 * @author pcy
 * 大华平台对接
 * @version 创建时间:2019年9月23日-下午9:03:43
 */
@Component
public class DahuaApp {
	
	public static int m_nDLLHandle = -1;
	Return_Value_Info_t nGroupLen = new Return_Value_Info_t();
	public static int nDownloadSeq = 0;
	public static boolean bDownloadFinish = false;
	
	public static String StrCarNum;
	
	/**
	 * 设备状态回调*/
	public fDPSDKDevStatusCallback fDeviceStatus = new fDPSDKDevStatusCallback() {
		@Override
		public void invoke(int nPDLLHandle, byte[] szDeviceId, int nStatus) {
			String status = "离线";
			if(nStatus == 1)
			{
				status = "在线";
				Device_Info_Ex_t deviceInfo = new Device_Info_Ex_t();
				int nRet = IDpsdkCore.DPSDK_GetDeviceInfoExById(m_nDLLHandle, szDeviceId, deviceInfo);
				if(deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_EVS || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_SMART_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_MATRIX_NVR6000)
				{
					nRet = IDpsdkCore.DPSDK_QueryNVRChnlStatus(m_nDLLHandle, szDeviceId, 10*1000);
					
					if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
					{
						//System.out.printf("查询NVR通道状态成功，deviceID = %s", new String(szDeviceId));
					}else
					{
//						System.out.printf("查询NVR通道状态失败，deviceID = %s, nRet = %d", new String(szDeviceId), nRet);
					}
					//System.out.println();
				}
			}
			//System.out.printf("Device Status Report!, szDeviceId = %s, nStatus = %s", new String(szDeviceId),status);
			//System.out.println();
		}
	};
	
	/**
	 * NVR通道状态回调函数*/
	public fDPSDKNVRChnlStatusCallback fNVRChnlStatus = new fDPSDKNVRChnlStatusCallback() {
		@Override
		public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus) {
			String status = "离线";
			if(nStatus == 1)
			{
				status = "在线";
			}
			//System.out.printf("NVR Channel Status Report!, szCameraId = %s, nStatus = %s", new String(szCameraId),status);
			//System.out.println();
		}
	};
	
	/**
	 * Json传输协议回调*/
	public fDPSDKGeneralJsonTransportCallback fGeneralJson = new fDPSDKGeneralJsonTransportCallback() {
		@Override
		public void invoke(int nPDLLHandle, byte[] szJson) {
			System.out.printf("General Json Return, ReturnJson = %s", new String(szJson));
			System.out.println();
		}
	};
	
	/**
	 * 创建DPSDK*/
	public int OnCreate() {
		int nRet = -1;
		Return_Value_Info_t res = new Return_Value_Info_t();
		nRet =IDpsdkCore.DPSDK_Create(dpsdk_sdk_type_e.DPSDK_CORE_SDK_SERVER,res);
		
		m_nDLLHandle = res.nReturnValue;
		String dpsdklog = "D:\\dahua\\dpsdkjavalog";
		nRet = IDpsdkCore.DPSDK_SetLog(m_nDLLHandle, dpsdklog.getBytes());
		String dumpfile = "D:\\dahua\\dpsdkjavadump";
		nRet = IDpsdkCore.DPSDK_StartMonitor(m_nDLLHandle, dumpfile.getBytes());
		if(m_nDLLHandle > 0)
		{
			//设置设备状态上报监听函数
			nRet = IDpsdkCore.DPSDK_SetDPSDKDeviceStatusCallback(m_nDLLHandle, fDeviceStatus);
			//设置NVR通道状态上报监听函数
			nRet =IDpsdkCore.DPSDK_SetDPSDKNVRChnlStatusCallback(m_nDLLHandle, fNVRChnlStatus);
			//设置通用JSON回调
			nRet = IDpsdkCore.DPSDK_SetGeneralJsonTransportCallback(m_nDLLHandle, fGeneralJson);
		}
//		System.out.print("创建DPSDK, 返回 m_nDLLHandle = ");
//		System.out.println(m_nDLLHandle);
		return nRet;
	}
	
	/**
	 * 登录*/
	public int OnLogin(String m_strIp,int m_nPort,String m_strUser,String m_strPassword)
	{
		Login_Info_t loginInfo = new Login_Info_t();
		loginInfo.szIp = m_strIp.getBytes();
		loginInfo.nPort = m_nPort;
		loginInfo.szUsername = m_strUser.getBytes();
		loginInfo.szPassword = m_strPassword.getBytes();
		loginInfo.nProtocol = dpsdk_protocol_version_e.DPSDK_PROTOCOL_VERSION_II;
		loginInfo.iType = 1;

		int nRet = IDpsdkCore.DPSDK_Login(m_nDLLHandle,loginInfo,10000);
//		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
//		{
//			System.out.printf("登录成功，nRet = %d", nRet);
//		}else
//		{
//			System.out.printf("登录失败，nRet = %d", nRet);
//		}
//		System.out.println();
		return nRet;
	}
	
	/**
	 * 加载所有组织树*/
	public int LoadAllGroup()
	{
		Return_Value_Info_t nGroupLen = new Return_Value_Info_t();
		
		int nRet = IDpsdkCore.DPSDK_LoadDGroupInfo(m_nDLLHandle, nGroupLen, 180000 );
		
//		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
//		{
//			System.out.printf("加载所有组织树成功，nRet = %d， nDepCount = %d", nRet, nGroupLen.nReturnValue);
//		}else
//		{
//			System.out.printf("加载所有组织树失败，nRet = %d", nRet);
//		}
		//加载组织结构之后，要延时5秒钟左右，等待与各服务模块取得联系
		System.out.println();
		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return nRet;
	}
	
	/**
	 * 获取通道信息
	 */
	public int GetChannelInfo() {
		Get_Dep_Count_Info_t DepCountInfo = new Get_Dep_Count_Info_t();
		Get_Channel_Info_t channelInfo = new Get_Channel_Info_t(DepCountInfo.nChannelCount);
		int nRet = IDpsdkCore.DPSDK_GetChannelInfo(m_nDLLHandle, channelInfo);
//		Get_Channel_Info_Ex_t channelInfo = new Get_Channel_Info_Ex_t(DepCountInfo.nChannelCount);
//		Enc_Channel_Info_Ex_t[] channelInfo1 = channelInfo.pEncChannelnfo;
		return nRet;
	}
//	http://192.168.2.254:7086/live/cameraid/1000078$0/substream/1.m3u8
	
	/**
	 * 获取视频URL
	 */
	public String GetExterUrlHLS(String m_strRealCamareID) {
		int targetID = Integer.parseInt(m_strRealCamareID)-1;
		String Url = "http://192.168.2.254:7086/live/cameraid/100078$"+targetID+"substream/1.m3u8";
		return Url;
	}
	
	/**
	 * 获取视频URL-RTSP格式*/
	public HashMap<String, String> GetExternUrlRTSP(String m_strRealCamareID)
	{
		HashMap<String, String> message = new HashMap<>();
		Get_ExternalRealStreamUrl_Info_t pExternalRealStreamUrlInfo = new Get_ExternalRealStreamUrl_Info_t();
		pExternalRealStreamUrlInfo.szCameraId = m_strRealCamareID.getBytes();
		pExternalRealStreamUrlInfo.nMediaType = 1;
		pExternalRealStreamUrlInfo.nStreamType = 1;
		pExternalRealStreamUrlInfo.nTrackId = 8011;
		pExternalRealStreamUrlInfo.nTransType = 1;
		pExternalRealStreamUrlInfo.bUsedVCS = 0;
		pExternalRealStreamUrlInfo.nVcsbps = 0;
		pExternalRealStreamUrlInfo.nVcsfps = 0;
		pExternalRealStreamUrlInfo.nVcsResolution = 0;
		pExternalRealStreamUrlInfo.nVcsVideocodec = 0;
		int nRet = IDpsdkCore.DPSDK_GetExternalRealStreamUrl(m_nDLLHandle, pExternalRealStreamUrlInfo, 10000);
		message.put("nRet", nRet+"");
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
//			System.out.println(new String(pExternalRealStreamUrlInfo.szUrl).trim());
			message.put("Url", new String(pExternalRealStreamUrlInfo.szUrl).trim());
		}else
		{
//			System.out.printf("获取URL失败，nRet = %d", nRet);
		}
//		System.out.println();
		return message;
	}
	
	/**
	 * 云台控制开始*/
	public int OnYuntaiStart(String m_strRealCamareID,int nDirect,int nStep) 
	{
		byte[] szCameraId = m_strRealCamareID.getBytes();
		int nRet = IDpsdkCore.DPSDK_PtzDirection(m_nDLLHandle, new Ptz_Direct_Info_t(szCameraId,nDirect,nStep,false),10000);
//		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
//			System.out.printf("云台操作成功,nRet = %d",nRet);
//		}else {
//			System.out.printf("云台操作失败,nRet = %d",nRet);
//		}
		return nRet;
	}
	
	/**
	 * 云台控制结束*/
	public int OnYuntaiStop(String m_strRealCamareID,int nDirect,int nStep) {
		byte[] szCameraId = m_strRealCamareID.getBytes();
		int nRet = IDpsdkCore.DPSDK_PtzDirection(m_nDLLHandle, new Ptz_Direct_Info_t(szCameraId,nDirect,nStep,true),10000);
//		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
//			System.out.printf("云台操作成功,nRet = %d",nRet);
//		}else {
//			System.out.printf("云台操作失败,nRet = %d",nRet);
//		}
		return nRet;
	}
	
	/**
	 * 数据库更新
	 */
	public Enc_Channel_Info_Ex_t[] updateInfo() {
		String strCoding="001";
		int nRet = -1;
		Get_Dep_Count_Info_t depCountInfo = new Get_Dep_Count_Info_t();
		depCountInfo.szCoding=strCoding.getBytes();
		nRet = IDpsdkCore.DPSDK_GetDGroupCount(m_nDLLHandle,depCountInfo);
		System.out.println("通道号个数："+depCountInfo.nChannelCount);
		
		//获取组织下子组织和子设备的信息
		Get_Dep_Info_t depInfo = new Get_Dep_Info_t(depCountInfo.nDepCount,depCountInfo.nDeviceCount);
		depInfo.szCoding = strCoding.getBytes();
		nRet = IDpsdkCore.DPSDK_GetDGroupInfo(m_nDLLHandle,depInfo);
		
		Get_Channel_Info_Ex_t channelInfo = new Get_Channel_Info_Ex_t(depCountInfo.nChannelCount);
		channelInfo.szDeviceId = depInfo.pDeviceInfo[0].szId;
		IDpsdkCore.DPSDK_GetChannelInfoEx(m_nDLLHandle,channelInfo);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
			return channelInfo.pEncChannelnfo;
		}else {
			throw new DahuaException(nRet+"","获取通道信息失败！！！",null);
		}
//		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
//			int i = 0;
//			while(channelInfo.pEncChannelnfo[i].szId[0] != 0) {
//				String channelNameBuf = "";
//				String channelIDBuf = "";
//				try {
//					channelNameBuf = new String(channelInfo.pEncChannelnfo[i].szName, "UTF-8");
//					channelIDBuf = new String(channelInfo.pEncChannelnfo[i].szId, "UTF-8");
//				} catch (IOException e) {  
//					e.printStackTrace();  
//				} 
//				System.out.println("通道名称信息："+channelNameBuf+"------------");
//				System.out.println("通道ID:"+channelIDBuf);
//				i++;
//				Device_Info_Ex_t deviceInfo = new Device_Info_Ex_t();
//				nRet = IDpsdkCore.DPSDK_GetDeviceInfoExById(m_nDLLHandle,channelInfo.pEncChannelnfo[i].szId,deviceInfo);
//				String devicebuf = "";
//				try {
//					devicebuf = new String(deviceInfo.szName, "UTF-8");
//				} catch (IOException e) {  
//					e.printStackTrace();  
//				} 
//				System.out.println("设备名称："+channelNameBuf+"------------");
//			}
//		}
		
	}
	
}
