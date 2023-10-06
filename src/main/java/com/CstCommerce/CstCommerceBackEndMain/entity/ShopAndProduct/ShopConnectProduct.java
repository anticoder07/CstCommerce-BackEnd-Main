package com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "shop_product")
@AllArgsConstructor
@Getter
public class ShopConnectProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "shop_id")
  private Shop shop;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  public ShopConnectProduct(Shop shop, Product product) {
    this.shop = shop;
    this.product = product;
  }

  public ShopConnectProduct() {
  }
}
