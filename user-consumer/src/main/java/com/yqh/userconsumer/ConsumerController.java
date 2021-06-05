package com.yqh.userconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther：yqh
 * @Date：2021/5/31
 * @Description：创建
 * @Version：1.0
 */
@RestController
public class ConsumerController {

    @Autowired
    UserApi userApi;
    @Autowired
    UserMan userMan;

    @GetMapping("/alive")
    public String alive(){

        return userApi.alive();
    }

    @GetMapping("/getUser")
    public String getUser(){

        return userMan.getUser();
    }

    @RequestMapping("/id")
    public String getId(String id){
        return userMan.getId(id);
    }
}
