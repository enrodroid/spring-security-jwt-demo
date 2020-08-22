package com.trucubusiness.lab.springsecjwt.filters;

import com.trucubusiness.lab.springsecjwt.services.MyUserDetailsService;
import com.trucubusiness.lab.springsecjwt.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  
  private final MyUserDetailsService userDetailsService;
  private final JwtUtil jwtService;
  
  public JwtRequestFilter(MyUserDetailsService userDetailsService, JwtUtil jwtService) {
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
  }
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    
    final String authorizationHeader = request.getHeader("Authorization");
    
    String username = null;
    String jwt = null;
    
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtService.extractUsername(jwt);
    }
    
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (Boolean.TRUE.equals(jwtService.validateToken(jwt, userDetails))) {
        UsernamePasswordAuthenticationToken uPAToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        uPAToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(uPAToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
