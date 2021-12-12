package com.whatcity.topup.model.payload;

import lombok.Data;

@Data
public class OrderTestPayload {
    private String productId;
    private Double quantity;
    private Double totalPrice;
}
