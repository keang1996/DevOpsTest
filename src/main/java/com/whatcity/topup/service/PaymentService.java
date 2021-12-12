package com.whatcity.topup.service;

import com.whatcity.topup.model.dto.IPaymentPODTO;
import com.whatcity.topup.repository.ProductOrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  final ProductOrderRepository productOrderRepository;

  public PaymentService(ProductOrderRepository productOrderRepository) {
    this.productOrderRepository = productOrderRepository;
  }

  public List<IPaymentPODTO> getPaymentProductOrder(String orderRef) {
    return this.productOrderRepository.getPaymentProductOrder(orderRef);
  }
}
