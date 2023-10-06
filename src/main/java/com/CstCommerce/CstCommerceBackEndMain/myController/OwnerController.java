package com.CstCommerce.CstCommerceBackEndMain.myController;

import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import com.CstCommerce.CstCommerceBackEndMain.repository.UserRepository;
import com.CstCommerce.CstCommerceBackEndMain.securityConfig.AuthenticationJwtFilter;
import com.CstCommerce.CstCommerceBackEndMain.securityConfig.NotificationSseEmitterManager;
import com.CstCommerce.CstCommerceBackEndMain.service.User.OwnerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
@PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
public class OwnerController {
  private final OwnerService ownerService;

  private final UserRepository userRepository;

  @Autowired
  private NotificationSseEmitterManager emitterManager;

  private Users getUser() {
    UserDetails userDetails = AuthenticationJwtFilter.getUserDetailsCirculate();
    Users user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    return user;
  }

  @PostMapping("/make-new-shop")
  public ResponseEntity<?> makeNewShop(@RequestBody String shopName){
    return ResponseEntity.ok(ownerService.makeTheShop(getUser(), shopName));
  }

  @GetMapping("/see-shop")
  public ResponseEntity<?> seeAllShop() {
    return ResponseEntity.ok(ownerService.seeAllShop(getUser()));
  }

  @GetMapping("/see-product-shop")
  public ResponseEntity<?> seeAllProductInTheSHop() {
    return ResponseEntity.ok(ownerService.seeAllProductInTheShop(getUser()));
  }

  @PostMapping("/add-product-shop")
  public ResponseEntity<?> addProductIntoTheShop(@RequestBody @Valid ProductDto productDto) {
    return ResponseEntity.ok(ownerService.addProductIntoTheShop(getUser(), productDto));
  }

  @PostMapping("/del-product-shop")
  public ResponseEntity<?> delProductInTheShop(@RequestBody Long id) {
    return ResponseEntity.ok(ownerService.delProductIntoTheShop(getUser(), id));
  }

  @PostMapping("/alter-description-product-shop")
  public ResponseEntity<?> alterDescriptionProductInTheShop(@RequestBody @Valid ProductDto productDto) {
    return ResponseEntity.ok(ownerService.alterDescriptionProductIntoTheShop(getUser(), productDto.getId(), productDto.getDescriptionProduct()));
  }

  @PostMapping("/alter-price-product-shop")
  public ResponseEntity<?> alterPriceProductInTheShop(@RequestBody @Valid ProductDto productDto) {
    return ResponseEntity.ok(ownerService.alterPriceProductIntoTheShop(getUser(), productDto.getId(), productDto.getPrice()));
  }

  @PostMapping("/restore-product")
  public ResponseEntity<?> restoreProductInTheShop(@RequestBody @Valid Long id) {
    return ResponseEntity.ok(ownerService.restoreProduce(getUser(), id));
  }

  @PostMapping("/alter-shop-name")
  public ResponseEntity<?> alterNameShop(@RequestBody @NotBlank String shopName) {
    return ResponseEntity.ok(ownerService.alterNameShop(getUser(), shopName));
  }

  @GetMapping("/see-direct-card")
  public ResponseEntity<?> seeTheDirectCard(){
    return ResponseEntity.ok(ownerService.seeTheDirectCard(getUser()));
  }

  @GetMapping("/del-shop")
  public ResponseEntity<?> delShop() {
    Boolean check = ownerService.delShop(getUser());
    if (check)
      return ResponseEntity.ok("Ok");
    return ResponseEntity.badRequest().body("error");
  }

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter stream() {
    SseEmitter emitter = new SseEmitter();
    emitter.onCompletion(() -> emitterManager.removeEmitter(emitter));
    emitterManager.addEmitter(emitter);
    return emitter;
  }
}
