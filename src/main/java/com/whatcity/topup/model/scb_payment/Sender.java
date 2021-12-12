package com.whatcity.topup.model.scb_payment;

import lombok.Data;

@Data
public class Sender {
    private String displayName;
    private String name;
    private Proxy proxy;
    private Account account;
}
