package com.CstCommerce.CstCommerceBackEndMain.dto;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class VoteOutputDto {
  private Long oneStar;

  private Long twoStar;

  private Long threeStar;

  private Long fourStar;

  private Long fiveStar;

  public VoteOutputDto(Vote vote) {
    this.oneStar = vote.getOneStar();
    this.twoStar = vote.getTwoStar();
    this.threeStar = vote.getThreeStar();
    this.fourStar = vote.getFourStar();
    this.fiveStar = vote.getFiveStar();
  }
}
