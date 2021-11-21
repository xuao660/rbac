package org.xa.rbac.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xa.rbac.mapper.*;
import org.xa.rbac.model.User;
import org.xa.rbac.utils.UserUtils;

@Service
public class UserService implements UserDetailsService{

		@Autowired
	    UserMapper userMapper;
	    @Autowired
	    UserRoleMapper userRoleMapper;
	    
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userMapper.loadUserByUsername(username);
	        System.out.println("3333333333333");
	        if (user == null) {
	           return null;
	        }
	        user.setRoles(userMapper.getUserRolesById(user.getId()));
	        System.out.println("return user");
	        return user;
	    }

	    public List<User> getAllUsers(String keywords) {
	        return userMapper.getAllUsers(UserUtils.getCurrentUser().getId(),keywords);
	    }

	    public Integer updateUser(User user) {
	        return userMapper.updateByPrimaryKeySelective(user);
	    }

	    @Transactional
	    public boolean updateUserRole(Integer uid, Integer[] rids) {
	        userRoleMapper.deleteByUid(uid);
	        if(rids==null) {
	        	return true;
	        }
	        return userRoleMapper.addRole(uid, rids) == rids.length;
	    }

	    public Integer deleteUserById(Integer id) {
	        return userMapper.deleteByPrimaryKey(id);
	    }

	    public List<User> getAllUsersExceptCurrentUser() {
	        return userMapper.getAllUsersExceptCurrentUser(UserUtils.getCurrentUser().getId());
	    }

	    public Integer updateHyById(User user) {
	        return userMapper.updateByPrimaryKeySelective(user);
	    }

	    public boolean updateUserPasswd(String oldpass, String pass, Integer uid) {
	        User user = userMapper.selectByPrimaryKey(uid);
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        if (encoder.matches(oldpass, user.getPassword())) {
	            String encodePass = encoder.encode(pass);
	            Integer result = userMapper.updatePasswd(uid, encodePass);
	            if (result == 1) {
	                return true;
	            }
	        }
	        return false;
	    }

	    public Integer updateUserface(String url, Integer id) {
	        return userMapper.updateUserface(url, id);
	    }
	    public Integer insert(User user) {
	    	String pass=new BCryptPasswordEncoder().encode(user.getPassword());
	    	System.out.println("加密后的密码："+pass);
	    	user.setPassword(pass);
	    	return userMapper.insert(user);
	    }
	}

	

