package org.xa.rbac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xa.rbac.model.RespBean;
import org.xa.rbac.model.Role;
import org.xa.rbac.model.User;
import org.xa.rbac.service.RoleService;
import org.xa.rbac.service.UserService;

@RestController
@RequestMapping("/role/basic")
public class RoleController {
	 @Autowired
	 RoleService roleService;
	 @Autowired
	 UserService userService;
	
	   
	 @GetMapping("/roles")
	 public List<Role> getAllRoles() {
		 
	     return roleService.getAllRoles();
	 }
	 @GetMapping("/")
	 public List<User> getAllUsers(String keywords) {
		    
	    return userService.getAllUsers(keywords);
	 }
	
	 @PutMapping("/role")
	 public RespBean updateUserRole(Integer uid, Integer[] rids) {
	 if (userService.updateUserRole(uid, rids)) {		 
	     return RespBean.ok("更新成功!");	     
	    }	 
	 return RespBean.error("更新失败!");
	 }
}
