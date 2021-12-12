package com.whatcity.topup.model.dto;

public interface IPaymentPODTO {

  String getProductName();

  Double getProductPrice();

  String getProductImg();

  Integer getQuantity();

  Double getTotalPrice();

  String getOrderRef();
}
