package com.web.seo.controller;

import com.web.seo.entity.User;
import com.web.seo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(name = "error", required = false) String error,
                        @RequestParam(name = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Email atau kata sandi salah");
        }

        if (logout != null) {
            model.addAttribute("message", "Berhasil keluar");
        }
        return "auth/login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        // create model object to store form data
        User user = new User();
        model.addAttribute("user", user);
        return "auth/signup";
    }

    @PostMapping("/signup/save")
    public String singUpSave(@Validated @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        User existingUser = userService.findUserByEmail(user.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "auth/signup";
        }

        userService.saveUser(user);
        return "redirect:/login?success";
    }
}
