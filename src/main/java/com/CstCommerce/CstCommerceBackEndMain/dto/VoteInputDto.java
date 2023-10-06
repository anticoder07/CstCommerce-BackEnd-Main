package com.CstCommerce.CstCommerceBackEndMain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteInputDto {
  private int vote;

  private Long billId;

  private Long productId;
}
