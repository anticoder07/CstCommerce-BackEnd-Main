package com.CstCommerce.CstCommerceBackEndMain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
  private Long id;

  private String shopName;

  private List<ProductDto> productDtoList;

  private List<DirectCardDto> directCardDtoList;

  public ShopDto(Long id, String shopName, List<DirectCardDto> directCardDtoList) {
    this.id = id;
    this.shopName = shopName;
    this.directCardDtoList = directCardDtoList;
  }
}
