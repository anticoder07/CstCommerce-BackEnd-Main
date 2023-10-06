package com.CstCommerce.CstCommerceBackEndMain.service.User;

import com.CstCommerce.CstCommerceBackEndMain.dto.DirectCardDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ShopDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.*;
import com.CstCommerce.CstCommerceBackEndMain.entity.admin.CommercePrice;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import com.CstCommerce.CstCommerceBackEndMain.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerServiceImpl implements OwnerService {
  private final DirectCardRepository directCardRepository;

  private final ShopRepository shopRepository;

  private final ProductRepository productRepository;

  private final ShopConnectProductRepository shopConnectProductRepository;

  private final DirectCardConnectProductRepository directCardConnectProductRepository;

  private final VoteRepository voteRepository;

  private final CommercePriceRepository commercePriceRepository;

  @Override
  public ShopDto makeTheShop(Users users, String shopName) {
    Shop shop = new Shop();
    shop.setShopType(ShopType.WORK);
    shop.setShopName(shopName);
    shop.setRegistrationDate(Instant.now());
    shop.setUsers(users);
    shopRepository.save(shop);
    ShopDto shopDto = new ShopDto(shop.getId(), shopName, null);
    return shopDto;
  }

  @Override
  public ShopDto seeAllShop(Users user) {
    Shop shop = shopRepository.findByUsers(user);

    if (shop == null) return null;

    ShopDto shopDto = new ShopDto(
            shop.getId(),
            shop.getShopName(),
            seeTheDirectCard(user)
    );

    List<ProductDto> productDtoList = new ArrayList<>();
    List<Product> products = shopConnectProductRepository.findProductIntoTheShop(shop);
    for (Product p : products) {
      productDtoList.add(new ProductDto(p));
    }

    shopDto.setProductDtoList(productDtoList);

    return shopDto;
  }

  @Override
  public List<ProductDto> seeAllProductInTheShop(Users user) {
    Shop shop = shopRepository.findByUsers(user);
    List<Product> products = shopConnectProductRepository.findProductIntoTheShopAll(shop);

    List<ProductDto>productDtoList = new ArrayList<>();
    for (Product product : products){
      productDtoList.add(
              new ProductDto(product)
      );
    }

    return productDtoList;
  }

  @Override
  public List<ProductDto> addProductIntoTheShop(Users user, ProductDto productDto) {
    Shop shop = shopRepository.findByUsers(user);
    if (shop.getShopType() == ShopType.INACTIVE){
      log.error("Shop: "+ shop.getShopName() +" inactive");
      return null;
    }
    Product product = new Product(productDto);
    productRepository.save(product);

    Vote vote = new Vote(product, productDto.getVote());
    product.setVote(vote);
    vote.setProduct(product);

    voteRepository.save(vote);

    ShopConnectProduct newShopConnectProduct = new ShopConnectProduct(shop, product);
    shopConnectProductRepository.save(newShopConnectProduct);

    return seeAllProductInTheShop(user);
  }

  @Override
  @Transactional
  public List<ProductDto> delProductIntoTheShop(Users user, Long id) {
    Shop shop = shopRepository.findByUsers(user);
    if (shop.getShopType() == ShopType.INACTIVE){
      log.error("Shop: "+ shop.getShopName() +" inactive");
      return null;
    }

    List<ShopConnectProduct> shopConnectProduct = shopConnectProductRepository.findByShop(shop);

    List<Product> productList = new ArrayList<>();
    for (ShopConnectProduct scp : shopConnectProduct){
      Product product = productRepository.findByShopConnectProducts(scp);
      productList.add(product);
    }

    for (Product p : productList) {
      if (p.getId().equals(id)) {
        try {
          p.setProductType(ProductType.INACTIVE);
          productRepository.alterProduct(id, p);
          return seeAllShop(user).getProductDtoList();
        } catch (Exception e) {
          log.error("Don't delete product in the shop: " + e);
        }
      }
    }
    return null;
  }

  @Override
  @Transactional
  public ProductDto alterDescriptionProductIntoTheShop(Users user, Long id, String descriptionProduct) {
    Shop shop = shopRepository.findByUsers(user);
    if (shop.getShopType() == ShopType.INACTIVE){
      log.error("Shop: "+ shop.getShopName() +" inactive");
      return null;
    }

    List<Product> productList = shopConnectProductRepository.findProductIntoTheShop(shop);
    for (Product p : productList) {
      if (p.getId().equals(id)) {
        productRepository.alterDescriptionProduct(id, descriptionProduct);
        p.setDescriptionProduct(descriptionProduct);
        return new ProductDto(p);
      }
    }
    return null;
  }

  @Override
  public ProductDto alterPriceProductIntoTheShop(Users user, Long id, float price) {
    Shop shop = shopRepository.findByUsers(user);
    List<Product> productList = shopConnectProductRepository.findProductIntoTheShop(shop);
    for (Product p : productList) {
      if (p.getId().equals(id)) {
        productRepository.alterPriceProduct(id, price);
        p.setPrice(price);
        return new ProductDto(p);
      }
    }
    return null;
  }

  @Override
  public ProductDto restoreProduce(Users user, Long id) {
    Shop shop = shopRepository.findByUsers(user);
    if (shop.getShopType() == ShopType.INACTIVE){
      log.error("Shop: "+ shop.getShopName() +" inactive");
      return null;
    }

    List<Product> productList = shopConnectProductRepository.findProductIntoTheShop(shop);
    for (Product p : productList) {
      if (p.getId().equals(id)) {
        p.setProductType(ProductType.WORK);
        productRepository.alterProduct(p.getId(), p);
        return new ProductDto(p);
      }
    }
    return null;
  }

  @Override
  @Transactional
  public ShopDto alterNameShop(Users user, String shopNameAlter) {
    Shop shop = shopRepository.findByUsers(user);
    if (shop.getShopType() == ShopType.INACTIVE){
      log.error("Shop: "+ shop.getShopName() +" inactive");
      return null;
    }

    shopRepository.alterShopName(shop.getId(), shopNameAlter);
    return seeAllShop(user);
  }

  @Override
  public Boolean delShop(Users user) {
    try {
      Shop shop = shopRepository.findByUsers(user);

      List<Product> products = shopConnectProductRepository.findProductIntoTheShop(shop)
              .stream()
              .map(product -> {
                product.setProductType(ProductType.INACTIVE);
                return product;
              })
              .collect(Collectors.toList());

      for (ProductDto p : seeAllProductInTheShop(user))
        shopConnectProductRepository.delete(shopConnectProductRepository.findByProduct(productRepository.findById(p.getId()).orElseThrow()));


      List<DirectCard> directCards = directCardRepository
              .findByShop(shop)
              .stream()
              .peek((dc) -> {
                directCardConnectProductRepository
                        .findByDirectCard(dc)
                        .stream().peek(
                                (dcp) -> {
                                  directCardConnectProductRepository.delete(dcp);
                                }
                        ).collect(Collectors.toList());
                directCardRepository.delete(dc);
              })
              .collect(Collectors.toList());
      shopRepository.delete(shop);

      return true;
    } catch (Exception e) {
      log.error("Cannot delete shop: " + e.getMessage());
    }
    return false;
  }

  @Override
  public List<DirectCardDto> seeTheDirectCard(Users user) {
    Shop shop = shopRepository.findByUsers(user);

    List<DirectCard> directCard = directCardRepository.findByShop(shop);

    List<DirectCardDto> directCardDtoList = new ArrayList<>();
    for (DirectCard dc : directCard) {

      List<DirectCardConnectProduct> directCardConnectProducts = directCardConnectProductRepository.findByDirectCard(dc);
      List<Product> products = new ArrayList<>();

      for (DirectCardConnectProduct dcp : directCardConnectProducts) {
        Product product = productRepository.findByDirectCardConnectProducts(dcp);
        products.add(product);
      }

      List<ProductDto> productDtoList = new ArrayList<>();
      for (Product p : products) {
        boolean found = false;
        for (ProductDto pd : productDtoList) {
          if (p.getId().equals(pd.getId())) {
            pd.setNumberOfProducts(pd.getNumberOfProducts() + 1);
            found = true;
            break;
          }
        }
        if (!found) {
          ProductDto productDto = new ProductDto(p);
          productDto.setNumberOfProducts(1L);
          productDtoList.add(productDto);
        }
      }

      DirectCardDto directCardDto = new DirectCardDto(dc, productDtoList);
      directCardDtoList.add(directCardDto);
    }
    return directCardDtoList;
  }

  @Override
  public ShopDto PayTheShopForAdmin(Users user, float money) {
    CommercePrice commercePrice = commercePriceRepository.findById(1L).orElseThrow();
    if (commercePrice.getPrice() > money){
      return null;
    } else {
      Shop shop = shopRepository.findByUsers(user);
      shopRepository.alterShopType(shop.getId(), ShopType.WORK);
      return seeAllShop(user);
    }
  }
}
