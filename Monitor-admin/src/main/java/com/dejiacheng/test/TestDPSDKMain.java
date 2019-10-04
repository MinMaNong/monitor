/*
 * DPSDK的java版demo
 * 运行时请先修改ip,端口，用户名，密码，修改通道ID，参考《组织树XML解析手册》和《常见问题解答》文档
 * 请修改lib的native library路径
 * */

package com.dejiacheng.test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

import com.dejiacheng.common.exception.dahua.DahuaException;
import com.dh.DpsdkCore.*;
import com.dh.DpsdkCore.TvWall.Set_TvWall_Screen_Window_Source_t;
import com.dh.DpsdkCore.TvWall.TvWall_Layout_Info_t;
import com.dh.DpsdkCore.TvWall.TvWall_List_Info_t;
import com.dh.DpsdkCore.TvWall.TvWall_Screen_Close_Source_t;
import com.dh.DpsdkCore.TvWall.TvWall_Screen_Open_Window_t;
import com.dh.DpsdkCore.TvWall.TvWall_Screen_Split_t;
import com.dh.DpsdkCore.TvWall.TvWall_Task_Info_List_t;

public class TestDPSDKMain 
{
	public static int m_nDLLHandle = -1;
	public String   m_strAlarmCamareID = "1000000";    //报警设备ID
	public String   m_strRealCamareID = "1000078$1$0$20";    //实时通道ID
	public String	m_strDownloadCamID = "1000000$1$0$12";	//下载通道ID
	public String	m_strPersonCountCamID = "1000000$1$0$0";//人数统计通道ID
	
	//public String 	m_strIp 		= "60.191.94.121";   //登录平台ip
	//public String 	m_strIp 		= "172.7.3.250";   //登录平台ip
	//public int    	m_nPort 		= 9000;            //端口
	//public String 	m_strUser 		= "DPSDK";        //用户名
	//public String 	m_strPassword 	= "qwer1234";    //密码
	
	public String 	m_strIp 		= "192.168.2.254";   //登录平台ip
	public int    	m_nPort 		= 9000;            //端口
	public String 	m_strUser 		= "system";        //用户名
	public String 	m_strPassword 	= "admin123";    //密码
	
	Return_Value_Info_t nGroupLen = new Return_Value_Info_t();
	
	public String   m_strQueryAlarmCamareID = "1000000$1$0$0";    //报警设备ID
	public String	m_strNVRDeviceID = "1000000";
	public String	davPath = "D:\\downoladtest.dav";
	FileOutputStream writer = null;
	public static int nDownloadSeq = 0;
	public static boolean bDownloadFinish = false;
	
	public static String StrCarNum;
	
	DPSDKAlarmCallback   m_AlarmCB = new DPSDKAlarmCallback();
	
