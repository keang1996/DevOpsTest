package com.whatcity.topup.model.scb_payment.response;

import lombok.Data;

@Data
public class SCBConfirmResponse {
    private String resCode;
    private String resDesc ;
    private String transactionId;
    private String confirmId;
}
