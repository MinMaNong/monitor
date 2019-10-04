package com.dejiacheng.framework.datasource;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.dejiacheng.common.config.datasouce.DynamicDataSourceContextHolder;

/**
 * @author pcy
 * 动态数据源
 * @version 创建时间:2019年9月3日-下午5:15:46
 */
public class DynamicDataSource extends AbstractRoutingDataSource
{
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources)
    {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey()
    {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}