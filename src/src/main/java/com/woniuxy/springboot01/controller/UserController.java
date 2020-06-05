package com.woniuxy.springboot01.controller;

import com.woniuxy.springboot01.entity.User;
import com.woniuxy.springboot01.resolver.CustomExceptionResolver;
import com.woniuxy.springboot01.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends CustomExceptionResolver {

    @Autowired
    UserService userService;

    @RequestMapping("/info")
    // modelMap只能带数据
    public String info(ModelMap modelMap) {
        modelMap.addAttribute("name", "zhangsan");
        modelMap.addAttribute("gender", "男");
        modelMap.addAttribute("age", 10);
        modelMap.addAttribute("isBoy", "true");
        modelMap.addAttribute("nickname", "小强");
        List<User> users = new ArrayList<>();
        users.add(new User(1, "zs", "123"));
        users.add(new User(2, "ls", "123"));
        users.add(new User(3, "ww", "123"));
        modelMap.addAttribute("users", users);
        return "/test.html";
    }

    @ResponseBody
    @RequestMapping("/del")
    public String del(int uid) {
        System.out.println(uid);
        return "OK";
    }

    @RequiresRoles("student")
    @ResponseBody
    @RequestMapping("/all")
    public List<User> all() {
        return userService.all();
    }

    @RequestMapping("/login")
    public String login(User user) {
        System.out.println("user=" + user);
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            user.setPwd(new SimpleHash("MD5", user.getPwd(), user.getUname(), 1024).toString());
            System.out.println("login=" + user.getPwd());
            // token
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUname(), user.getPwd());
            try {
                subject.login(token);
                System.out.println("成功");
                return "/main.html";
            } catch (Exception e) {
                e.printStackTrace();
                return "/error.html";
            }
        }
        return "/main.html";
    }

}
