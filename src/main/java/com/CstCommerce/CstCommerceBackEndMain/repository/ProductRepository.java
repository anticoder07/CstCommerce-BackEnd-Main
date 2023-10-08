package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.*;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.BasketConnectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Product findByProductName(String productName);

  @Query("""
          select p from Product p where p.productType = :pt
          """)
  List<Product> findByProductType(@Param("pt") ProductType p);

  List<Product> findAllByProductNameAndProductType(String productName, ProductType p);

  Product findByBasketConnectProducts(BasketConnectProduct basketConnectProduct);

  @Modifying
  @Query("""
          update Product p set p.numberOfProducts = :num where p.id = :id
          """)
  void alterNumberProduct(@Param("id") Long id, @Param("num") Long num);

  Product findByDirectCardConnectProducts(DirectCardConnectProduct directCardConnectProduct);

  @Modifying
  @Query("""
          update Product p set p.descriptionProduct = :descriptionProduct where  p.id = :id
          """)
  void alterDescriptionProduct(@Param("id") Long id, @Param("descriptionProduct") String descriptionProduct);

  @Modifying
  @Query("""
          update Product p set p.price = :price where  p.id = :id
          """)
  void alterPriceProduct(@Param("id") Long id, @Param("price") float price);

  @Modifying
  @Query("""
          update Product p set p = :product where  p.id = :id
          """)
  void alterProduct(@Param("id") Long id, @Param("product") Product product);

  Product findByShopConnectProducts(ShopConnectProduct shopConnectProduct);
}
