package com.dejiacheng.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.dejiacheng.common.annotation.Log;
import com.dejiacheng.common.enums.BusinessType;
import com.dejiacheng.common.exception.dahua.DahuaException;
import com.dejiacheng.system.service.ISysExceptionService;
import com.dejiacheng.system.service.ISysOperateService;

@Service
public class SearchReceive implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
    private ISysOperateService operateService;
	@Autowired
	private ISysExceptionService exceptionService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null) {
			try {
				int nRet = -1;
				nRet = operateService.OnCreate();
				if(nRet == 0) {
					nRet = operateService.OnLogin();
					if(nRet == 0) {
						nRet = operateService.LoadAllGroup();
						if(nRet == 0) {
							operateService.updateInfo();
						}else {
							exceptionService.selectSysExceptionByCode(nRet+""); 
							throw new DahuaException(nRet+"",
									exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
						}
					}else {
						exceptionService.selectSysExceptionByCode(nRet+""); 
						throw new DahuaException(nRet+"",
								exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
					}
				}else {
					exceptionService.selectSysExceptionByCode(nRet+""); 
					throw new DahuaException(nRet+"",
							exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
				}
			} catch (DahuaException e) {
				e.printStackTrace();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
