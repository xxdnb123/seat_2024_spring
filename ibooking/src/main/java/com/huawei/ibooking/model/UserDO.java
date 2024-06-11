package com.huawei.ibooking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDO {
    private String number;
    private String password;

    public UserDO(String number) {
        this.number = number;
    }
}
