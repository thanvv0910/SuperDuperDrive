package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.type.UserServiceStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


@Controller
public class UserController {

    private static final String loginSuccess = "loginSuccess";
    private static final String loginFailed = "loginFailed";

    private static final String signUpSuccess = "signUpSuccess";
    private static final String signUpFailed = "signUpFailed";


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signUpView() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpUser(User user, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("signUpUser - username" + user.getUsername());

        UserServiceStatus result = userService.createUser(user);
        System.out.println("signUpUser " + result);
        switch (result) {
            case SUCCESS: {
                redirectAttributes.addFlashAttribute(signUpSuccess, true);
                return "redirect:/login";
            }

            case USERNAME_EXITS: {
                model.addAttribute(signUpSuccess, false);
                model.addAttribute(signUpFailed, "The username already exists");
                return "signup";
            }
            default: {
                model.addAttribute(signUpSuccess, false);
                model.addAttribute(signUpFailed, "There was an error signing you up. Please try again.");
                return "signup";
            }
        }
    }

    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

//    @PostMapping("/login")
//    public String loginUser(User user, Model model){
//        System.out.println("loginUser - username: " + user.getUsername());
//        if (userService.isUsernameAvailable(user.getUsername())) model.addAttribute(loginSuccess, false);
//        else {
//            has
//        }
//        return "login";
//    }
}
