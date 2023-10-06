package com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct;

import com.CstCommerce.CstCommerceBackEndMain.dto.VoteInputDto;
import com.CstCommerce.CstCommerceBackEndMain.dto.VoteOutputDto;
import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "product_id")
  private Product product;

  private Long oneStar = 0L;

  private Long twoStar = 0L;

  private Long threeStar = 0L;

  private Long fourStar = 0L;

  private Long fiveStar = 0L;

  public Vote(Long oneStar, Long twoStar, Long threeStar, Long fourStar, Long fiveStar) {
    this.oneStar = oneStar;
    this.twoStar = twoStar;
    this.threeStar = threeStar;
    this.fourStar = fourStar;
    this.fiveStar = fiveStar;
  }

  public Vote(Product product, VoteOutputDto voteOutputDto) {
    this.product = product;
    this.oneStar = voteOutputDto.getOneStar();
    this.twoStar = voteOutputDto.getTwoStar();
    this.threeStar = voteOutputDto.getThreeStar();
    this.fourStar = voteOutputDto.getFourStar();
    this.fiveStar = voteOutputDto.getFiveStar();
  }
}
