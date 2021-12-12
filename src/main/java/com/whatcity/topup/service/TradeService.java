package com.whatcity.topup.service;

import com.whatcity.topup.model.payload.TradePayload;
import com.whatcity.topup.model.table.Trade;
import com.whatcity.topup.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    public Trade save(Trade trade){
        return tradeRepository.save(trade);
    }

    public Trade queryTradeByOrderId(String orderId){
        return tradeRepository.queryTradeByOrderId(orderId);
    }

    public List<TradePayload> findTradeByIdSteam(String steamId){
        return tradeRepository.findTradeByIdSteam(steamId);
    }
}
