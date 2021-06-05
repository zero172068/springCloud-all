package com.yqh.eurekaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther：yqh
 * @Date：2021/5/30
 * @Description：创建
 * @Version：1.0
 */
@RestController
public class EurekaConsumerController1 {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;


    @GetMapping("/client1")
    public String client1() {
        String url = "http://provider/getHi";
        String respStr = restTemplate.getForObject(url, String.class);

        return respStr;

    }

    @GetMapping("/client2")
    public Object client2() {
        String url = "http://provider/getHi";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

        return forEntity;

    }

    @GetMapping("/client3")
    public Object client3() {
        String url = "http://provider/getMap";
        Map forObject = restTemplate.getForObject(url, Map.class);

        return forObject;

    }

    @GetMapping("/client4")
    public Object client4() {
        String url = "http://provider/getMap";
        Map forObject = restTemplate.getForObject(url, Map.class);

        return forObject;

    }

    @GetMapping("/client5")
    public Object client5() {
        String url = "http://provider/getMap1?name={1}";
        Map forObject = restTemplate.getForObject(url, Map.class, "yqh");

        return forObject;

    }

    @GetMapping("/client6")
    public Object client6() {
        String url = "http://provider/getMap1?name={name}";
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", "asasas");
        Map forObject = restTemplate.getForObject(url, Map.class, objectObjectHashMap);

        return forObject;

    }

    @GetMapping("/client7")
    public Object client7() {
        String url = "http://provider/postMap";
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", "asasas");
        Map forObject = restTemplate.postForObject(url, objectObjectHashMap, Map.class);

        return forObject;

    }


}