	DPSDKMediaDataCallback  m_MediaCB = new DPSDKMediaDataCallback();
	DPSDKMediaDataCallback  m_MediaDownloadCB = new DPSDKMediaDataCallback(){
		@Override
		public void invoke(int nPDLLHandle, int nSeq, int nMediaType, byte[] szNodeId, int nParamVal, byte[] szData, int nDataLen)
		{
			if(nMediaType == 2 && nDataLen == 0)
			{ //录像下载结束,开线程调用停止录像，否则接口会超时
				nDownloadSeq = nSeq;
				Thread t = new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {  
				     		if(writer != null){
				     			writer.flush();
				     			writer.close();
				     			writer = null;
				    		}
				        } catch (IOException e) {  
				            e.printStackTrace();  
				        } 
						
						int nRet = IDpsdkCore.DPSDK_CloseRecordStreamBySeq( m_nDLLHandle, nDownloadSeq, 10000);
						if(nRet == 0){
							nDownloadSeq = -1;
						}
						System.out.printf("下载结束，停止下载nRet = %d", nRet);
						System.out.println();
					}
					
				});
				t.start();
			}
			try {  
				if(davPath != null){
					if(writer == null)
					{
						writer = new FileOutputStream(davPath,true);
				   	}
					if(nDataLen>0){
						writer.write(szData);    
					}
				}
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}
	};
	public String   m_strTvWallSourceCamareID = "1000605$1$0$0";    //报警设备ID
	
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
						System.out.printf("查询NVR通道状态失败，deviceID = %s, nRet = %d", new String(szDeviceId), nRet);
					}
					//System.out.println();
				}
			}
			//System.out.printf("Device Status Report!, szDeviceId = %s, nStatus = %s", new String(szDeviceId),status);
			//System.out.println();
		}
	};
	
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
	
	public fDPSDKGeneralJsonTransportCallback fGeneralJson = new fDPSDKGeneralJsonTransportCallback() {
		@Override
		public void invoke(int nPDLLHandle, byte[] szJson) {
			System.out.printf("General Json Return, ReturnJson = %s", new String(szJson));
			System.out.println();
		}
	};
	//卡口过车数据回调
	public fDPSDKGetBayCarInfoCallbackEx fBayCarInfo = new fDPSDKGetBayCarInfoCallbackEx() {
		@Override
		public void invoke(int nPDLLHandle, byte[] szDeviceId, int nDeviceIdLen, int nDevChnId, byte[] szChannelId, int nChannelIdLen, byte[] szDeviceName, int	nDeviceNameLen, byte[] szDeviceChnName, int	nChanNameLen, byte[] szCarNum, int nCarNumLen, int	nCarNumType, int nCarNumColor, int nCarSpeed,int nCarType, int	nCarColor, int	nCarLen, int	nCarDirect, int	nWayId, long lCaptureTime, long lPicGroupStoreID, int nIsNeedStore, int nIsStoraged, byte[] szCaptureOrg, int nCaptureOrgLen, byte[] szOptOrg, int nOptOrgLen, byte[] szOptUser, int nOptUserLen, byte[] szOptNote, int nOptNoteLen, byte[] szImg0Path, int nImg0PathLen, byte[] szImg1Path, int nImg1PathLen, byte[] szImg2Path, int nImg2PathLen, byte[] szImg3Path, int nImg3PathLen, byte[] szImg4Path, int nImg4PathLen, byte[] szImg5Path, int nImg5PathLen, byte[] szImgPlatePath, int nImgPlatePathLen, int icarLog, int iPlateLeft, int iPlateRight, int iPlateTop, int iPlateBottom) {
			try {
					StrCarNum = new String(szCarNum, "UTF-8");
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } 
			System.out.printf("Bay Car Info Report, DeviceId=%s, szChannelId=%s, szDeviceChnName=%s, szCarNum=%s, szImg0Path=%s", new String(szDeviceId), new String(szChannelId), new String(szDeviceChnName), StrCarNum, new String(szImg0Path));
			System.out.println();
		}
	};
	
	//违章报警回调
	public fDPSDKTrafficAlarmCallback fTrafficAlarmCallback = new fDPSDKTrafficAlarmCallback() {
		@Override
		public void invoke(int nPDLLHandle, Traffic_Alarm_Info_t trafficAlarmInfo) {
			try {
					StrCarNum = new String(trafficAlarmInfo.szCarNum, "UTF-8");
		    } catch (IOException e) {  
		            e.printStackTrace();  
		    } 
			System.out.printf("TrafficAlarm Car Info Report, DeviceId=%s, szChannelId=%s, szDeviceChnName=%s, szCarNum=%s, szImg0Path=%s", new String(trafficAlarmInfo.szDeviceId).trim(), new String(trafficAlarmInfo.szCameraId).trim(), new String(trafficAlarmInfo.szDeviceName).trim(), StrCarNum.trim(), new String(trafficAlarmInfo.szPicUrl0).trim());
			System.out.println();
		}
	};
	
	//区间测速回调
	public fDPSDKGetAreaSpeedDetectCallback fGetAreaSpeedDetectCallback = new fDPSDKGetAreaSpeedDetectCallback() {
		@Override
		public void invoke(int nPDLLHandle, Area_Detect_Info_t areaSpeedDetectInfo) {
			try {
					StrCarNum = new String(areaSpeedDetectInfo.szCarNum, "UTF-8");
			} catch (IOException e) {  
			        e.printStackTrace();  
			} 
			System.out.printf("AreaSpeedDetectInfo Report, szCarNum=%s, szPicName0=%s", StrCarNum.trim(), new String(areaSpeedDetectInfo.szPicName0).trim());
			System.out.println();
		}
	};
	
	public fSaveRecordFinishedCallback fSaveRecordFinished = new fSaveRecordFinishedCallback() {
		@Override
		public void invoke(int nPDLLHandle, int nPlaybackSeq) {
			System.out.printf("录像下载完成，nPlaybackSeq = %d", nPlaybackSeq);
			System.out.println();
			bDownloadFinish = true;
		}
	};
	
	/*
	 * 创建DPSDK
	 * */
	public void OnCreate()
	{
		int nRet = -1;
		Return_Value_Info_t res = new Return_Value_Info_t();
		nRet =IDpsdkCore.DPSDK_Create(dpsdk_sdk_type_e.DPSDK_CORE_SDK_SERVER,res);
		
		m_nDLLHandle = res.nReturnValue;
		String dpsdklog = "D:\\dpsdkjavalog";
		nRet = IDpsdkCore.DPSDK_SetLog(m_nDLLHandle, dpsdklog.getBytes());
		String dumpfile = "D:\\dpsdkjavadump";
		System.out.println(nRet);
		try {
			nRet = IDpsdkCore.DPSDK_StartMonitor(m_nDLLHandle, dumpfile.getBytes());
			if(nRet != 0) {
				throw new DahuaException(nRet+"");
			}
		} catch (DahuaException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(m_nDLLHandle > 0)
		{
			//设置设备状态上报监听函数
			nRet = IDpsdkCore.DPSDK_SetDPSDKDeviceStatusCallback(m_nDLLHandle, fDeviceStatus);
			//设置NVR通道状态上报监听函数
			nRet =IDpsdkCore.DPSDK_SetDPSDKNVRChnlStatusCallback(m_nDLLHandle, fNVRChnlStatus);
			//设置通用JSON回调
			nRet = IDpsdkCore.DPSDK_SetGeneralJsonTransportCallback(m_nDLLHandle, fGeneralJson);
			
			nRet = IDpsdkCore.DPSDK_SetDPSDKGetBayCarInfoCallbackEx(m_nDLLHandle, fBayCarInfo);
			
			nRet = IDpsdkCore.DPSDK_SetDPSDKTrafficAlarmCallback(m_nDLLHandle, fTrafficAlarmCallback);
			
			nRet = IDpsdkCore.DPSDK_SetDPSDKGetAreaSpeedDetectCallback(m_nDLLHandle, fGetAreaSpeedDetectCallback);
		}
		
		System.out.print("创建DPSDK, 返回 m_nDLLHandle = ");
		System.out.println(m_nDLLHandle);
	}
	
	/*
	 * 登录
	 * */
	public void OnLogin()
	{
		Login_Info_t loginInfo = new Login_Info_t();
		loginInfo.szIp = m_strIp.getBytes();
		loginInfo.nPort = m_nPort;
		loginInfo.szUsername = m_strUser.getBytes();
		loginInfo.szPassword = m_strPassword.getBytes();
		loginInfo.nProtocol = dpsdk_protocol_version_e.DPSDK_PROTOCOL_VERSION_II;
		loginInfo.iType = 1;

		int nRet = IDpsdkCore.DPSDK_Login(m_nDLLHandle,loginInfo,10000);
		
		try {
			if(nRet != 0) {
				String code = nRet+"";
				throw new DahuaException(code,null,null);
			}
		}  catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
//		{
//			System.out.printf("登录成功，nRet = %d", nRet);
//		}else
//		{
//			System.out.printf("登录失败，nRet = %d", nRet);
//		}
		System.out.println();
	}
	
	/*
	 * 加载所有组织树
	 * */
	public void LoadAllGroup()
	{
		int nRet = IDpsdkCore.DPSDK_LoadDGroupInfo(m_nDLLHandle, nGroupLen, 180000 );
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("加载所有组织树成功，nRet = %d， nDepCount = %d", nRet, nGroupLen.nReturnValue);
		}else
		{
			System.out.printf("加载所有组织树失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	/*
	 * 获取所有组织树串
	 * */
	public void GetGroupStr()
	{
		byte[] szGroupBuf = new byte[nGroupLen.nReturnValue];
		int nRet = IDpsdkCore.DPSDK_GetDGroupStr(m_nDLLHandle, szGroupBuf, nGroupLen.nReturnValue, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			String GroupBuf = "";
			try {
				GroupBuf = new String(szGroupBuf, "UTF-8");
			} catch (IOException e) {  
            e.printStackTrace();  
			} 
			System.out.printf("获取所有组织树串成功，nRet = %d， szGroupBuf = [%s]", nRet, GroupBuf);
			try {
				File file = new File("D:\\text.xml");
				if(!file.exists())
				{
					file.createNewFile();	
				}
				
				FileOutputStream  out = new FileOutputStream(file);
				out.write(szGroupBuf);
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else
		{
			System.out.printf("获取所有组织树串失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	/*
	 * 获取用户组织信息
	 * */
	public void GetUserOrgInfo()
	{
		GetUserOrgInfo userOrgInfo = new GetUserOrgInfo();
		int nRet = IDpsdkCore.DPSDK_GetUserOrgInfo(m_nDLLHandle, userOrgInfo, 10000 );
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("获取用户组织信息成功，nRet = %d， UserOrgInfo = %s", nRet, userOrgInfo.strUserOrgInfo);
		}else
		{
			System.out.printf("获取用户组织信息失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	/*
	 * 获取Ftp信息
	 * */
	public void GetFtpInfo()
	{
		byte[] szFtpInfo = new byte[64];
		int nRet = IDpsdkCore.DPSDK_GetFTPInfo(m_nDLLHandle, szFtpInfo, 64);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			String FtpInfo = "";
			try {
				FtpInfo = new String(szFtpInfo, "UTF-8");
			} catch (IOException e) {  
            e.printStackTrace();  
			} 
			System.out.printf("获取Ftp信息成功，nRet = %d， szFtpInfo = [%s]", nRet, FtpInfo);
		}else
		{
			System.out.printf("获取Ftp信息失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public void GetGPSXML()
	{
		Return_Value_Info_t nGpsXMLLen = new Return_Value_Info_t();
		int nRet = IDpsdkCore.DPSDK_AskForLastGpsStatusXMLStrCount(m_nDLLHandle, nGpsXMLLen, 10*1000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS && nGpsXMLLen.nReturnValue > 0)
		{
			byte[] LastGpsIStatus = new byte[nGpsXMLLen.nReturnValue - 1];
			nRet = IDpsdkCore.DPSDK_AskForLastGpsStatusXMLStr(m_nDLLHandle, LastGpsIStatus, nGpsXMLLen.nReturnValue);
			
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("获取GPS XML成功，nRet = %d， LastGpsIStatus = [%s]", nRet, new String(LastGpsIStatus));
				try {
					File file = new File("D:\\GPS.xml");
					if(!file.exists())
					{
						file.createNewFile();	
					}
					
					FileOutputStream  out = new FileOutputStream(file);
					out.write(LastGpsIStatus);
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else
			{
				System.out.printf("获取GPS XML失败，nRet = %d", nRet);
			}
		}else if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS && nGpsXMLLen.nReturnValue == 0)
		{
			System.out.printf("获取GPS XML  XMLlength = 0");
		}
		else
		{
			System.out.printf("获取GPS XML失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	
	/*
	 * 查询NVR设备的通道状态
	 * */
	public void QureyNVRChannelStatus()
	{
		Device_Info_Ex_t deviceInfo = new Device_Info_Ex_t();
		int nRet = IDpsdkCore.DPSDK_GetDeviceInfoExById(m_nDLLHandle, m_strNVRDeviceID.getBytes(),deviceInfo);
		if(deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_EVS || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_SMART_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_MATRIX_NVR6000)
		{
			nRet = IDpsdkCore.DPSDK_QueryNVRChnlStatus(m_nDLLHandle, m_strNVRDeviceID.getBytes(), 10*1000);
			
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("查询NVR通道状态成功，nRet = %d", nRet);
			}else
			{
				System.out.printf("查询NVR通道状态失败，nRet = %d", nRet);
			}
			System.out.println();
		}
	}
	
	/*
	 * 加载组织树
	 * */
	public void LoadGroup()
	{
		String strCoding="001";
		Load_Dep_Info_t depInfo = new Load_Dep_Info_t();
		depInfo.nOperation = dpsdk_getgroup_operation_e.DPSDK_CORE_GEN_GETGROUP_OPERATION_CHILD;
		depInfo.szCoding=strCoding.getBytes();
		Return_Value_Info_t nLen = new Return_Value_Info_t();
		int nRet = IDpsdkCore.DPSDK_LoadDGroupInfoLayered(m_nDLLHandle,depInfo,nLen,10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("加载组织树成功，nRet = %d， nGroupLen = %d", nRet,nLen.nReturnValue);
		}else
		{
			System.out.printf("加载组织树失败，nRet = %d", nRet);
		}
		System.out.println();
		
		Get_Dep_Count_Info_t depCountInfo = new Get_Dep_Count_Info_t();
		depCountInfo.szCoding=strCoding.getBytes();
		nRet = IDpsdkCore.DPSDK_GetDGroupCount(m_nDLLHandle,depCountInfo);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("获取子组织子设备个数成功，nRet = %d， nDepCount = %d, nDeviceCount= %d", nRet,depCountInfo.nDepCount,depCountInfo.nDeviceCount);
		}else
		{
			System.out.printf("获取子组织子设备失败，nRet = %d", nRet);
		}
		System.out.println();
		
	}
	
	int GetReal()
	{
		Return_Value_Info_t nRealSeq = new Return_Value_Info_t();
		Get_RealStream_Info_t getInfo = new Get_RealStream_Info_t();
		getInfo.szCameraId = m_strRealCamareID.getBytes();
		getInfo.nStreamType = dpsdk_stream_type_e.DPSDK_CORE_STREAMTYPE_MAIN;
		getInfo.nRight = dpsdk_check_right_e.DPSDK_CORE_NOT_CHECK_RIGHT; //不检查权限，请求视频流，无需加载组织结构
		getInfo.nMediaType = dpsdk_media_type_e.DPSDK_CORE_MEDIATYPE_VIDEO;
		getInfo.nTransType = dpsdk_trans_type_e.DPSDK_CORE_TRANSTYPE_TCP;
		getInfo.nTrackID = 701;
		
		int nRet = IDpsdkCore.DPSDK_GetRealStream(m_nDLLHandle, nRealSeq, getInfo, m_MediaCB, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("打开实时视频成功，nRet = %d， nSeq = %d", nRet, nRealSeq.nReturnValue);
		}else
		{
			System.out.printf("打开实时视频失败，nRet = %d", nRet);
		}
		System.out.println();
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			return nRealSeq.nReturnValue;
		else
			return -1;
	}
	
	void CloseReal(int nRealSeq)
	{
		int nRet = IDpsdkCore.DPSDK_CloseRealStreamBySeq(m_nDLLHandle, nRealSeq, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("关闭实时视频成功，nRet = %d， nSeq = %d", nRet, nRealSeq);
		}else
		{
			System.out.printf("关闭实时视频失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	
	void GetExternUrl()
	{
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
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.println(new String(pExternalRealStreamUrlInfo.szUrl).trim());
		}else
		{
			System.out.printf("获取URL失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	/*
	 * 报警布控
	 * */
	public void SetAlarm()
	{
		int nRet = IDpsdkCore.DPSDK_SetDPSDKAlarmCallback(m_nDLLHandle,m_AlarmCB);//设置报警监听函数
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("开启报警监听成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("开启报警监听失败，nRet = %d", nRet);
		}
		System.out.println();
		
		//Alarm_Enable_Info_t alarmInfo = new Alarm_Enable_Info_t(1);
		//alarmInfo.sources[0].szAlarmDevId = m_strAlarmCamareID.getBytes();
		//alarmInfo.sources[0].nVideoNo = 0;
		//alarmInfo.sources[0].nAlarmInput = 0;
		//alarmInfo.sources[0].nAlarmType = dpsdk_alarm_type_e.DPSDK_CORE_ALARM_TYPE_VIDEO_LOST;
		//int nRet =  IDpsdkCore.DPSDK_EnableAlarm(m_nDLLHandle, alarmInfo,10000);
		
		//if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		//{
		//	System.out.printf("报警布控成功，nRet = %d", nRet);
		//}else
		//{
		//	System.out.printf("报警布控失败，nRet = %d", nRet);
		//}
		//System.out.println();
	}
	
	/*
	 * 报警查询
	 * */
	public void OnQueryAlarm()
	{
		Date tmStart = new Date(2019-1900,3-1,20,0,0,0);
		Date tmEnd = new Date(2019-1900,3-1,27,12,0,0);
		
		//System.out.printf("%s",tmStart.toLocaleString());
		//System.out.println();
		//System.out.printf("%s",tmEnd.toLocaleString());
		//System.out.println();
		Alarm_Query_Info_t stuQueryInfo = new Alarm_Query_Info_t();
		stuQueryInfo.szCameraId = m_strQueryAlarmCamareID.getBytes();
		stuQueryInfo.nAlarmType = dpsdk_alarm_type_e.DPSDK_CORE_ALARM_TYPE_Unknown;
		stuQueryInfo.uStartTime = tmStart.getTime()/1000;//转换成秒
		stuQueryInfo.uEndTime = tmEnd.getTime()/1000;
		
		//System.out.printf("查询报警时间：%d - %d",stuQueryInfo.uStartTime, stuQueryInfo.uEndTime );
		//System.out.println();
		
		Return_Value_Info_t nCount = new Return_Value_Info_t();
		int nRet = IDpsdkCore.DPSDK_QueryAlarmCount(m_nDLLHandle, stuQueryInfo, nCount, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("查询报警数量成功，nRet = %d， nCount= %d", nRet, nCount.nReturnValue);
		}else
		{
			System.out.printf("查询报警数量失败，nRet = %d", nRet);
		}
		System.out.println();
		
		if (nCount.nReturnValue > 0)
		{
			Alarm_Info_t stuAlarmInfo = new Alarm_Info_t(nCount.nReturnValue);
			
			nRet = IDpsdkCore.DPSDK_QueryAlarmInfo(m_nDLLHandle, stuQueryInfo, stuAlarmInfo, 0, nCount.nReturnValue,10000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("查询报警信息成功，nRet = %d， nCount= %d", nRet, stuAlarmInfo.nRetCount);
				for(int i=0;i<stuAlarmInfo.nRetCount;i++)
				{
					System.out.println();
					Date dTime = new Date(stuAlarmInfo.pAlarmInfo[i].uAlarmTime * 1000);
					System.out.printf("序号=%d，类型 = %d，时间=%s，事件=%d，设备ID=%s，通道号=%d， 处理状态= %d", i+1,
							stuAlarmInfo.pAlarmInfo[i].nAlarmType,dTime.toLocaleString(), stuAlarmInfo.pAlarmInfo[i].nEventType,
							new String(stuAlarmInfo.pAlarmInfo[i].szDevId).trim(),stuAlarmInfo.pAlarmInfo[i].uChannel, stuAlarmInfo.pAlarmInfo[i].nDealWith);
				}
			}else
			{
				System.out.printf("查询报警信息失败，nRet = %d", nRet);
			}
			System.out.println();
		}
	}
	
	public void OnSendOSDInfo()
	{
		String strDeviceId = new String("1000001");
		String strMsg = new String("qqq"); 
		Send_OSDInfo_t stuSendOSDInfo = new Send_OSDInfo_t();
		stuSendOSDInfo.szDevId = strDeviceId.getBytes();
		stuSendOSDInfo.szMessage = strMsg.getBytes();
		int nRet = IDpsdkCore.DPSDK_SendOSDInfo(m_nDLLHandle, stuSendOSDInfo, 10000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("发送OSD信息成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("发送OSD信息失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public void OnGetTvWallList()
	{
		Return_Value_Info_t nCount = new Return_Value_Info_t();
		int nRet = IDpsdkCore.DPSDK_GetTvWallListCount(m_nDLLHandle, nCount, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("查询电视墙列表数量成功，nRet = %d， nCount= %d", nRet, nCount.nReturnValue);
		}else
		{
			System.out.printf("查询电视墙列表数量失败，nRet = %d", nRet);
		}
		System.out.println();
		
		int nCurTvWallId = -1;
		if (nCount.nReturnValue > 0)
		{
			TvWall_List_Info_t pTvWallListInfo = new TvWall_List_Info_t(nCount.nReturnValue);
			
			nRet = IDpsdkCore.DPSDK_GetTvWallList(m_nDLLHandle, pTvWallListInfo);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("获取电视墙列表信息成功，nRet = %d， nCount= %d", nRet, pTvWallListInfo.nCount);
				for(int i=0;i<pTvWallListInfo.nCount;i++)
				{
					System.out.println();
					System.out.printf("序号=%d，nTvWallId = %d，nState=%s，szName=%s", i+1,pTvWallListInfo.pTvWallInfo[i].nTvWallId,pTvWallListInfo.pTvWallInfo[i].nState,
							new String(pTvWallListInfo.pTvWallInfo[i].szName).trim());
				
					if(i==0)
					{
						nCurTvWallId = pTvWallListInfo.pTvWallInfo[i].nTvWallId;
					}
				}
			}else
			{
				System.out.printf("获取电视墙列表信息失败，nRet = %d", nRet);
			}
			System.out.println();
		}
		if(nCurTvWallId > 0)
			GetTvWallLayout(nCurTvWallId);
		
	}
	
	public void GetTvWallLayout(int nTvWallId)
	{
		System.out.printf("nTvWallId=%d;",nTvWallId);
		Return_Value_Info_t nCount = new Return_Value_Info_t();
		int nRet = IDpsdkCore.DPSDK_GetTvWallLayoutCount(m_nDLLHandle, nTvWallId, nCount, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("查询电视墙布局成功，nRet = %d， nCount= %d", nRet, nCount.nReturnValue);
		}else
		{
			System.out.printf("查询电视墙布局失败，nRet = %d", nRet);
		}
		System.out.println();
		
		int nScreenId = -1;
		float fLeft = 0;
		float fTop = 0;
		float fWidth = 0;
		float fHeight = 0;
		if (nCount.nReturnValue > 0)
		{
			TvWall_Layout_Info_t pTvWallLayoutInfo = new TvWall_Layout_Info_t(nCount.nReturnValue);
			pTvWallLayoutInfo.nTvWallId = nTvWallId;
			
			nRet = IDpsdkCore.DPSDK_GetTvWallLayout(m_nDLLHandle, pTvWallLayoutInfo);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("获取电视墙布局信息成功，nRet = %d， nCount= %d", nRet, pTvWallLayoutInfo.nCount);
				for(int i=0;i<pTvWallLayoutInfo.nCount;i++)
				{
					if(i == 0)
					{
						nScreenId = pTvWallLayoutInfo.pScreenInfo[i].nScreenId;
					}
					System.out.println();
					System.out.printf("序号=%d，nScreenId = %d，szName=%s, szDecoderId=%s,fLeft=%f,fTop=%f,fWidth=%f,fHeight=%f,bBind=%d", i+1,pTvWallLayoutInfo.pScreenInfo[i].nScreenId,new String(pTvWallLayoutInfo.pScreenInfo[i].szName).trim(),
							new String(pTvWallLayoutInfo.pScreenInfo[i].szDecoderId).trim(), pTvWallLayoutInfo.pScreenInfo[i].fLeft, pTvWallLayoutInfo.pScreenInfo[i].fTop,
							pTvWallLayoutInfo.pScreenInfo[i].fWidth, pTvWallLayoutInfo.pScreenInfo[i].fHeight, pTvWallLayoutInfo.pScreenInfo[i].bBind?1:0);
					fLeft = pTvWallLayoutInfo.pScreenInfo[i].fLeft;
					fTop = pTvWallLayoutInfo.pScreenInfo[i].fTop;
					fWidth = pTvWallLayoutInfo.pScreenInfo[i].fWidth/2;
					fHeight = pTvWallLayoutInfo.pScreenInfo[i].fHeight/2;
				}
			}else
			{
				System.out.printf("获取电视墙布局信息失败，nRet = %d", nRet);
			}
			System.out.println();
		}
		
		if(nScreenId > 0)
		{
			{
				TvWall_Screen_Split_t  pInfo = new TvWall_Screen_Split_t();
				pInfo.nTvWallId = nTvWallId;
				pInfo.nScreenId = nScreenId;
				pInfo.enSplitNum = tvwall_screen_split_caps.Screen_Split_4;
				
				nRet = IDpsdkCore.DPSDK_SetTvWallScreenSplit(m_nDLLHandle, pInfo,1000);
				if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
				{
					System.out.printf("分割窗口成功，nRet = %d", nRet);
				}else
				{
					System.out.printf("分割窗口失败，nRet = %d", nRet);
					TvWall_Screen_Open_Window_t pOpenWindowInfo = new TvWall_Screen_Open_Window_t();
					pOpenWindowInfo.nTvWallId = nTvWallId;
					pOpenWindowInfo.nScreenId = nScreenId;
					pOpenWindowInfo.fLeft = fLeft;
					pOpenWindowInfo.fTop = fTop;
					pOpenWindowInfo.fWidth = fWidth;
					pOpenWindowInfo.fHeight = fHeight;
					nRet = IDpsdkCore.DPSDK_TvWallScreenOpenWindow(m_nDLLHandle, pOpenWindowInfo, 10000);
					if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
					{
						System.out.printf("窗口开窗成功，nRet = %d，nWindowId = %d。", nRet, pOpenWindowInfo.nWindowId);
						/*TvWall_Screen_Close_Window_t pCloseWindowInfo = new TvWall_Screen_Close_Window_t();
						pCloseWindowInfo.nTvWallId = nTvWallId;
						pCloseWindowInfo.nScreenId = nScreenId;
						pCloseWindowInfo.nWindowId = pOpenWindowInfo.nWindowId;
						nRet = IDpsdkCore.DPSDK_TvWallScreenCloseWindow(m_nDLLHandle, pCloseWindowInfo, 10000);
						if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
						{
							System.out.printf("窗口关窗成功，nRet = %d", nRet);
						}else
						{
							System.out.printf("窗口关窗失败，nRet = %d", nRet);
						}*/
					}else
					{
						System.out.printf("窗口开窗失败，nRet = %d", nRet);
					}
				}
				System.out.println();
			}
			SetWndSource(nTvWallId, nScreenId, 0);
		}
	}
	
	void SetWndSource(int nTvWallId, int nScreenId, int nWndId)
	{
		{
			Set_TvWall_Screen_Window_Source_t pInfo = new Set_TvWall_Screen_Window_Source_t();
			pInfo.nTvWallId = nTvWallId;
			pInfo.nScreenId = nScreenId;
			pInfo.nWindowId = nWndId;
			pInfo.szCameraId = m_strTvWallSourceCamareID.getBytes();
			pInfo.enStreamType = dpsdk_stream_type_e.DPSDK_CORE_STREAMTYPE_MAIN;
			pInfo.nStayTime = 30;
			
			int nRet = IDpsdkCore.DPSDK_SetTvWallScreenWindowSource(m_nDLLHandle, pInfo,1000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("设置视频源成功，nRet = %d", nRet);
			}else
			{
				System.out.printf("设置视频源失败，nRet = %d", nRet);
			}
			System.out.println();
		}
		
		{
			TvWall_Screen_Close_Source_t pInfo = new TvWall_Screen_Close_Source_t();
			pInfo.nTvWallId = nTvWallId;
			pInfo.nScreenId = nScreenId;
			pInfo.nWindowId = nWndId;
			int nRet = IDpsdkCore.DPSDK_CloseTvWallScreenWindowSource(m_nDLLHandle, pInfo,1000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("关闭视频源成功，nRet = %d", nRet);
			}else
			{
				System.out.printf("关闭视频源失败，nRet = %d", nRet);
			}
			System.out.println();
		}
		
		{
			int nRet = IDpsdkCore.DPSDK_ClearTvWallScreen(m_nDLLHandle, nTvWallId, 1000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("清屏成功，nRet = %d", nRet);
			}else
			{
				System.out.printf("清屏失败，nRet = %d", nRet);
			}
			System.out.println();
		}
	}
	
	public void MapTaskToTvWall(int nTvWallId)
	{
		Return_Value_Info_t nCount = new Return_Value_Info_t();
		int nRet = IDpsdkCore.DPSDK_GetTvWallTaskListCount(m_nDLLHandle, nTvWallId, nCount, 10000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("查询电视墙任务列表数量成功，nRet = %d， nCount= %d", nRet, nCount.nReturnValue);
		}else
		{
			System.out.printf("查询电视墙任务列表数量失败，nRet = %d", nRet);
		}
		System.out.println();
		
		int nCurTaskId = -1;
		if(nCount.nReturnValue > 0)
		{
			TvWall_Task_Info_List_t pTvWallTaskListInfo = new TvWall_Task_Info_List_t(nCount.nReturnValue);
			pTvWallTaskListInfo.nCount = nCount.nReturnValue;
			nRet = IDpsdkCore.DPSDK_GetTvWallTaskList(m_nDLLHandle, nTvWallId, pTvWallTaskListInfo, 10000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("获取电视墙任务列表信息成功，nRet = %d， nCount= %d", nRet, pTvWallTaskListInfo.nCount);
				for(int i=0;i<pTvWallTaskListInfo.nCount;i++)
				{
					System.out.println();
					System.out.printf("序号=%d，nTaskId=%d，szName=%s, szDes=%s", i+1, pTvWallTaskListInfo.pTvWallTaskInfo[i].nTaskId, new String(pTvWallTaskListInfo.pTvWallTaskInfo[i].szName).trim(),
							new String(pTvWallTaskListInfo.pTvWallTaskInfo[i].szDes).trim());
				
					if(i==0)
					{
						nCurTaskId = pTvWallTaskListInfo.pTvWallTaskInfo[i].nTaskId;
					}
				}
			}else
			{
				System.out.printf("获取电视墙任务列表信息失败，nRet = %d", nRet);
			}
			System.out.println();
		}
		
		if(nCurTaskId >= 0)
		{
			Return_Value_Info_t nTaskInfoLen = new Return_Value_Info_t();
			nRet = IDpsdkCore.DPSDK_GetTvWallTaskInfoLen(m_nDLLHandle, nTvWallId, nCurTaskId, nTaskInfoLen, 10000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS && nTaskInfoLen.nReturnValue > 0)
			{
				byte[] szTaskInfoBuf = new byte[nGroupLen.nReturnValue];
				nRet = IDpsdkCore.DPSDK_GetTvWallTaskInfoStr(m_nDLLHandle, szTaskInfoBuf, nTaskInfoLen.nReturnValue);
				if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
				{
					String TaskInfoBuf = "";
					try {
						TaskInfoBuf = new String(szTaskInfoBuf, "UTF-8");
					} catch (IOException e) {  
		            e.printStackTrace();  
					} 
					System.out.printf("查询电视墙任务信息成功，szTaskInfoBuf = %s", TaskInfoBuf);
					System.out.println();
				}
				
				nRet = IDpsdkCore.DPSDK_MapTaskToTvWall(m_nDLLHandle, nTvWallId, nCurTaskId, 10000);
				if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
				{
					System.out.printf("电视墙任务上墙成功。");
					
				}
				else
				{
					System.out.printf("电视墙任务上墙失败，nRet = %d.", nRet);
				}
				System.out.println();
			}
		}
	}
	
	public String GetStringAsUTF8(byte[] data)
	{
		String str = "";
		try {
			str = new String(data, "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	public void OnSetDoorCmd()
	{
		SetDoorCmd_Request_t pInfo = new SetDoorCmd_Request_t();
		String strDeviceID = "1000000$4$0$0";    //设备ID
		pInfo.szCameraId = strDeviceID.getBytes();
		pInfo.cmd = dpsdk_SetDoorCmd_e.DPSDK_CORE_DOOR_CMD_ALWAYS_OPEN;
		pInfo.start = 10;
		pInfo.end = 110;
		
		int nRet = IDpsdkCore.DPSDK_SetDoorCmd(m_nDLLHandle, pInfo, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("DPSDK_SetDoorCmd:成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("DPSDK_SetDoorCmd:失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public void OnGetLinkResource()
	{
		Return_Value_Info_t nCount = new Return_Value_Info_t();
		int nRet = IDpsdkCore.DPSDK_QueryLinkResource(m_nDLLHandle, nCount, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("DPSDK_QueryLinkResource成功，nRet = %d， nCount= %d", nRet, nCount.nReturnValue);
		}else
		{
			System.out.printf("DPSDK_QueryLinkResource失败，nRet = %d", nRet);
		}
		System.out.println();
		
		if (nCount.nReturnValue > 0)
		{
			GetLinkResource_Responce_t pResponse = new GetLinkResource_Responce_t(nCount.nReturnValue);
			
			nRet = IDpsdkCore.DPSDK_GetLinkResource(m_nDLLHandle, pResponse);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("DPSDK_GetLinkResource成功，nRet = %d， nCount= %d", nRet, pResponse.nLen);
				System.out.println();
				System.out.printf("pXmlData=%s", new String(pResponse.pXmlData).trim());
				
			}else
			{
				System.out.printf("DPSDK_GetLinkResource失败，nRet = %d", nRet);
			}
			System.out.println();
		}
	}
	
	public void BlindShots()
	{
		String szJson = "{ \"method\":\"dev.snap\",\"params\":{\"DevID\":\"1000003\",\"DevChannel\":0,\"PicNum\":1,\"SnapType\":2,\"CmdSrc\":1},\"id\":88 }";
		int mdltype = dpsdk_mdl_type_e.DPSDK_MDL_DMS;
		int trantype = generaljson_trantype_e.GENERALJSON_TRAN_REQUEST;
		
		int nRet = IDpsdkCore.DPSDK_GeneralJsonTransport(m_nDLLHandle, szJson.getBytes(), mdltype, trantype, 30*1000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("DPSDK_GeneralJsonTransport:成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("DPSDK_GeneralJsonTransport:失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public int StartDownLoadRecordByTime(long begintime, long endtime)
	{
		Query_Record_Info_t queryInfo = new Query_Record_Info_t();
		Return_Value_Info_t nRecordCount = new Return_Value_Info_t();
		queryInfo.szCameraId = m_strDownloadCamID.getBytes();
		queryInfo.nRecordType = dpsdk_record_type_e.DPSDK_CORE_PB_RECORD_UNKONWN;//下载模式
		queryInfo.nRight = dpsdk_check_right_e.DPSDK_CORE_NOT_CHECK_RIGHT; //不检查权限，请求视频流，无需加载组织结构
		queryInfo.nSource = 2;//设备录像
		queryInfo.uBeginTime = begintime;//转换成秒;
		queryInfo.uEndTime = endtime;
		int nRet = IDpsdkCore.DPSDK_QueryRecord( m_nDLLHandle, queryInfo, nRecordCount, 60*1000);
		
		if(nRet != 0)
		{
			System.out.printf("录像查询失败，nRet= %d", nRet);
			System.out.println();
			return -1;
		}
		
		if(nRecordCount.nReturnValue == 0)
		{
			System.out.printf("没有录像！！！！！");
			System.out.println();
			return -1;
		}
		
		Return_Value_Info_t nDownLoadSeq = new Return_Value_Info_t();
		Get_RecordStream_Time_Info_t getInfo = new Get_RecordStream_Time_Info_t();
		getInfo.szCameraId = m_strDownloadCamID.getBytes();
		getInfo.nMode = 2;//下载模式
		getInfo.nRight = dpsdk_check_right_e.DPSDK_CORE_NOT_CHECK_RIGHT; //不检查权限，请求视频流，无需加载组织结构
		getInfo.nSource = 2;//设备录像
		
		System.out.printf("开始录像下载   begintime = %d， endtime = %d", begintime, endtime);
		getInfo.uBeginTime = begintime;//转换成秒;
		getInfo.uEndTime = endtime;
		
		nRet = IDpsdkCore.DPSDK_GetRecordStreamByTime( m_nDLLHandle, nDownLoadSeq, getInfo, m_MediaDownloadCB, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("开始录像下载成功，nRet = %d， nSeq = %d", nRet, nDownLoadSeq.nReturnValue);
		}else
		{
			System.out.printf("开始录像下载失败，nRet = %d", nRet);
		}
		System.out.println();
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			return nDownLoadSeq.nReturnValue;
		else
			return -1;
	}
	
	public void StopDownLoadRecordByTime(int nDownloadSeq)
	{
		int nRet = IDpsdkCore.DPSDK_CloseRecordStreamBySeq(m_nDLLHandle, nDownloadSeq, 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			//关闭文件
			try {  
	     		if(writer != null){
	     			writer.flush();
	     			writer.close();
	     			writer = null;
	    		}
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } 
			
			System.out.printf("停止下载成功，nRet = %d， nSeq = %d", nRet, nDownloadSeq);
		}else
		{
			System.out.printf("停止下载失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public int SaveRecordStreamByTime(long begintime, long endtime)
	{
		Query_Record_Info_t queryInfo = new Query_Record_Info_t();
		Return_Value_Info_t nRecordCount = new Return_Value_Info_t();
		queryInfo.szCameraId = m_strDownloadCamID.getBytes();
		queryInfo.nRecordType = dpsdk_record_type_e.DPSDK_CORE_PB_RECORD_UNKONWN;//下载模式
		queryInfo.nRight = dpsdk_check_right_e.DPSDK_CORE_NOT_CHECK_RIGHT; //不检查权限，请求视频流，无需加载组织结构
		queryInfo.nSource = 2;//设备录像
		queryInfo.uBeginTime = begintime;//转换成秒;
		queryInfo.uEndTime = endtime;
		int nRet = IDpsdkCore.DPSDK_QueryRecord( m_nDLLHandle, queryInfo, nRecordCount, 60*1000);
		
		if(nRet != 0)
		{
			System.out.printf("录像查询失败，nRet= %d", nRet);
			System.out.println();
			return -1;
		}
		
		if(nRecordCount.nReturnValue == 0)
		{
			System.out.printf("没有录像！！！！！");
			System.out.println();
			return -1;
		}
		
		bDownloadFinish = false;
		IDpsdkCore.DPSDK_SetDPSDKSaveRecordFinishedCallback(m_nDLLHandle, fSaveRecordFinished);
		
		Return_Value_Info_t nDownLoadSeq = new Return_Value_Info_t();
		Get_RecordStream_Time_Info_t getInfo = new Get_RecordStream_Time_Info_t();
		getInfo.szCameraId = m_strDownloadCamID.getBytes();
		getInfo.nMode = 2;//下载模式
		getInfo.nRight = dpsdk_check_right_e.DPSDK_CORE_NOT_CHECK_RIGHT; //不检查权限，请求视频流，无需加载组织结构
		getInfo.nSource = 2;//设备录像
		
		System.out.printf("开始录像下载   begintime = %d， endtime = %d", begintime, endtime);
		System.out.println();
		getInfo.uBeginTime = begintime;//转换成秒;
		getInfo.uEndTime = endtime;
		int scType = 3;
		
		nRet = IDpsdkCore.DPSDK_SaveRecordStreamByTime( m_nDLLHandle, nDownLoadSeq, getInfo, scType, "D:\\test.mp4".getBytes(), 10000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("开始录像下载成功，nRet = %d， nSeq = %d", nRet, nDownLoadSeq.nReturnValue);
		}else
		{
			System.out.printf("开始录像下载失败，nRet = %d", nRet);
		}
		System.out.println();
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			return nDownLoadSeq.nReturnValue;
		else
			return -1;
	}
	
	public void SubscribeALLBayCarInfo()
	{
		Subscribe_Bay_Car_Info_t pGetInfo = new Subscribe_Bay_Car_Info_t(1);
		pGetInfo.nSubscribeFlag = 1;
		pGetInfo.nChnlCount = 0;
		int nRet = IDpsdkCore.DPSDK_SubscribeBayCarInfo(m_nDLLHandle, pGetInfo, 5000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{	
			System.out.printf("订阅所有卡口过车信息成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("订阅所有卡口过车信息失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public void QueryPersonCount()
	{
		Return_Value_Info_t nQuerySeq = new Return_Value_Info_t();
		Return_Value_Info_t nTotalCount = new Return_Value_Info_t();
		Date tmStart = new Date(2017-1900,3-1,31,0,0,0);
		Date tmEnd = new Date(2017-1900,3-1,31,23,59,59);
		int nStartTime = (int)(tmStart.getTime()/1000);
		int nEndTime = (int)(tmEnd.getTime()/1000);
		int nGranularity = 2;
		int nRet = IDpsdkCore.DPSDK_QueryPersonCount(m_nDLLHandle, m_strPersonCountCamID.getBytes(), nQuerySeq, nTotalCount, nStartTime, nEndTime, nGranularity, 10000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{	
			System.out.printf("查询人数统计总数成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("查询人数统计总数失败，nRet = %d", nRet);
		}
		System.out.println();
		
		if(nTotalCount.nReturnValue > 0)
		{
			Person_Count_Info_t[] pPersonInfo = new Person_Count_Info_t[nTotalCount.nReturnValue];
			nRet = IDpsdkCore.DPSDK_QueryPersonCountBypage(m_nDLLHandle, m_strPersonCountCamID.getBytes(), nQuerySeq.nReturnValue, 0, nTotalCount.nReturnValue, pPersonInfo, 10000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{	
				System.out.printf("分页查询统计结果成功，nRet = %d", nRet);
				for(int i = 0; i < nTotalCount.nReturnValue; i++)
				{
					System.out.println();
					System.out.printf("统计通道号=%d, 规则名称=%s, 开始时间=%d, 结束时间=%d, 进入人数小计=%d, 出去人数小计=%d, 平均保有人数=%d, 最大保有人数=%d", pPersonInfo[i].nChannelID, pPersonInfo[i].szRuleName,
							pPersonInfo[i].nStartTime, pPersonInfo[i].nEndTime, pPersonInfo[i].nEnteredSubTotal, pPersonInfo[i].nExitedSubtotal, pPersonInfo[i].nAvgInside, pPersonInfo[i].nMaxInside);
				}
			}else
			{
				System.out.printf("分页查询统计结果失败，nRet = %d", nRet);
			}
			System.out.println();
			IDpsdkCore.DPSDK_StopQueryPersonCount(m_nDLLHandle, m_strPersonCountCamID.getBytes(), nQuerySeq.nReturnValue, 100000);
		}
	}
	
	public void PlatformRecord()
	{	
		String strCameraId = "1000000$1$0$12";
		int nRet = IDpsdkCore.DPSDK_StartPlatformReocrd(m_nDLLHandle, strCameraId.getBytes(), 1, 10000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("开启平台录像成功");
			System.out.println();
			
			//等待30秒，录制30秒平台录像
			try{
				Thread.sleep(30*1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			nRet = IDpsdkCore.DPSDK_StopPlatformReocrd(m_nDLLHandle, strCameraId.getBytes(), 10000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("关闭平台录像成功");
			}
			else
			{
				System.out.printf("关闭平台录像失败，nRet = %d", nRet);
			}
			System.out.println();
		}
		else
		{
			System.out.printf("开启平台录像失败，nRet = %d", nRet);
			System.out.println();
		}
	}
	
	public void SubscribeAreaSpeedDetectInfo()
	{
		int nSubscribeFlag = 1;
		int nRet = IDpsdkCore.DPSDK_SubscribeAreaSpeedDetectInfo(m_nDLLHandle, nSubscribeFlag, 5000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{	
			System.out.printf("订阅区间测速上报成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("订阅区间测速上报失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public void ClearDevAlarm()
	{
		String szCameraId = "1000007$1$0$0";
		int nAlarmType = 312;
		int nRet = IDpsdkCore.DPSDK_ClearDevAlarm(m_nDLLHandle, szCameraId.getBytes(), nAlarmType, 10*1000);
		
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("DPSDK_ClearDevAlarm:成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("DPSDK_ClearDevAlarm:失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public void GetAlarmScheme()
	{
		Return_Value_Info_t nCount = new Return_Value_Info_t();
		int nRet = IDpsdkCore.DPSDK_GetAlarmSchemeCount(m_nDLLHandle, nCount, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS && nCount.nReturnValue > 0)
		{
			AlarmSchemeInfo_t[] alarmSchemeList = new AlarmSchemeInfo_t[nCount.nReturnValue];
			nRet = IDpsdkCore.DPSDK_GetAlarmSchemeList(m_nDLLHandle, alarmSchemeList, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				for (int i = 0; i < alarmSchemeList.length; ++i)
			 	{
					Return_Value_Info_t nLen = new Return_Value_Info_t();
					nRet = IDpsdkCore.DPSDK_GetSchemeFileDataLen(m_nDLLHandle, alarmSchemeList[i].id, nLen, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
					if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS && nLen.nReturnValue > 0)
					{
						AlarmSchemeFileInfo_t alarmSchemeFileInfo = new AlarmSchemeFileInfo_t(nLen.nReturnValue);
						nRet = IDpsdkCore.DPSDK_GetSchemeFile(m_nDLLHandle, alarmSchemeList[i].id, alarmSchemeFileInfo, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
						if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
						{	
							String strAlarmSchemeFile = "";
							try {
								strAlarmSchemeFile = new String(alarmSchemeFileInfo.data, "UTF-8");
							} catch (IOException e) {  
								e.printStackTrace();  
							}
							System.out.printf("获取报警预案成功，nRet = %d， szAlarmSchemeFile = [%s]", nRet, strAlarmSchemeFile);
							System.out.println();
						}
					}
			 	}
			}
		}else
		{
			System.out.printf("查询报警预案数量失败，nRet = %d", nRet);
			System.out.println();
		}
		
	}
	
	/*
	 * 登出
	 * */
	public void OnLogout()
	{
		int nRet = IDpsdkCore.DPSDK_Logout(m_nDLLHandle, 10000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("登出成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("登出失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	/*
	 * 云台控制
	 */
	public void OnYuntaiStart(String m_strRealCamareID,int nDirect,int nStep) 
	{
		byte[] szCameraId = m_strRealCamareID.getBytes();
		int nRet = IDpsdkCore.DPSDK_PtzDirection(m_nDLLHandle, new Ptz_Direct_Info_t(szCameraId,nDirect,nStep,false),10000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
			System.out.printf("云台操作成功,nRet = %d",nRet);
		}else {
			System.out.printf("云台操作失败,nRet = %d",nRet);
		}
	}
	
	public void OnYuntaiStop(String m_strRealCamareID,int nDirect,int nStep) {
		byte[] szCameraId = m_strRealCamareID.getBytes();
		int nRet = IDpsdkCore.DPSDK_PtzDirection(m_nDLLHandle, new Ptz_Direct_Info_t(szCameraId,nDirect,nStep,true),10000);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
			System.out.printf("云台操作成功,nRet = %d",nRet);
		}else {
			System.out.printf("云台操作失败,nRet = %d",nRet);
		}
	}
	

	
	/*
	 * 释放内存
	 * */
	public void OnDestroy()
	{
		int nRet = IDpsdkCore.DPSDK_Destroy(m_nDLLHandle);
		if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
		{
			System.out.printf("释放内存成功，nRet = %d", nRet);
		}else
		{
			System.out.printf("释放内存失败，nRet = %d", nRet);
		}
		System.out.println();
	}
	
	public void run()
	{
		Menu menu = new Menu();
		menu.Run();
	}
	
	public static void main(String[] args) 
	{
		TestDPSDKMain app=new TestDPSDKMain();
		app.OnCreate();//初始化
		app.OnLogin();//登陆
		/*{
			app.OnLogout();
			
			LoginWithEncryption_Info_t loginInfo = new LoginWithEncryption_Info_t();
			loginInfo.szIp = "10.35.92.116".getBytes();
			loginInfo.nPort = 9000;
			loginInfo.szUsername = "system".getBytes();
			loginInfo.szPassword = "admin123".getBytes();
			loginInfo.nProtocol = dpsdk_protocol_version_e.DPSDK_PROTOCOL_VERSION_II;
			loginInfo.iType = 1;

			int nRet = IDpsdkCore.DPSDK_PreLoginWithEncryption(m_nDLLHandle,loginInfo,10000);
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("szRealm = %s, szNonce = %s.", new String(loginInfo.szRealm).trim(), new String(loginInfo.szNonce).trim());
				System.out.println();
				
				//根据szRealm和szNonce加密，生成认证密码保存到szEncryptedPassword
				loginInfo.szEncryptedPassword = "".getBytes();
				nRet = IDpsdkCore.DPSDK_LoginWithEncryption(m_nDLLHandle,loginInfo,10000);
			}else
			{
				System.out.printf("登录失败，nRet = %d", nRet);
				System.out.println();
			}
			
			Get_RecordStreamUrl_Time_Info_t pRecordStreamUrlInfo = new Get_RecordStreamUrl_Time_Info_t();
			pRecordStreamUrlInfo.szCameraId = "1000000$1$0$0".getBytes();
			pRecordStreamUrlInfo.nSource = 3;
			Date tmStart = new Date(2019-1900,2-1,15,0,0,0);
			Date tmEnd = new Date(2019-1900,2-1,15,12,0,0);
			pRecordStreamUrlInfo.uBeginTime = tmStart.getTime()/1000;
			pRecordStreamUrlInfo.uEndTime = tmEnd.getTime()/1000;
			nRet = IDpsdkCore.DPSDK_GetPlaybackByTimeUrl(m_nDLLHandle, pRecordStreamUrlInfo, 10000);
			
			if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
			{
				System.out.printf("URL = %s, nSessionId = %d, nToken = %d.", new String(pRecordStreamUrlInfo.szUrl).trim(), pRecordStreamUrlInfo.nSessionId, pRecordStreamUrlInfo.nToken);
			}else
			{
				System.out.printf("获取URL失败，nRet = %d", nRet);
			}
			System.out.println();
		}*/
		
		app.LoadAllGroup();//加载组织结构
		
		app.GetGroupStr();//获取组织结构串
		
		//app.GetUserOrgInfo();//获取用户组织信息
		
		//app.GetFtpInfo();//获取ftp信息
		
		//加载组织结构之后，要延时5秒钟左右，等待与各服务模块取得联系
		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		/*
		//平台录像
		app.PlatformRecord();
		
		//订阅所有卡口过车信息
		app.SubscribeALLBayCarInfo();
		
		//订阅区间测速上报
		app.SubscribeAreaSpeedDetectInfo();
		*/
		
		/*
		//按时间下载录像，下载的dav文件可以用大华官网下在的SmartPlayer播放器播放
		@SuppressWarnings("deprecation")
		Date tmStart = new Date(2016-1900,9-1,29,11,57,0);
		@SuppressWarnings("deprecation")
		Date tmEnd = new Date(2016-1900,9-1,29,11,57,20);
		nDownloadSeq = app.StartDownLoadRecordByTime(tmStart.getTime()/1000, tmEnd.getTime()/1000);
		
		//int DownloadSeq = app.StartDownLoadRecordByTime(1475121300, 1475121420);
		try{
			Thread.sleep(180*1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		if(nDownloadSeq > 0)
		{
			app.StopDownLoadRecordByTime(nDownloadSeq);
		}
		*/
		
		/*
		//按时间下载mp4格式录像
		@SuppressWarnings("deprecation")
		Date tmStart = new Date(2018-1900,7-1,31,0,0,0);
		@SuppressWarnings("deprecation")
		Date tmEnd = new Date(2018-1900,7-1,31,0,1,0);
		nDownloadSeq = app.SaveRecordStreamByTime(tmStart.getTime()/1000, tmEnd.getTime()/1000);
		if(nDownloadSeq > 0)
		{
			int nCount = 0;
			while(nCount < 300)
			{
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				nCount++;
				if(bDownloadFinish)
					break;
			}
			
			app.StopDownLoadRecordByTime(nDownloadSeq);
		}*/
		
		//app.GetGPSXML(); //获取gpsXML
		//app.BlindShots();//无预览抓图
		//app.SetAlarm();//打开报警监听,加载组织结构后才能接收到报警信息
//		app.OnQueryAlarm();//查询报警信息
//		app.OnSendOSDInfo();//给摄像头发送信息
		//app.OnGetTvWallList();//获取电视墙列表
		//app.MapTaskToTvWall(28);//电视墙任务上墙
		app.GetExternUrl();//获取视频URL
		String   m_strRealCamareID = "1000078$1$0$21";    //实时通道ID
		app.OnYuntaiStart(m_strRealCamareID, 3, 1);
		app.OnYuntaiStop(m_strRealCamareID, 3, 1);
//		app.OnYuntaiEnd();
		//门禁功能接口 begin
		//app.OnGetLinkResource();//获取门禁绑定的视频源信息
		//app.OnSetDoorCmd();//远程开门
		//app.ClearDevAlarm();//报警复位
		//门禁功能接口 end
		/*int nRealSeq = app.GetReal();//请求实时码流，成功后视频码流进入m_MediaCB函数中
		
		try{
			Thread.sleep(300000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		//停止拉实时码流
		if( nRealSeq > 0)
		{
			app.CloseReal(nRealSeq);
		}*/
		
		//人数统计
		//app.QueryPersonCount();
		
		//获取报警预案
//		app.GetAlarmScheme();
		
		app.run();
		

		//if(nRealSeq > 0)
		//{
		//	app.CloseReal(nRealSeq);//关闭实时码流
		//}
		app.OnLogout();
		app.OnDestroy();
	}
}
