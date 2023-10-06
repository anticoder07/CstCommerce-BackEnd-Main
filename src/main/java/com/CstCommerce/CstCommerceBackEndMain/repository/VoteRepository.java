package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
  Vote findByProduct(Product product);

  @Modifying
  @Query("""
          update Vote v
          set v.oneStar = :one, 
          v.twoStar = :two,
          v.threeStar = :three,
          v.fourStar = :four,
          v.fiveStar = :five
          where v.id = :id
          """)
  void alterVoteProduct(
          @Param("id") Long id,
          @Param("one") Long one,
          @Param("two") Long two,
          @Param("three") Long three,
          @Param("four") Long four,
          @Param("five") Long five
  );
}
