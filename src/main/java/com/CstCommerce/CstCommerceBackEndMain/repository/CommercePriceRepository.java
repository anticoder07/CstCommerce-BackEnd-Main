package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.admin.CommercePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercePriceRepository extends JpaRepository<CommercePrice, Long> {
  @Modifying
  @Query(
          """
    update CommercePrice cp set cp.price =: money where cp.id = 1
"""
  )
  CommercePrice alterPrice(@Param("money") float money);
}
