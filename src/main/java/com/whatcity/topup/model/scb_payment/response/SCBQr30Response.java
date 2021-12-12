package com.whatcity.topup.model.scb_payment.response;


import com.whatcity.topup.model.scb_payment.Status;
import com.whatcity.topup.model.scb_payment.response_data.DataSCBQr30;
import lombok.Data;

@Data
public class SCBQr30Response {
    private Status status;
    private DataSCBQr30 data;
}
