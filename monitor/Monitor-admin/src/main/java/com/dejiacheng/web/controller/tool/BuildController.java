package com.dejiacheng.web.controller.tool;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dejiacheng.common.core.controller.BaseController;

/**
 * @author pcy
 * build 表单构建
 * @version 创建时间:2019年10月6日-上午1:44:13
 */
@Controller
@RequestMapping("/tool/build")
public class BuildController extends BaseController
{
    private String prefix = "tool/build";

    @RequiresPermissions("tool:build:view")
    @GetMapping()
    public String build()
    {
        return prefix + "/build";
    }
}
