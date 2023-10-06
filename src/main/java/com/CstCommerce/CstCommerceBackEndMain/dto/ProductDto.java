package com.CstCommerce.CstCommerceBackEndMain.dto;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.ProductType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  private Long id;

  @NotBlank
  private String productName;

  private Long numberOfProducts;

  @NotBlank
  private String descriptionProduct;

  private float price;

  private String productType;

  private VoteOutputDto vote;

  public ProductDto(Product product) {
    this.id = product.getId();
    this.productName = product.getProductName();
    this.numberOfProducts = product.getNumberOfProducts();
    this.descriptionProduct = product.getDescriptionProduct();
    this.price = product.getPrice();
    if (product.getProductType() == ProductType.WORK)
      this.productType = "work";
    else
      this.productType = "inactive";
    this.vote = new VoteOutputDto(product.getVote());
  }
}
