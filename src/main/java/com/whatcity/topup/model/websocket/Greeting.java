package com.whatcity.topup.model.websocket;

import lombok.Data;

@Data
public class Greeting {
    private String orderRef;
    private String content;

}