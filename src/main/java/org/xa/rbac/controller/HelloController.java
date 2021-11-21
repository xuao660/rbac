package org.xa.rbac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class 	HelloController {

	@GetMapping("/hello")
	public String hello() {
		
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
