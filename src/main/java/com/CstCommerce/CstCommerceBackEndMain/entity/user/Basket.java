package com.CstCommerce.CstCommerceBackEndMain.entity.user;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "basket")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Basket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private Users users;

  @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
  private List<BasketConnectProduct> basketConnectProducts;

  private float amount = 0;
}
