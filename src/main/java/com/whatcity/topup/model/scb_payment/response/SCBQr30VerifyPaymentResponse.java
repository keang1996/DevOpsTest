package com.whatcity.topup.model.scb_payment.response;


import com.whatcity.topup.model.scb_payment.Status;
import com.whatcity.topup.model.scb_payment.response_data.DataSCBQr30ConfirmPayment;
import lombok.Data;

@Data
public class SCBQr30VerifyPaymentResponse {
    private Status status;
    private DataSCBQr30ConfirmPayment data;
    private String amount;
    private String paidLocalAmount;
    private String paidLocalCurrency;
    private String countryCode;
    private String ref1;
    private String ref2;
    private String ref3;
}
