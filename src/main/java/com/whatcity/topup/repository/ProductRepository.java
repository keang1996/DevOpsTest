package com.whatcity.topup.repository;

import com.whatcity.topup.model.dto.IPaymentPODTO;
import com.whatcity.topup.model.dto.IProductDTO;
import com.whatcity.topup.model.table.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query(value = " SELECT "
    + " product_id productId, "
    + " product_name productName, "
    + " product_ref_id productRefId, "
    + " product_price productPrice, "
    + " product_img productImg, "
    + " product_status productStatus, "
    + " product_cate productCate, "
    + " (select count(*) from product p WHERE p.product_status = '1' and p.product_cate = '1') count "
    + " FROM Product p "
    + " WHERE p.product_status = '1' "
    + "  and p.product_cate = '1' "
    + " limit :page,4 ", nativeQuery = true)
  List<IProductDTO> findAllByProductStatusGachapon(@Param("page") Long page);

  @Query(value = " SELECT "
    + " product_id productId, "
    + " product_name productName, "
    + " product_ref_id productRefId, "
    + " product_price productPrice, "
    + " product_img productImg, "
    + " product_status productStatus, "
    + " product_cate productCate, "
    + " (select count(*) from product p WHERE p.product_status = '1' and p.product_cate = '1') count "
    + " FROM Product p "
    + " WHERE p.product_status = '1' "
    + "  and p.product_cate = '2' "
    + " limit :page,4 ", nativeQuery = true)
  List<IProductDTO> findAllByProductStatusVip(@Param("page") Long page);

  @Query(value = " SELECT "
    + " product_id productId, "
    + " product_name productName, "
    + " product_ref_id productRefId, "
    + " product_price productPrice, "
    + " product_img productImg, "
    + " product_status productStatus, "
    + " product_cate productCate, "
    + " (select count(*) from product p WHERE p.product_status = '1' and p.product_cate = '1') count "
    + " FROM Product p "
    + " WHERE p.product_status = '1' "
    + "  and p.product_cate = '3' "
    + " limit :page,4 ", nativeQuery = true)
  List<IProductDTO> findAllByProductStatusWhiteList(@Param("page") Long page);
}
