package com.web.user_management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @GetMapping("/user")
    public String userpage(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "user/home";
    }
}
