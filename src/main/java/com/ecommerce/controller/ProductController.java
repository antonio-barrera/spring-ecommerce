/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jeant
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("products", productService.getAll());
        return "product/show";
    }
    
    @GetMapping("/create")
    public String create() {
        return "/product/create";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "product/edit";
    }
    
    @PostMapping("/update")
    public String update(Product product) {
        productService.update(product);
        return "redirect:/product";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/product";
    }
}
