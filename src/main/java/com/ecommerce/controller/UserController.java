/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.service.IOrderService;
import com.ecommerce.service.IUserService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping("/user")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/save")
    public String save(User user) {
        user.setType("USER");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/access")
    public String access(HttpSession session) {
        User user = userService.get(Integer.parseInt(session.getAttribute("userId").toString()));
//        session.setAttribute("userId", user.getId());
        if (user.getType().equals("ADMIN")) {
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    @GetMapping("/purchases")
    public String getPurchases(Model model, HttpSession session) {
        User user = userService.get(Integer.parseInt(session.getAttribute("userId").toString()));
        List<Order> orders = orderService.getByUser(user);
        model.addAttribute("userSession", session.getAttribute("userId"));
        model.addAttribute("orders", orders);
        return "user/purchases";
    }

    @GetMapping("/purchaseDetail/{id}")
    public String purchaseDetail(@PathVariable Integer id, HttpSession session, Model model) {
        model.addAttribute("details", orderService.get(id).getDetails());
        model.addAttribute("userSession", session.getAttribute("userId"));
        return "user/purchaseDetail";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        return "redirect:/";
    }
}
