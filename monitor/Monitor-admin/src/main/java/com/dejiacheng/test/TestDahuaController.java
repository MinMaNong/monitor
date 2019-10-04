package com.dejiacheng.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dejiacheng.system.service.ISysOperateService;

@Controller
@RequestMapping("/dahua/operate")
public class TestDahuaController {
	
	@Autowired
    private ISysOperateService operateService;
	
	//页面请求
	@GetMapping()
	public String Dahua() {
		System.out.println("////////////////////////////////////成功////////////////////");
		operateService.OnCreate();
		operateService.OnLogin();
		operateService.LoadAllGroup();
		return "dahua";
	}
}
