package com.CstCommerce.CstCommerceBackEndMain.dto;

import lombok.Data;

@Data
public class PurchaseRequestDto {
  private float money;

  private String address;

  private String note;

}
