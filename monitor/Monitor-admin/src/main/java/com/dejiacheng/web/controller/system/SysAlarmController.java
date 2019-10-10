package com.dejiacheng.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.core.domain.BaseEntity;
import com.dejiacheng.system.domain.SysAlarmInfo;
import com.dejiacheng.system.service.ISysAlarmInfoService;
import com.dejiacheng.web.websocket.domain.WebSocketServer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
@Api("报警信息管理")
@Controller
@RequestMapping("/system/alert_admin")
public class SysAlarmController extends BaseController{
	@Autowired
    private ISysAlarmInfoService sysAlarmInfoService;
	/**
	 * 跳转报警页面
	 */
	@GetMapping("/alert_admin")
	public String alertAdmin() {
		return "alert_admin";
	}
	
	/**
	 * 查询报警参数列表
	 */
	@ApiOperation("获取报警信息列表")
	@GetMapping("/list")
	public AjaxResult alertAdminList() {
		SysAlarmInfo sysAlarmInfo = new SysAlarmInfo();
		return AjaxResult.success(sysAlarmInfoService.selectSysAlarmInfoList(sysAlarmInfo));
	}
	
	/**
	 * 获取报警详细信息
	 */
	
	@ApiOperation("获取报警详细")
	@ApiImplicitParam(name = "alarmId", value = "报警ID", required = true, dataType = "long", paramType = "path")
	@GetMapping("/{alarmId}")
	public AjaxResult getalarm(@PathVariable Long alarmId) {
			return AjaxResult.success(sysAlarmInfoService.selectSysAlarmInfoById(alarmId));
	 }
	
//    @ApiOperation("报警新增")
//    @ApiImplicitParam(name = "sysAlarmInfo", value = "报警信息实体" ,dataType = "SysAlarmInfo")
//    @PostMapping("/savealarm")
//	public AjaxResult savealarm(SysAlarmInfo sysAlarmInfo) {
//		return AjaxResult.success(sysAlarmInfoService.insertSysAlarmInfo(sysAlarmInfo));
//	}
//    
//	@ApiOperation("报警更新")
//	@ApiImplicitParam(name = "sysAlarmInfo", value = "报警信息实体" ,dataType = "SysAlarmInfo")
//	@PutMapping("")
//    public AjaxResult updatealarm() {
//		return null;
//    	
//    }
//	@GetMapping("/alarm/sendOne")
//	@ResponseBody
//	public String sendone(@RequestParam(required=true) String message) {
//		WebSocketServer.sendInfo(message, "");
//		return "ok";
//	}
	
	
} 


