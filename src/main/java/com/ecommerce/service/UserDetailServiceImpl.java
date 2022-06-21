/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecommerce.service;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeant
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    private Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    
    @Autowired
    private IUserService userService;
   
    @Autowired
    private HttpSession session;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.getByEmail(username);
        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getId());
            return User
                    .builder()
                    .username(user.get().getEmail())
                    .password(user.get().getPassword())
                    .roles(user.get().getType())
                    .build();
        }
        LOGGER.info("{} is not present.", username);
        throw new UsernameNotFoundException("Could not find user " + username);
    }
    
}
