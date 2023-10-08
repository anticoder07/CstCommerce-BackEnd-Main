package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Vote;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Bill;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.BillConnectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillConnectProductRepository extends JpaRepository<BillConnectProduct, Long> {
  List<BillConnectProduct> findByBill(Bill bill);

  List<BillConnectProduct> findByBillAndProduct(Bill bill, Product product);

  void deleteByBill(Bill bill);
}
