package com.yqh.eurekaprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther：yqh
 * @Date：2021/5/30
 * @Description：创建
 * @Version：1.0
 */
@RestController
public class EurekaProviderController {
    @Autowired
    HealthStatusService statusService;

    @Value("${server.port}")
    String port;

    @GetMapping("/getHi")
    public String getHi() {

        return "hi"+port;
    }

    @GetMapping("/getMap")
    public Map<String,Object> getMap() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("port",port);
        return stringObjectHashMap;
    }

    @GetMapping("/getMap1")
    public Map<String,Object> getMap1(@RequestParam("name") String name) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("port",port);
        stringObjectHashMap.put("name",name);
        return stringObjectHashMap;
    }

    @PostMapping("/postMap")
    public Map<String,Object> postMap(@RequestBody Map name) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("port",port);
        stringObjectHashMap.put("name",name.get("name"));
        return stringObjectHashMap;
    }


    @GetMapping("/health")
    public Object health(@RequestParam("status") Boolean status) {

        statusService.setStatus(status);

        return statusService.isStatus();

    }

}
