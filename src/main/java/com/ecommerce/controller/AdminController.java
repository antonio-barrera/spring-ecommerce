/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ecommerce.service.IProductService;
import com.ecommerce.service.IUserService;

/**
 *
 * @author jeant
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private IUserService userService;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin/home";
    }
    
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "admin/users";
    }
}
