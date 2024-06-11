package com.huawei.ibooking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdminDO extends UserDO {
    private int id;
    private String name;

    public AdminDO(int id) {
        this.id = id;
    }
}
