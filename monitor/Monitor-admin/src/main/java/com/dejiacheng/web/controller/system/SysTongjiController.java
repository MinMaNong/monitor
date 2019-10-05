package com.dejiacheng.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.system.domain.SysDeviceInfo;
import com.dejiacheng.system.service.ISysDeviceInfoService;

@Controller
public class SysTongjiController {

	@Autowired
	private ISysDeviceInfoService deviceInfoService;
	
	@GetMapping("/tongji")
	public String tongji() {
		return "tongji";
	}
	
	@GetMapping("/tongji/list")
	public AjaxResult tongjiList(SysDeviceInfo sysDeviceInfo) {
		return AjaxResult.success(deviceInfoService.selectSysDeviceInfoList(sysDeviceInfo));
	}
}
