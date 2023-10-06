package com.CstCommerce.CstCommerceBackEndMain.entity.user;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "basket_product")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasketConnectProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "basket_id")
  private Basket basket;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  public BasketConnectProduct(Basket basket, Product product) {
    this.basket = basket;
    this.product = product;
  }
}
