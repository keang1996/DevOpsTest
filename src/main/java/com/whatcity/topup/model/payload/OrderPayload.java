package com.whatcity.topup.model.payload;

import lombok.Data;

@Data
public class OrderPayload {
    private Long productId;
    private Double quantity;
}
