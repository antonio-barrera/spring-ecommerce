/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ecommerce.service;

import com.ecommerce.model.Product;

/**
 *
 * @author jeant
 */
public interface ProductService {
    
    Product save(Product product);
    Product get(Integer id);
    void update(Product product);
    void delete(Integer id);
}
