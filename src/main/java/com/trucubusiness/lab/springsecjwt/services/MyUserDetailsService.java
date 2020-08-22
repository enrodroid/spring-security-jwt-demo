package com.trucubusiness.lab.springsecjwt.services;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
  
  private final ApplicationContext context;
  
  public MyUserDetailsService(ApplicationContext context) {
    this.context = context;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username) {
    String encryptedPass = context.getBean(PasswordEncoder.class).encode("foo");
    return User.withUsername(username).password(encryptedPass).roles("USER").build();
  }
}
