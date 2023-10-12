package com.CstCommerce.CstCommerceBackEndMain.myController;

import com.CstCommerce.CstCommerceBackEndMain.dto.DirectCardDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ShopDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import com.CstCommerce.CstCommerceBackEndMain.payload.response.ResponseHandler;
import com.CstCommerce.CstCommerceBackEndMain.repository.UserRepository;
import com.CstCommerce.CstCommerceBackEndMain.securityConfig.AuthenticationJwtFilter;
import com.CstCommerce.CstCommerceBackEndMain.service.User.OwnerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
@PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
public class OwnerController {
  private final OwnerService ownerService;

  private final UserRepository userRepository;

  private Users getUser() {
    UserDetails userDetails = AuthenticationJwtFilter.getUserDetailsCirculate();
    Users user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    return user;
  }

  @PostMapping("/make-new-shop")
  public ShopDto makeNewShop(
          @RequestBody
          @NotNull(message = "Shop name cannot null")
          String shopName
  ) {
    return ownerService.makeTheShop(getUser(), shopName);
  }

  @GetMapping("/see-shop")
  public ShopDto seeAllShop() {
    return ownerService.seeAllShop(getUser());
  }

  @GetMapping("/see-product-shop")
  public List<ProductDto> seeAllProductInTheSHop() {
    return ownerService.seeAllProductInTheShop(getUser());
  }

  @PostMapping("/add-product-shop")
  public List<ProductDto> addProductIntoTheShop(
          @RequestBody @Valid
          @NotNull(message = "Product cannot null")
          ProductDto productDto
  ) {
    return ownerService.addProductIntoTheShop(getUser(), productDto);
  }

  @PostMapping("/del-product-shop")
  public List<ProductDto> delProductInTheShop(
          @RequestBody
          @NotNull(message = "Id cannot null")
          @NotBlank(message = "Id cannot blank")
          @Min(value = 1, message = "id > 1")
          Long id
  ) {
    return ownerService.delProductIntoTheShop(getUser(), id);
  }

  @PostMapping("/alter-description-product-shop")
  public ProductDto alterDescriptionProductInTheShop(
          @RequestBody @Valid
          @NotNull(message = "Product cannot null")
          ProductDto productDto
  ) {
    return ownerService.alterDescriptionProductIntoTheShop(getUser(), productDto.getId(), productDto.getDescriptionProduct());
  }

  @PostMapping("/alter-price-product-shop")
  public ProductDto alterPriceProductInTheShop(
          @RequestBody @Valid
          @NotNull(message = "Product cannot null")
          ProductDto productDto
  ) {
    return ownerService.alterPriceProductIntoTheShop(getUser(), productDto.getId(), productDto.getPrice());
  }

  @PostMapping("/restore-product")
  public ProductDto restoreProductInTheShop(
          @RequestBody
          @NotNull(message = "Id cannot null")
          @Min(value = 1, message = "id > 1")
          Long id
  ) {
    return ownerService.restoreProduce(getUser(), id);
  }

  @PostMapping("/alter-shop-name")
  public ShopDto alterNameShop(
          @RequestBody
          @NotNull(message = "Shop name cannot null")
          String shopName
  ) {
    return ownerService.alterNameShop(getUser(), shopName);
  }

  @GetMapping("/see-direct-card")
  public List<DirectCardDto> seeTheDirectCard() {
    return ownerService.seeTheDirectCard(getUser());
  }

  @GetMapping("/del-shop")
  public ResponseEntity<?> delShop() {
    try {
      Boolean check = ownerService.delShop(getUser());
      if (check)
        return ResponseEntity.ok("Delete shop success");
    } catch (Exception e) {
      return ResponseHandler.generateErrorResponse(e);
    }
    return ResponseEntity.badRequest().body("Error delete shop");
  }
}
