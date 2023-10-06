package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.DirectCard;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.DirectCardConnectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectCardConnectProductRepository extends JpaRepository<DirectCardConnectProduct, Long> {
  List<DirectCardConnectProduct> findByDirectCard(DirectCard directCard);
}
