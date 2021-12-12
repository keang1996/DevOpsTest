package com.whatcity.topup.service;

import com.whatcity.topup.model.payload.Qr30GeneratePayLoad;
import com.whatcity.topup.model.scb_payment.payload.SCBConfirmPayload;
import com.whatcity.topup.model.scb_payment.response.SCBQr30Response;
import com.whatcity.topup.model.scb_payment.response.SCBQr30VerifyPaymentResponse;
import com.whatcity.topup.model.table.BankOrder;
import com.whatcity.topup.repository.BankOrderRepository;
import com.whatcity.topup.repository.TbOrderRepository;
import com.whatcity.topup.steam.repository.UserRepository;
import com.whatcity.topup.util.SCBPaymentHelper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCBPaymentService {

  SCBPaymentHelper scbPaymentHelper = new SCBPaymentHelper();

  @Autowired
  BankOrderRepository bankOrderRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  TbOrderRepository tbOrderRepository;

  public SCBQr30Response generateSCBQr30(Qr30GeneratePayLoad payload) throws Exception {
    try {
      SCBQr30Response response = scbPaymentHelper.generateQR30(payload);
      return response;
    } catch (Exception ex) {
      throw new Exception(ex.getMessage());
    }
  }

  public void scbConfirmPayment(SCBConfirmPayload payload) throws Exception {
    try {
      SCBQr30VerifyPaymentResponse verifyResponse = scbPaymentHelper.scbVerifyPaymentQr30(payload);
      int statusCode = verifyResponse.getStatus().getCode();
      String statusDesc = verifyResponse.getStatus().getDescription();
      if (statusCode == 1000) {

        //Implement BusinessLogic
        BankOrder bankOrder = new BankOrder();
        bankOrder.setTransactionId(payload.getTransactionId());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date transactionDateAndTime = format.parse(payload.getTransactionDateandTime());
        bankOrder.setTransactionDateAndTime(transactionDateAndTime);

        bankOrder.setPayeeProxyId(payload.getPayeeProxyId());
        bankOrder.setPayeeAccountNumber(payload.getPayeeAccountNumber());
        bankOrder.setPayerAccountNumber(payload.getPayerAccountNumber());
        bankOrder.setPayerAccountName(payload.getPayerAccountName());
        bankOrder.setPayerName(payload.getPayerName());
        bankOrder.setSendingBankCode(payload.getSendingBankCode());
        bankOrder.setReceivingBankCode(payload.getReceivingBankCode());
        bankOrder.setAmount(Double.parseDouble(payload.getAmount()));
        bankOrder.setBillPaymentRef1(payload.getBillPaymentRef1());
        bankOrder.setBillPaymentRef2(payload.getBillPaymentRef2());
        bankOrderRepository.save(bankOrder);
      }
    } catch (Exception ex) {
      throw new Exception(ex.getMessage());
    }
  }
}
