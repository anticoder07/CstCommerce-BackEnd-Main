package com.CstCommerce.CstCommerceBackEndMain.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogInRequest {
  @NotBlank(message = "Email cannot blank")
  @Email(message = "Email invalidate")
  private String userEmail;

  @NotBlank(message = "Password cannot blank")
  private String password;
}
