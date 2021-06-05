package com.yqh.eurekaconsumer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Auther：yqh
 * @Date：2021/5/30
 * @Description：创建
 * @Version：1.0
 */
@RestController
public class EurekaConsumerController {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    EurekaClient eurekaClient;
    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/getHi")
    public String getHi() {

        return "hi";
    }

    @GetMapping("/getServer")
    public String getServer() {

        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
        }


        return "hi";
    }

    @GetMapping("/getServer1")
    public Object getServer1() {

        return discoveryClient.getInstances("provider");
    }

    @GetMapping("/getServer2")
    public Object getServer2() {

        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        for (ServiceInstance serviceInstance : provider) {
            System.out.println(ToStringBuilder.reflectionToString(serviceInstance));

        }

        return provider;

    }

    @GetMapping("/getServer3")
    public Object getServer3() {

        List<InstanceInfo> instancesByVipAddress = eurekaClient.getInstancesByVipAddress("provider", false);
        for (InstanceInfo byVipAddress : instancesByVipAddress) {
            String url = "http://" + byVipAddress.getHostName() + ":" + byVipAddress.getPort() + "/getHi";
            System.out.println(url);

            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject(url, String.class);
            System.out.println(forObject);

        }

        return instancesByVipAddress;

    }

    @GetMapping("/getServer4")
    public Object getServer4() {

        String url = "http://provider/getHi";
        System.out.println(url);

        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println(forObject);


        return forObject;

    }


}
