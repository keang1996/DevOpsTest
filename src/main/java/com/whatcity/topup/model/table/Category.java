package com.whatcity.topup.model.table;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "category_news")
@Data
public class Category implements Serializable   {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "news_category_id")
    private Long newsCategoryId;
    @Column(name = "news_category")
    private String newsCategory;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "status")
    private int status;
}
