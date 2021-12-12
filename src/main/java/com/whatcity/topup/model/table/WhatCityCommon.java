package com.whatcity.topup.model.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "whatcity_common")
@Data
public class WhatCityCommon {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_value")
    private String itemValue;

    @Column(name = "item_long_value")
    private String itemLongValue;

    @Column(name = "remark")
    private String remark;
}
