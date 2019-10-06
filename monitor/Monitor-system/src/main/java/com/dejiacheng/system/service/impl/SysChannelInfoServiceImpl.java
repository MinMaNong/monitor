package com.dejiacheng.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dejiacheng.system.mapper.SysChannelInfoMapper;
import com.dejiacheng.system.domain.SysChannelInfo;
import com.dejiacheng.system.service.ISysChannelInfoService;
import com.dejiacheng.common.core.text.Convert;

/**
 * 系统设备通道信息Service业务层处理
 * 
 * @author yukai
 * @date 2019-10-06
 */
@Service
public class SysChannelInfoServiceImpl implements ISysChannelInfoService 
{
    @Autowired
    private SysChannelInfoMapper sysChannelInfoMapper;

    /**
     * 查询系统设备通道信息
     * 
     * @param channelId 系统设备通道信息ID
     * @return 系统设备通道信息
     */
    @Override
    public SysChannelInfo selectSysChannelInfoById(Long channelId)
    {
        return sysChannelInfoMapper.selectSysChannelInfoById(channelId);
    }

    /**
     * 查询系统设备通道信息列表
     * 
     * @param sysChannelInfo 系统设备通道信息
     * @return 系统设备通道信息
     */
    @Override
    public List<SysChannelInfo> selectSysChannelInfoList(SysChannelInfo sysChannelInfo)
    {
        return sysChannelInfoMapper.selectSysChannelInfoList(sysChannelInfo);
    }

    /**
     * 新增系统设备通道信息
     * 
     * @param sysChannelInfo 系统设备通道信息
     * @return 结果
     */
    @Override
    public int insertSysChannelInfo(SysChannelInfo sysChannelInfo)
    {
        return sysChannelInfoMapper.insertSysChannelInfo(sysChannelInfo);
    }

    /**
     * 修改系统设备通道信息
     * 
     * @param sysChannelInfo 系统设备通道信息
     * @return 结果
     */
    @Override
    public int updateSysChannelInfo(SysChannelInfo sysChannelInfo)
    {
        return sysChannelInfoMapper.updateSysChannelInfo(sysChannelInfo);
    }

    /**
     * 删除系统设备通道信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysChannelInfoByIds(String ids)
    {
        return sysChannelInfoMapper.deleteSysChannelInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统设备通道信息信息
     * 
     * @param channelId 系统设备通道信息ID
     * @return 结果
     */
    public int deleteSysChannelInfoById(Long channelId)
    {
        return sysChannelInfoMapper.deleteSysChannelInfoById(channelId);
    }
}
