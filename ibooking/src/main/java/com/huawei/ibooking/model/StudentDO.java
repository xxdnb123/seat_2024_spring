package com.huawei.ibooking.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StudentDO extends UserDO {
    private int id;

    private String number;
    private String name;
    private String major;
    private String college;
    private Boolean isBlacklisted;
    private Integer defaultCount;

    public StudentDO(String number, String name, String major, String college) {
        super(number);
        this.number=number;
        this.name = name;
        this.major = major;
        this.college = college;
    }
}
