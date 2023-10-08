package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Basket;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.BasketConnectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketConnectProductRepository extends JpaRepository<BasketConnectProduct, Long> {

  @Modifying
  @Query("delete from BasketConnectProduct bcp where bcp.basket = :basket")
  void deleteByBasket(@Param("basket") Basket basket);

  List<BasketConnectProduct> findByBasket(Basket basket);

  @Query("""
                select bcp.product from BasketConnectProduct bcp where bcp.id = :id
          """)
  Product findByBasketConnectProduct(@Param("id") Long id);

  @Query("""
              select bcp from BasketConnectProduct bcp where bcp.product = :product and bcp.basket = :basket
          """)
  List<BasketConnectProduct> findByBasketAndProduct(@Param("basket") Basket basket, @Param("product") Product product);
}
