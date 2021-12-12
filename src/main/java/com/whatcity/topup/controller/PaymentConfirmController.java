package com.whatcity.topup.controller;

import com.whatcity.topup.model.scb_payment.payload.SCBConfirmPayload;
import com.whatcity.topup.model.scb_payment.response.SCBConfirmPaymentResponse;
import com.whatcity.topup.service.SCBPaymentService;
import com.whatcity.topup.model.websocket.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scb")
public class PaymentConfirmController {
    @Autowired
    SCBPaymentService scbPaymentService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/payment/confirm")
    public ResponseEntity<SCBConfirmPaymentResponse> scbCallBackConfirmPayment(@RequestBody SCBConfirmPayload payload) {
        try {
            scbPaymentService.scbConfirmPayment(payload);
            Greeting greeting = new Greeting();
            greeting.setContent(payload.getBillPaymentRef1());
            greeting.setOrderRef(payload.getBillPaymentRef2());
            this.simpMessagingTemplate.convertAndSendToUser(payload.getBillPaymentRef1(), "queue/greetings", greeting);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            SCBConfirmPaymentResponse response = new SCBConfirmPaymentResponse();
            response.setTransactionId(payload.getTransactionId());
            response.setResCode("1000");
            response.setResDesc("success");
            return new ResponseEntity<SCBConfirmPaymentResponse>(response, HttpStatus.OK);
        }
    }

//    @PostMapping("/payment/confirm")
//    public void Test(@RequestParam String user) throws Exception {
//        Greeting greeting = new Greeting();
//        greeting.setContent(user);
//        this.simpMessagingTemplate.convertAndSendToUser(user, "queue/greetings", greeting);
//    }
}
