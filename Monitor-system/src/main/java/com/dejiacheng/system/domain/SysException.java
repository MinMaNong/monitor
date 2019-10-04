package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.domain.BaseEntity;

/**
 * 系统异常信息对象 sys_exception
 * 
 * @author yukai
 * @date 2019-09-28
 */
public class SysException extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 错误码 */
    @Excel(name = "错误码")
    private String errorcode;

    /** 定义 */
    @Excel(name = "定义")
    private String definition;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setErrorcode(String errorcode) 
    {
        this.errorcode = errorcode;
    }

    public String getErrorcode() 
    {
        return errorcode;
    }
    public void setDefinition(String definition) 
    {
        this.definition = definition;
    }

    public String getDefinition() 
    {
        return definition;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("errorcode", getErrorcode())
            .append("definition", getDefinition())
            .append("description", getDescription())
            .toString();
    }
}
