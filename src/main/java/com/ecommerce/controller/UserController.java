/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.model.UserType;
import com.ecommerce.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jeant
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private IUserService userService;
    
    @GetMapping("/register")
    public String register() {
        return "user/register";
    }
    
    @PostMapping("/save")
    public String save(User user) {
        LOGGER.info("User: {}", user);
        user.setType(UserType.USER);
        userService.save(user);
        return "redirect:/";
    }
}
