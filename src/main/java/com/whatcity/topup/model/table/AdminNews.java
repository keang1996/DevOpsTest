package com.whatcity.topup.model.table;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity()
@Table(name = "admin_news")
@Data
public class AdminNews {

  @Id
  @Column(name = "news_id")
  private Long newsId;

  @Column(name = "topic")
  private String topic;

  @Column(name = "cover_image")
  private String coverImage;

  @Column(name = "description")
  private String description;

  @Column(name = "news_category_id")
  private Long newsCategoryId;

  @Column(name = "create_date")
  private Date createDate;

  @Column(name = "news_status")
  private Boolean newsStatus;
}
