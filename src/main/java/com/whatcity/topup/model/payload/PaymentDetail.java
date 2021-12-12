package com.whatcity.topup.model.payload;

import lombok.Data;

@Data
public class PaymentDetail {
    String orderRef;
    String date;
    String time;
}
