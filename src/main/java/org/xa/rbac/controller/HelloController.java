package org.xa.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class 	HelloController {
	@Autowired
	private RedisTemplate redisTemplate;
	@GetMapping("/hello")
	public String hello() {
		ValueOperations<String,String> valueOperations=	redisTemplate.opsForValue();
		valueOperations.set("姓名","徐奥");

		return "hello";
	}
	@GetMapping("/employee/basic/hello")
	public String hello2() {
		System.out.println("aaa");
		return "/employee/basic/hello";
	}
	@GetMapping("/employee/advanced/hello")
	public String hello3() {
		
		return "/employee/advanced/hello";
	}
	@GetMapping("/salary/table/hello")
	public String hello4() {

		return "/salary/table/hello";
	}
}
