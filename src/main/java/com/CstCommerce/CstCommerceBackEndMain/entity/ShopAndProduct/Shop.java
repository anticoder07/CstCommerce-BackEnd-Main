package com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct;

import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "shop")
@Data
public class Shop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String shopName;

  private String notification;

  private Instant registrationDate;

  @Enumerated(EnumType.STRING)
  private ShopType shopType;

  @OneToOne
  @JoinColumn(name = "user_id")
  private Users users;

  @OneToMany(mappedBy = "shop")
  private List<ShopConnectProduct> shopConnectProducts;

  @OneToMany(mappedBy = "shop")
  private List<DirectCard> directCards;
}
