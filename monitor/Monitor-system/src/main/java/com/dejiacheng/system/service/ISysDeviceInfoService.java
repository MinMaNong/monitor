package com.dejiacheng.system.service;

import com.dejiacheng.system.domain.SysDeviceInfo;
import java.util.List;

/**
 * 系统设备信息Service接口
 * 
 * @author yukai
 * @date 2019-09-24
 */
public interface ISysDeviceInfoService 
{
    /**
     * 查询系统设备信息
     * 
     * @param deviceId 系统设备信息ID
     * @return 系统设备信息
     */
    public SysDeviceInfo selectSysDeviceInfoById(Long deviceId);

    /**
     * 查询系统设备信息列表
     * 
     * @param sysDeviceInfo 系统设备信息
     * @return 系统设备信息集合
     */
    public List<SysDeviceInfo> selectSysDeviceInfoList(SysDeviceInfo sysDeviceInfo);

    /**
     * 新增系统设备信息
     * 
     * @param sysDeviceInfo 系统设备信息
     * @return 结果
     */
    public int insertSysDeviceInfo(SysDeviceInfo sysDeviceInfo);

    /**
     * 修改系统设备信息
     * 
     * @param sysDeviceInfo 系统设备信息
     * @return 结果
     */
    public int updateSysDeviceInfo(SysDeviceInfo sysDeviceInfo);

    /**
     * 批量删除系统设备信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDeviceInfoByIds(String ids);

    /**
     * 删除系统设备信息信息
     * 
     * @param deviceId 系统设备信息ID
     * @return 结果
     */
    public int deleteSysDeviceInfoById(Long deviceId);
}
