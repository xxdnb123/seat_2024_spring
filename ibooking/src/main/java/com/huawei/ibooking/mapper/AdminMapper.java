package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.AdminDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<AdminDO> getManagerByName(@Param("name") String name);
}
