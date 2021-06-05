package com.springboot.redistest.redisapi.entity;

import java.io.Serializable;

/**
 * @Auther：yqh
 * @Date：2021/5/20
 * @Description：创建
 * @Version：1.0
 */
public class Person implements Serializable {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
