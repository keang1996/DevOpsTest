package com.whatcity.topup.model.payload;

import java.util.Date;

public interface TradePayload {
     String getOrderId();
     Date getTradeDate();
     Date getTradeTime();
     String getStatus();
     String getStatusName();
     String getAmount();
     String getItemCode();
}
