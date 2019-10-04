package com.dejiacheng.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dejiacheng.common.annotation.Log;
import com.dejiacheng.common.enums.BusinessType;
import com.dejiacheng.system.domain.SysDeviceInfo;
import com.dejiacheng.system.service.ISysDeviceInfoService;
import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.utils.poi.ExcelUtil;
import com.dejiacheng.common.core.page.TableDataInfo;

/**
 * 系统设备信息Controller
 * 
 * @author yukai
 * @date 2019-09-24
 */
@Controller
@RequestMapping("/system/deviceinfo")
public class SysDeviceInfoController extends BaseController
{
    private String prefix = "system/deviceinfo";

    @Autowired
    private ISysDeviceInfoService sysDeviceInfoService;

    @RequiresPermissions("system:deviceinfo:view")
    @GetMapping()
    public String deviceinfo()
    {
        return prefix + "/deviceinfo";
    }

    /**
     * 查询系统设备信息列表
     */
    @RequiresPermissions("system:deviceinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDeviceInfo sysDeviceInfo)
    {
        startPage();
        List<SysDeviceInfo> list = sysDeviceInfoService.selectSysDeviceInfoList(sysDeviceInfo);
        return getDataTable(list);
    }

    /**
     * 导出系统设备信息列表
     */
    @RequiresPermissions("system:deviceinfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDeviceInfo sysDeviceInfo)
    {
        List<SysDeviceInfo> list = sysDeviceInfoService.selectSysDeviceInfoList(sysDeviceInfo);
        ExcelUtil<SysDeviceInfo> util = new ExcelUtil<SysDeviceInfo>(SysDeviceInfo.class);
        return util.exportExcel(list, "deviceinfo");
    }

    /**
     * 新增系统设备信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存系统设备信息
     */
    @RequiresPermissions("system:deviceinfo:add")
    @Log(title = "系统设备信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDeviceInfo sysDeviceInfo)
    {
        return toAjax(sysDeviceInfoService.insertSysDeviceInfo(sysDeviceInfo));
    }

    /**
     * 修改系统设备信息
     */
    @GetMapping("/edit/{deviceId}")
    public String edit(@PathVariable("deviceId") Long deviceId, ModelMap mmap)
    {
        SysDeviceInfo sysDeviceInfo = sysDeviceInfoService.selectSysDeviceInfoById(deviceId);
        mmap.put("sysDeviceInfo", sysDeviceInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统设备信息
     */
    @RequiresPermissions("system:deviceinfo:edit")
    @Log(title = "系统设备信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDeviceInfo sysDeviceInfo)
    {
        return toAjax(sysDeviceInfoService.updateSysDeviceInfo(sysDeviceInfo));
    }

    /**
     * 删除系统设备信息
     */
    @RequiresPermissions("system:deviceinfo:remove")
    @Log(title = "系统设备信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysDeviceInfoService.deleteSysDeviceInfoByIds(ids));
    }
}
