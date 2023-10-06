package com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct;

import com.CstCommerce.CstCommerceBackEndMain.dto.ProductDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String productName;

  private Long numberOfProducts;

  private String descriptionProduct;

  private float price;

  @Enumerated(EnumType.STRING)
  private ProductType productType;

  @OneToMany(mappedBy = "product")
  private List<BasketConnectProduct> basketConnectProducts;

  @OneToOne(mappedBy = "product")
  private Vote vote;

  @OneToMany(mappedBy = "product")
  private List<BillConnectProduct> billConnectProducts;

  @OneToMany(mappedBy = "product")
  private List<ShopConnectProduct> shopConnectProducts;

  @OneToMany(mappedBy = "product")
  private List<DirectCardConnectProduct> directCardConnectProducts;

  public Product(String productName, Long numberOfProducts, String descriptionProduct, float price, String productType) {
    this.productName = productName;
    this.numberOfProducts = numberOfProducts;
    this.descriptionProduct = descriptionProduct;
    this.price = price;
    if (productType.equals("work")) {
      this.productType = ProductType.WORK;
    } else {
      this.productType = ProductType.INACTIVE;
    }
  }

  public Product(ProductDto productDto) {
    this.productName = productDto.getProductName();
    this.numberOfProducts = productDto.getNumberOfProducts();
    this.descriptionProduct = productDto.getDescriptionProduct();
    this.price = productDto.getPrice();
    if (productDto.getProductType().equals("work")) {
      this.productType = ProductType.WORK;
    } else {
      this.productType = ProductType.INACTIVE;
    }
//    this.vote = new Vote(
//            productDto.getVote().getOneStar(),
//            productDto.getVote().getTwoStar(),
//            productDto.getVote().getThreeStar(),
//            productDto.getVote().getFourStar(),
//            productDto.getVote().getFiveStar()
//    );
  }
}
