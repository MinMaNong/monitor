package com.dejiacheng.web.controller.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dejiacheng.common.annotation.Log;
import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.enums.BusinessType;
import com.dejiacheng.common.exception.dahua.DahuaException;
import com.dejiacheng.common.utils.StringUtils;
import com.dejiacheng.system.service.ISysChannelInfoService;
import com.dejiacheng.system.service.ISysExceptionService;
import com.dejiacheng.system.service.ISysOperateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

/**
 * @author pcy
 * 功能操作业务处理
 * @version 创建时间:2019年9月23日-下午7:49:33
 */
@Controller
@RequestMapping("/system/operate")
@Api("大华平台操作")
public class SysOperateController extends BaseController{
	
	@Autowired
    private ISysOperateService operateService;
	@Autowired
	private ISysExceptionService exceptionService;
	@Autowired
	private ISysChannelInfoService channelInfoService;
	
	
//	/**
//	 * 大华平台初始化
//	 */
//	@ApiOperation("大华平台初始化")
//	@Log(title = "大华平台初始化", businessType = BusinessType.INITIAL)
//	@PostMapping("/initial")
//	@ResponseBody
//	public AjaxResult initial() {
////		try {
//			int nRet = -1;
//			nRet = operateService.OnCreate();
//			if(nRet == 0) {
//				nRet = operateService.OnLogin();
//				if(nRet == 0) {
//					nRet = operateService.LoadAllGroup();
//					if(nRet == 0) {
//						return success();
//					}else {
//						exceptionService.selectSysExceptionByCode(nRet+""); 
//						throw new DahuaException(nRet+"",
//								exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
//					}
//				}else {
//					exceptionService.selectSysExceptionByCode(nRet+""); 
//					throw new DahuaException(nRet+"",
//							exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
//				}
//			}else {
//				exceptionService.selectSysExceptionByCode(nRet+""); 
//				throw new DahuaException(nRet+"",
//						exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
//			}
////		} 
////		catch (DahuaException e) {
////			String msg = "";
////            if (StringUtils.isNotEmpty(e.getMessage()))
////            {
////                msg = e.getMessage();
////            }
////            return error(msg);
////		} catch (Exception e) {
////			e.printStackTrace();
////			return error(e.getMessage());
////		}
//	}
	
//	/**
//	 * 更新数据库信息
//	 */
//	@GetMapping("/updateInfo")
//	public void updateInfo() {
//		operateService.updateInfo();
//		
//	}
	
	
	/**
	 * 获取HLS方式地址列表
	 */
	@ApiOperation("获取HLS方式地址")
	@ApiImplicitParam(name = "m_strRealCamareID", value = "通道ID", required = true, dataType = "String", paramType = "path")
	@Log(title = "获取视频地址", businessType = BusinessType.GETURL)
	@GetMapping("/getexterurlHLS/{m_strRealCamareID}")
	@ResponseBody
	public String getExterUrlHLS(@PathVariable String m_strRealCamareID) {
		return operateService.GetExternUrlHLS(m_strRealCamareID);
	}
	
	
	/**
	 * 云台操作
	 */
	@ApiOperation("云台操作")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "chennelID", value = "通道ID", required = true, dataType = "String"),
		@ApiImplicitParam(name = "bStop", value = "是否开始", required = true, dataType = "String"),
		@ApiImplicitParam(name = "nDirect", value = "方向", required = true, dataType = "String"),
		@ApiImplicitParam(name = "nStep", value = "步长", required = true, dataType = "String")
		})
//	@ApiImplicitParam(name = "mapp", paramType = "body",examples = @Example({
//		@ExampleProperty(value = "{'m_strRealCamareID':'1(String,通道ID)'}")
////		@ExampleProperty(value = "{'bStop':'1(String,开始-0或结束-1)'}"),
////		@ExampleProperty(value = "{'nDirect':'1(String,方向1-8)'}"),
////		@ExampleProperty(value = "{'nStep':'1(String,步长1-8)'}"),
//	}))
	@Log(title = "云台操作", businessType = BusinessType.YUNTAI)
	@PostMapping("/yuntai")
	@ResponseBody
	public AjaxResult yuntai(@RequestParam String channelId, @RequestParam String bStop, @RequestParam String nDirect, @RequestParam String nStep ) {
		try {
			int nRet = -1;
			String m_strRealCamareID = channelInfoService.selectSysChannelInfoById(new Long((long)Integer.parseInt(channelId))).getChannelcode();
			if(StringUtils.isEmpty(m_strRealCamareID)) {
				return error("云台操作数据获取失败");
			}else {
				if(bStop == "0") {
					nRet = operateService.OnYuntaiStart(m_strRealCamareID, //
							Integer.parseInt(nDirect), Integer.parseInt(nStep));
					if(nRet == 0) {
						return success();
					}else {
						throw new DahuaException(nRet+"",//
								exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
					}
				}else {
					nRet = operateService.OnYuntaiStop(m_strRealCamareID, //
							Integer.parseInt(nDirect), Integer.parseInt(nStep));
					if(nRet == 0) {
						return success();
					}else {
						throw new DahuaException(nRet+"",//
								exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
					}
				}
			}
		} catch (DahuaException e) {
			String msg = "";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
		}
	}
}
