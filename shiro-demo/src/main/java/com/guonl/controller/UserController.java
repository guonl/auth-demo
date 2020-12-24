package com.guonl.controller;

import com.guonl.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";
    }

    @RequestMapping("/unauth")
    public String unauthorized() {
        return "unauth";
    }

    //    @RequiresRoles("admin,customer")
    @RequestMapping("/auth")
    public String auth(ModelMap mmap) {
        mmap.put("msg", "测试页面标签");
        return "auth";
    }

    @RequiresRoles("admin")
    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin success";
    }

    @RequiresPermissions("user:edit")
    @GetMapping("/save")
    @ResponseBody
    public String save() {
        return "edit success";
    }

    @RequiresPermissions("user:edit")
    @GetMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }


    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            session.setAttribute("user", user);
            return "index";
        } catch (Exception e) {
            log.error("登录出错", e);
            return "login";
        }
    }
}
