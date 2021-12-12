package com.whatcity.topup.model.scb_payment.response;


import com.whatcity.topup.model.scb_payment.Status;
import com.whatcity.topup.model.scb_payment.response_data.DataSCBQrCS;
import lombok.Data;


@Data
public class SCBQrCSResponse {
    private Status status;
    private DataSCBQrCS data;
}
