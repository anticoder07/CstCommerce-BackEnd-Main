package com.CstCommerce.CstCommerceBackEndMain.dto;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.ProductType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
  private Long id;

  @NotBlank(message = "Product name not null")
  private String productName;

  @Min(value = 1, message = "Number of product min as one")
  private Long numberOfProducts;

  @NotBlank(message = "Description not null")
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
