package com.CstCommerce.CstCommerceBackEndMain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PayloadException extends RuntimeException{
  private int code;
  private String message;
}
