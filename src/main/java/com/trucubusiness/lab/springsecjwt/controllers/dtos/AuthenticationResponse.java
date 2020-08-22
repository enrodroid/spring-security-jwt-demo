package com.trucubusiness.lab.springsecjwt.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AuthenticationResponse {
  String jwt;
}
