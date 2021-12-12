package com.whatcity.topup.model.scb_payment.response_data;

import lombok.Data;

@Data
public class DataSCBToken {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private int expiresAt;
}
