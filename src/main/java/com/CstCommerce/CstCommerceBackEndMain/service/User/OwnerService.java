package com.CstCommerce.CstCommerceBackEndMain.service.User;

import com.CstCommerce.CstCommerceBackEndMain.dto.DirectCardDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ShopDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;

import java.util.List;

public interface OwnerService {
//  Users findUserByJwt();
  ShopDto seeAllShop(Users user);

  List<ProductDto> seeAllProductInTheShop(Users user);

  List<ProductDto> addProductIntoTheShop(Users user, ProductDto productDto);

  List<ProductDto> delProductIntoTheShop(Users user, Long id);

  ProductDto alterDescriptionProductIntoTheShop(Users user, Long id, String descriptionProduct);

  ProductDto alterPriceProductIntoTheShop(Users user, Long id, float price);

  ProductDto restoreProduce(Users user, Long id);

  ShopDto alterNameShop(Users user, String shopNameAlter);

  Boolean delShop(Users user);

  List<DirectCardDto> seeTheDirectCard(Users user);

  ShopDto PayTheShopForAdmin(Users user, float money);
}
