package com.CstCommerce.CstCommerceBackEndMain.myController;

import com.CstCommerce.CstCommerceBackEndMain.dto.EmailDto;
import com.CstCommerce.CstCommerceBackEndMain.service.User.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
  private final AdminService adminService;

  @GetMapping("/see-all-product")
  public ResponseEntity<?> seeAllProducts() {
    return ResponseEntity.ok(adminService.seeAllProduct());
  }

  @GetMapping("/see-all-shop")
  public ResponseEntity<?> seeAllShops(){
    return ResponseEntity.ok(adminService.seeAllShop());
  }

  @GetMapping("/see-all-user")
  public ResponseEntity<?> seeAllUsers(){
    return ResponseEntity.ok(adminService.seeAllUser());
  }

  @PostMapping("/delete-account")
  public ResponseEntity<?> deleteAccount(@RequestBody EmailDto emailDto){
    return ResponseEntity.ok(adminService.deleteAccount(emailDto.getEmail()));
  }

}
