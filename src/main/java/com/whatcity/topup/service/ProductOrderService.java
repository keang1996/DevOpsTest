package com.whatcity.topup.service;

import com.whatcity.topup.model.table.ProductOrder;
import com.whatcity.topup.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderService {

    @Autowired
    ProductOrderRepository productOrderRepository;

    public ProductOrder save(ProductOrder productOrder){
        return productOrderRepository.save(productOrder);
    }

    public List<ProductOrder> saveAll(List<ProductOrder> productOrders){
        return productOrderRepository.saveAll(productOrders);
    }
}
