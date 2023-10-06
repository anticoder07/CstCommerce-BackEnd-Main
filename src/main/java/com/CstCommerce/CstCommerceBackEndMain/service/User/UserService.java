package com.CstCommerce.CstCommerceBackEndMain.service.User;

import com.CstCommerce.CstCommerceBackEndMain.dto.BasketDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.BillDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
  List<ProductDto> allProduct();

  List<Product> findByProductName(String productName);

  List<ProductDto> sortProductByPrice(List<ProductDto> products, Boolean sortType);

  BasketDto addProductIntoBasket(Users users, List<ProductDto> product);

  BasketDto deleteProductInTheBasket(Users users, List<ProductDto> products);

  BasketDto seeTheBasket(Users users);

  List<BillDto> buyTheBasket(Users users, float money, String address, String note);

  List<BillDto> seeAllBill(Users users);

  ResponseEntity<Object> evaluateTheProduct(Users users, int vote, Long billId, Long productId);

  String deleteUserAccount(Users users);
}
