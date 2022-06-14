/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.service;

import com.ecommerce.model.OrderDetail;
import com.ecommerce.repository.IOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeant
 */
@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
    
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    
    @Override
    public OrderDetail get(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
