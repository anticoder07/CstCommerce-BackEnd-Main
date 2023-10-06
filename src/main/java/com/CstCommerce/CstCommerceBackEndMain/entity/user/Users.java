package com.CstCommerce.CstCommerceBackEndMain.entity.user;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Shop;
import com.CstCommerce.CstCommerceBackEndMain.entity.token.Token;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String email;

  private String password;

  private String sdt;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToOne(mappedBy = "users")
  private Basket baskets;

  @OneToMany(mappedBy = "users")
  private List<Bill> bills;

  @OneToOne(mappedBy = "users")
  private Shop shop;

  @OneToMany(mappedBy = "users")
  private List<Token> token;


  public Users(String username, String email, String password,String sdt, String role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.sdt = sdt;
    if (role.equals("admin")) {
      this.role = Role.ADMIN;
    } else if (role.equals("Owner")) {
      this.role = Role.OWNER;
    } else {
      this.role = Role.USER;
    }
  }
}
