package com.whatcity.topup.model.table;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_order")
@Data
public class TbOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "steam_id")
  private String steamId;

  @Column(name = "order_ref")
  private String orderRef;

  @Column(name = "amount")
  private BigDecimal amount;
}
