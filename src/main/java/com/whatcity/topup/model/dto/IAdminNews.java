package com.whatcity.topup.model.dto;

import java.util.Date;

public interface IAdminNews {

  Long getNewsId();

  String getTopic();

  String getCoverImage();

  String getDescription();

  Long getNewsCategoryId();

  Date getCreateDate();

  Boolean getNewsStatus();

  String getNewsCategory();

  java.sql.Date getCatagoryCreateDate();

  java.sql.Date getCatagoryUpdateDate();

  String getCreateDateFormated();

  String setCreateDateFormated(String dateFormated);

  Long getCount();
}
