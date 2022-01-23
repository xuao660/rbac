package org.xa.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xa.rbac.model.RespBean;
import org.xa.rbac.model.User;
import org.xa.rbac.service.UserService;
import org.xa.rbac.utils.RSAUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/get/")
@RestController
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	RedisTemplate redisTemplate;
	@GetMapping("publickey")
	public RespBean getPublickey(String username) throws Exception {
		Map<String, Object> keys=new HashMap();
		Map<String,Object> data=new HashMap();

		KeyPair keyPair =RSAUtils.getKeyPair();
		//私钥存放到redis
		String priKeyStr=RSAUtils.getPrivateKey(keyPair);
		ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
		valueOperations.set("privatekey",priKeyStr);
		data.put("priKeyStr",priKeyStr);
		//返回公钥
		String pubKeyStr = RSAUtils.getPublicKey(keyPair);
		data.put("publickey",pubKeyStr);
		PublicKey publicKey = RSAUtils.string2PublicKey(pubKeyStr);
		byte[] enStr=RSAUtils.publicEncrypt("feifei".getBytes("UTF-8"),publicKey);
		PrivateKey privateKey = RSAUtils.string2PrivateKey(priKeyStr);

		byte[] deStr=RSAUtils.privateDecrypt(enStr,privateKey);
		System.out.println("密文："+new String(enStr,"UTF-8"));
		data.put("密文：",new String(enStr));
		data.put("解密：",new String(deStr));
		RespBean ok=RespBean.ok("获取成功",data);
		System.out.println("公钥："+pubKeyStr+" "+pubKeyStr.getBytes().length);
		System.out.println("私钥："+priKeyStr+" "+priKeyStr.getBytes().length);


		return ok;
	}


	@PostMapping("login")
	public RespBean login(String username, String password, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> data = new HashMap();
		String str = null;
		RespBean errorBean = RespBean.build();
		RestTemplate restTemplate = new RestTemplate();


		System.out.println("私钥解密前的密码："+username + " ==== " + password);
		System.out.println(username.getBytes().length+"  "+password.getBytes().length);
		ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
		String privateKsyStr=valueOperations.get("privatekey");
		PrivateKey privateKey=RSAUtils.string2PrivateKey(privateKsyStr);

		byte[] u=RSAUtils.privateDecrypt(Base64.getDecoder().decode(username),privateKey);
		byte[] p=RSAUtils.privateDecrypt(Base64.getDecoder().decode(password),privateKey);
		username=new String(u);
		password=new String(p);
		System.out.println("私钥解密后的密码："+username + " ==== " + password);
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
