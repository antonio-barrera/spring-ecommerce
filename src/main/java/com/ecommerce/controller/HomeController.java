/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetail;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    private List<OrderDetail> details = new ArrayList<>();
    
    private Order order = new Order();
    
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("products", productService.getAll());
        return "user/home";
    }
    
    @GetMapping("product/{id}")
    public String product(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.get(id));
        LOGGER.info("Product: {}", id);
        return "user/product";
    }
    
    @PostMapping("cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer quantity, Model model) {
        double total = 0;
        
        Product product = productService.get(id);
        
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setName(product.getName());
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setTotal(product.getPrice() * quantity);
        orderDetail.setProduct(product);
        details.add(orderDetail);
        
        total = details.stream().mapToDouble(dt -> dt.getTotal()).sum();
        order.setTotal(total);
        
        model.addAttribute("details", details);
        model.addAttribute("order", order);
        
        LOGGER.info("Product: {}", product);
        LOGGER.info("Quantity: {}", quantity);
        
        return "user/cart";
    }
}
