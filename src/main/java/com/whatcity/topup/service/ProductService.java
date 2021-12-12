package com.whatcity.topup.service;

import com.whatcity.topup.model.dto.IProductDTO;
import com.whatcity.topup.model.table.Product;
import com.whatcity.topup.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  public List<Product> findAllProduct() {
    return productRepository.findAll();
  }

  public List<IProductDTO> findAllByProductStatusGachapon(Long page) {
    return productRepository.findAllByProductStatusGachapon(page);
  }

  public List<IProductDTO> findAllByProductStatusVip(Long page) {
    return productRepository.findAllByProductStatusVip(page);
  }

  public List<IProductDTO> findAllByProductStatusWhiteList(Long page) {
    return productRepository.findAllByProductStatusWhiteList(page);
  }

  public Optional<Product> findByIdProduct(Long productId) {
    return productRepository.findById(productId);
  }
}
