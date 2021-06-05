package com.springboot.redistest;

import com.springboot.redistest.redisapi.RedisApiTest;
import com.springboot.redistest.redisapi.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedistestApplication.class)
public class RedistestApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	RedisApiTest redisApiTest;

    @Test
    public void redisTest() {
		redisTemplate.opsForValue().set("k1","sasas");
		System.out.println(redisTemplate.opsForValue().get("k1"));
		String s = redisApiTest.testRedis();
		System.err.println(s);
	}
	@Test
    public void testStringRedis() {
		String s = redisApiTest.testStringRedis();
		System.err.println(s);
	}

	@Test
    public void testLow_API_Redis() {
		String s = redisApiTest.testLow_API_Redis();
		System.err.println(s);
	}

	@Test
    public void testString_HashRedis() {
		Map map = redisApiTest.testString_HashRedis();
		System.err.println(map);
	}
	@Test
    public void testString_HashRedis1() {
		Person person = redisApiTest.testString_HashRedis1();

		System.err.println(person.getAge());
	}
	@Test
    public void testString_HashRedis2() {
		Person person = redisApiTest.testString_HashRedis2();

		System.err.println(person.getAge());
	}

	@Test
    public void testString_Redis3() {
		String s = redisApiTest.testString_Redis3();

		System.err.println(s);
	}

	@Test
    public void test_API_Redis_publish() {
		redisApiTest.test_API_Redis_publish("kk2");

	}

	@Test
    public void test_API_Redis_subcribe() throws Exception {
		redisApiTest.test_API_Redis_subcribe("kk3");
		while (true){
			Thread.sleep(2000);
			redisApiTest.test_API_Redis_publish("kk3");
		}
	}

}
