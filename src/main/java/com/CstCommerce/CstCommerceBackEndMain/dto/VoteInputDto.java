package com.CstCommerce.CstCommerceBackEndMain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteInputDto {
  @Min(value = 1)
  @Max(value = 5)
  private int vote;

  private Long billId;

  private Long productId;
}
