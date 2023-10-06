package com.CstCommerce.CstCommerceBackEndMain.entity.admin;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Commerce_price")
@Data
public class CommercePrice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private float price;
}
