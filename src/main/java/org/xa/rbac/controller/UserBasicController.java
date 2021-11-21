package org.xa.rbac.controller;


import org.xa.rbac.model.User;
import org.xa.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user/basic")
public class UserBasicController {
    @Autowired
    UserService userService;
    
    @GetMapping("/")
    public List<User> getAllUsers(String keywords) {
    
        return userService.getAllUsers(keywords);
    }
}
