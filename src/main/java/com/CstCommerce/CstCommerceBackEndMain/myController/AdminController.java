package com.CstCommerce.CstCommerceBackEndMain.myController;

import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ShopDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.UserDto;
import com.CstCommerce.CstCommerceBackEndMain.service.User.AdminService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
  private final AdminService adminService;

  @GetMapping("/see-all-product")
  public List<ProductDto> seeAllProducts() {
    return adminService.seeAllProduct();
  }

  @GetMapping("/see-all-shop")
  public List<ShopDto> seeAllShops() {
    return adminService.seeAllShop();
  }

  @GetMapping("/see-all-user")
  public List<UserDto> seeAllUsers() {
    return adminService.seeAllUser();
  }

  @PostMapping("/delete-account")
  public String deleteAccount(
          @RequestBody
          @NotNull(message = "Email cannot null")
          String email
  ) {
    return adminService.deleteAccount(email);
  }

}
