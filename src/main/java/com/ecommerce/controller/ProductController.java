/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.FileService;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private FileService fileService;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("products", productService.getAll());
        return "product/show";
    }

    @GetMapping("/create")
    public String create() {
        return "/product/create";
    }

    @PostMapping("/save")
    public String save(Product product, @RequestParam("img") MultipartFile file) throws IOException {
        User user = new User(1, "", "", "", "", "", "", "");
        product.setUser(user);

        if (product.getId() == null) {
            String imageName = fileService.saveImage(file);
            product.setImage(imageName);
        }

        productService.save(product);
        LOGGER.info("This is the product {}", product);
        return "redirect:/product";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "product/edit";
    }

    @PostMapping("/update")
    public String update(Product product, @RequestParam("img") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            Product oldProduct = productService.get(product.getId());
            product.setImage(oldProduct.getImage());
        } else {
            Product oldProduct = productService.get(product.getId());

            if (!oldProduct.getImage().equals("default.jpg")) {
                fileService.deleteImage(oldProduct.getImage());
            }
            
            String imageName = fileService.saveImage(file);
            product.setImage(imageName);
        }
        productService.update(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Product product = productService.get(id);
        
        if (!product.getImage().equals("default.jpg")) {
            fileService.deleteImage(product.getImage());
        }

        productService.delete(id);
        return "redirect:/product";
    }

}
