package org.xa.rbac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xa.rbac.model.RespBean;
import org.xa.rbac.model.Role;
import org.xa.rbac.model.User;
import org.xa.rbac.service.RoleService;
import org.xa.rbac.service.UserService;

@RestController
@RequestMapping("/user/manage")
public class UserManageController {
	@Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @GetMapping("/")
    public List<User> getAllUsers(String keywords) {
    
        return userService.getAllUsers(keywords);
    }
    @PostMapping("/")
    public RespBean addUser(@RequestBody User user) {
        if (userService.insert(user)==1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @PutMapping("/")
    public RespBean updateUser(@RequestBody User user) {
        if (userService.updateUser(user) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    

    @DeleteMapping("/{id}")
    public RespBean deleteUserById(@PathVariable Integer id) {
        if (userService.deleteUserById(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

}
