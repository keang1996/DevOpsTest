package com.whatcity.topup.model.scb_payment.response_data;

import com.whatcity.topup.model.scb_payment.Channels;
import lombok.Data;

import java.util.List;

@Data
public class DataSCBQrCS {
    private String qrRawData;
    private String qrImage;
    //private Date csExtExpiryTime;
    private String qrcodeId;
    private String merchantId;
    private String merchantName;
    private double amount;
    private String currencyCode;
    private String invoice;
    private List<Channels> channels;
    private String terminalId;
    private String qrCodeType;
    private String poi;
    private String currencyName;
    private String terminalName;
    private String responseCode;
}
