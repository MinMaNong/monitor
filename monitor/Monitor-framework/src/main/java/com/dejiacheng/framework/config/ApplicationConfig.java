package com.dejiacheng.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author pcy
 * 程序注解配置
 * @version 创建时间:2019年9月3日-下午4:45:59
 */
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.dejiacheng.*.mapper")
public class ApplicationConfig
{

}
