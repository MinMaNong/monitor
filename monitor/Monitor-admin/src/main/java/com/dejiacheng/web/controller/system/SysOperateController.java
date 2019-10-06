package com.dejiacheng.web.controller.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dejiacheng.common.annotation.Log;
import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.enums.BusinessType;
import com.dejiacheng.common.exception.dahua.DahuaException;
import com.dejiacheng.common.utils.StringUtils;
import com.dejiacheng.system.service.ISysExceptionService;
import com.dejiacheng.system.service.ISysOperateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author pcy
 * 功能操作业务处理
 * @version 创建时间:2019年9月23日-下午7:49:33
 */
@Api("大华平台功能接口")
@Controller
@RequestMapping("/system/operate")
public class SysOperateController extends BaseController{
	
	@Autowired
    private ISysOperateService operateService;
	@Autowired
	private ISysExceptionService exceptionService;
	
	
	/**
	 * 大华平台初始化
	 */
	@ApiOperation("大华平台初始化")
	@Log(title = "大华平台初始化", businessType = BusinessType.INITIAL)
	@PostMapping("/initial")
	@ResponseBody
	public AjaxResult initial() {
//		try {
			int nRet = -1;
			nRet = operateService.OnCreate();
			if(nRet == 0) {
				nRet = operateService.OnLogin();
				if(nRet == 0) {
					nRet = operateService.LoadAllGroup();
					if(nRet == 0) {
						return success();
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
//		} 
//		catch (DahuaException e) {
//			String msg = "";
//            if (StringUtils.isNotEmpty(e.getMessage()))
//            {
//                msg = e.getMessage();
//            }
//            return error(msg);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return error(e.getMessage());
//		}
	}
	
	/**
	 * 更新数据库信息
	 */
	@GetMapping("/updateInfo")
	public void updateInfo() {
		operateService.updateInfo();
	}
	
	
	/**
	 * 获取HLS方式地址列表
	 */
	@ApiOperation("获取HLS视频地址")
	@ApiImplicitParam(name = "m_strRealCamareID",value = "摄像头ID", required = true, dataType = "String", paramType = "path")
	@ApiResponses({@ApiResponse(code = 0, message = "responseResult => 'data':{'hlsUrl':'String'}")})
	@Log(title = "获取视频地址", businessType = BusinessType.GETURL)
	@PostMapping("/getexterurlHLS")
	@ResponseBody
	public AjaxResult getExterUrlHLS(@RequestBody String m_strRealCamareID) {
		if(StringUtils.isNull(m_strRealCamareID)) {
			return error("摄像头ID不能为空");
		}
		return AjaxResult.success(operateService.GetExternUrlHLS(m_strRealCamareID));
	}
	
	
	/**
	 * 云台操作
	 */
	@Log(title = "云台操作", businessType = BusinessType.YUNTAI)
	@PostMapping("/yuntai")
	@ResponseBody
	public AjaxResult yuntai(@RequestBody Map<String, String> mapp) {
		try {
			int nRet = -1;
			if(StringUtils.isEmpty(mapp)) {
				return error("云台操作数据获取失败");
			}else {
				if(mapp.get("bStop") == "0") {
					nRet = operateService.OnYuntaiStart(mapp.get("m_strRealCamareID"), //
							Integer.parseInt(mapp.get("nDirect")), Integer.parseInt(mapp.get("nStep")));
					if(nRet == 0) {
						return success();
					}else {
						throw new DahuaException(nRet+"",//
								exceptionService.selectSysExceptionByCode(nRet+"").getDescription(),null);
					}
				}else {
					nRet = operateService.OnYuntaiStop(mapp.get("m_strRealCamareID"), //
							Integer.parseInt(mapp.get("nDirect")), Integer.parseInt(mapp.get("nStep")));
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
