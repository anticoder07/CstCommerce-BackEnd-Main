package com.CstCommerce.CstCommerceBackEndMain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull(message = "Purchase request cannot null")
public class PurchaseRequestDto {
  private float money;

  private String address;

  private String note;

}
