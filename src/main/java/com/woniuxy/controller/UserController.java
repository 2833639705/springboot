package com.woniuxy.springboot01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/info")
    // modelMap只能带数据
    public String info(ModelMap modelMap) {
        modelMap.addAttribute("name", "zhangsan");
        modelMap.addAttribute("gender", "男");
        modelMap.addAttribute("age", 10);
        return "test.html";
    }
}
