/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.exception;

/**
 *
 * @author jeant
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(Integer id) {
        super("Could not fin user: " + id);
    }
}
