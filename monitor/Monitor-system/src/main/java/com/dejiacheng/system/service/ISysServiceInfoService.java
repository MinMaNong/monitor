package com.dejiacheng.system.service;

import com.dejiacheng.system.domain.SysServiceInfo;
import java.util.List;

/**
 * 系统服务信息Service接口
 * 
 * @author yukai
 * @date 2019-09-24
 */
public interface ISysServiceInfoService 
{
    /**
     * 查询系统服务信息
     * 
     * @param serviceId 系统服务信息ID
     * @return 系统服务信息
     */
    public SysServiceInfo selectSysServiceInfoById(Integer serviceId);

    /**
     * 查询系统服务信息列表
     * 
     * @param sysServiceInfo 系统服务信息
     * @return 系统服务信息集合
     */
    public List<SysServiceInfo> selectSysServiceInfoList(SysServiceInfo sysServiceInfo);

    /**
     * 新增系统服务信息
     * 
     * @param sysServiceInfo 系统服务信息
     * @return 结果
     */
    public int insertSysServiceInfo(SysServiceInfo sysServiceInfo);

    /**
     * 修改系统服务信息
     * 
     * @param sysServiceInfo 系统服务信息
     * @return 结果
     */
    public int updateSysServiceInfo(SysServiceInfo sysServiceInfo);

    /**
     * 批量删除系统服务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysServiceInfoByIds(String ids);

    /**
     * 删除系统服务信息信息
     * 
     * @param serviceId 系统服务信息ID
     * @return 结果
     */
    public int deleteSysServiceInfoById(Integer serviceId);
}
