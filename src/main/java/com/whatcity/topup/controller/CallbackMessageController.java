package com.whatcity.topup.controller;

import com.whatcity.topup.model.websocket.Greeting;
import com.whatcity.topup.model.websocket.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.inject.Singleton;
import java.security.Principal;

@Controller
@Singleton
public class CallbackMessageController {

//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greeting(HelloMessage message, Principal user,
//                             @Header("simpSessionId") String sessionId) throws Exception {
//        System.out.println("Sending Greeting");
//        Thread.sleep(3000); // simulated delay
//        Greeting greeting = new Greeting();
//        greeting.setContent("Hello, " + message.getName() + "!");
//        return greeting;
//    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    public void greeting(@Payload HelloMessage message,

                         Principal user,
                         @Header("simpSessionId") String sessionId) throws  Exception {
        Greeting greeting = new Greeting();
        greeting.setContent("Hello !");

        messagingTemplate.convertAndSendToUser(message.getToUser(), "queue/greetings", greeting);
    }
}
