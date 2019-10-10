package com.dejiacheng.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dejiacheng.common.config.Global;
import com.dejiacheng.common.core.controller.BaseController;
import com.dejiacheng.framework.util.ShiroUtils;
import com.dejiacheng.system.domain.SysMenu;
import com.dejiacheng.system.domain.SysUser;
import com.dejiacheng.system.service.ISysMenuService;

import io.swagger.annotations.ApiOperation;

/**
 * @author pcy
 * 首页 业务处理
 * @version 创建时间:2019年9月5日-下午4:52:14
 */
@Controller
public class SysIndexController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    
    // 系统首页
    @ApiOperation("跳转管理平台页面")
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", Global.getCopyrightYear());
        mmap.put("demoEnabled", Global.isDemoEnabled());
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", Global.getVersion());
        return "main";
    }
}
