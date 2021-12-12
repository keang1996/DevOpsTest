package com.whatcity.topup.model.scb_payment.payload;

import lombok.Data;

@Data
public class SCBConfirmPayload {
    private String payeeProxyId;
    private String payeeProxyType;
    private String payeeAccountNumber;
    private String payerAccountNumber;
    private String payerAccountName;
    private String payerName;
    private String sendingBankCode;
    private String receivingBankCode;
    private String amount;
    private String transactionId;
    private String transactionDateandTime;
    private String billPaymentRef1;
    private String billPaymentRef2;
    private String billPaymentRef3;
    private String currencyCode;
    private String channelCode;
    private String transactionType;
}
