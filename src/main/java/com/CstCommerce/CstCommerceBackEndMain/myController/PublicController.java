package com.CstCommerce.CstCommerceBackEndMain.myController;

import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 2600)
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {
  private final UserService userService;

  @GetMapping("/menu")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public List<ProductDto> menu() {
    return userService.allProduct();
  }

  @PostMapping("/sort-by-price/asc")
  public ResponseEntity<?> sortAsc(@Valid @RequestBody List<ProductDto> productDtoList) {
    if (productDtoList == null)
      return ResponseEntity.badRequest().body("Request not null");
    return ResponseEntity.ok(userService.sortProductByPrice(productDtoList, true));
  }

  @PostMapping("/sort-by-price/des")
  public ResponseEntity<?> sortDes(@Valid @RequestBody List<ProductDto> productDtoList) {
    if (productDtoList == null)
      return ResponseEntity.badRequest().body("Request not null");
    return ResponseEntity.ok(userService.sortProductByPrice(productDtoList, false));
  }
}
