/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ecommerce.service.IProductService;
import com.ecommerce.service.IUserService;
import com.ecommerce.service.IOrderService;

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
    
    @Autowired
    private IOrderService orderService;

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
    
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "admin/orders";
    }
    
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("details", orderService.get(id).getDetails());
        return "admin/orderDetail";
    }
}
