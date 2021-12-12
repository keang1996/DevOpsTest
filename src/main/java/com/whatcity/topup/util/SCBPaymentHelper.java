package com.whatcity.topup.util;

import com.whatcity.topup.model.payload.Qr30GeneratePayLoad;
import com.whatcity.topup.model.scb_payment.payload.SCBConfirmPayload;
import com.whatcity.topup.model.scb_payment.response.SCBQr30Response;
import com.whatcity.topup.model.scb_payment.response.SCBQr30VerifyPaymentResponse;
import com.whatcity.topup.model.scb_payment.response.SCBTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SCBPaymentHelper {
    RestTemplate restTemplate = new RestTemplate();

    private String applicationKey = "l7b87a4af5fbbe4fdf96910933eaff041b";
    private String applicationSecret = "727d51d5c3a841168f1ee21fc74cc742";
    private String merchantId = "617744168900157";
    private String terminalId = "091758448475481";
    private String billerId = "854845626188122";
    private int csExtExpiryTime = 60;

    UUID uuid = UUID.randomUUID();
    HttpHeaders headers = new HttpHeaders();
    Map<String, Object> requestBody = new HashMap<>();

    public SCBQr30Response generateQR30(Qr30GeneratePayLoad payload) throws Exception {
        try {
            String url = "https://api-sandbox.partners.scb/partners/sandbox/v1/payment/qrcode/create";

            headers.clear();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("resourceOwnerId", applicationKey.toString());
            headers.add("requestUId", uuid.toString());
            headers.add("Authorization", getSCBAccessToken());

            requestBody.clear();
            requestBody.put("qrType", "PP");
            requestBody.put("ppType", "BILLERID");
            requestBody.put("ppId", billerId);
            requestBody.put("amount", payload.getAmount());
            requestBody.put("ref1", payload.getReference1());
            requestBody.put("ref2", payload.getReference2());
            requestBody.put("ref3", payload.getReference3());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<SCBQr30Response> response = restTemplate.exchange(url, HttpMethod.POST, entity, SCBQr30Response.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                SCBQr30Response dataResult = response.getBody();
                return dataResult;
            }
            return null;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public SCBQr30VerifyPaymentResponse scbVerifyPaymentQr30(SCBConfirmPayload payload) throws Exception {
        try {
            String paymentTransaction = payload.getTransactionId();
            String sendingBankCode = payload.getSendingBankCode();
            String url = String.format("https://api-sandbox.partners.scb/partners/sandbox/v1/payment/billpayment/transactions/%s?sendingBank=%s", paymentTransaction, sendingBankCode);

            headers.clear();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("resourceOwnerId", applicationKey.toString());
            headers.add("requestUId", uuid.toString());
            headers.add("Authorization", getSCBAccessToken());

            ResponseEntity<SCBQr30VerifyPaymentResponse> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), SCBQr30VerifyPaymentResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                SCBQr30VerifyPaymentResponse dataResult = response.getBody();
                return dataResult;
            }
            return null;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private String getSCBAccessToken() throws Exception {
        try {
            String url = "https://api-sandbox.partners.scb/partners/sandbox/v1/oauth/token";

            headers.clear();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("resourceOwnerId", applicationKey);
            headers.add("requestUId", uuid.toString());

            requestBody.clear();
            requestBody.put("applicationKey", applicationKey);
            requestBody.put("applicationSecret", applicationSecret);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<SCBTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, SCBTokenResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                SCBTokenResponse dataResult = response.getBody();
                return "Bearer " + dataResult.getData().getAccessToken();
            }
            return null;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
