package com.dejiacheng.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dejiacheng.system.mapper.SysDeviceInfoMapper;
import com.dejiacheng.system.domain.SysDeviceInfo;
import com.dejiacheng.system.service.ISysDeviceInfoService;
import com.dejiacheng.common.core.text.Convert;

/**
 * 系统设备信息Service业务层处理
 * 
 * @author yukai
 * @date 2019-09-24
 */
@Service
public class SysDeviceInfoServiceImpl implements ISysDeviceInfoService 
{
    @Autowired
    private SysDeviceInfoMapper sysDeviceInfoMapper;

    /**
     * 查询系统设备信息
     * 
     * @param deviceId 系统设备信息ID
     * @return 系统设备信息
     */
    @Override
    public SysDeviceInfo selectSysDeviceInfoById(Long deviceId)
    {
        return sysDeviceInfoMapper.selectSysDeviceInfoById(deviceId);
    }

    /**
     * 查询系统设备信息列表
     * 
     * @param sysDeviceInfo 系统设备信息
     * @return 系统设备信息
     */
    @Override
    public List<SysDeviceInfo> selectSysDeviceInfoList(SysDeviceInfo sysDeviceInfo)
    {
        return sysDeviceInfoMapper.selectSysDeviceInfoList(sysDeviceInfo);
    }

    /**
     * 新增系统设备信息
     * 
     * @param sysDeviceInfo 系统设备信息
     * @return 结果
     */
    @Override
    public int insertSysDeviceInfo(SysDeviceInfo sysDeviceInfo)
    {
        return sysDeviceInfoMapper.insertSysDeviceInfo(sysDeviceInfo);
    }

    /**
     * 修改系统设备信息
     * 
     * @param sysDeviceInfo 系统设备信息
     * @return 结果
     */
    @Override
    public int updateSysDeviceInfo(SysDeviceInfo sysDeviceInfo)
    {
        return sysDeviceInfoMapper.updateSysDeviceInfo(sysDeviceInfo);
    }

    /**
     * 删除系统设备信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysDeviceInfoByIds(String ids)
    {
        return sysDeviceInfoMapper.deleteSysDeviceInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统设备信息信息
     * 
     * @param deviceId 系统设备信息ID
     * @return 结果
     */
    public int deleteSysDeviceInfoById(Long deviceId)
    {
        return sysDeviceInfoMapper.deleteSysDeviceInfoById(deviceId);
    }
}
