package com.CstCommerce.CstCommerceBackEndMain.myController;

import com.CstCommerce.CstCommerceBackEndMain.dto.*;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import com.CstCommerce.CstCommerceBackEndMain.repository.UserRepository;
import com.CstCommerce.CstCommerceBackEndMain.securityConfig.AuthenticationJwtFilter;
import com.CstCommerce.CstCommerceBackEndMain.service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('USER','OWNER', 'ADMIN')")
public class UserController {
  private final UserService userService;

  private final UserRepository userRepository;

  private Users getUser() {
    UserDetails userDetails = AuthenticationJwtFilter.getUserDetailsCirculate();
    var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    return user;
  }

  @PostMapping("/add-product-basket")
  public ResponseEntity<?> addProductBasket(
          @Valid @RequestBody List<ProductDto> productDtos
  ) {
    return ResponseEntity.ok(userService.addProductIntoBasket(getUser(), productDtos));
  }

  @PostMapping("/del-product-basket")
  public ResponseEntity<?> delProductBasket(
          @Valid @RequestBody List<ProductDto> productDtos
  ) {
    return ResponseEntity.ok(userService.deleteProductInTheBasket(getUser(), productDtos));
  }

  @GetMapping("/see-basket")
  public ResponseEntity<?> seeBasket() {
    return ResponseEntity.ok(userService.seeTheBasket(getUser()));
  }

  @PostMapping("/buy-basket")
  public ResponseEntity<?> buyBasket(
          @Valid @RequestBody PurchaseRequestDto purchaseRequestDto
  ) {
    List<BillDto> billDtoList = userService.buyTheBasket(getUser(), purchaseRequestDto.getMoney(), purchaseRequestDto.getAddress(), purchaseRequestDto.getNote());
    if (billDtoList == null) {
      return ResponseEntity.badRequest().body("Not enough money");
    }
    return ResponseEntity.ok(billDtoList);
  }

  @GetMapping("/see-bill")
  public ResponseEntity<?> seeBill() {
    return ResponseEntity.ok(userService.seeAllBill(getUser()));
  }

  @PostMapping("/vote-product")
  public ResponseEntity<?> voteProduct(@RequestBody @Valid VoteInputDto voteDto) {
    return ResponseEntity.ok(userService.evaluateTheProduct(getUser(), voteDto.getVote(), voteDto.getBillId(), voteDto.getProductId()));
  }

  @GetMapping("/del-account")
  public ResponseEntity<?> delAccount() {
    return ResponseEntity.ok(userService.deleteUserAccount(getUser()));
  }
}
