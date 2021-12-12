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
@Table(name = "product")
@Data
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "product_id")
  private Long productId;
  @Column(name = "product_name")
  private String productName;
  @Column(name = "product_ref_id")
  private String productRefId;
  @Column(name = "product_price")
  private double productPrice;
  @Column(name = "product_img")
  private String productImg;
  @Column(name = "product_status")
  private Boolean productStatus;
  @Column(name = "product_cate")
  private Long productCate;

}
