package com.zz.blog.controller;

import com.zz.blog.entity.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model, UserVO user) {
        model.addAttribute("user", user);
        return "login";
    }
}
