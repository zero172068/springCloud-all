package com.yqh.eurekaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther：yqh
 * @Date：2021/5/30
 * @Description：创建
 * @Version：1.0
 */
@RestController
public class EurekaConsumerBlController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;


    @GetMapping("/clientbl")
    public String clientbl() {

//        ServiceInstance instance = loadBalancerClient.choose("provider");
//        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/getHi";
        String url = "http://provider/getHi";
        String respStr = restTemplate.getForObject(url, String.class);

        return respStr;

    }


}
