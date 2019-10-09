package com.dejiacheng.system.mapper;

import com.dejiacheng.system.domain.SysInfraredInfo;
import java.util.List;

/**
 * 红外设备信息Mapper接口
 * 
 * @author yukai
 * @date 2019-10-09
 */
public interface SysInfraredInfoMapper 
{
    /**
     * 查询红外设备信息
     * 
     * @param infraredId 红外设备信息ID
     * @return 红外设备信息
     */
    public SysInfraredInfo selectSysInfraredInfoById(Long infraredId);

    /**
     * 查询红外设备信息列表
     * 
     * @param sysInfraredInfo 红外设备信息
     * @return 红外设备信息集合
     */
    public List<SysInfraredInfo> selectSysInfraredInfoList(SysInfraredInfo sysInfraredInfo);

    /**
     * 新增红外设备信息
     * 
     * @param sysInfraredInfo 红外设备信息
     * @return 结果
     */
    public int insertSysInfraredInfo(SysInfraredInfo sysInfraredInfo);

    /**
     * 修改红外设备信息
     * 
     * @param sysInfraredInfo 红外设备信息
     * @return 结果
     */
    public int updateSysInfraredInfo(SysInfraredInfo sysInfraredInfo);

    /**
     * 删除红外设备信息
     * 
     * @param infraredId 红外设备信息ID
     * @return 结果
     */
    public int deleteSysInfraredInfoById(Long infraredId);

    /**
     * 批量删除红外设备信息
     * 
     * @param infraredIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysInfraredInfoByIds(String[] infraredIds);
}
