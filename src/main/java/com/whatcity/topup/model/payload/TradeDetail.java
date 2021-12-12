package com.whatcity.topup.model.payload;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TradeDetail {
    private String orderId;
    private Date tradeDate;
    private Date tradeTime;
    private String status;
    private String statusName;
    private String amount;
    private String itemCode;
    private String orderDetailsJson;
    private List<OrderDetail> orderDetails;
}
