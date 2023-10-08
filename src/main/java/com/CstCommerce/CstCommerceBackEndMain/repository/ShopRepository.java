package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Shop;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.ShopConnectProduct;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.ShopType;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
  Shop findByUsers(Users users);

  Shop findByShopConnectProducts(ShopConnectProduct shopConnectProduct);

  @Modifying
  @Query("""
          update Shop s set s.shopName = :shopName where s.id = :id
          """)
  void alterShopName(@Param("id") Long id, @Param("shopName") String shopName);

  @Modifying
  @Query("""
          update Shop s set s.shopType = :type where s.id = :id
          """)
  Shop alterShopType(@Param("id") Long id, @Param("type") ShopType shopType);

  @Modifying
  @Query("""
          update Shop s set s.notification = :notification where s.registrationDate < :dayP and s.registrationDate > :dayB 
          """)
  void alterNotificationExpired(@Param("notification") String notification, @Param("dayP") Instant dayPer, @Param("dayB") Instant dayBack);

  @Query("""
          select s from Shop s where s.notification = :notification
          """)
  List<Shop> findByNotification(@Param(("notification")) String notification);
}
