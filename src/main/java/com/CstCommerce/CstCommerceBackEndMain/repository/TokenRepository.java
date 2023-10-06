package com.CstCommerce.CstCommerceBackEndMain.repository;

import com.CstCommerce.CstCommerceBackEndMain.entity.token.Token;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

  @Query(value = """
            select t from Token t inner join Users u\s
            on t.users.id = u.id\s 
            where u.id = :id and (t.expired = false or t.revoked = false)\s
          """)
  List<Token> findAllValidTokenByUser(Long id);

  Optional<Token> findByToken(String token);

  void deleteByUsers(Users users);

  @Query("""
          select t from Token t where t.users = :user
          """)
  Token findByUsers(@Param("user") Users users);
}
