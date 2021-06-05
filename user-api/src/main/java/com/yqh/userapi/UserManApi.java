package com.yqh.userapi;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther：yqh
 * @Date：2021/5/31
 * @Description：创建
 * @Version：1.0
 */
@RequestMapping("/user")
public interface UserManApi {

    @GetMapping("/getUser")
    public String getUser();
}
