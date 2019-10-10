package com.dejiacheng.web.controller.system;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.core.page.TableDataInfo;
import com.dejiacheng.common.json.JSON;
import com.dejiacheng.common.json.JSONObject;
import com.dejiacheng.framework.util.ShiroUtils;
import com.dejiacheng.system.domain.SysChannelInfo;
import com.dejiacheng.system.domain.SysUser;
import com.dejiacheng.system.service.ISysChannelInfoService;

import io.swagger.annotations.Api;
<<<<<<< HEAD
import io.swagger.annotations.ApiOperation;

/**
 * @author pcy
 * 监控系统主界面
 * @version 创建时间:2019年9月28日-下午9:52:29
 */
@Api("监控系统主界面")
@Controller
//@RequestMapping("/panoramaPreview")
public class SysPanoramaPreviewController extends BaseController{
	
	@Autowired
	private ISysChannelInfoService channelInfoService;
	
	@ApiOperation("跳转监控系统主界面")
	@GetMapping("/panoramaPreview")
	public String panoramaPreview() {
		 // 取身份信息
		ModelMap mmap = new ModelMap();
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user", user);
		return "panoramaPreview";
	}
	
	@ApiOperation("获取通道信息列表")
	@PostMapping("/panoramaPreview/list")
    @ResponseBody
    public AjaxResult panoramaList() {
		SysChannelInfo sysChannelInfo = new SysChannelInfo();
		return AjaxResult.success(channelInfoService.selectSysChannelInfoList(sysChannelInfo));
	}
	
//	@ApiOperation("注销登录")
	
	
}
