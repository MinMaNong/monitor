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
import com.dejiacheng.system.domain.SysServiceInfo;
import com.dejiacheng.system.service.ISysServiceInfoService;
import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.utils.poi.ExcelUtil;
import com.dejiacheng.common.core.page.TableDataInfo;

/**
 * 系统服务信息Controller
 * 
 * @author yukai
 * @date 2019-09-24
 */
@Controller
@RequestMapping("/system/serviceinfo")
public class SysServiceInfoController extends BaseController
{
    private String prefix = "system/serviceinfo";

    @Autowired
    private ISysServiceInfoService sysServiceInfoService;

    @RequiresPermissions("system:serviceinfo:view")
    @GetMapping()
    public String serviceinfo()
    {
        return prefix + "/serviceinfo";
    }

    /**
     * 查询系统服务信息列表
     */
    @RequiresPermissions("system:serviceinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysServiceInfo sysServiceInfo)
    {
        startPage();
        List<SysServiceInfo> list = sysServiceInfoService.selectSysServiceInfoList(sysServiceInfo);
        return getDataTable(list);
    }

    /**
     * 导出系统服务信息列表
     */
    @RequiresPermissions("system:serviceinfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysServiceInfo sysServiceInfo)
    {
        List<SysServiceInfo> list = sysServiceInfoService.selectSysServiceInfoList(sysServiceInfo);
        ExcelUtil<SysServiceInfo> util = new ExcelUtil<SysServiceInfo>(SysServiceInfo.class);
        return util.exportExcel(list, "serviceinfo");
    }

    /**
     * 新增系统服务信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存系统服务信息
     */
    @RequiresPermissions("system:serviceinfo:add")
    @Log(title = "系统服务信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysServiceInfo sysServiceInfo)
    {
        return toAjax(sysServiceInfoService.insertSysServiceInfo(sysServiceInfo));
    }

    /**
     * 修改系统服务信息
     */
    @GetMapping("/edit/{serviceId}")
    public String edit(@PathVariable("serviceId") Integer serviceId, ModelMap mmap)
    {
        SysServiceInfo sysServiceInfo = sysServiceInfoService.selectSysServiceInfoById(serviceId);
        mmap.put("sysServiceInfo", sysServiceInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统服务信息
     */
    @RequiresPermissions("system:serviceinfo:edit")
    @Log(title = "系统服务信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysServiceInfo sysServiceInfo)
    {
        return toAjax(sysServiceInfoService.updateSysServiceInfo(sysServiceInfo));
    }

    /**
     * 删除系统服务信息
     */
    @RequiresPermissions("system:serviceinfo:remove")
    @Log(title = "系统服务信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysServiceInfoService.deleteSysServiceInfoByIds(ids));
    }
}
