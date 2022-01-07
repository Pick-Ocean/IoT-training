package com.ali.amqp.controller;

import com.ali.amqp.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UserController {
    @RequestMapping("/login")
    public String index(){
        return "login";
    }
    @RequestMapping("/shouye")
    public String shouye(){
        return "shouye";
    }
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping("/index")
    public String login(User user){
        return "index";
    }
}
