package com.whatcity.topup.model.table;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product_order")
@Data
public class ProductOrder implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "order_ref")
  private String orderIdRef;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "quantity")
  private Double quantity;

  @Column(name = "total_price")
  private Double totalPrice;
}
