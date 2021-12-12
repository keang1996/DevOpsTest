package com.whatcity.topup.model.scb_payment.response;


import com.whatcity.topup.model.scb_payment.Status;
import com.whatcity.topup.model.scb_payment.response_data.DataSCBToken;
import lombok.Data;

@Data
public class SCBTokenResponse {
    private Status status;
    private DataSCBToken data;
}


