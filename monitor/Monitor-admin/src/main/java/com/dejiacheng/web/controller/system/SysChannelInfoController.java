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
import com.dejiacheng.system.domain.SysChannelInfo;
import com.dejiacheng.system.service.ISysChannelInfoService;
import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.utils.poi.ExcelUtil;
import com.dejiacheng.common.core.page.TableDataInfo;

/**
 * 系统设备通道信息Controller
 * 
 * @author yukai
 * @date 2019-10-06
 */
@Controller
@RequestMapping("/system/channelinfo")
public class SysChannelInfoController extends BaseController
{
    private String prefix = "system/channelinfo";

    @Autowired
    private ISysChannelInfoService sysChannelInfoService;

    @RequiresPermissions("system:channelinfo:view")
    @GetMapping()
    public String channelinfo()
    {
        return prefix + "/channelinfo";
    }

    /**
     * 查询系统设备通道信息列表
     */
    @RequiresPermissions("system:channelinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysChannelInfo sysChannelInfo)
    {
        startPage();
        List<SysChannelInfo> list = sysChannelInfoService.selectSysChannelInfoList(sysChannelInfo);
        return getDataTable(list);
    }

    /**
     * 导出系统设备通道信息列表
     */
    @RequiresPermissions("system:channelinfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysChannelInfo sysChannelInfo)
    {
        List<SysChannelInfo> list = sysChannelInfoService.selectSysChannelInfoList(sysChannelInfo);
        ExcelUtil<SysChannelInfo> util = new ExcelUtil<SysChannelInfo>(SysChannelInfo.class);
        return util.exportExcel(list, "channelinfo");
    }

    /**
     * 新增系统设备通道信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存系统设备通道信息
     */
    @RequiresPermissions("system:channelinfo:add")
    @Log(title = "系统设备通道信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysChannelInfo sysChannelInfo)
    {
        return toAjax(sysChannelInfoService.insertSysChannelInfo(sysChannelInfo));
    }

    /**
     * 修改系统设备通道信息
     */
    @GetMapping("/edit/{channelId}")
    public String edit(@PathVariable("channelId") Long channelId, ModelMap mmap)
    {
        SysChannelInfo sysChannelInfo = sysChannelInfoService.selectSysChannelInfoById(channelId);
        mmap.put("sysChannelInfo", sysChannelInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统设备通道信息
     */
    @RequiresPermissions("system:channelinfo:edit")
    @Log(title = "系统设备通道信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysChannelInfo sysChannelInfo)
    {
        return toAjax(sysChannelInfoService.updateSysChannelInfo(sysChannelInfo));
    }

    /**
     * 删除系统设备通道信息
     */
    @RequiresPermissions("system:channelinfo:remove")
    @Log(title = "系统设备通道信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysChannelInfoService.deleteSysChannelInfoByIds(ids));
    }
}
