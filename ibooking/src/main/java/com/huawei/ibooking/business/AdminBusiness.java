package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.AdminDao;
import com.huawei.ibooking.model.AdminDO;
import com.huawei.ibooking.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AdminBusiness {
    @Autowired
    private AdminDao adminDao;

    public Map<String, String> loginManager(final UserDO userDO) {
        List<AdminDO> managers = adminDao.getManagerByName(userDO.getNumber());
        if (managers.isEmpty()) {
            return null;
        }
        AdminDO adminDO = managers.get(0);
        if (!adminDO.getPassword().equals(userDO.getPassword())) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(adminDO.getId()));
        return map;
    }
}
