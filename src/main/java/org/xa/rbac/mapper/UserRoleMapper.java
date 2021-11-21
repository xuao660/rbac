package org.xa.rbac.mapper;

import org.apache.ibatis.annotations.Param;
import org.xa.rbac.model.UserRole;


public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    void deleteByUid(Integer uid);

    Integer addRole(@Param("uid") Integer uid, @Param("rids") Integer[] rids);
}