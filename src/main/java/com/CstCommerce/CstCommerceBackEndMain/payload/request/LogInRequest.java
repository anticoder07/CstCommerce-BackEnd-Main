package com.CstCommerce.CstCommerceBackEndMain.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogInRequest {
  @NotBlank
  private String userEmail;

  @NotBlank
  private String password;
}
