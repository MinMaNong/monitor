package com.dejiacheng.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.web.websocket.domain.WebSocketServer;

@Controller
public class SysAlarmController extends BaseController{

	@GetMapping("/alarm")
	public String alarm() {
		WebSocketServer.sendInfo("你好", "");
		return "alarm";
	}
	
	@GetMapping("/alarm/sendOne")
	@ResponseBody
	public String sendone(@RequestParam(required=true) String message) {
		WebSocketServer.sendInfo(message, "");
		return "ok";
	}
	
} 
