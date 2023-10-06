package com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "directCard_product")
@NoArgsConstructor
public class DirectCardConnectProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "directCard_id")
  private DirectCard directCard;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  public DirectCardConnectProduct(DirectCard directCard, Product product) {
    this.directCard = directCard;
    this.product = product;
  }
}
