package org.xa.rbac.mapper;

import org.apache.ibatis.annotations.Param;
import org.xa.rbac.model.Role;
import org.xa.rbac.model.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User loadUserByUsername(String username);

    List<Role> getUserRolesById(Integer id);

    List<User> getAllUsers(@Param("uid") Integer uid, @Param("keywords") String keywords);

    List<User> getAllUsersExceptCurrentUser(Integer id);

    Integer updatePasswd(@Param("uid") Integer uid, @Param("encodePass") String encodePass);

    Integer updateUserface(@Param("url") String url, @Param("id") Integer id);
}