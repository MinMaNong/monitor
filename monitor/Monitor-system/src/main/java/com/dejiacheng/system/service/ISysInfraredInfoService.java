package com.dejiacheng.system.service;

import com.dejiacheng.system.domain.SysInfraredInfo;
import java.util.List;
import com.dejiacheng.common.core.domain.Ztree;

/**
 * 红外设备信息Service接口
 * 
 * @author dejiacheng
 * @date 2019-10-09
 */
public interface ISysInfraredInfoService 
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
     * 批量删除红外设备信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysInfraredInfoByIds(String ids);

    /**
     * 删除红外设备信息信息
     * 
     * @param infraredId 红外设备信息ID
     * @return 结果
     */
    public int deleteSysInfraredInfoById(Long infraredId);

    /**
     * 查询红外设备信息树列表
     * 
     * @return 所有红外设备信息信息
     */
    public List<Ztree> selectSysInfraredInfoTree();
}
