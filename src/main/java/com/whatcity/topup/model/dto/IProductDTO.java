package com.whatcity.topup.model.dto;

public interface IProductDTO {

  Long getProductId();

  String getProductName();

  String getProductRefId();

  double getProductPrice();

  String getProductImg();

  Boolean getProductStatus();

  Long getProductCate();

  Long getCount();
}
