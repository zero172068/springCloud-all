package com.springboot.redistest.redisapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.redistest.redisapi.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Auther：yqh
 * @Date：2021/5/20
 * @Description：创建
 * @Version：1.0
 */
@Component
public class RedisApiTest {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    StringRedisTemplate myRedisStringTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public String testRedis(){
        redisTemplate.opsForValue().set("kk1","123654");
        Object kk1 = redisTemplate.opsForValue().get("kk1");
        return String.valueOf(kk1);
    }
    public String testStringRedis(){
        stringRedisTemplate.opsForValue().set("kk2","123654");
        Object kk1 = stringRedisTemplate.opsForValue().get("kk2");
        return String.valueOf(kk1);
    }
    public Map testString_HashRedis(){
        HashOperations<String, Object, Object> hashmap = stringRedisTemplate.opsForHash();
        hashmap.put("yqh","name","yqhqq");
        hashmap.put("yqh","age","29");
        Map<Object, Object> yqh = hashmap.entries("yqh");
        return yqh;
    }
    public Person testString_HashRedis1(){
        Person person = new Person();
        person.setAge(12);
        person.setName("asas");
        stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));

        Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(objectMapper,false);
        Map<String, Object> stringObjectMap = jackson2HashMapper.toHash(person);
        stringRedisTemplate.opsForHash().putAll("yqh1",stringObjectMap);
        Map yqh1 = stringRedisTemplate.opsForHash().entries("yqh1");
        Person person1 = objectMapper.convertValue(yqh1, Person.class);
        return person1;
    }

    public Person testString_HashRedis2(){
        Person person = new Person();
        person.setAge(122);
        person.setName("asa33s");

        Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(objectMapper,false);
        Map<String, Object> stringObjectMap = jackson2HashMapper.toHash(person);
        myRedisStringTemplate.opsForHash().putAll("yqh2",stringObjectMap);
        Map yqh1 = myRedisStringTemplate.opsForHash().entries("yqh2");
        Person person1 = objectMapper.convertValue(yqh1, Person.class);
        return person1;
    }

    public String testString_Redis3(){
        myRedisStringTemplate.opsForValue().set("yqh13123","stringObjectMap");
        String yqh13123 = myRedisStringTemplate.opsForValue().get("yqh13123");
        return yqh13123;
    }

    public String testLow_API_Redis(){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.set("low1".getBytes(),"low".getBytes());
        return new String(connection.get("low1".getBytes()));
    }

    public void test_API_Redis_publish(String chnl){
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String format = dateFormatter.format(new Date());
        myRedisStringTemplate.convertAndSend(chnl,format+"-------send from 廖小姐");
    }

    public void test_API_Redis_subcribe(String chnl){
        RedisConnection connection = myRedisStringTemplate.getConnectionFactory().getConnection();
        connection.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                System.out.println(new String(message.getBody())+"-------send from 廖小姐");
            }
        },chnl.getBytes());


    }
}
