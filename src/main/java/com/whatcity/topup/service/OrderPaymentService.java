package com.whatcity.topup.service;

import com.whatcity.topup.model.payload.OrderPayload;
import com.whatcity.topup.model.payload.Qr30GeneratePayLoad;
import com.whatcity.topup.model.response.OrderResponse;
import com.whatcity.topup.model.scb_payment.response.SCBQr30Response;
import com.whatcity.topup.model.table.ProductOrder;
import com.whatcity.topup.model.table.TbOrder;
import com.whatcity.topup.repository.ProductOrderRepository;
import com.whatcity.topup.repository.TbOrderRepository;
import com.whatcity.topup.steam.model.User;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class OrderPaymentService {

    @Autowired
    private TbOrderRepository tbOrderRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private SCBPaymentService scbPaymentService;

    Qr30GeneratePayLoad qr30GeneratePayLoad = new Qr30GeneratePayLoad();

//    public OrderResponse addOrder(List<OrderPayload> payloads, Principal principal)
//            throws Exception {
//        try {
//            double sum = 0.0;
//            if (principal != null) {
//                OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
//                User user = (User) token.getPrincipal();
//                Calendar calendar = Calendar.getInstance(Locale.US);
//
//                int year = calendar.get(Calendar.YEAR);
//                String month = new SimpleDateFormat("MM").format(calendar.getTime());
//                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//                String hour = new SimpleDateFormat("HH").format(calendar.getTime());
//                String minute = new SimpleDateFormat("mm").format(calendar.getTime());
//                String second = new SimpleDateFormat("ss").format(calendar.getTime());
//
//                String orderRef = String.format("%d%s%d%s%s%s", year, month, day, hour, minute, second);
//                orderRef = orderRef + this.generateRandomNumber(6);
//
//                TbOrder order = new TbOrder();
//                order.setOrderRef(orderRef);
//                order.setSteamId(user.getSteamId());
//
//                List<ProductOrder> productOrders = new ArrayList<>();
//                for (OrderPayload orderPayload : payloads) {
//                    ProductOrder item = new ProductOrder();
//                    item.setOrderIdRef(order.getOrderRef());
//                    item.setProductId(orderPayload.getProductId());
//                    item.setQuantity(orderPayload.getQuantity());
//                    item.setTotalPrice(orderPayload.getTotalPrice());
//                    sum += orderPayload.getTotalPrice();
//                    productOrders.add(item);
//                }
//
//                order.setAmount(BigDecimal.valueOf(sum));
//
//                tbOrderRepository.save(order);
//                productOrderRepository.saveAll(productOrders);
//
//                qr30GeneratePayLoad.setAmount(sum);
//                qr30GeneratePayLoad.setReference1(user.getSteamId());
//                qr30GeneratePayLoad.setReference2(orderRef);
//                qr30GeneratePayLoad.setReference3("SCB");
//                SCBQr30Response qr30Response = scbPaymentService.generateSCBQr30(qr30GeneratePayLoad);
//                OrderResponse response = new OrderResponse();
//                response.setQr30Response(qr30Response);
//                response.setOrderRef(orderRef);
//                return response;
//            } else {
//                return null;
//            }
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
//    }

    public long generateRandomNumber(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }
}
