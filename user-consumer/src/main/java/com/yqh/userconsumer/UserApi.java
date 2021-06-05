package com.yqh.userconsumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther：yqh
 * @Date：2021/5/31
 * @Description：创建
 * @Version：1.0
 */
// 不结合eureka使用，直接定义一个client名字，用url指定服务器列表
@FeignClient(name = "xxoo" ,url = "http://localhost:93")
//@FeignClient(name = "user-provider" )
public interface UserApi {
    @GetMapping("/alive")
    public String alive();

}
