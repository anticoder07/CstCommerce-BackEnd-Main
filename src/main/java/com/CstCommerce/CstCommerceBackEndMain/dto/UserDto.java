package com.CstCommerce.CstCommerceBackEndMain.dto;

import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
  private Long id;

  private String username;

  private String email;

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
