package com.yqh.userprovider;

import com.yqh.userapi.UserManApi;
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
public class ProviderController implements UserManApi {
    /**
     * 需要写接口文档，如果有别的系统调用如PHP
     * 改造成类似dubbo的jar包引用
     *
     * @return
     */
    @GetMapping("/alive")
    public String alive(){

        return "ok";
    }

    @Override
    public String getUser() {
        return "get user";
    }

    @RequestMapping("/getId")
    public String getId(@RequestParam("id") String id){
        return "asdasd"+id;
    }
}
