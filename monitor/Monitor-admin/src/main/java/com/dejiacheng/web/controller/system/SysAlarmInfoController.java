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
import org.springframework.web.bind.annotation.RestController;

import com.dejiacheng.common.annotation.Log;
import com.dejiacheng.common.enums.BusinessType;
import com.dejiacheng.system.domain.SysAlarmInfo;
import com.dejiacheng.system.service.ISysAlarmInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.utils.poi.ExcelUtil;
import com.dejiacheng.common.core.page.TableDataInfo;

/**
 * 系统报警信息Controller
 * 
 * @author yukai
 * @date 2019-10-06
 */
@Controller
@RequestMapping("/system/alarminfo")
public class SysAlarmInfoController extends BaseController
{
    private String prefix = "system/alarminfo";

    @Autowired
    private ISysAlarmInfoService sysAlarmInfoService;

    @RequiresPermissions("system:alarminfo:view")
    @GetMapping()
    public String alarminfo()
    {
        return prefix + "/alarminfo";
    }

    /**
     * 查询系统报警信息列表
     */
    @RequiresPermissions("system:alarminfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysAlarmInfo sysAlarmInfo)
    {
        startPage();
        List<SysAlarmInfo> list = sysAlarmInfoService.selectSysAlarmInfoList(sysAlarmInfo);
        return getDataTable(list);
    }

    /**
     * 导出系统报警信息列表
     */
    @RequiresPermissions("system:alarminfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysAlarmInfo sysAlarmInfo)
    {
        List<SysAlarmInfo> list = sysAlarmInfoService.selectSysAlarmInfoList(sysAlarmInfo);
        ExcelUtil<SysAlarmInfo> util = new ExcelUtil<SysAlarmInfo>(SysAlarmInfo.class);
        return util.exportExcel(list, "alarminfo");
    }

    /**
     * 新增系统报警信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存系统报警信息
     */
    @RequiresPermissions("system:alarminfo:add")
    @Log(title = "系统报警信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysAlarmInfo sysAlarmInfo)
    {
        return toAjax(sysAlarmInfoService.insertSysAlarmInfo(sysAlarmInfo));
    }

    /**
     * 修改系统报警信息
     */
    @GetMapping("/edit/{alarmId}")
    public String edit(@PathVariable("alarmId") Long alarmId, ModelMap mmap)
    {
        SysAlarmInfo sysAlarmInfo = sysAlarmInfoService.selectSysAlarmInfoById(alarmId);
        mmap.put("sysAlarmInfo", sysAlarmInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统报警信息
     */
    @RequiresPermissions("system:alarminfo:edit")
    @Log(title = "系统报警信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysAlarmInfo sysAlarmInfo)
    {
        return toAjax(sysAlarmInfoService.updateSysAlarmInfo(sysAlarmInfo));
    }

    /**
     * 删除系统报警信息
     */
    @RequiresPermissions("system:alarminfo:remove")
    @Log(title = "系统报警信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysAlarmInfoService.deleteSysAlarmInfoByIds(ids));
    }
}
