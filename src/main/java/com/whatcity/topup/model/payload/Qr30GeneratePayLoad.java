package com.whatcity.topup.model.payload;

import lombok.Data;

@Data
public class Qr30GeneratePayLoad {
    private double amount;
    private String reference1;
    private String reference2;
    private String reference3;
}
