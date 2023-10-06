package com.CstCommerce.CstCommerceBackEndMain.dto;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.DirectCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DirectCardDto {
  private Long id;

  private String username;

  private List<ProductDto> productDtoList;

  private Instant purchaseDate;

  private String address;

  private String sdt;

  private float amount;

  public DirectCardDto(DirectCard directCard, List<ProductDto> productDtoList) {
    this.id = directCard.getId();
    this.username = directCard.getUsername();
    this.productDtoList = productDtoList;
    this.purchaseDate = directCard.getPurchaseDate();
    this.address = directCard.getAddress();
    this.sdt = directCard.getSdt();
    this.amount = directCard.getAmount();
  }
}
