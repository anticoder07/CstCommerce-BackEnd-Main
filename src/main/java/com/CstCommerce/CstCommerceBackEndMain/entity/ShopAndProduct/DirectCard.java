package com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "directCard")
@NoArgsConstructor
@Data
public class DirectCard {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  @OneToMany(mappedBy = "directCard")
  private List<DirectCardConnectProduct> directCardConnectProduct;

  @ManyToOne
  @JoinColumn(name = "shop_id")
  private Shop shop;

  private Instant purchaseDate;

  private String address;

  private String sdt;

  private float amount = 0;

  public DirectCard(String username, Shop shop, Instant purchaseDate, String address, String sdt, float amount) {
    this.username = username;
    this.shop = shop;
    this.purchaseDate = purchaseDate;
    this.address = address;
    this.sdt = sdt;
    this.amount = amount;
  }
}
