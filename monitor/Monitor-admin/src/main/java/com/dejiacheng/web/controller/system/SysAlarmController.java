package com.dejiacheng.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.web.websocket.domain.WebSocketServer;

@Controller
public class SysAlarmController extends BaseController{

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
	@GetMapping("/alert_admin/list")
	public AjaxResult alertAdminList() {
		return AjaxResult.success();
	}
	
//	@GetMapping("/alarm/sendOne")
//	@ResponseBody
//	public String sendone(@RequestParam(required=true) String message) {
//		WebSocketServer.sendInfo(message, "");
//		return "ok";
//	}
	
} 
