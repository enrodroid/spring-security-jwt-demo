package com.trucubusiness.lab.springsecjwt.controllers;

import com.trucubusiness.lab.springsecjwt.controllers.dtos.AuthenticationRequest;
import com.trucubusiness.lab.springsecjwt.controllers.dtos.AuthenticationResponse;
import com.trucubusiness.lab.springsecjwt.services.MyUserDetailsService;
import com.trucubusiness.lab.springsecjwt.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  
  private final AuthenticationManager authenticationManager;
  private final MyUserDetailsService userDetailsService;
  private final JwtUtil jwtTokenUtil;
  
  public HomeController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtTokenUtil) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }
  
  @GetMapping("/home")
  public String home() {
    return "Hello world";
  }
  
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    } catch (BadCredentialsException ex) {
      throw new SecurityException("Incorrect username or password!", ex);
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtTokenUtil.generateToken(userDetails);
    
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }
}
