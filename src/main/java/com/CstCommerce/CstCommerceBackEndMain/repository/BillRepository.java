package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.user.Bill;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

  @Query("""
          select b from Bill b where b.users = :user
          """)
  List<Bill> findByUsers(@Param("user") Users users);
}
