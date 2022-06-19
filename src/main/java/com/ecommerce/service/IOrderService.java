/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import java.util.List;

/**
 *
 * @author jeant
 */
public interface IOrderService {
    
    Order save(Order order);
    Order get(Integer id);
    List<Order> getAll();
    List<Order> getByUser(User user);
}
