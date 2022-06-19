/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.service;

import com.ecommerce.exception.OrderNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.repository.IOrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeant
 */
@Service
public class OrderServiceImpl implements IOrderService {
    
    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
    
    @Override
    public Order get(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
    
    @Override
    public List<Order> getByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
