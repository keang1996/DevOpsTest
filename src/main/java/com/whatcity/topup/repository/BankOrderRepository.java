package com.whatcity.topup.repository;

import com.whatcity.topup.model.table.BankOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankOrderRepository extends JpaRepository<BankOrder,String> {
}
