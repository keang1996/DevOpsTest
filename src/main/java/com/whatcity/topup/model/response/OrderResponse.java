package com.whatcity.topup.model.response;

import com.whatcity.topup.model.scb_payment.response.SCBQr30Response;
import lombok.Data;

@Data
public class OrderResponse {
    private SCBQr30Response qr30Response;
    private String orderRef;
}
