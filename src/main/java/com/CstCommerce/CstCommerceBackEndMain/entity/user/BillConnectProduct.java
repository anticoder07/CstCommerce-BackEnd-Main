package com.CstCommerce.CstCommerceBackEndMain.entity.user;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "bill_product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillConnectProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "bill_id")
  private Bill bill;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  private int vote;

  private Instant purchaseDate;

  public BillConnectProduct(Bill bill, Product product, Instant purchaseDate) {
    this.bill = bill;
    this.product = product;
    this.purchaseDate = purchaseDate;
  }

  public BillConnectProduct(Bill bill, Product product, int vote, Instant purchaseDate) {
    this.bill = bill;
    this.product = product;
    this.vote = vote;
    this.purchaseDate = purchaseDate;
  }
}
