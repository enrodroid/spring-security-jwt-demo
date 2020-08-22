package com.trucubusiness.lab.springsecjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Step 0:
 *    A starter Spring Security App with one hard-coded user.
 *
 * Step 1:
 *    A /authenticate API endpoint:
 *    - Accept user Id and pass,
 *    - Return JWT as a response.
 *
 * Step 2:
 *    Intercept all incoming requests:
 *    - Extract JWT from the header,
 *    - Validate and set in execution context.
 */

@SpringBootApplication
public class SpringSecurityJwtApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityJwtApplication.class, args);
  }
  
}
