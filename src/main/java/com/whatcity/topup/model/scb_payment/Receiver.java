package com.whatcity.topup.model.scb_payment;

import lombok.Data;

@Data
public class Receiver {
    private String displayName;
    private String name;
    private Proxy proxy;
    private Account account;
}
