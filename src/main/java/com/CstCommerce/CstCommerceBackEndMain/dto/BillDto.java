package com.CstCommerce.CstCommerceBackEndMain.dto;

import com.CstCommerce.CstCommerceBackEndMain.entity.user.Bill;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.BillStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@NotBlank
@Data
public class BillDto {
  private Long billId;

  private Instant localDate;

  @Enumerated(EnumType.STRING)
  private BillStatus billStatus;

  private List<ProductDto> products;

  private String note;

  private float amount;
}
