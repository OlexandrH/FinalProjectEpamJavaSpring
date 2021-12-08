package ua.oblikchasu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String indexPage(){
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/logout-success")
    public String logoutPage() {
        return "index";
    }

//    @RequestMapping("/error")
//    public String errorPage() {
//        return "error";
//    }
}
