package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.user.Basket;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
  Basket findByUsers(Users users);

}
