package com.CstCommerce.CstCommerceBackEndMain.service.User;

import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ShopDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.UserDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import com.CstCommerce.CstCommerceBackEndMain.entity.admin.CommercePrice;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import com.CstCommerce.CstCommerceBackEndMain.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
  private final ProductRepository productRepository;

  private final ShopRepository shopRepository;

  private final OwnerService ownerService;

  private final UserService userService;

  private final ShopConnectProductRepository shopConnectProductRepository;

  private final CommercePriceRepository commercePriceRepository;

  private final UserRepository userRepository;

  @Override
  @Transactional
  public String deleteAccount(String email) {
    Users user = userRepository.findByEmail(email).orElseThrow();
    return userService.deleteUserAccount(user);
  }

  @Override
  public List<UserDto> seeAllUser() {
    List<Users> usersList = userRepository.findAll();
    List<UserDto> userDtoList = new ArrayList<>();
    for (Users users : usersList) {
      UserDto userDto = new UserDto(users);
      userDto.setBasketDto(userService.seeTheBasket(users));
      userDto.setBillDtoList(userService.seeAllBill(users));
      userDto.setShopDto(ownerService.seeAllShop(users));
      userDtoList.add(userDto);
    }
    return userDtoList;
  }

  @Override
  public List<ProductDto> seeAllProduct() {
    List<ProductDto> productDtoList = productRepository.findAll()
            .stream().map(
                    product -> new ProductDto(product)
            ).collect(Collectors.toList());
    return productDtoList;
  }

  @Override
  public List<ShopDto> seeAllShop() {
    List<ShopDto> shopDtoList = shopRepository.findAll()
            .stream().map(
                    shop -> {
                      ShopDto shopDto = new ShopDto(
                              shop.getId(),
                              shop.getShopName(),
                              ownerService.seeTheDirectCard(shop.getUsers())
                      );

                      List<ProductDto> productDtoList = new ArrayList<>();
                      List<Product> products = shopConnectProductRepository.findProductIntoTheShop(shop);
                      for (Product p : products) {
                        productDtoList.add(new ProductDto(p));
                      }

                      shopDto.setProductDtoList(productDtoList);
                      return shopDto;
                    }
            ).collect(Collectors.toList());
    return shopDtoList;
  }

  @Override
  public CommercePrice setTheMoneyCommerce(float money) {
    return commercePriceRepository.alterPrice(money);
  }
}
