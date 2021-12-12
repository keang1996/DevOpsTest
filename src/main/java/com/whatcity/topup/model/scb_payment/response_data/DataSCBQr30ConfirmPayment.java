package com.whatcity.topup.model.scb_payment.response_data;


import com.whatcity.topup.model.scb_payment.Receiver;
import com.whatcity.topup.model.scb_payment.Sender;
import lombok.Data;

@Data
public class DataSCBQr30ConfirmPayment {
    private String transRef;
    private String sendingBank;
    private String receivingBank;
    private String transDate;
    private String transTime;

    private Sender sender;
    private Receiver receiver;
}
