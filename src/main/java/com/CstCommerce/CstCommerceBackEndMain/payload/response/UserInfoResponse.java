package com.CstCommerce.CstCommerceBackEndMain.payload.response;

import lombok.Data;

import java.util.Set;

@Data
public class UserInfoResponse {
  private String username;

  private String email;

  private String password;

  private Set<String> roles;
}
