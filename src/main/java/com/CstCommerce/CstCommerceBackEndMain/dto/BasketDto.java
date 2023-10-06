package com.CstCommerce.CstCommerceBackEndMain.dto;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasketDto {
  private List<ProductDto> products;

  private float amount = 0F;

}
