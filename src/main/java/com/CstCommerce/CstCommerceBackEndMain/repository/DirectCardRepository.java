package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.DirectCard;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DirectCardRepository extends JpaRepository<DirectCard, Long> {
  List<DirectCard> findByShop(Shop shop);

  List<DirectCard> findByAddressAndPurchaseDate(String address, Instant purchaseDate);

}
