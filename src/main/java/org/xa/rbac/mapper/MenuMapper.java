package org.xa.rbac.mapper;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xa.rbac.model.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getMenusByUserId(Integer uid);

    List<Menu> getAllMenusWithRole();

    List<Menu> getAllMenus();

    List<Integer> getMidsByRid(Integer rid);
    
    List<HashMap<String,String>> getAllMenusToRoles();
}