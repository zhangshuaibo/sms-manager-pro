package com.roadm.manager.service.impl;

import com.roadm.manager.mapper.SystemMapper;
import com.roadm.manager.service.SystemService;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhangshuaibo
 * Date: 2019-06-10
 * Time: 10:06
 */
public class SystemServiceImpl implements SystemService {

    private SystemMapper systemMapper;

    public SystemMapper getSystemMapper() {
        return systemMapper;
    }

    public void setSystemMapper(SystemMapper systemMapper) {
        this.systemMapper = systemMapper;
    }

    @Override
    public List<Map<String, Object>> getData() {

        return systemMapper.getData();
    }
}
