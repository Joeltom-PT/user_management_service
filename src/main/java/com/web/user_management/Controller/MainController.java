package com.web.user_management.Controller;

import com.web.user_management.Model.User;
import com.web.user_management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping("/login")
    public String loginpage(Principal principal) {
        if (principal != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains("ADMIN")) {

                return "redirect:/admin";
            } else if (roles.contains("USER")) {

                return "redirect:/user";
            }
        }
        return "user/login";
    }

    @GetMapping("/signup")
    private String signuppage(Principal principal) {
        if (principal != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains("ADMIN")) {

                return "redirect:/admin";
            } else if (roles.contains("USER")) {

                return "redirect:/user";
            }
        }

        return "user/signup";
    }


    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user) {
        if (userService.usernameExist(user.getUsername())) {
            return "redirect:/signup?userNameError";
        }
        if (userService.emailExist(user.getEmail())) {
            return "redirect:/signup?emailError";
        }
        userService.saveUser(user);
        return "redirect:/login?success";
    }


}
