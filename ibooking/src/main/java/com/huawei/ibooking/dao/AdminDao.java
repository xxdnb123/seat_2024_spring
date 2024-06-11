package com.huawei.ibooking.dao;

import com.huawei.ibooking.mapper.AdminMapper;
import com.huawei.ibooking.model.AdminDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminDao {
    @Autowired
    private AdminMapper adminMapper;

    public List<AdminDO> getManagerByName(final String name) {
        return adminMapper.getManagerByName(name);
    }
}
