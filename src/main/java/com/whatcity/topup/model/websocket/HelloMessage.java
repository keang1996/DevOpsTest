package com.whatcity.topup.model.websocket;

import lombok.Data;

@Data
public class HelloMessage {
    private String toUser;
    private String name;

}