package com.guonl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guonl
 * Date 2020/12/27 10:19 AM
 * Description:
 */
@Slf4j
@RestController
public class OrderController {

    @GetMapping("/allow")
    public String allow(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登陆用户：" + name);
        model.addAttribute("name",name);
        return "home";
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name + ",有ROLE_ADMIN角色";
    }

    @RequestMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name + ",有ROLE_USER角色";
    }


}
