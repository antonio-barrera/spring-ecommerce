/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ecommerce.service;

import com.ecommerce.model.User;
import java.util.Optional;
import java.util.List;

/**
 *
 * @author jeant
 */
public interface IUserService {
    
    User get(Integer id);
    List<User> getAll();
    Optional<User> getByEmail(String email);
    User save(User user);
}
