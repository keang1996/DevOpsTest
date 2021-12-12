package com.whatcity.topup.controller;

import com.google.gson.Gson;
import com.whatcity.topup.model.dto.IPaymentPODTO;
import com.whatcity.topup.model.payload.OrderDetail;
import com.whatcity.topup.model.payload.TradeDetail;
import com.whatcity.topup.model.payload.TradePayload;
import com.whatcity.topup.model.table.Trade;
import com.whatcity.topup.model.table.WhatCityCommon;
import com.whatcity.topup.service.PaymentService;
import com.whatcity.topup.service.TradeService;
import com.whatcity.topup.service.WhatCityCommonService;
import com.whatcity.topup.steam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    TradeService tradeService;

    @Autowired
    WhatCityCommonService whatCityCommonService;

    @Autowired
    PaymentService paymentService;

    @GetMapping("/order")
    public String linkPage(Principal principal, Model model) {
        final String page = "order/order";
        List<TradeDetail> tradeDetails = new ArrayList<TradeDetail>();
        TradeDetail tradeDetail = new TradeDetail();
        OrderDetail orderDetail = new OrderDetail();
        if (principal != null) {
            OpenIDAuthenticationToken principalToken = (OpenIDAuthenticationToken) principal;
            User user = (User) principalToken.getPrincipal();

            List<TradePayload> trades = tradeService.findTradeByIdSteam(user.getSteamId());
            WhatCityCommon whatCityCommon = whatCityCommonService.findByIdItemName("qrcode");

            for(TradePayload tradePayload : trades){
                tradeDetail = new TradeDetail();
                List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
                List<IPaymentPODTO> iPaymentPODTOs = paymentService.getPaymentProductOrder(tradePayload.getOrderId());

                tradeDetail.setOrderId(tradePayload.getOrderId());
                tradeDetail.setTradeDate(tradePayload.getTradeDate());
                tradeDetail.setTradeTime(tradePayload.getTradeTime());
                tradeDetail.setStatus(tradePayload.getStatus());
                tradeDetail.setStatusName(tradePayload.getStatusName());
                tradeDetail.setAmount(tradePayload.getAmount());
                tradeDetail.setItemCode(tradePayload.getItemCode());

                for(IPaymentPODTO iPaymentPODTO : iPaymentPODTOs){
                    orderDetail = new OrderDetail();
                    orderDetail.setProductName(iPaymentPODTO.getProductName());
                    orderDetail.setProductImg("data:image/jpeg;base64,"+iPaymentPODTO.getProductImg());
                    orderDetail.setQuantity(String.valueOf(iPaymentPODTO.getQuantity()));
                    orderDetails.add(orderDetail);
                }

               // tradeDetail.setOrderDetails(orderDetails);
                String orderDetailsJson = new Gson().toJson(orderDetails);
                tradeDetail.setOrderDetailsJson(orderDetailsJson);
                tradeDetails.add(tradeDetail);
            }

            model.addAttribute("user", user);
            model.addAttribute("trades", tradeDetails);
            model.addAttribute("qrcode",whatCityCommon.getItemLongValue());
        }
        return page;
    }

}
