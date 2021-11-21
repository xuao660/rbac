package org.xa.rbac.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xa.rbac.model.Employee;
import org.xa.rbac.model.RespPageBean;
import org.xa.rbac.service.EmployeeService;

@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {
	@Autowired
    EmployeeService employeeService;
   

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, Employee employee, Date[] beginDateScope) {
        return employeeService.getEmployeeByPage(page, size, employee,beginDateScope);
    }
}
