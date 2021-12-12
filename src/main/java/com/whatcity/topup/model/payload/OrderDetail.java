package com.whatcity.topup.model.payload;

import lombok.Data;

@Data
public class OrderDetail {
    private String productName;
    private String productImg;
    private String quantity;
}
