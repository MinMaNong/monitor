package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.domain.BaseEntity;

/**
 * 系统服务信息对象 sys_service_info
 * 
 * @author yukai
 * @date 2019-09-24
 */
public class SysServiceInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 平台ID */
    private Integer serviceId;

    /** 服务IP */
    @Excel(name = "服务IP")
    private String serviceIp;

    /** 端口 */
    @Excel(name = "端口")
    private Integer servicePort;

    /** 平台用户名 */
    @Excel(name = "平台用户名")
    private String serviceName;

    /** 平台密码 */
    @Excel(name = "平台密码")
    private String servicePassword;

    public void setServiceId(Integer serviceId) 
    {
        this.serviceId = serviceId;
    }

    public Integer getServiceId() 
    {
        return serviceId;
    }
    public void setServiceIp(String serviceIp) 
    {
        this.serviceIp = serviceIp;
    }

    public String getServiceIp() 
    {
        return serviceIp;
    }
    public void setServicePort(Integer servicePort) 
    {
        this.servicePort = servicePort;
    }

    public Integer getServicePort() 
    {
        return servicePort;
    }
    public void setServiceName(String serviceName) 
    {
        this.serviceName = serviceName;
    }

    public String getServiceName() 
    {
        return serviceName;
    }
    public void setServicePassword(String servicePassword) 
    {
        this.servicePassword = servicePassword;
    }

    public String getServicePassword() 
    {
        return servicePassword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("serviceId", getServiceId())
            .append("serviceIp", getServiceIp())
            .append("servicePort", getServicePort())
            .append("serviceName", getServiceName())
            .append("servicePassword", getServicePassword())
            .toString();
    }
}
