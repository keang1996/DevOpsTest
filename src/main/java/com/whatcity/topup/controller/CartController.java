package com.whatcity.topup.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatcity.topup.model.payload.OrderCart;
import com.whatcity.topup.model.payload.OrderPayload;
import com.whatcity.topup.model.response.OrderResponse;
import com.whatcity.topup.model.scb_payment.response.SCBQr30Response;
import com.whatcity.topup.model.table.*;
import com.whatcity.topup.service.*;
import com.whatcity.topup.steam.model.User;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private TbOrderService tbOrderService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private WhatCityCommonService whatCityCommonService;

    @GetMapping("/cart")
    public String linkPage(Principal principal, Model model) {
        if (principal != null) {
            OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
            User user = (User) token.getPrincipal();
            model.addAttribute("user", user);
        }
        OrderCart orderCart = new OrderCart();
        model.addAttribute("orderCart", orderCart);
        model.addAttribute("checkPayment", "no");
        final String page = "cart/cart";
        return page;
    }

    @GetMapping(value = {"/scanQrCode/{orderRef}"})
    public String ScanQeCode(Principal principal, Model model,
                             @PathVariable("orderRef") String orderRef) {
        final String page = "cart/cart";
        if (principal != null) {
            OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
            User user = (User) token.getPrincipal();
            model.addAttribute("user", user);
            if(!"".equals(orderRef)) {
                model.addAttribute("orderCart", new OrderCart());
                model.addAttribute("orderText", "คำสั่งซื้อหมายเลข");
                model.addAttribute("orderRef", orderRef);
                model.addAttribute("checkPayment", "yes");

                WhatCityCommon whatCityCommon = whatCityCommonService.findByIdItemName("qrcode");
                model.addAttribute("qrcode",whatCityCommon.getItemLongValue());
            }
        }
        return page;
    }

    @PostMapping("/getOrderRef")
    public String getOrderRef(Principal principal,
                            Model model,
                            @ModelAttribute("orderCart") OrderCart orderCart) {
        final String page = "cart/cart";
        try {
            ObjectMapper mapper = new ObjectMapper();
            if(!"".equals(orderCart.getStrCarts())){
                final List<OrderPayload> orderPayloadList = mapper.readValue(orderCart.getStrCarts(), new TypeReference<List<OrderPayload>>(){});
                if (orderPayloadList.size() > 0 && principal != null) {
                    OpenIDAuthenticationToken principalToken = (OpenIDAuthenticationToken) principal;
                    User user = (User) principalToken.getPrincipal();

                    final String orderRef = genOrderRef();
                    List<ProductOrder> productOrderds = setProductOrder(orderPayloadList,orderRef);
                    if(productOrderds.size() > 0){

                        productOrderService.saveAll(productOrderds);

                        TbOrder tbOrder = new TbOrder();
                        tbOrder.setSteamId(user.getSteamId());
                        tbOrder.setOrderRef(orderRef);
                        Double amount = productOrderds.stream().mapToDouble(res -> res.getTotalPrice()).sum();
                        tbOrder.setAmount(new BigDecimal(amount));
                        tbOrderService.save(tbOrder);

                        Trade trade = getTrade(orderRef,user);
                        tradeService.save(trade);
                    }
                    model.addAttribute("user", user);
                    final String redirect = "redirect:scanQrCode/"+orderRef;
                    return redirect;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    private List<ProductOrder> setProductOrder(List<OrderPayload> orderPayloadList,String orderRef){
        List<ProductOrder> productOrderds = new ArrayList<ProductOrder>();
        for(OrderPayload orderPayload : orderPayloadList){
            Optional<Product> product = null;
            product = productService.findByIdProduct(orderPayload.getProductId());
            if(product.isPresent()){
                ProductOrder productOrder = new ProductOrder();
                productOrder.setOrderIdRef(orderRef);
                productOrder.setProductId(product.get().getProductId());
                productOrder.setQuantity(orderPayload.getQuantity());
                productOrder.setTotalPrice(product.get().getProductPrice() * orderPayload.getQuantity());
                productOrderds.add(productOrder);
            }
        }
        return productOrderds;
    }

    private Trade getTrade(String orderRef,User user){
        Trade trade = new Trade();
        trade.setOrderId(orderRef);
        final Date date = new Date();
        trade.setTradeDate(date);
        trade.setTradeTime(date);
        trade.setProveStatus(false);
        trade.setCreateBy(user.getSteamId());
        trade.setCreateDate(new Date());
        trade.setUpdateBy(user.getSteamId());
        trade.setUpdateDate(new Date());
        trade.setStatus("1");
        return trade;
    }

    private String genOrderRef(){
        Calendar calendar = Calendar.getInstance(Locale.US);
        int year = calendar.get(Calendar.YEAR);
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String hour = new SimpleDateFormat("HH").format(calendar.getTime());
        String minute = new SimpleDateFormat("mm").format(calendar.getTime());
        String second = new SimpleDateFormat("ss").format(calendar.getTime());

        String orderRef = String.format("%d%s%d%s%s%s", year, month, day, hour, minute, second);
        orderRef = orderRef + this.generateRandomNumber(6);

        return orderRef;
    }

    private Long generateRandomNumber(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }
}
