package com.pdb.web.mapper;

import com.pdb.web.config.mybatis.DefaultMapper;
import com.pdb.web.entity.SystemConfig;

public interface SystemConfigMapper extends DefaultMapper {

    SystemConfig selectByPrimaryKey(String key);

}
