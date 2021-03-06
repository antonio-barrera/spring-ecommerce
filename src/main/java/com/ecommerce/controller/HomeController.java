/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetail;
import com.ecommerce.model.Product;
import com.ecommerce.service.IOrderDetailService;
import com.ecommerce.service.IOrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import com.ecommerce.service.IProductService;
import com.ecommerce.service.IUserService;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jeant
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    private List<OrderDetail> details = new ArrayList<>();

    private Order order = new Order();

    @GetMapping("")
    public String home(Model model, HttpSession session) {
        model.addAttribute("products", productService.getAll());
        model.addAttribute("userSession", session.getAttribute("userId"));
        return "user/home";
    }

    @GetMapping("product/{id}")
    public String product(@PathVariable Integer id, Model model, HttpSession session) {
        model.addAttribute("product", productService.get(id));
        model.addAttribute("userSession", session.getAttribute("userId"));
        return "user/product";
    }

    @PostMapping("cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer quantity, Model model, HttpSession session) {
        double total = 0;
        Product product = productService.get(id);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setName(product.getName());
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setTotal(product.getPrice() * quantity);
        orderDetail.setProduct(product);
        boolean isAdded = false;
        for (OrderDetail detail : details) {
            if (Objects.equals(product.getId(), detail.getProduct().getId())) {
                isAdded = true;
                quantity += (int) detail.getQuantity();
                detail.setQuantity(quantity);
                detail.setTotal(product.getPrice() * quantity);
                break;
            }
        }
        if (!isAdded) {
            details.add(orderDetail);
        }
        total = details.stream().mapToDouble(dt -> dt.getTotal()).sum();
        order.setTotal(total);
        model.addAttribute("details", details);
        model.addAttribute("order", order);
        model.addAttribute("userSession", session.getAttribute("userId"));
        return "user/cart";
    }

    @GetMapping("cart/{id}/delete")
    public String takeOutProduct(@PathVariable Integer id, Model model, HttpSession session) {
        List<OrderDetail> newOrders = new ArrayList<>();
        double total = 0.0;
        for (OrderDetail detail : details) {
            if (!Objects.equals(detail.getProduct().getId(), id)) {
                newOrders.add(detail);
            }
        }
        details = newOrders;
        total = details.stream().mapToDouble(dt -> dt.getTotal()).sum();
        order.setTotal(total);
        model.addAttribute("details", details);
        model.addAttribute("order", order);
        model.addAttribute("userSession", session.getAttribute("userId"));
        return "user/cart";
    }

    @GetMapping("getCart")
    public String getCart(Model model, HttpSession session) {
        model.addAttribute("details", details);
        model.addAttribute("order", order);
        model.addAttribute("userSession", session.getAttribute("userId"));
        return "user/cart";
    }

    @GetMapping("order")
    public String order(Model model, HttpSession session) {
        model.addAttribute("user", userService.get(Integer.parseInt(session.getAttribute("userId").toString())));
        model.addAttribute("details", details);
        model.addAttribute("order", order);
        model.addAttribute("userSession", session.getAttribute("userId"));
        return "user/order";
    }

    @GetMapping("saveOrder")
    public String saveOrder(HttpSession session) {
        order.setCreationDate(new Date());
        order.setUser(userService.get(Integer.parseInt(session.getAttribute("userId").toString())));
        orderService.save(order);
        for (OrderDetail detail : details) {
            detail.setOrder(order);
            orderDetailService.save(detail);
        }
        details.clear();
        order = new Order();
        return "redirect:/";
    }

    @GetMapping("searchProduct")
    public String searchProduct(@RequestParam String name, Model model, HttpSession session) {
        List<Product> products = productService.getAll()
                .stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        model.addAttribute("userSession", session.getAttribute("userId"));
        return "user/home";
    }

}
