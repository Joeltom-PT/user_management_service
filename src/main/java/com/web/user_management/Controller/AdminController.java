package com.web.user_management.Controller;

import com.web.user_management.Model.User;
import com.web.user_management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {

        List<User> users = userService.findByRole();
        model.addAttribute("users", users);
        model.addAttribute("name", principal.getName());
        return "admin/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String updateUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/update-user";
    }

    @PostMapping("/admin/update/save/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable("id") Long id) {
        if (userService.usernameExist(user.getUsername()) && !user.getUsername().equals(userService.findUserById(id).getUsername())) {
            return "redirect:/admin/update/{id}?userNameError";
        }
        if (userService.emailExist(user.getEmail()) && !user.getEmail().equals(userService.findUserById(id).getEmail())) {
            return "redirect:/admin/update/{id}?emailError";
        }
        userService.updateById(id, user);
        return "redirect:/admin?success";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin?deletesuccess";

    }

    @GetMapping("admin/createuser")
    public String crateUser() {
        return "/admin/create-user";
    }

    @PostMapping("admin/create/user/save")
    public String create(@ModelAttribute User user) {
        userService.saveUser(user);

        return "redirect:/admin?createsuccess";

    }

    @PostMapping("/admin/serch/result")
    public String searchres(Model model, @RequestParam("search") String keyword, Principal principal) {
        List<User> users = userService.findByRole();
        model.addAttribute("users", users);
        model.addAttribute("name", principal.getName());
        List<User> searchresult = userService.listUser(keyword,"USER");
        model.addAttribute("serchresult", searchresult);

        return "admin/admin";
    }

}
