package com.CstCommerce.CstCommerceBackEndMain.service.User;

import com.CstCommerce.CstCommerceBackEndMain.dto.BasketDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.BillDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.VoteOutputDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.*;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.*;
import com.CstCommerce.CstCommerceBackEndMain.payload.response.ResponseHandler;
import com.CstCommerce.CstCommerceBackEndMain.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  private final TokenRepository tokenRepository;

  private final ProductRepository productRepository;

  private final BasketRepository basketRepository;

  private final BillRepository billRepository;

  private final ShopRepository shopRepository;

  private final DirectCardRepository directCardRepository;

  private final VoteRepository voteRepository;

  private final BasketConnectProductRepository basketConnectProductRepository;

  private final BillConnectProductRepository billConnectProductRepository;

  private final ShopConnectProductRepository shopConnectProductRepository;

  private final DirectCardConnectProductRepository directCardConnectProductRepository;


  @Override
  public List<ProductDto> allProduct() {
    List<Product> products = productRepository.findByProductType(ProductType.WORK);
    List<ProductDto> productDto = new ArrayList<>();
    for (Product p : products) {
      VoteOutputDto voteOutputDto = new VoteOutputDto(p.getVote());

      String type;
      if (p.getProductType() == ProductType.WORK)
        type = "work";
      else
        type = "inactive";

      if (shopRepository.findByShopConnectProducts(shopConnectProductRepository.findByProduct(p)).getShopType() == ShopType.WORK)
        productDto.add(
                new ProductDto(
                        p.getId(),
                        p.getProductName(),
                        p.getNumberOfProducts(),
                        p.getDescriptionProduct(),
                        p.getPrice(),
                        type,
                        voteOutputDto
                ));
    }
    return productDto;
  }

  @Override
  public List<Product> findByProductName(String productName) {
    List<Product> productList = productRepository.findAllByProductNameAndProductType(productName, ProductType.WORK)
            .stream()
            .filter(p -> shopRepository.findByShopConnectProducts(shopConnectProductRepository.findByProduct(p)).getShopType() == ShopType.WORK)
            .collect(Collectors.toList());

    return productList;
  }


  @Override
  public List<ProductDto> sortProductByPrice(List<ProductDto> products, Boolean sortType) {
    Comparator<ProductDto> priceComparator = (product1, product2) -> {
      if (sortType) {
        return Double.compare(product1.getPrice(), product2.getPrice());
      } else {
        return Double.compare(product2.getPrice(), product1.getPrice());
      }
    };
    Collections.sort(products, priceComparator);
    return products;
  }

  public Basket findBasketByJwt(Users user) {
    return basketRepository.findByUsers(user);
  }

  public List<Product> findByBasket(Basket basket) {
    List<Product> products = new ArrayList<>();
    for (BasketConnectProduct bcp : basketConnectProductRepository.findAll()) {
      if (bcp.getBasket().getId().equals(basket.getId())) {
        products.add(
                productRepository.findById(bcp.getProduct().getId()).orElseThrow()
        );
      }
    }
    return products;
  }

  @Override
  public BasketDto addProductIntoBasket(Users user, List<ProductDto> products) {
    Basket basket = findBasketByJwt(user);

    for (ProductDto p : products) {
      Product product = productRepository
              .findById(p.getId())
              .orElseThrow(
                      () -> new UsernameNotFoundException("Not found product with ID: " + p.getId())
              );

      Long numberOfPd = 0L;
      for (ProductDto pd : seeTheBasket(user).getProducts()) {
        if (pd.getId().equals(product.getId())) {
          numberOfPd = pd.getNumberOfProducts();
          break;
        }
      }

      if (p.getNumberOfProducts() > (product.getNumberOfProducts() - numberOfPd))
        p.setNumberOfProducts(product.getNumberOfProducts() - numberOfPd);

      for (int i = 0; i < p.getNumberOfProducts(); i++) {
        BasketConnectProduct connect = new BasketConnectProduct(basket, product);
        basketConnectProductRepository.save(connect);
        basket.setAmount(basket.getAmount() + product.getPrice());
      }
      basketRepository.save(basket);
    }

    return seeTheBasket(user);
  }

  @Override
  public BasketDto deleteProductInTheBasket(Users user, List<ProductDto> products) {
    Basket basket = findBasketByJwt(user);

    for (ProductDto p : products) {
      Product product = productRepository.findById(p.getId()).orElseThrow();

      Long numberOfPd = 0L;
      for (ProductDto pd : seeTheBasket(user).getProducts()) {
        if (pd.getId().equals(product.getId())) {
          numberOfPd = pd.getNumberOfProducts();
          break;
        }
      }

      if (p.getNumberOfProducts() > numberOfPd)
        p.setNumberOfProducts(numberOfPd);

      for (int i = 0; i < p.getNumberOfProducts(); i++) {
        basketConnectProductRepository.delete(basketConnectProductRepository.findByBasketAndProduct(basket, product).get(0));
        basket.setAmount(basket.getAmount() - product.getPrice());
      }

      basketRepository.save(basket);
    }

    return seeTheBasket(user);
  }

  @Override
  public BasketDto seeTheBasket(Users user) {
    Basket basket = findBasketByJwt(user);
    if (basket == null){
      return null;
    }
    List<Product> productList = findByBasket(basket);

    List<ProductDto> productDtoList = new ArrayList<>();
    for (Product p : productList) {
      boolean found = false;
      for (int i = 0; i < productDtoList.size(); i++) {
        if (productDtoList.get(i).getId().equals(p.getId())) {
          ProductDto pd = productDtoList.get(i);
          pd.setNumberOfProducts(pd.getNumberOfProducts() + 1);
          productDtoList.set(i, pd);
          found = true;
          break;
        }
      }
      if (!found) {
        ProductDto newProductDto = new ProductDto(
                p.getId(),
                p.getProductName(),
                1L,
                p.getDescriptionProduct(),
                p.getPrice(),
                null,
                new VoteOutputDto(p.getVote())
        );

        if (p.getProductType() == ProductType.WORK
                && shopRepository.findByShopConnectProducts(shopConnectProductRepository.findByProduct(p)).getShopType() == ShopType.WORK)
          newProductDto.setProductType("work");
        else
          newProductDto.setProductType("inactive");

        productDtoList.add(newProductDto);
      }
    }

    return new BasketDto(
            productDtoList,
            basket.getAmount()
    );
  }


  @Override
  @Transactional
  public List<BillDto> buyTheBasket(Users user, float money, String address, String note) {
    Basket basket = findBasketByJwt(user);

    if (money < basket.getAmount())
      return null;

    List<BasketConnectProduct> basketConnectProducts = basketConnectProductRepository.findByBasket(basket);
    List<Long> idList = new ArrayList<>();
    for (BasketConnectProduct bcp : basketConnectProducts) {
      idList.add(bcp.getId());
    }

    List<Product> productList = new ArrayList<>();
    for (Long id : idList) {
      Product product = basketConnectProductRepository.findByBasketConnectProduct(id);
      if (product.getProductType() == ProductType.WORK && shopRepository.findByShopConnectProducts(shopConnectProductRepository.findByProduct(product)).getShopType() == ShopType.WORK
      )
      productList.add(product);
    }

    Instant now = Instant.now();

    Bill bill = new Bill(
            user,
            now,
            BillStatus.PREPARED,
            note,
            basket.getAmount()
    );
    billRepository.save(bill);

    for (Product p : productList) {
      BillConnectProduct connect = new BillConnectProduct(bill, p, now);
      billConnectProductRepository.save(connect);
    }

    for (Product p : productList) {
      for (ProductDto pd : seeTheBasket(user).getProducts()) {
        if (pd.getId().equals(p.getId()))
          productRepository.alterNumberProduct(p.getId(), p.getNumberOfProducts() - pd.getNumberOfProducts());
      }

      Shop shop = shopConnectProductRepository.findByProduct(p).getShop();
      List<DirectCard> directCardList = directCardRepository.findByAddressAndPurchaseDate(address, now);
      if (directCardList.isEmpty()) {
        DirectCard directCard = new DirectCard(
                user.getUsername(),
                shop,
                now,
                address,
                user.getSdt(),
                p.getPrice()
        );
        directCard.setShop(shop);
        DirectCardConnectProduct directCardConnectProduct = new DirectCardConnectProduct(directCard, p);

        directCardConnectProductRepository.save(directCardConnectProduct);
        directCardRepository.save(directCard);
      } else {
        List<DirectCard> directCard = directCardRepository.findByAddressAndPurchaseDate(address, now);
        DirectCardConnectProduct directCardConnectProduct = new DirectCardConnectProduct(directCard.get(0), p);

        directCardConnectProductRepository.save(directCardConnectProduct);
      }
    }

    basketConnectProductRepository.deleteByBasket(basket);
    basket.setAmount(0);
    basket.setBasketConnectProducts(null);
    basketRepository.save(basket);

    return seeAllBill(user);
  }

  @Override
  public List<BillDto> seeAllBill(Users user) {
    List<Bill> bills = billRepository.findByUsers(user);

    if (bills == null) return null;

    List<BillDto> billDtoList = new ArrayList<>();
    for (Bill b : bills) {
      List<Product> productList = new ArrayList<>();
      List<BillConnectProduct> billConnectProductsList = billConnectProductRepository.findByBill(b);
      for (BillConnectProduct bcp : billConnectProductsList) {
        Product tempProduct = productRepository.findById(bcp.getProduct().getId()).orElseThrow();
        productList.add(tempProduct);
      }
      List<ProductDto> productDtoList = new ArrayList<>();

      for (Product p : productList) {
        boolean found = false;
        for (int i = 0; i < productDtoList.size(); i++) {
          if (p.getId().equals(productDtoList.get(i).getId())) {
            ProductDto pd = productDtoList.get(i);
            pd.setNumberOfProducts(pd.getNumberOfProducts() + 1);
            productDtoList.set(i, pd);
            found = true;
            break;
          }
        }
        if (!found) {
          ProductDto newProductDto = new ProductDto(
                  p.getId(),
                  p.getProductName(),
                  1L,
                  p.getDescriptionProduct(),
                  p.getPrice(),
                  null,
                  new VoteOutputDto(p.getVote())
          );

          if (p.getProductType() == ProductType.WORK)
            newProductDto.setProductType("work");
          else
            newProductDto.setProductType("inactive");

          productDtoList.add(newProductDto);
        }
      }

      BillDto tempBillDto = new BillDto(
              b.getId(),
              b.getPurchaseDate(),
              b.getStatus(),
              productDtoList,
              b.getNote(),
              b.getAmount()
      );

      billDtoList.add(tempBillDto);

    }

    return billDtoList;
  }

  @Override
  @Transactional
  public ResponseEntity<Object> evaluateTheProduct(Users user, int meVote, Long billId, Long productId) {
    List<Bill> billList = billRepository.findByUsers(user);

    ProductDto tempProduct = new ProductDto();
    for (Bill bill : billList) {
      if (bill.getId().equals(billId)) {
        Product product = productRepository.findById(productId).orElseThrow();

        if (product.getProductType().equals(ProductType.INACTIVE)) {
          return ResponseHandler.generateResponse("product inactive", HttpStatus.BAD_REQUEST, null);

        }

        List<BillConnectProduct> billConnectProducts = billConnectProductRepository.findByBillAndProduct(bill, product);

        for (int i = 0; i < billConnectProducts.size(); i++) {
          if (billConnectProducts.get(i).getProduct().getId().equals(productId)) {

            Vote vote = voteRepository.findByProduct(product);
            if (meVote == 1) {
              vote.setOneStar(vote.getOneStar() + 1);
            } else if (meVote == 2) {
              vote.setTwoStar(vote.getTwoStar() + 1);
            } else if (meVote == 3) {
              vote.setThreeStar(vote.getThreeStar() + 1);
            } else if (meVote == 4) {
              vote.setFourStar(vote.getFourStar() + 1);
            } else {
              vote.setFiveStar(vote.getFiveStar() + 1);

            }
            voteRepository.alterVoteProduct(vote.getId(), vote.getOneStar(), vote.getTwoStar(), vote.getThreeStar(), vote.getFourStar(), vote.getFiveStar());
            tempProduct = new ProductDto(product);
            break;
          }
        }
      }
    }
    return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, tempProduct);
  }

  @Transactional
  @Override
  public String deleteUserAccount(Users user) {
    Basket basket = findBasketByJwt(user);

    basketConnectProductRepository.deleteByBasket(basket);
    basketRepository.delete(basket);

    List<Bill> bill = billRepository.findByUsers(user);
    for (Bill b : bill) {
      billConnectProductRepository.deleteByBill(b);
    }
    int longB = bill.size();
    for (int i = 0; i < longB; i++) {
      billRepository.delete(bill.get(i));
    }

    Shop shop = shopRepository.findByUsers(user);
    List<DirectCard> directCard = directCardRepository.findByShop(shop);
    for (DirectCard dc : directCard) {
      directCardConnectProductRepository.findByDirectCard(dc).stream().peek(
              (dcp) ->
                directCardConnectProductRepository.delete(dcp)

      ).collect(Collectors.toList());
    }
    int longDc = directCard.size();
    for (int i = 0; i < longDc; i++) {
      directCardRepository.delete(directCard.get(i));
    }
    shopConnectProductRepository.deleteByShop(shop);

    tokenRepository.deleteByUsers(user);
    userRepository.delete(user);

    return "ok";
  }
}
