package com.CstCommerce.CstCommerceBackEndMain.service.User;

import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ShopDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.UserDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.admin.CommercePrice;

import java.util.List;

public interface AdminService {
  String deleteAccount(String email);
  List<UserDto> seeAllUser();
  List<ProductDto> seeAllProduct();
  List<ShopDto> seeAllShop();
  CommercePrice setTheMoneyCommerce(float money);
}
