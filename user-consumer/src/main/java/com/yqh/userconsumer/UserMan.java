package com.yqh.userconsumer;

import com.yqh.userapi.UserManApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther：yqh
 * @Date：2021/5/31
 * @Description：创建
 * @Version：1.0
 */
@FeignClient(name = "user-provider" )
public interface UserMan extends UserManApi {

    /**
     *
     * @RequestMapping("/getId") 必须写，是给feign组装请求用的
     * @RequestParam("id") 必须写，feign的要求,这样参数才能传进来
     */
    @RequestMapping("/getId")
    public String getId(@RequestParam("id") String id);

}
