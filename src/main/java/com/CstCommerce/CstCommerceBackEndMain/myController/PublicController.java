package com.CstCommerce.CstCommerceBackEndMain.myController;

import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<?> menu(){
    return ResponseEntity.ok(userService.allProduct());
  }

  @PostMapping("/sort-by-price/asc")
  public ResponseEntity<?> sortAsc(@Valid @RequestBody List<ProductDto> productDtos){
    return ResponseEntity.ok(userService.sortProductByPrice(productDtos, true));
  }

  @PostMapping("/sort-by-price/des")
  public ResponseEntity<?> sortDes(@Valid @RequestBody List<ProductDto> productDtos){
    return ResponseEntity.ok(userService.sortProductByPrice(productDtos, false));
  }
}
