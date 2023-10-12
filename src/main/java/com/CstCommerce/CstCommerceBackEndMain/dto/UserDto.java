package com.CstCommerce.CstCommerceBackEndMain.dto;

import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
  @NotNull(message = "Id cannot null")
  private Long id;

  @NotNull(message = "username cannot null")
  private String username;

  @NotNull(message = "email cannot null")
  private String email;

  @NotNull(message = "Id cannot null")
  private String password;

  private String role;

  private BasketDto basketDto;

  private List<BillDto> billDtoList;

  private ShopDto shopDto;

  private String sdt;

  public UserDto(Users users) {
    this.id = users.getId();
    this.username = users.getUsername();
    this.email = users.getEmail();
    this.password = users.getPassword();
    this.role = users.getRole().toString();
    this.sdt = users.getSdt();
  }
}
