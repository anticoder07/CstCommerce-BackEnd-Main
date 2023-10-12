package com.CstCommerce.CstCommerceBackEndMain.payload.request;

import com.CstCommerce.CstCommerceBackEndMain.customAnnotation.customValidation.config.PasswordMatching;
import com.CstCommerce.CstCommerceBackEndMain.customAnnotation.customValidation.config.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Passwords do not match!"
)
public class SignUpRequest {
  @NotBlank(message = "User name cannot blank")
  @Size(min = 3, max = 20, message = "size digits username from 3 to 20")
  private String username;

  @NotBlank(message = "User email cannot blank")
  @Size(max = 50)
  @Email(message = "Email invalidate")
  private String email;

  private String role;

  @NotBlank(message = "Password cannot blank")
  @StrongPassword
  private String password;

  private String confirmPassword;

  @NotBlank(message = "sdt cannot blank")
  @Size(min = 9, max = 11, message = "Sdt min 9 max 11")
  private String sdt;
}
