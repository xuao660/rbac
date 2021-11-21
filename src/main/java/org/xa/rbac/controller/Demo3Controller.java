package org.xa.rbac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/r")
public class Demo3Controller {

    @GetMapping(value = "/r1")
    public String r1(){
        System.out.println("r1");
        return "访问资源r1";
    }

    @GetMapping(value = "/r2")
    public String r2(){
        System.out.println("r2");
        return "访问资源r2";
    }


    @GetMapping(value = "/r3")
    public String r3(){
        System.out.println("r3");
        return "访问资源r3";
    }
    @GetMapping(value = "/r4")
    public String r4(){
        //获取用户身份信息
        System.out.println("r4");
        return "访问资源r4";
    }

    @GetMapping(value = "/r5")
    public String r5(){
        //获取用户身份信息
        System.out.println("r5");
        return "r5";
    }

    @GetMapping(value = "/r6")
    public String r6(){
        //获取用户身份信息
        System.out.println("r6");
        return "r6";
    }

}
