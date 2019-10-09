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
import com.dejiacheng.system.domain.SysInfraredInfo;
import com.dejiacheng.system.service.ISysInfraredInfoService;
import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.common.core.domain.AjaxResult;
import com.dejiacheng.common.utils.poi.ExcelUtil;
import com.dejiacheng.common.utils.StringUtils;
import com.dejiacheng.common.core.domain.Ztree;

/**
 * 红外设备信息Controller
 * 
 * @author dejiacheng
 * @date 2019-10-09
 */
@Controller
@RequestMapping("/system/infraredinfo")
public class SysInfraredInfoController extends BaseController
{
    private String prefix = "system/infraredinfo";

    @Autowired
    private ISysInfraredInfoService sysInfraredInfoService;

    @RequiresPermissions("system:infraredinfo:view")
    @GetMapping()
    public String infraredinfo()
    {
        return prefix + "/infraredinfo";
    }

    /**
     * 查询红外设备信息树列表
     */
    @RequiresPermissions("system:infraredinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysInfraredInfo> list(SysInfraredInfo sysInfraredInfo)
    {
        List<SysInfraredInfo> list = sysInfraredInfoService.selectSysInfraredInfoList(sysInfraredInfo);
        return list;
    }

    /**
     * 导出红外设备信息列表
     */
    @RequiresPermissions("system:infraredinfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysInfraredInfo sysInfraredInfo)
    {
        List<SysInfraredInfo> list = sysInfraredInfoService.selectSysInfraredInfoList(sysInfraredInfo);
        ExcelUtil<SysInfraredInfo> util = new ExcelUtil<SysInfraredInfo>(SysInfraredInfo.class);
        return util.exportExcel(list, "infraredinfo");
    }

    /**
     * 新增红外设备信息
     */
    @GetMapping(value = { "/add/{infraredId}", "/add/" })
    public String add(@PathVariable(value = "infraredId", required = false) Long infraredId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(infraredId))
        {
            mmap.put("sysInfraredInfo", sysInfraredInfoService.selectSysInfraredInfoById(infraredId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存红外设备信息
     */
    @RequiresPermissions("system:infraredinfo:add")
    @Log(title = "红外设备信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysInfraredInfo sysInfraredInfo)
    {
        return toAjax(sysInfraredInfoService.insertSysInfraredInfo(sysInfraredInfo));
    }

    /**
     * 修改红外设备信息
     */
    @GetMapping("/edit/{infraredId}")
    public String edit(@PathVariable("infraredId") Long infraredId, ModelMap mmap)
    {
        SysInfraredInfo sysInfraredInfo = sysInfraredInfoService.selectSysInfraredInfoById(infraredId);
        mmap.put("sysInfraredInfo", sysInfraredInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存红外设备信息
     */
    @RequiresPermissions("system:infraredinfo:edit")
    @Log(title = "红外设备信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysInfraredInfo sysInfraredInfo)
    {
        return toAjax(sysInfraredInfoService.updateSysInfraredInfo(sysInfraredInfo));
    }

    /**
     * 删除
     */
    @RequiresPermissions("system:infraredinfo:remove")
    @Log(title = "红外设备信息", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{infraredId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("infraredId") Long infraredId)
    {
        return toAjax(sysInfraredInfoService.deleteSysInfraredInfoById(infraredId));
    }

    /**
     * 选择红外设备信息树
     */
    @GetMapping(value = { "/selectInfraredinfoTree/{infraredId}", "/selectInfraredinfoTree/" })
    public String selectInfraredinfoTree(@PathVariable(value = "infraredId", required = false) Long infraredId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(infraredId))
        {
            mmap.put("sysInfraredInfo", sysInfraredInfoService.selectSysInfraredInfoById(infraredId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载红外设备信息树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = sysInfraredInfoService.selectSysInfraredInfoTree();
        return ztrees;
    }
}
