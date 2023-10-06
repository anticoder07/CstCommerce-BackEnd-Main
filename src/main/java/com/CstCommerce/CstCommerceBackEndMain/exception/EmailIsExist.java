package com.CstCommerce.CstCommerceBackEndMain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailIsExist extends RuntimeException{
  private String message;
}
