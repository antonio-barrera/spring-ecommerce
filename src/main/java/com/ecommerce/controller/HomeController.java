/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import com.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jeant
 */
@Controller
@RequestMapping("/")
public class HomeController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("products", productService.getAll());
        return "user/home";
    }
    
    @GetMapping("product/{id}")
    public String product(@PathVariable Integer id) {
        LOGGER.info("Product: {}", id);
        return "user/product";
    }
}
