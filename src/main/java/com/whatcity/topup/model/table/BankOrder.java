package com.whatcity.topup.model.table;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bank_order")
@Data
public class BankOrder {

  @Id
  @Column(name = "transaction_id")
  private String transactionId;
  @Column(name = "transaction_date_and_time")
  private Date transactionDateAndTime;
  @Column(name = "payee_proxy_id")
  private String payeeProxyId;
  @Column(name = "payee_account_number")
  private String payeeAccountNumber;
  @Column(name = "payer_account_number")
  private String payerAccountNumber;
  @Column(name = "payer_account_name")
  private String payerAccountName;
  @Column(name = "payer_name")
  private String payerName;
  @Column(name = "sending_bank_code")
  private String sendingBankCode;
  @Column(name = "receiving_bank_code")
  private String receivingBankCode;
  @Column(name = "amount")
  private double amount;
  @Column(name = "bill_payment_ref1")
  private String billPaymentRef1;
  @Column(name = "bill_payment_ref2")
  private String billPaymentRef2;
}
