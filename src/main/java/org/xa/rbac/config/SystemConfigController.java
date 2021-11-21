package org.xa.rbac.config;

import org.xa.rbac.model.Menu;
import org.xa.rbac.model.RespBean;
import org.xa.rbac.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    MenuService menuService;
    @GetMapping("/menu")
    public List<Menu> getMenusByUserId() {
        return menuService.getMenusByUserId();
    }
}