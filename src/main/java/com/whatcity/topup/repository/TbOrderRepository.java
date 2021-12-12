package com.whatcity.topup.repository;

import com.whatcity.topup.model.table.TbOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbOrderRepository extends JpaRepository<TbOrder, Long> {

}
