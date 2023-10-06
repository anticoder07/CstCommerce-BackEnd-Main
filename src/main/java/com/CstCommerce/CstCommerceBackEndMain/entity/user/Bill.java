package com.CstCommerce.CstCommerceBackEndMain.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bill")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bill {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users users;

  @OneToMany(mappedBy = "bill")
  private List<BillConnectProduct> billConnectProducts;

  private Instant purchaseDate;

  @Enumerated(EnumType.STRING)
  private BillStatus status;

  private String note;

  private float amount;

  public Bill(Users users, Instant purchaseDate, BillStatus status, String note, float amount) {
    this.users = users;
    this.purchaseDate = purchaseDate;
    this.status = status;
    this.note = note;
    this.amount = amount;
  }
}
