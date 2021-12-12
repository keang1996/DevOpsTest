package com.whatcity.topup.repository;

import com.whatcity.topup.model.dto.IPaymentPODTO;
import com.whatcity.topup.model.table.ProductOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

  @Query(value = " select "
    + "       p.product_name productName, "
    + "       p.product_price productPrice, "
    + "       p.product_img productImg, "
    + "       po.quantity quantity, "
    + "       po.total_price totalPrice, "
    + "       po.order_ref orderRef"
    + " from product_order po "
    + "    inner join product p on po.product_id = p.product_id "
    + " where 1=1 "
    + " and po.order_ref = :orderRef ", nativeQuery = true)
  List<IPaymentPODTO> getPaymentProductOrder(@Param("orderRef") String orderRef);
}
