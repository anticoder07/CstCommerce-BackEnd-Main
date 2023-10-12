package com.CstCommerce.CstCommerceBackEndMain.dto;

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
