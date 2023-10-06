package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Shop;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.ShopConnectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopConnectProductRepository extends JpaRepository<ShopConnectProduct, Long> {

  @Query("""
          select scp from ShopConnectProduct scp where scp.product = :product
          """)
  ShopConnectProduct findByProduct(@Param("product") Product product);

  void deleteByShop(Shop shop);

  @Query("""
          select scp.product from ShopConnectProduct scp where scp.shop = :shop and scp.product.productType = 'WORK' 
           """)
  List<Product> findProductIntoTheShop(@Param("shop") Shop shop);

  @Query("""
          select scp.product from ShopConnectProduct scp where scp.shop = :shop 
           """)
  List<Product> findProductIntoTheShopAll(@Param("shop") Shop shop);

  List<ShopConnectProduct> findByShop(Shop shop);
}
