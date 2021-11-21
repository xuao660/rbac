package org.xa.rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xa.rbac.config.BASE64Util;
import org.xa.rbac.model.RespBean;
import org.xa.rbac.model.User;
import org.xa.rbac.service.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("/get/")
@RestController
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("login")
	public RespBean login(String username, String password, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> data = new HashMap();
		String str = null;
		RespBean errorBean = RespBean.build();
		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println(username + " ==== " + password);
		username=BASE64Util.decode(new StringBuilder(username).reverse().toString());
		password=BASE64Util.decode(new StringBuilder(password).reverse().toString());

		System.out.println(username + " ==== " + password);
		System.out.println(username+"  by加密之后："+passwordEncoder.encode(password));
		User user = (User) userService.loadUserByUsername(username);
		if(user==null || !passwordEncoder.matches(password, user.getPassword())) {
			errorBean.setMsg("用户名或密码输入错误！");
			errorBean.setStatus(500);
			return errorBean;
		}
		

		MultiValueMap<String, String> map = new LinkedMultiValueMap();
		map.add("client_id", "client_2");
		map.add("client_secret", "123");
		map.add("grant_type", "password");
		map.add("username", username);
		map.add("password", password);
		map.add("scope", "select");
		Map<String, String> resp = restTemplate.postForObject("http://localhost:8088/oauth/token", map, Map.class);
		if (resp == null || resp.get("access_token") == null) {
			System.out.println("errorBean");
			errorBean.setMsg("token获取失败！");
			errorBean.setStatus(500);
//			try {
//				str=new ObjectMapper().writeValueAsString(errorBean);
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			System.out.println(str);
//			out.write(str);
//            out.flush();
//            out.close();
			return errorBean;
		}

		user.setPassword(null);
		System.out.println("resp" + resp);
		data.put("token", resp);
		data.put("user", user);
		RespBean ok = RespBean.ok("登陆成功!", data);


		return ok;
	}
}
